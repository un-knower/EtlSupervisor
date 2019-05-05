package com.dr.leo.etlsupervisor.service.impl;

import com.dr.leo.etlsupervisor.azkaban.model.*;
import com.dr.leo.etlsupervisor.config.AzkabanConfig;
import com.dr.leo.etlsupervisor.entity.EtlAzkabanSession;
import com.dr.leo.etlsupervisor.exception.ServiceException;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static com.dr.leo.etlsupervisor.common.EtlSupervisorConst.*;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/18 15:32
 */
@Service
public class AzkabanRestApiServiceImpl {
    private static final Logger LOG = LoggerFactory.getLogger(AzkabanRestApiServiceImpl.class);
    private final AzkabanConfig azkabanConfig;
    private final EtlAzkabanSessionServiceImpl azkabanSessionService;

    @Autowired
    public AzkabanRestApiServiceImpl(AzkabanConfig azkabanConfig, EtlAzkabanSessionServiceImpl azkabanSessionService) {
        this.azkabanConfig = azkabanConfig;
        this.azkabanSessionService = azkabanSessionService;
    }

    private static void disableSslVerification() {
        try {
            // Create a trust manager that does not validate certificate chains
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }
            };
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = (hostname, session) -> true;
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders hs = new org.springframework.http.HttpHeaders();
        hs.add("Content-Type", azkabanConfig.getContentType());
        hs.add("X-Requested-With", azkabanConfig.getXRequestWith());
        return hs;
    }

    private boolean sessionIsExpired(EtlAzkabanSession session) {
        if (null == session) {
            return true;
        }
        return session.getExpiredDate().isBefore(LocalDateTime.now());
    }

    private LocalDateTime createSessionExpiredDate() {
        return LocalDateTime.now().plusHours(AZKABAN_SESSION_EXPIRED);
    }

    private void saveAzkabanSessionId(String sessionId) {
        EtlAzkabanSession etlAzkabanSession = new EtlAzkabanSession();
        etlAzkabanSession.setSessionId(sessionId);
        etlAzkabanSession.setExpiredDate(createSessionExpiredDate());
        azkabanSessionService.saveAzkabanSessionId(etlAzkabanSession);
    }


    public String login() {
        disableSslVerification();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders hs = getHttpHeaders();
        LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
        linkedMultiValueMap.add("action", "login");
        linkedMultiValueMap.add("username", azkabanConfig.getUsername());
        linkedMultiValueMap.add("password", azkabanConfig.getPassword());
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(linkedMultiValueMap, hs);
        String result = restTemplate.postForObject(azkabanConfig.getUrl(), httpEntity, String.class);
        JsonObject resMap = new Gson().fromJson(result, JsonObject.class);
        JsonElement jsonElement = resMap.get("session.id");
        if (null == jsonElement) {
            LOG.error("登录azkaban失败！", resMap.get("error").getAsString());
            return null;
        } else {
            return jsonElement.getAsString();
        }
    }

    public String getAzkabanSessionId() throws ServiceException {
        EtlAzkabanSession etlAzkabanSession = azkabanSessionService.queryAzkabanSessionId();
        if (null == etlAzkabanSession) {
            String sessionId = login();
            if (null == sessionId) {
                throw new ServiceException("azkaban认证失败！");
            }
            saveAzkabanSessionId(sessionId);
            return sessionId;
        }
        if (sessionIsExpired(etlAzkabanSession)) {
            LOG.info("azkaban session过期，正在重新获取。", etlAzkabanSession.getExpiredDate());
            azkabanSessionService.deleteSessionById(etlAzkabanSession.getSessionId());
            String sessionId = login();
            if (null == sessionId) {
                throw new ServiceException("azkaban认证失败！");
            }
            saveAzkabanSessionId(sessionId);
            return sessionId;
        }
        return etlAzkabanSession.getSessionId();
    }

    public boolean createProject(String sessionId, String name, String description) {
        disableSslVerification();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders hs = getHttpHeaders();
        LinkedMultiValueMap<String, String> linkedMultiValueMap = new LinkedMultiValueMap<>();
        linkedMultiValueMap.add("action", "create");
        linkedMultiValueMap.add("session.id", sessionId);
        linkedMultiValueMap.add("name", name);
        linkedMultiValueMap.add("description", description);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(linkedMultiValueMap, hs);
        String result = restTemplate.postForObject(azkabanConfig.getUrl().concat("/").concat("manager"),
                httpEntity, String.class);
        System.out.println(result);
        if (!AZKABAN_RES_OK.equals(new Gson().fromJson(result, JsonObject.class).get(AZKABAN_STATUS).getAsString())) {
            if (!AZKABAN_PROJECT_EXISTS.equals(new Gson().fromJson(result, JsonObject.class)
                    .get("message").getAsString())) {
                LOG.error("创建Azkaban Project失败:", name);
                LOG.error("创建Azkaban Project失败!返回错误信息：" + result);
                return false;
            }
        } else {
            LOG.info("创建Azkaban Project成功:", name);
        }
        return true;
    }


    /**
     * 执行一个任务流
     *
     * @param sessionId
     * @param project
     * @param flow
     * @return
     */
    public AzkabanExecutorResponse executeFlow(String sessionId, String project, String flow) {
        disableSslVerification();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders hs = getHttpHeaders();
        hs.add("Accept", "text/plain;charset=utf-8");
        Map<String, String> params = new HashMap<>(3);
        params.put("id", sessionId);
        params.put("project", project);
        params.put("flow", flow);
        ResponseEntity<String> exchange = restTemplate
                .exchange(azkabanConfig.getUrl() + "/executor?session.id={id}&ajax=executeFlow&" +
                                "project={project}&flow={flow}", HttpMethod.GET,
                        new HttpEntity<String>(hs), String.class, params);
        String res = exchange.getBody();
        Gson gson = new Gson();
        return gson.fromJson(res, AzkabanExecutorResponse.class);
    }

    /**
     * 获取一个流的执行情况
     *
     * @param sessionId
     * @param executorId
     * @return
     */
    public AzkabanExecutorFinishResponse fetchOneFlowOfExecutor(String sessionId, Integer executorId) {
        disableSslVerification();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders hs = getHttpHeaders();
        hs.add("Accept", "text/plain;charset=utf-8");
        Map<String, String> params = new HashMap<>(2);
        params.put("id", sessionId);
        params.put("execid", String.valueOf(executorId));
        final String url = azkabanConfig.getUrl() + "/executor?session.id={id}&ajax=fetchexecflow&"
                + "execid={execid}";
        ResponseEntity<String> exchange = restTemplate
                .exchange(url, HttpMethod.GET, new HttpEntity<String>(hs), String.class, params);
        String res = exchange.getBody();
        Gson gson = new Gson();
        return gson.fromJson(res, AzkabanExecutorFinishResponse.class);
    }

    /**
     * 查看一个流下的执行情况
     *
     * @param sessionId
     * @param project
     * @param flow
     * @param start
     * @param length
     * @return
     */
    public AzkabanExecutionsOfOneFlow fetchExecutionsOfOneFlow(String sessionId, String project, String flow, Integer start, Integer length) {
        disableSslVerification();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders hs = getHttpHeaders();
        hs.add("Accept", "text/plain;charset=utf-8");
        Map<String, String> params = new HashMap<>(5);
        params.put("id", sessionId);
        params.put("project", project);
        params.put("flow", flow);
        params.put("start", start.toString());
        params.put("length", length.toString());
        final String url = azkabanConfig.getUrl() + "/manager?session.id={id}&ajax=fetchFlowExecutions&"
                + "project={project}&flow={flow}&start={start}&length={length}";
        ResponseEntity<String> exchange = restTemplate
                .exchange(url, HttpMethod.GET, new HttpEntity<String>(hs), String.class, params);
        String res = exchange.getBody();
        Gson gson = new Gson();
        return gson.fromJson(res, AzkabanExecutionsOfOneFlow.class);
    }

    /**
     * 查看一个项目下的任务流
     *
     * @param sessionId
     * @param project
     * @return
     */
    public AzkabanProject fetchFlowsOfOneProject(String sessionId, String project) {
        disableSslVerification();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders hs = getHttpHeaders();
        hs.add("Accept", "text/plain;charset=utf-8");
        Map<String, String> params = new HashMap<>(2);
        params.put("id", sessionId);
        params.put("project", project);
        ResponseEntity<String> exchange = restTemplate
                .exchange(azkabanConfig.getUrl() + "/manager?session.id={id}&ajax=fetchprojectflows&" +
                                "project={project}", HttpMethod.GET,
                        new HttpEntity<String>(hs), String.class, params);
        String res = exchange.getBody();
        Gson gson = new Gson();
        return gson.fromJson(res, AzkabanProject.class);
    }

    /**
     * 查看一个流下的job
     *
     * @param sessionId
     * @param project
     * @param flow
     * @return
     */
    public AzkabanFlowGraph fetchJobsOfOneFlow(String sessionId, String project, String flow) {
        disableSslVerification();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders hs = getHttpHeaders();
        hs.add("Accept", "text/plain;charset=utf-8");
        Map<String, String> params = new HashMap<>(3);
        params.put("id", sessionId);
        params.put("project", project);
        params.put("flow", flow);
        ResponseEntity<String> exchange = restTemplate
                .exchange(azkabanConfig.getUrl() + "/manager?session.id={id}&ajax=fetchflowgraph&" +
                                "project={project}&flow={flow}", HttpMethod.GET,
                        new HttpEntity<String>(hs), String.class, params);
        String res = exchange.getBody();
        Gson gson = new Gson();
        return gson.fromJson(res, AzkabanFlowGraph.class);
    }

    /**
     * 取消一个正在运行的流
     *
     * @param sessionId
     * @param execId
     */
    public AzkabanCancelFlowResponse cancelOneFlow(String sessionId, Integer execId) {
        disableSslVerification();
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders hs = getHttpHeaders();
        hs.add("Accept", "text/plain;charset=utf-8");
        Map<String, String> params = new HashMap<>(2);
        params.put("id", sessionId);
        params.put("execId", execId.toString());
        ResponseEntity<String> exchange = restTemplate
                .exchange(azkabanConfig.getUrl() + "/executor?session.id={id}&ajax=cancelFlow&" +
                        "execid={execId}", HttpMethod.GET, new HttpEntity<String>(hs), String.class, params);
        String res = exchange.getBody();
        Gson gson = new Gson();
        return gson.fromJson(res, AzkabanCancelFlowResponse.class);
    }


}

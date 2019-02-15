package com.dr.leo.etlsupervisor.azkaban;

import com.dr.leo.etlsupervisor.common.EtlSupervisorConst;
import com.dr.leo.etlsupervisor.common.IoUtil;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/15 9:40
 */
@Data
public abstract class AbstractAzkabanCommand implements IAzkabanCommand {
    private static final String SPARK_TEMPLATE_SAVE_PATH = "/static/upload/azkabanJob/";
    private String commandType;
    private String projectName;
    private String jobName;
    private String dependenciesJobName;
    @Qualifier("stringTemplateEngine")
    @Autowired
    private TemplateEngine templateEngine;


    File getAzkabanJobSaveFile(String projectName, String jobName) {
        Path jobPath = Paths.get(EtlSupervisorConst.CLASS_PATH + SPARK_TEMPLATE_SAVE_PATH +
                projectName + "/" + jobName + ".job");
        return jobPath.toFile();
    }

    void outTemplate(File saveFile, IAzkabanCommand azkabanCommand, String templatePath) {
        Context context = new Context();
        context.setVariable("azkabanCommand", azkabanCommand);
        String content = templateEngine.process(templatePath, context);
        IoUtil.toFile(saveFile, content);
    }

}

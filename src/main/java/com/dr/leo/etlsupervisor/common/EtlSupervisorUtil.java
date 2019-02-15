package com.dr.leo.etlsupervisor.common;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/15 13:43
 */
public final class EtlSupervisorUtil {
    private static boolean isWindows = System.getProperties().getProperty("os.name").toLowerCase().contains("win");

    public static String getCurrentClassPath() {
        URL url = EtlSupervisorUtil.class.getResource("/");
        String path;
        if (null == url) {
            File f = new File(EtlSupervisorUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            path = f.getPath();
        } else {
            path = url.getPath();
        }
        return isWindows() ? decode(path.replaceFirst("^/(.:/)", "$1")) : decode(path);
    }

    public static boolean isWindows() {
        return isWindows;
    }

    private static String decode(String path) {
        try {
            return URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException var2) {
            return path;
        }
    }

}

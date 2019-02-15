package com.dr.leo.etlsupervisor.common;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author :leo.jie
 * @version :v1.0
 * @date :2019/2/15 13:37
 */
public class IoUtil {
    public static void toFile(File saveFile, String content) {
        File parent = saveFile.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
        }
        try (PrintWriter out = new PrintWriter(new FileWriter(saveFile))) {
            out.print(content);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

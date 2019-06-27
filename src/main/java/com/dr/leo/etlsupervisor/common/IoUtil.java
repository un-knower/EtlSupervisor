package com.dr.leo.etlsupervisor.common;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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


    public static int getLineNumber(String fileName) {
        int lineNum = 0;
        char[] buf = new char[8192];
        LineNumberReader lnr = null;

        try {
            lnr = new LineNumberReader(new InputStreamReader(
                    new FileInputStream(fileName)));

            while (lnr.read(buf) != -1) {

            }

            lineNum = lnr.getLineNumber() + 1;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != lnr) {
                    lnr.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }


        return lineNum;
    }

    public static List<String> readLines(File file) {
        List<String> lines = new ArrayList<>();
        FileReader fileReader = null;
        BufferedReader bufferedReader = null;
        try {
            fileReader = new FileReader(file);
            bufferedReader = new BufferedReader(fileReader);
            String line = bufferedReader.readLine();

            while (line != null) {
                lines.add(line);
                line = bufferedReader.readLine();
            }
        } catch (IOException e) {
            return lines;
        } finally {
            try {
                if (bufferedReader != null) {
                    bufferedReader.close();
                }
                if (fileReader != null) {
                    fileReader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    public static void main(String[] args) {
        File file = new File("C:\\Users\\leo\\AppData\\Local\\Temp\\R10001\\20190626\\bigdata_buy2_2019_06_26.DAT");
        System.out.println(getLineNumber(file.getPath()));

    }
}

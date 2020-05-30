package com.ffl.study.common.utils;

import java.io.File;

/**
 * 对文件进行操作
 *
 * @author liufeifei
 * @date 2018/05/09
 */
public class FileUtils {

    /**
     * 删除目录 及 子内容，传入字符串
     *
     * @param dir
     */
    public static void deleteDir(String dir) {
        deleteDir(new File(dir));
    }

    /**
     * 删除目录 及 子内容 传入文件
     *
     * @param file
     */
    public static void deleteDir(File file) {
        if (!file.exists()) {
            System.out.println("目录不存在");
            return;
        } else if (file.isDirectory()) {
            File[] fileList = file.listFiles();
            for (File childfile : fileList) {
                deleteDir(childfile);
            }
        }

        file.delete();
    }
}

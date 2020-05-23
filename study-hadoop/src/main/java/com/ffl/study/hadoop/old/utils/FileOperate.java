package com.ffl.study.hadoop.old.utils;

import java.io.File;

/**
 * 对文件进行操作
 *
 * @author liufeifei
 * @date 2018/05/09
 */
public class FileOperate {

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
        if(!file.exists()) {
            System.out.println("目录不存在");
        } else if(file.isDirectory()) {
            File[] fileList = file.listFiles();
            for(File childfile:fileList) {
                deleteDir(childfile);
            }
        }

        file.delete();
    }

    public static void main(String[] args)throws Exception {
        File directory = new File(".");
        System.out.println(directory.getCanonicalPath());
        System.out.println(directory.getAbsolutePath());
        System.out.println(directory.getPath());
    }
}

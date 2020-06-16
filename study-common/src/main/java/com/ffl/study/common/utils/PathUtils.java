package com.ffl.study.common.utils;

import com.ffl.study.common.constants.CharConstants;

/**
 * @author lff
 * @datetime 2020/06/16 22:34
 */
public class PathUtils {

    private static final String USER_DIR = "user.dir";

    /**
     * 得到项目路径
     *
     * @return 项目路径
     */
    public static String getProjectPath() {
        return System.getProperty(USER_DIR);
    }

    /**
     * 得到资源文件夹路径
     *
     * @param module 模块路径 请使用
     * @return 模块资源路径
     * @see com.ffl.study.common.constants.PathConstants
     */
    public static String getResPath(String module) {
        return getProjectPath() + module;
    }

    /**
     * 返回资源下的文件名称路径
     *
     * @param module   模块路径 请使用
     * @param filePath 文件名
     * @return 资源下
     * @see com.ffl.study.common.constants.PathConstants
     */
    public static String getResFilePath(String module, String filePath) {
        return filePath.charAt(0) == CharConstants.DIVIDE ?
                getResPath(module) + filePath : getResPath(module) + CharConstants.DIVIDE + filePath;
    }

}

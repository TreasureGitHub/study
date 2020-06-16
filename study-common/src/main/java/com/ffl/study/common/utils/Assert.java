package com.ffl.study.common.utils;

/**
 * @author lff
 * @datetime 2020/06/14 10:25
 */
public class Assert {

    /**
     * 非空判断
     *
     * @param obj
     */
    public static void notNull(Object obj){
        if(obj == null){
            throw new RuntimeException(String.format("%s can not be null",obj));
        }
    }
}

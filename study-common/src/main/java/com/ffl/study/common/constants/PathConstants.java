package com.ffl.study.common.constants;

/**
 * @author lff
 * @datetime 2020/05/22 18:34
 *
 * path常量
 */
public interface PathConstants {

    /**
     * spark 资源绝对路径
     */
    String SPARK_RES_ABS = "/study-spark/src/main/resources";

    /**
     * hadoop 资源绝对路径
     */
    String HADOOP_RES_ABS = "/study-hadoop/src/main/resources";

    /**
     * java 资源 相对路径
     */
    String JAVA_RES_ABS = "/study-java/src/main/resources";

    /**
     * hadoop resource 路径 ，以 . 开头
     */
    String HADOOP_RES = StringConstants.DOT + HADOOP_RES_ABS;


    /**
     * spark resource 路径 ，以 . 开头
     */
    String SPARK_RES = StringConstants.DOT + SPARK_RES_ABS;
}

package com.ffl.study.spark.java;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import scala.Tuple2;

import java.util.Arrays;

import static com.ffl.study.spark.java.constant.PathConstants.RESOURCE_PATH;

/**
 * @author lff
 * @datetime 2020/01/15 20:01
 */
public class JavaWordCount {

    public static void main(String[] args) {
        // 获取配置信息
        SparkConf conf = new SparkConf().setAppName("JavaWordCount").setMaster("local");

        //
        JavaSparkContext jsc = new JavaSparkContext(conf);

        // 读取文件得到 lines
        JavaRDD<String> lines = jsc.textFile(RESOURCE_PATH + "/input/words.txt");

        System.out.println(RESOURCE_PATH + "/input/words.txt");

        // 得到words
        JavaRDD<String> words = lines.flatMap(line -> Arrays.asList(line.split(" ")).iterator());

        // 得到键值对
        JavaPairRDD<String, Integer> wordAndOne = words.mapToPair(word -> new Tuple2<>(word, 1));

        // 聚合
        JavaPairRDD<String, Integer> reduced = wordAndOne.reduceByKey((m,n) -> m + n);

        // 调换顺序
        JavaPairRDD<Integer, String> swaped = reduced.mapToPair(tp -> tp.swap());

        //排序
        JavaPairRDD<Integer, String> sorted = swaped.sortByKey(false);

        // 调整顺序
        JavaPairRDD<String, Integer> result = sorted.mapToPair(tp -> tp.swap());

        result.saveAsTextFile(RESOURCE_PATH + "/output/text");

        jsc.stop();
    }

}

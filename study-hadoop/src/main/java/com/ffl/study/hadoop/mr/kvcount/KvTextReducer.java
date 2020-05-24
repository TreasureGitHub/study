package com.ffl.study.hadoop.mr.kvcount;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/23 11:53
 */
public class KvTextMapper extends Mapper<Text,Text, Text, IntWritable> {

    private IntWritable ONE = new IntWritable(1);


    @Override
    protected void map(Text key, Text value, Context context) throws IOException, InterruptedException {
        // 1。封装对象


    }
}

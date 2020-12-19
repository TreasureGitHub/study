package com.ffl.study.hbase.mr.fruit;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/12/19 11:17
 *
 * mapper端主要是读取数据，怎进来怎么出去
 */
public class FruitMapper extends Mapper<LongWritable, Text,LongWritable,Text> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        super.map(key, value, context);
    }
}
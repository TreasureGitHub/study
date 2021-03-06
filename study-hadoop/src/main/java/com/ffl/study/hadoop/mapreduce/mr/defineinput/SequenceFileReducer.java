package com.ffl.study.hadoop.mapreduce.mr.defineinput;

import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/23 12:56
 */
public class SequenceFileReducer extends Reducer<Text, BytesWritable,Text,BytesWritable> {

    @Override
    protected void reduce(Text key, Iterable<BytesWritable> values, Context context) throws IOException, InterruptedException {
        // 循环写
        for (BytesWritable value:values){
            context.write(key,value);
        }
    }
}

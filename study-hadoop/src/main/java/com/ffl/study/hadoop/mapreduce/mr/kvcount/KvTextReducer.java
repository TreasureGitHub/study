package com.ffl.study.hadoop.mapreduce.mr.kvcount;


import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/23 11:53
 */
public class KvTextReducer extends Reducer<Text, IntWritable, Text, IntWritable> {

    private IntWritable cnt = new IntWritable();

    @Override
    protected void reduce(Text key, Iterable<IntWritable> values, Context context) throws IOException, InterruptedException {

        int sum = 0;

        for (IntWritable value : values) {
            sum += value.get();
        }

        cnt.set(sum);

        context.write(key, cnt);
    }
}

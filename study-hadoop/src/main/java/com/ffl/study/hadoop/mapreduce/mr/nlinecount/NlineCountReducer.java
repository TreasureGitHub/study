package com.ffl.study.hadoop.mapreduce.mr.nlinecount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/22 17:59
 *
 * reducer
 */
public class NlineCountReducer extends Reducer<Text, LongWritable, Text,LongWritable> {

    private static LongWritable outValue = new LongWritable();


    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {
        long sum = 0;

        for(LongWritable value:values){
            sum += value.get();
        }

        outValue.set(sum);
        context.write(key,outValue);

    }
}

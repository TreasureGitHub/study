package com.ffl.study.hadoop.mr.wordcount;

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
public class WordCountReducer extends Reducer<Text, LongWritable, Text,LongWritable> {

    private static LongWritable OUT_VALUE = new LongWritable();


    @Override
    protected void reduce(Text key, Iterable<LongWritable> values, Context context) throws IOException, InterruptedException {


        long sum = 0;

        for(LongWritable value:values){
            sum += value.get();
        }

        OUT_VALUE.set(sum);
        context.write(key,OUT_VALUE);

    }
}

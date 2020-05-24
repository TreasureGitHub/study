package com.ffl.study.hadoop.mr.nlinecount;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/22 17:50
 * <p>
 * map 阶段
 */
public class NlineCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

    private Text outKey = new Text();

    private static LongWritable ONE = new LongWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] words = value.toString().split(" ");

        for (String word : words) {
            outKey.set(word);
            context.write(outKey, ONE);
        }
    }
}

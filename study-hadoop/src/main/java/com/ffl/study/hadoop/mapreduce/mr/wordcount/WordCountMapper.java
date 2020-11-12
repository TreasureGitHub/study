package com.ffl.study.hadoop.mapreduce.mr.wordcount;

import com.ffl.study.common.constants.StringConstants;
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
public class WordCountMapper extends Mapper<LongWritable, Text, Text, LongWritable> {

    private static Text KEY = new Text();

    private static LongWritable ONE = new LongWritable(1);

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] words = value.toString().split(StringConstants.SPACE);

        for (String word : words) {
            KEY.set(word);
            context.write(KEY, ONE);
        }
    }
}

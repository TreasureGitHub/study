package com.ffl.study.hadoop.mapreduce.mr.filterlog;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/24 02:34
 */
public class FilterReducer extends Reducer<Text, NullWritable, Text, NullWritable> {

    private Text data = new Text();

    @Override
    protected void reduce(Text key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {

        for (NullWritable value : values) {

            data.set(key.toString() + "\r\n");
            context.write(data, NullWritable.get());
        }
    }
}

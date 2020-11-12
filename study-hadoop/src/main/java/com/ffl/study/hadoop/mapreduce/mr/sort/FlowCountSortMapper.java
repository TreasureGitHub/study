package com.ffl.study.hadoop.mapreduce.mr.sort;

import com.ffl.study.hadoop.mapreduce.pojo.FlowBeanSort;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/22 21:47
 */
public class FlowCountSortMapper extends Mapper<LongWritable, Text, FlowBeanSort,Text> {

    private Text number = new Text();

    private FlowBeanSort bean = new FlowBeanSort();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split("\t");

        number.set(fields[1]);

        long upFlow = Long.parseLong(fields[fields.length - 3]);
        long downFlow = Long.parseLong(fields[fields.length - 2]);


        bean.set(upFlow,downFlow);

        context.write(bean,number);
    }
}

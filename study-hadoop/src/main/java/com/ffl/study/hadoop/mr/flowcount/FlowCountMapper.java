package com.ffl.study.hadoop.flowcount;

import com.ffl.study.hadoop.pojo.FlowBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/22 21:47
 */
public class FlowCountMapper extends Mapper<LongWritable, Text, Text, FlowBean> {

    private Text number = new Text();

    private FlowBean bean = new FlowBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split("\t");

        number.set(fields[1]);

        long upFlow = Long.parseLong(fields[fields.length - 3]);
        long downFlow = Long.parseLong(fields[fields.length - 2]);

        bean.setUpFlow(upFlow);
        bean.setDownFlow(downFlow);

        context.write(number,bean);
    }
}

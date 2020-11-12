package com.ffl.study.hadoop.mapreduce.mr.flowcount;

import com.ffl.study.common.constants.StringConstants;
import com.ffl.study.hadoop.mapreduce.pojo.FlowBean;
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

    private FlowBean flowBean = new FlowBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

        //获取数据big切割
        String[] fields = value.toString().split(StringConstants.TAB);

        number.set(fields[1]);

        // 封装数据
        long upFlow = Long.parseLong(fields[fields.length - 3]);
        long downFlow = Long.parseLong(fields[fields.length - 2]);

        flowBean.set(upFlow,downFlow);

        // 写入数据
        context.write(number,flowBean);
    }
}

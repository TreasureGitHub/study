package com.ffl.study.hadoop.mapreduce.mr.orderprice;

import com.ffl.study.common.constants.StringConstants;
import com.ffl.study.hadoop.mapreduce.pojo.OrderBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/23 23:36
 */
public class OrderMapper extends Mapper<LongWritable, Text, OrderBean, NullWritable> {

    private OrderBean orderBean = new OrderBean();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String[] fields = value.toString().split(StringConstants.TAB);

        orderBean.setOrder(fields[0]);
        orderBean.setPrice(Double.parseDouble(fields[2]));

        context.write(orderBean,NullWritable.get());
    }
}

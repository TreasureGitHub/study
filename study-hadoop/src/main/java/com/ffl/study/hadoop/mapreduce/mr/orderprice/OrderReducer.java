package com.ffl.study.hadoop.mapreduce.mr.orderprice;

import com.ffl.study.hadoop.mapreduce.pojo.OrderBean;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/23 23:41
 */
public class OrderReducer extends Reducer<OrderBean, NullWritable,OrderBean,NullWritable> {

    @Override
    protected void reduce(OrderBean key, Iterable<NullWritable> values, Context context) throws IOException, InterruptedException {
        context.write(key,NullWritable.get());

        // 打开此处输出可以验证
        // for (NullWritable value : values) {
        //     System.out.println("----------key: " + key + "----------value:  " + value);
        // }
    }
}

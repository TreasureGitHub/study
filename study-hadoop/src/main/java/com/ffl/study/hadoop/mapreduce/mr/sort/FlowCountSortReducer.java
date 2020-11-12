package com.ffl.study.hadoop.mapreduce.mr.sort;

import com.ffl.study.hadoop.mapreduce.pojo.FlowBeanSort;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/22 21:57
 */
public class FlowCountSortReducer extends Reducer<FlowBeanSort, Text, Text, FlowBeanSort> {

    private FlowBeanSort bean = new FlowBeanSort();

    @Override
    protected void reduce(FlowBeanSort key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        for (Text value : values) {
            context.write(value,key);
        }
    }
}

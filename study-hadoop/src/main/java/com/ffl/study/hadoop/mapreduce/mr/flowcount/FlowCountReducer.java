package com.ffl.study.hadoop.mapreduce.mr.flowcount;

import com.ffl.study.hadoop.mapreduce.pojo.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/22 21:57
 */
public class FlowCountReducer extends Reducer<Text, FlowBean, Text, FlowBean> {

    private FlowBean bean = new FlowBean();

    @Override
    protected void reduce(Text key, Iterable<FlowBean> values, Context context) throws IOException, InterruptedException {
        long sumUpFlow = 0;
        long sumDownFlow = 0;

        for (FlowBean flowBean : values) {
            sumUpFlow += flowBean.getUpFlow();
            sumDownFlow += flowBean.getDownFlow();
        }

        bean.set(sumUpFlow, sumDownFlow);
        context.write(key, bean);
    }
}

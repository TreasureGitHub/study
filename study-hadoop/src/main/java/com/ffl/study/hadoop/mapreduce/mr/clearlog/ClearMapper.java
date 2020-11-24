package com.ffl.study.hadoop.mapreduce.mr.clearlog;

import com.ffl.study.common.constants.StringConstants;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/11/16 20:31
 */
public class ClearMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 获取数据
        String line = value.toString();

        // 解析数据
        boolean result = parseLog(line, context);

        // 如果为脏数据则舍弃，直接返回；否则写出去
        if (!result) {
            return;
        } else {
            context.write(value, NullWritable.get());
        }
    }

    /**
     * 解析数据，如果长度大于2，则认为是 有效数据，否则为脏数据
     *
     * @param line
     * @param context
     * @return
     */
    private boolean parseLog(String line, Context context) {

        String[] lines = line.split(StringConstants.TAB);

        if(lines.length > 2){
            context.getCounter("map","true").increment(1);
            return true;
        } else {
            context.getCounter("map","false").increment(1);
            return false;
        }
    }


}

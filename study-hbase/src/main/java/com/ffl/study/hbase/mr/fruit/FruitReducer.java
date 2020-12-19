package com.ffl.study.hbase.mr.fruit;

import com.ffl.study.common.constants.StringConstants;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.mapreduce.TableReducer;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/12/19 11:17
 */
public class FruitReducer extends TableReducer<LongWritable, Text, NullWritable> {

    @Override
    protected void reduce(LongWritable key, Iterable<Text> values, Context context) throws IOException, InterruptedException {

        // 1.遍历value对象
        for (Text value : values) {

            // 2.获取每一行内容
            String[] field = value.toString().split(StringConstants.TAB);

            // 3.构建put对象
            Put put = new Put(Bytes.toBytes(field[0]));

            // 4.给put对象赋值
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("name"), Bytes.toBytes(field[1]));
            put.addColumn(Bytes.toBytes("info"), Bytes.toBytes("color"), Bytes.toBytes(field[2]));

            // 5.写出
            context.write(NullWritable.get(), put);
        }
    }
}

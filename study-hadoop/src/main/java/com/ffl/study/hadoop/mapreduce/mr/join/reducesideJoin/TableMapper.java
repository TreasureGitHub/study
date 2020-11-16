package com.ffl.study.hadoop.mapreduce.mr.join.reducesideJoin;

import com.ffl.study.common.constants.StringConstants;
import com.ffl.study.hadoop.mapreduce.pojo.TableBean;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/24 03:23
 */
public class TableMapper extends Mapper<LongWritable, Text, Text, TableBean> {

    private String fileName;

    private TableBean tableBean = new TableBean();

    private Text keyData = new Text();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // 获取文件名称
        FileSplit inputSplit = (FileSplit) context.getInputSplit();
        fileName = inputSplit.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        String[] fields = line.split(StringConstants.TAB);

        if (fileName.startsWith("order")) {
            // 订单表
            tableBean.setId(fields[0]);
            tableBean.setPId(fields[1]);
            tableBean.setAmount(Integer.parseInt(fields[2]));
            tableBean.setPName(StringConstants.EMPTY);
            tableBean.setFlag("order");
            keyData.set(fields[1]);
        } else {
            // 产品表
            tableBean.setId(StringConstants.EMPTY);
            tableBean.setPId(fields[0]);
            tableBean.setAmount(0);
            tableBean.setPName(fields[1]);
            tableBean.setFlag("pd");
            keyData.set(fields[0]);
        }

        context.write(keyData, tableBean);
    }
}

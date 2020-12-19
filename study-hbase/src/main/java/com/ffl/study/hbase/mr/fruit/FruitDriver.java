package com.ffl.study.hbase.mr.fruit;

import com.ffl.study.common.constants.PathConstants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.mapreduce.TableMapReduceUtil;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

/**
 * @author lff
 * @datetime 2020/12/19 11:17
 * <p>
 * 注意，此为 Driver的第二种写法
 */
public class FruitDriver implements Tool {

    private Configuration configuration = null;

    @Override
    public int run(String[] args) throws Exception {
        // 1.设置job对象
        Job job = Job.getInstance(configuration);

        // 2.设置驱动类
        job.setJarByClass(FruitDriver.class);

        // 3.设置map和reducer输出kv
        job.setMapperClass(FruitMapper.class);
        job.setMapOutputKeyClass(LongWritable.class);
        job.setMapOutputValueClass(Text.class);


        // 4.设置reducer类
        TableMapReduceUtil.initTableReducerJob("fruit", FruitReducer.class, job);

        // 5.设置输入参数
        FileInputFormat.setInputPaths(job, PathConstants.HBASE_RES + "/data");

        boolean result = job.waitForCompletion(true);


        return result ? 0 : 1;
    }

    @Override
    public void setConf(Configuration conf) {
        configuration = conf;
    }

    @Override
    public Configuration getConf() {
        return configuration;
    }

    public static void main(String[] args) throws Exception {

        Configuration configuration = HBaseConfiguration.create();
        ToolRunner.run(configuration,new FruitDriver(),args);
    }
}

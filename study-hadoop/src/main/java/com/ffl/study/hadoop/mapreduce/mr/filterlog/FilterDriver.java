package com.ffl.study.hadoop.mapreduce.mr.filterlog;

import com.ffl.study.common.constants.PathConstants;
import com.ffl.study.common.utils.FileUtils;
import com.ffl.study.hadoop.mapreduce.outformat.FilterOutputFormat;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/23 12:58
 *
 * 要求对输入数据中有baidu.com的数据输入到指定文件中，其它数据输入到另一个文件中
 *
 */
public class FilterDriver {


    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // 1.获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2.设置jar存储位置
        job.setJarByClass(FilterDriver.class);

        // 3.关联Map和Reduce类
        job.setMapperClass(FilterMapper.class);
        job.setReducerClass(FilterReducer.class);

        // 4.设置Mapper阶段输出数据的key和value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(NullWritable.class);

        // 5.设置最终输出输出的key和value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        // 设置输入输出类型
        job.setOutputFormatClass(FilterOutputFormat.class);

        String input = ArrayUtils.getLength(args) == 2 ? args[0] : PathConstants.HADOOP_RES + "/filter_input";
        String output = ArrayUtils.getLength(args) == 2 ? args[1] : PathConstants.HADOOP_RES + "/filter_output";

        // 如果用集群跑，此处注释掉
        FileUtils.deleteDir(output);

        // 6.设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, input);
        FileOutputFormat.setOutputPath(job, new Path(output));

        // 7.提交job
        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 : 1);
    }
}

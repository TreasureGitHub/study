package com.ffl.study.hadoop.mr.wordcount;

import com.ffl.study.common.constants.PathConstants;
import com.ffl.study.common.utils.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.CombineTextInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/22 18:07
 * <p>
 * Driver
 */
public class WordCountDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        // 1.获取job对象
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        // 2.设置jar存储位置
        job.setJarByClass(WordCountDriver.class);

        // 3.关联Map和Reduce类
        job.setMapperClass(WordCountMapper.class);
        job.setReducerClass(WordCountReducer.class);

        // 4.设置Mapper阶段输出数据的key和value类型
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(LongWritable.class);

        // 5.设置最终输出输出的key和value类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(LongWritable.class);

        // 默认使用 TextInputFormat，此处调整切片大小
        job.setInputFormatClass(CombineTextInputFormat.class);
        CombineTextInputFormat.setMaxInputSplitSize(job,4194304); // 4M

        String input = ArrayUtils.getLength(args) == 2 ? args[0] : PathConstants.HADOOP_RES + "/wc_input";
        String output = ArrayUtils.getLength(args) == 2 ? args[1] : PathConstants.HADOOP_RES + "/wc_output";

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

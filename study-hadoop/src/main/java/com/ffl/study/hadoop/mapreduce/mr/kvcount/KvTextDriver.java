package com.ffl.study.hadoop.mapreduce.mr.kvcount;

import com.ffl.study.common.constants.PathConstants;
import com.ffl.study.common.constants.StringConstants;
import com.ffl.study.common.utils.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.input.KeyValueLineRecordReader;
import org.apache.hadoop.mapreduce.lib.input.KeyValueTextInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/23 12:00
 *
 * 需求：原始数据为k、v对，用tab键分割，统计收个单词的数量(v的内容不管)
 *
 */
public class KvTextDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration conf = new Configuration();
        conf.set(KeyValueLineRecordReader.KEY_VALUE_SEPERATOR, StringConstants.SPACE);

        Job job = Job.getInstance(conf);

        job.setJarByClass(KvTextDriver.class);

        job.setMapperClass(KvTextMapper.class);
        job.setReducerClass(KvTextReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(IntWritable.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(IntWritable.class);

        // 此参数设置InputFormat类型
        job.setInputFormatClass(KeyValueTextInputFormat.class);

        String input = ArrayUtils.getLength(args) == 2 ? args[0] : PathConstants.HADOOP_RES + "/input_kv";
        String output = ArrayUtils.getLength(args) == 2 ? args[1] : PathConstants.HADOOP_RES + "/output_kv";

        // 如果用集群跑，此处注释掉
        FileUtils.deleteDir(output);

        // 6.设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, input);
        FileOutputFormat.setOutputPath(job, new Path(output));

        job.setInputFormatClass(KeyValueTextInputFormat.class);

        boolean result = job.waitForCompletion(true);

        System.exit(result ? 0 :1);

    }

}

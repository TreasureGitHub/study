package com.ffl.study.hadoop.mapreduce.mr.sort;

import com.ffl.study.common.constants.PathConstants;
import com.ffl.study.common.utils.FileUtils;
import com.ffl.study.hadoop.mapreduce.pojo.FlowBeanSort;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/22 22:05
 *
 * 需求，对输入的内容，按照sumFlow进行降序排序输出到文件中
 *
 * 分5个分区，分区内数据也要降序排序
 */
public class FlowCountSortDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(FlowCountSortDriver.class);

        job.setMapperClass(FlowCountSortMapper.class);
        job.setReducerClass(FlowCountSortReducer.class);

        job.setMapOutputKeyClass(FlowBeanSort.class);
        job.setMapOutputValueClass(Text.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(FlowBeanSort.class);

        job.setPartitionerClass(ProvinceSortPartitioner.class);
        job.setNumReduceTasks(5);

        // 设置分区
        String input = ArrayUtils.getLength(args) == 2 ? args[0] : PathConstants.HADOOP_RES + "/flow_input";
        String output = ArrayUtils.getLength(args) == 2 ? args[1] : PathConstants.HADOOP_RES + "/flow_output";

        // 如果用集群跑，此处注释掉
        FileUtils.deleteDir(output);

        // 6.设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, input);
        FileOutputFormat.setOutputPath(job,new Path(output));
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}

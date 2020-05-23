package com.ffl.study.hadoop.old.mapreduce;

import com.ffl.study.hadoop.old.utils.FileOperate;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Partitioner;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * sort partition 示例
 *
 * @author liufeifei
 * @date 2018/05/09
 */
public class SortPartition {

    private static int maxNum = 0;

    public static class Map extends Mapper<LongWritable,Text,IntWritable,IntWritable> {

        private static IntWritable data = new IntWritable();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {

            String line = value.toString();
            int num = Integer.parseInt(line);

            if(num > maxNum) {
                maxNum = num;
            }

            data.set(num);
            context.write(data,new IntWritable(1));
        }
    }

    public static class Reduce extends Reducer<IntWritable,IntWritable,IntWritable,IntWritable> {

        private static IntWritable linenum = new IntWritable(1);

        @Override
        protected void reduce(IntWritable key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
            for(IntWritable val:values) {
                context.write(linenum,key);
                linenum = new IntWritable(linenum.get() + 1);
            }
        }
    }

    public static class Partition extends Partitioner<IntWritable,IntWritable> {

        @Override
        public int getPartition(IntWritable key, IntWritable value, int numPartitions) {
            // numPartitions 为设置reduce task的数量
            int bound = 1000 / numPartitions + 1;
            int keynumber = key.get();
            for(int i = 1 ; i <= numPartitions ; i++) {
                if(keynumber < bound * i && keynumber >= bound * (i - 1)) {
                    return i - 1;
                }
            }
            return -1;
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration() ;
        Job job = Job.getInstance(conf, "Sort");

        job.setJarByClass(SortPartition.class);
        job.setOutputKeyClass(IntWritable.class);
        job.setOutputValueClass(IntWritable.class);

        job.setMapperClass(Map.class);
        // 设置 partition
        job.setPartitionerClass(Partition.class);
        job.setReducerClass(Reduce.class);

        // reduce数量设置为3，启动三个reduce，并且结果文件为3份
        job.setNumReduceTasks(3);

        String input = "./src/main/resources/sort_input";
        String output = "./src/main/resources/sort_output";
        FileOperate.deleteDir(output);

        FileInputFormat.addInputPath(job,new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(output));

        System.exit(job.waitForCompletion(true) ? 0 :1);
    }
}

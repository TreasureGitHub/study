package com.ffl.study.hadoop.mapreduce;

import com.ffl.study.hadoop.utils.FileOperate;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.StringTokenizer;

/**
 * word count
 *
 * @author liufeifei
 * @date 2018/05/08
 */
public class WordCount {

    public static class WordCountMap extends Mapper<LongWritable,Text,Text,IntWritable> {

        private Text word = new Text();
        private static final IntWritable one = new IntWritable(1);

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            StringTokenizer st = new StringTokenizer(value.toString());
            while(st.hasMoreTokens()) {
                String str = st.nextToken();
                word.set(str);
                context.write(word,one);
            }
        }
    }

    public static class WordCountReduce extends Reducer<Text,IntWritable,Text,IntWritable> {

        private IntWritable value = new IntWritable();

        @Override
        protected void reduce(Text key, Iterable<IntWritable> iter, Context context) throws IOException, InterruptedException {
            int sum = 0;
            for(IntWritable one:iter) {
                sum += one.get();
            }

            value.set(sum);
            context.write(key,value);
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        // 得到默认配置
        Configuration conf = new Configuration();
        // 得到job
        Job job = Job.getInstance(conf,"Wordcount");

        job.setJarByClass(WordCount.class);
        // 设置map class
        job.setMapperClass(WordCountMap.class);
        // 设置combline class
        job.setCombinerClass(WordCountReduce.class);
        // 设置reduce class
        job.setReducerClass(WordCountReduce.class);

        // 设置输出key值
        job.setOutputKeyClass(Text.class);
        // 设置输出value值
        job.setOutputValueClass(IntWritable.class);

        String input = "./src/main/resources/wc_input";
        String output = "./src/main/resources/wc_output";
        // 删除结果文件，
        FileOperate.deleteDir(output);

        FileInputFormat.addInputPath(job,new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(output));

        System.out.println(job.waitForCompletion(true) ? 0 : 1);
    }
}

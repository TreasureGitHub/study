package com.ffl.study.hadoop.old.mapreduce;

import com.ffl.study.common.constants.PathConstants;
import com.ffl.study.common.utils.FileUtils;
import com.ffl.study.hadoop.old.pojo.User;
import org.apache.commons.lang.ArrayUtils;
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

/**
 * User 排序
 *
 * @author liufeifei
 * @date 2018/05/20
 */
public class UserScore {

    public static class UserScoreMap extends Mapper<LongWritable,Text, User,IntWritable> {

        private User user = new User();

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] arr = value.toString().split(",");

            user.setName(arr[0]);
            user.setAge(Integer.parseInt(arr[1]));
            context.write(user,new IntWritable(Integer.parseInt(arr[3])));
        }
    }

    public static class UserScoreReducer extends Reducer<User,IntWritable,User,IntWritable> {
        @Override
        protected void reduce(User key, Iterable<IntWritable> values, Context context)
            throws IOException, InterruptedException {
            int sum = 0;
            int cnt = 0;
            for(IntWritable value: values) {
                sum = sum + value.get();
                cnt ++;
            }
            context.write(key, new IntWritable(sum / cnt));
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("mapreduce.output.textoutputformat.separator",",");

        Job job = Job.getInstance(conf,"UserScore");

        job.setJarByClass(UserScore.class);
        job.setMapperClass(UserScoreMap.class);
        job.setReducerClass(UserScoreReducer.class);

        job.setOutputKeyClass(User.class);
        job.setOutputValueClass(IntWritable.class);

        String input = ArrayUtils.getLength(args) == 2 ? args[0] : PathConstants.HADOOP_RES + "/userscore_input";
        String output = ArrayUtils.getLength(args) == 2 ? args[1] : PathConstants.HADOOP_RES + "/userscore_output";

        // 如果用集群跑，此处注释掉
        FileUtils.deleteDir(output);

        FileInputFormat.addInputPath(job,new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(output));

        job.waitForCompletion(true);
    }


}

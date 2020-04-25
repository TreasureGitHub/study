package com.ffl.study.hadoop.mapreduce;

import com.ffl.study.hadoop.utils.FileOperate;
import com.google.common.collect.Lists;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.util.List;

/**
 *
 * reduce 端join 示例
 *
 * @date 2018/05/09
 */
public class ReduceSideJoin {

    //private static int time = 0;

    public static class ReduceSideJoinMap extends Mapper<LongWritable,Text,Text,Text> {

        private int side = 0;

        private static final String FACTORY_NAME = "factoryname";

        private static final String ADDRESS_ID = "addressid";

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] strArr = value.toString().split(",");

            if(FACTORY_NAME.equals(strArr[0]) || ADDRESS_ID.equals(strArr[0])){
                side = FACTORY_NAME.equals(strArr[0]) ? 1 : 2;
                return ;
            }

            if(side == 1) {
                // 左表
                context.write(new Text(strArr[1]),new Text("1+" + strArr[0]));
            } else {
                // 右表
                context.write(new Text(strArr[0]),new Text("2+" + strArr[1]));
            }
        }
    }

    public static class ReduceSideJoinReduce extends Reducer<Text,Text,Text,Text> {

        private static int time = 0;

        @Override
        protected void reduce(Text key, Iterable<Text> values, Context context)
            throws IOException, InterruptedException {

            if(time == 0 ) {
                context.write(new Text("factory"),new Text("address"));
                time ++;
            }


            List<String> left = Lists.newLinkedList();
            List<String> right = Lists.newLinkedList();

            for(Text value:values) {

                String word = value.toString();
                String subValue = (word == null) ? "":word.substring(2) ;

                if(word.startsWith("1+")) {
                    // 左表值
                    left.add(subValue);
                } else {
                    // 右表值
                    right.add(subValue);
                }

            }

            if(left.size() == 0) {
                return;
            } else {
                // 左表和右表求笛卡尔值
                for(String item:left) {
                    if(right.size() == 0) {
                        context.write(new Text(item),new Text(""));
                    } else {
                        for (String item1 : right) {
                            context.write(new Text(item), new Text(item1));
                        }
                    }
                }
            }

        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {

        Configuration conf = new Configuration();
        // 设置输出分隔符
        conf.set("mapreduce.output.textoutputformat.separator",",");

        Job job = Job.getInstance(conf);

        job.setJobName("ReduceSideJoin");
        job.setMapperClass(ReduceSideJoinMap.class);
        job.setReducerClass(ReduceSideJoinReduce.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        String input = "./src/main/resources/rsj_input";
        String output = "./src/main/resources/rsj_output";
        FileOperate.deleteDir(output);

        FileInputFormat.addInputPath(job, new Path(input));
        FileOutputFormat.setOutputPath(job, new Path(output));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}

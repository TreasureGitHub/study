package com.ffl.study.hadoop.old.mapreduce;

import com.ffl.study.hadoop.old.utils.FileOperate;
import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

/**
 *
 * map 端 join 示例
 *
 * @date 2018/05/09
 */
public class MapSideJoin {

    public static class MapSideJoinMap extends Mapper<LongWritable,Text,Text,Text> {

        private static final String FACTORY_NAME = "factoryname";

        private Configuration conf;

        private Map<String,String> addMap = Maps.newHashMap();

        @Override
        protected void setup(Context context) throws IOException, InterruptedException {
            conf = context.getConfiguration();
            URI[] uris = Job.getInstance(conf).getCacheFiles();
            for(URI uri : uris) {
                String fileName = uri.getPath();

                BufferedReader bf = new BufferedReader(new FileReader(fileName));

                String str = null;
                while( (str = bf.readLine()) != null) {
                    String[] arr =str.split(",");
                    if(StringUtils.equals("addressid",arr[0])) {
                        continue;
                    } else {
                        addMap.put(arr[0],arr[1]);
                    }
                }

            }
        }

        @Override
        protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
            String[] strArr = value.toString().split(",");

            if(FACTORY_NAME.equals(strArr[0])){
                return;
            }

            if( addMap.get(strArr[1]) != null) {
                context.write(new Text(strArr[0]),new Text(addMap.get(strArr[1])));
            } else {
                context.write(new Text(strArr[0]),new Text(""));
            }


        }
    }

    public static void main(String[] args)
        throws IOException, URISyntaxException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        conf.set("mapreduce.output.textoutputformat.separator",",");

        Job job = Job.getInstance(conf,"MapSideJoin");

        String cache = "/tmp/address.txt";
        String input = "./src/main/resources/msj_input";
        String output = "./src/main/resources/msj_output";
        FileOperate.deleteDir(output);

        job.setMapperClass(MapSideJoinMap.class);
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        FileInputFormat.addInputPath(job,new Path(input));
        FileOutputFormat.setOutputPath(job,new Path(output));

        // 加入缓存
        job.addCacheFile(new URI(cache));

        System.exit(job.waitForCompletion(true)? 0:1);

    }
}

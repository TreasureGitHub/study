package com.ffl.study.hadoop.mapreduce.mr.join.mapsidejoin;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.util.Map;

/**
 * @author lff
 * @datetime 2020/05/24 03:23
 */
public class DistributeCacheMapper extends Mapper<LongWritable, Text, Text, NullWritable> {

    private Map<String,String> pdMap = Maps.newHashMap();

    private Text data = new Text();

    @Override
    protected void setup(Context context) throws IOException {
        //获取路径
        URI[] cacheFiles = context.getCacheFiles();
        String path = cacheFiles[0].getPath();

        // 获取bufferReader
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), "UTF-8"));

        // 读取数据
        String line;
        while (StringUtils.isNotEmpty(line = bufferedReader.readLine())){

            String[] fields = line.split("\t");
            pdMap.put(fields[0],fields[1]);
        }

        // 关闭资源
        IOUtils.closeStream(bufferedReader);
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();

        String pId = line.split("\t")[1];

        String pName = pdMap.get(pId);

        line = line + "\t" + pName;
        data.set(line);

        // 计数器,每次加1 ，测试用，会在控制台中显示
        context.getCounter("map", "cnt").increment(1);

        context.write(data,NullWritable.get());
    }
}

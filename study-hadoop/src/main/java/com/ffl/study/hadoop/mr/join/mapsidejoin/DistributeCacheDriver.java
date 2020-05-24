package com.ffl.study.hadoop.mr.join.mapsidejoin;

import com.ffl.study.common.constants.PathConstants;
import com.ffl.study.common.utils.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author lff
 * @datetime 2020/05/22 22:05
 *
 * 产品表和订单表，需要将订单表中的产品名称补齐
 * 此处采用reduce端join
 *
 * 执行此操作之前，请在终端执行以下命令，否则为报没有权限 (本人在mac部署的伪分布式环境，执行从local模式)
 * 详细请看 https://stackoverflow.com/questions/23903113/mapreduce-error-usergroupinformation-priviledgedactionexception
 * cp -r ./study-hadoop/src/main/resources/mjoin_cache /tmp
 *
 *
 */
public class DistributeCacheDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException, URISyntaxException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(DistributeCacheDriver.class);

        job.setMapperClass(DistributeCacheMapper.class);

        // 最终输出格式设置
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(NullWritable.class);

        // 设置缓存，且设置reduce数量为0
        job.addCacheFile(new URI("/tmp/mjoin_cache/pd.txt"));
        job.setNumReduceTasks(0);

        String input = ArrayUtils.getLength(args) == 2 ? args[0] : PathConstants.HADOOP_RES + "/mjoin_input";
        String output = ArrayUtils.getLength(args) == 2 ? args[1] : PathConstants.HADOOP_RES + "/mjoin_output";

        // 如果用集群跑，此处注释掉
        FileUtils.deleteDir(output);

        // 6.设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, input);
        FileOutputFormat.setOutputPath(job,new Path(output));
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}

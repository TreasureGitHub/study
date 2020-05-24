package com.ffl.study.hadoop.mr.join.mapsidejoin;

import com.ffl.study.common.constants.PathConstants;
import com.ffl.study.common.utils.FileUtils;
import com.ffl.study.hadoop.mr.join.reducesideJoin.TableMapper;
import com.ffl.study.hadoop.mr.join.reducesideJoin.TableReducer;
import com.ffl.study.hadoop.pojo.TableBean;
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
 * 产品表和订单表，需要将订单表中的产品名称补齐
 * 此处采用reduce端join
 */
public class MapTableDriver {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job job = Job.getInstance(conf);

        job.setJarByClass(MapTableDriver.class);

        job.setMapperClass(TableMapper.class);
        job.setReducerClass(TableReducer.class);

        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(TableBean.class);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(TableBean.class);

        String input = ArrayUtils.getLength(args) == 2 ? args[0] : PathConstants.HADOOP_RES + "/rjoin_input";
        String output = ArrayUtils.getLength(args) == 2 ? args[1] : PathConstants.HADOOP_RES + "/rjoin_output";

        // 如果用集群跑，此处注释掉
        FileUtils.deleteDir(output);

        // 6.设置输入路径和输出路径
        FileInputFormat.setInputPaths(job, input);
        FileOutputFormat.setOutputPath(job,new Path(output));
        boolean result = job.waitForCompletion(true);
        System.exit(result ? 0 : 1);
    }
}

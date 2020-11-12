package com.ffl.study.hadoop.mapreduce.outformat.writer;

import com.ffl.study.common.constants.PathConstants;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/24 02:39
 */
public class FRecordWriter extends RecordWriter<Text, NullWritable> {

    private FSDataOutputStream fsBaidu;

    private FSDataOutputStream fsOther;

    public FRecordWriter(TaskAttemptContext job) {
        // 1.获取文件系统
        try {
            FileSystem fs = FileSystem.get(job.getConfiguration());

            // 2.创建输出到 baidu.log的输出流
            fsBaidu = fs.create(new Path(PathConstants.HADOOP_RES + "/filter_output/baidu.log"));

            // 3.创建输出到other的输出流
            fsOther = fs.create(new Path(PathConstants.HADOOP_RES + "/filter_output/other.log"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void write(Text key, NullWritable value) throws IOException, InterruptedException {
        if(key.toString().contains("baidu")){
            fsBaidu.write(key.toString().getBytes());
        } else {
            fsOther.write(key.toString().getBytes());
        }

    }

    @Override
    public void close(TaskAttemptContext context) throws IOException, InterruptedException {
        IOUtils.closeStream(fsBaidu);
        IOUtils.closeStream(fsOther);
    }
}

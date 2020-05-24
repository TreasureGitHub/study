package com.ffl.study.hadoop.reader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.BytesWritable;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/23 12:34
 *
 * 几个文件会产生几个task
 */
public class WholeRecordReader extends RecordReader<Text, BytesWritable> {

    private FileSplit split;

    private Text key = new Text();

    private BytesWritable value = new BytesWritable();

    private Configuration config;

    private boolean isProgress = true;

    /**
     * 初始化
     *
     * @param split
     * @param context
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public void initialize(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        // 初始化
        this.split = (FileSplit) split;
        this.config = context.getConfiguration();

    }

    /**
     * 获取key value
     * <p>
     * 核心业务逻辑
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public boolean nextKeyValue() throws IOException, InterruptedException {

        if (isProgress) {
            byte[] buff = new byte[(int) split.getLength()];

            // 1.获取fs对象
            Path path = split.getPath();
            FileSystem fs = path.getFileSystem(config);

            // 2.获取输入流
            FSDataInputStream fis = fs.open(path);

            // 3.拷贝 ，将文件内容读入buff缓冲区中
            IOUtils.readFully(fis, buff, 0, buff.length);

            // 4.封装value
            value.set(buff, 0, buff.length);

            // 5.封装key
            key.set(path.toString());

            // 6.关闭资源
            IOUtils.closeStream(fis);

            isProgress = false;

            return true;
        }

        return false;
    }

    @Override
    public Text getCurrentKey() throws IOException, InterruptedException {
        return key;
    }

    @Override
    public BytesWritable getCurrentValue() throws IOException, InterruptedException {
        return value;
    }

    /**
     * 获取进度条
     *
     * @return
     * @throws IOException
     * @throws InterruptedException
     */
    @Override
    public float getProgress() throws IOException, InterruptedException {
        return 0;
    }

    /**
     * 关闭资源
     *
     * @throws IOException
     */
    @Override
    public void close() throws IOException {

    }
}

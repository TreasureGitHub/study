package com.ffl.study.hadoop.hdfs;


import com.ffl.study.common.constants.PathConstants;
import com.ffl.study.common.utils.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author lff
 * @datetime 2020/12/20 19:09
 */
public class HdfsIOClient {

    private static final String HDFS_HOST = "localhost:9000";

    private static final String HDFS_PATH = "hdfs://" + HDFS_HOST;

    private static final String TEST_PATH = "/test/hdfs";

    private static final String TEST_PATH_NEW = "/test/hdfs_new";

    public static void main(String[] args) throws IOException, URISyntaxException, InterruptedException {
        // 获取文件系统
        Configuration configuration = new Configuration();
        configuration.set("fs.defaultFS", HDFS_HOST);
        FileSystem fs = FileSystem.get(new URI(HDFS_PATH), configuration, "feifeiliu");

        // 1.判断文件是否存在
        if (fs.exists(new Path(TEST_PATH))) {
            // 2.删除文件
            fs.delete(new Path(TEST_PATH), true);
        } else {
            // 3.没存在创建文件
            fs.mkdirs(new Path(TEST_PATH));
        }

        // ----------------上传单个文件
        String input = PathConstants.HADOOP_RES + "/wc_input/text1.txt";

        // 1 创建输入流
        FileInputStream fis = new FileInputStream(new File(input));

        // 2 获取输出流
        FSDataOutputStream fos = fs.create(new Path(TEST_PATH + "/text1.txt"));

        // 3 流对拷
        IOUtils.copyBytes(fis, fos, configuration);

        // 4.关闭资源
        IOUtils.closeStream(fis);
        IOUtils.closeStream(fos);

        // ----------------下载文件

        // 1 获取输入流
        FSDataInputStream fis1 = fs.open(new Path(TEST_PATH + "/text1.txt"));

        // 2 获取输出流
        String output = PathConstants.HADOOP_RES + "/wc_output_hdfs/test";
        FileUtils.createDir(output);
        FileOutputStream fos1 = new FileOutputStream(new File(output + "/test.txt"));

        // 3 流的对拷
        IOUtils.copyBytes(fis1, fos1, configuration);

        // 4 关闭资源
        IOUtils.closeStream(fos1);
        IOUtils.closeStream(fis1);

        // 关闭资源
        fs.close();

    }
}

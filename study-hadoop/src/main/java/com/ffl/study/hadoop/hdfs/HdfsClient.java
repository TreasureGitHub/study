package com.ffl.study.hadoop.hdfs;


import com.ffl.study.common.constants.PathConstants;
import com.ffl.study.common.utils.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author lff
 * @datetime 2020/12/20 19:09
 */
public class HdfsClient {

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

        // 4 上传文件
        String input = PathConstants.HADOOP_RES + "/wc_input";
        fs.copyFromLocalFile(new Path(input), new Path(TEST_PATH + "/wc_input.txt"));

        // 5.下载文件
        String output = PathConstants.HADOOP_RES + "/wc_output_hdfs";
        FileUtils.deleteDir(output);
        fs.copyToLocalFile(new Path(TEST_PATH + "/wc_input.txt"), new Path(output));

        // 6.修改文件夹名称
        fs.rename(new Path(TEST_PATH), new Path(TEST_PATH_NEW));

        // 7.文件详情查看
        RemoteIterator<LocatedFileStatus> listFiles = fs.listFiles(new Path(TEST_PATH_NEW), true);
        while (listFiles.hasNext()) {
            LocatedFileStatus status = listFiles.next();

            // 输出详情
            // 文件名称
            System.out.println(status.getPath().getName());
            // 长度
            System.out.println(status.getLen());
            // 权限
            System.out.println(status.getPermission());
            // 分组
            System.out.println(status.getGroup());

            // 获取存储的块信息
            BlockLocation[] blockLocations = status.getBlockLocations();

            for (BlockLocation blockLocation : blockLocations) {

                // 获取块存储的主机节点
                String[] hosts = blockLocation.getHosts();

                for (String host : hosts) {
                    System.out.println(host);
                }
            }

            System.out.println("-----------分割线----------");
        }


        // 关闭资源
        fs.close();

    }
}

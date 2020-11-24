package com.ffl.study.hadoop.mapreduce.mr.compress;

import com.ffl.study.common.constants.PathConstants;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.compress.*;
import org.apache.hadoop.util.ReflectionUtils;

import java.io.*;

/**
 * @author lff
 * @datetime 2020/05/24 11:06
 *
 * 对文件 compress_input/text1.txt 进行压缩
 *
 * 测试压缩
 */
public class TestCompress {

    public static void main(String[] args) throws IOException {
        String compressFile = PathConstants.HADOOP_RES + "/compress_input/text1.txt";
        compress(compressFile, BZip2Codec.class);
        compress(compressFile, GzipCodec.class);
        compress(compressFile, DefaultCodec.class);

        // String deCompressFile = PathConstants.HADOOP_RES + "/compress_input/text1.txt.bz2";
        // deCompress(deCompressFile);
    }

    /**
     * 压缩
     *
     * @param fileName
     * @param clz
     * @throws IOException
     */
    private static void compress(String fileName, Class clz) throws IOException {
        // 1.获取输入流
        FileInputStream fis = new FileInputStream(new File(fileName));

        CompressionCodec codec = (CompressionCodec) ReflectionUtils.newInstance(clz, new Configuration());

        // 2.获取输出流
        FileOutputStream fos = new FileOutputStream(new File(fileName + codec.getDefaultExtension()));
        CompressionOutputStream cos = codec.createOutputStream(fos);

        // 3.流的拷贝
        IOUtils.copyBytes(fis,cos,  1024 * 1024,false);

        // 4.关闭流
        IOUtils.closeStream(cos);
        IOUtils.closeStream(fos);
        IOUtils.closeStream(fis);
    }

    /**
     * 解压缩
     *
     * @param fileName
     * @throws IOException
     */
    private static void deCompress(String fileName) throws IOException {
        // 1.检查 是否能压缩
        CompressionCodecFactory factory = new CompressionCodecFactory(new Configuration());
        CompressionCodec codec = factory.getCodec(new Path(fileName));

        if(codec == null){
            System.out.println("can not process");
            return;
        }

        // 2.获取输入流
        FileInputStream fis = new FileInputStream(new File(fileName));
        CompressionInputStream cis = codec.createInputStream(fis);

        // 3.获取输出流
        FileOutputStream fos = new FileOutputStream((new File(fileName + "decode")));

        // 4.流拷贝
        IOUtils.copyBytes(cis,fos,1024 * 1024,false);

        // 5.关闭资源
        IOUtils.closeStream(fos);
        IOUtils.closeStream(cis);
        IOUtils.closeStream(fis);
    }
}

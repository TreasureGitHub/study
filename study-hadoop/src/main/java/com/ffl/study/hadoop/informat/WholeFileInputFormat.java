package com.ffl.study.hadoop.inputformat;

import org.apache.hadoop.io.ByteWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.InputSplit;
import org.apache.hadoop.mapreduce.RecordReader;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;

import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/23 12:32
 */
public class WholeFileInputFormat extends FileInputFormat<Text, ByteWritable> {



    @Override
    public RecordReader<Text, ByteWritable> createRecordReader(InputSplit split, TaskAttemptContext context) throws IOException, InterruptedException {
        return null;
    }
}

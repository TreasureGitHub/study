package com.ffl.study.hadoop.outformat.writer;

import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;

/**
 * @author lff
 * @datetime 2020/05/24 02:39
 */
public class FRecordWriter extends RecordWriter<Text, NullWritable> {
}

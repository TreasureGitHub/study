package com.ffl.study.hadoop.mr.flowcount;

import com.ffl.study.hadoop.pojo.FlowBean;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Partitioner;

/**
 * @author lff
 * @datetime 2020/05/23 17:03
 * <p>
 * 将数据中136、137、137、139开头的分别放一个分区，其它的放一个分区
 */
public class ProvincePartitioner extends Partitioner<Text, FlowBean> {

    @Override
    public int getPartition(Text text, FlowBean flowBean, int numPartitions) {
        int partition;

        String preNum = text.toString().substring(0, 3);

        switch (preNum) {
            case "136":
                partition = 0;
                break;
            case "137":
                partition = 1;
                break;
            case "138":
                partition = 2;
                break;
            case "139":
                partition = 3;
                break;
            default:
                partition = 4;
                break;
        }

        return partition;
    }
}

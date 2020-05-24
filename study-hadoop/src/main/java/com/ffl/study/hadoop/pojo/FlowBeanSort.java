package com.ffl.study.hadoop.mr.sort;

import lombok.Data;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/23 22:30
 */
@Data
public class FlowBean implements WritableComparable<FlowBean> {

    /**
     * 上行流量
     */
    private long upFlow;

    /**
     * 下行流量
     */
    private long downFlow;

    /**
     * 总流量
     */
    private long sumFlow;

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeLong(upFlow);
        out.writeLong(downFlow);
        out.writeLong(sumFlow);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        upFlow = in.readLong();
        downFlow = in.readLong();
        sumFlow = in.readLong();
    }

    /**
     * 倒序排
     *
     * @param other
     * @return
     */
    @Override
    public int compareTo(FlowBean other) {
        return - Long.compare(this.sumFlow,other.sumFlow);
    }
}

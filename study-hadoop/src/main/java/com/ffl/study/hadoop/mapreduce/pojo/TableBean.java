package com.ffl.study.hadoop.mapreduce.pojo;

import com.ffl.study.common.constants.StringConstants;
import lombok.Data;
import org.apache.hadoop.io.Writable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/24 03:13
 */
@Data
public class TableBean implements Writable {

    /**
     * 订单id
     */
    private String id;

    /**
     * 产品id
     */
    private String pId;

    /**
     * 产品名称
     */
    private String pName;

    /**
     * 数量
     */
    private int amount;

    /**
     * 标志位
     */
    private String flag;

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(id);
        out.writeUTF(pId);
        out.writeUTF(pName);
        out.writeInt(amount);
        out.writeUTF(flag);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        id = in.readUTF();
        pId = in.readUTF();
        pName = in.readUTF();
        amount = in.readInt();
        flag = in.readUTF();
    }

    @Override
    public String toString() {
        return id + StringConstants.TAB + pName + StringConstants.TAB + amount;
    }
}

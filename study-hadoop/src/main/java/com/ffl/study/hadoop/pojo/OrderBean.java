package com.ffl.study.hadoop.pojo;

import lombok.Data;
import org.apache.hadoop.io.WritableComparable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

/**
 * @author lff
 * @datetime 2020/05/23 23:28
 */
@Data
public class OrderBean implements WritableComparable<OrderBean> {

    /**
     * 订单ID
     */
    private String order;

    /**
     * 价格
     */
    private double price;

    @Override
    public void write(DataOutput out) throws IOException {
        out.writeUTF(order);
        out.writeDouble(price);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        order = in.readUTF();
        price = in.readDouble();
    }

    /**
     * 先按照order进行正序排序
     * 然后按照price降序排序
     *
     * @param other
     * @return
     */
    @Override
    public int compareTo(OrderBean other) {
        int ord;

        ord = this.order.compareTo(other.getOrder());

        if (ord == 0) {
            ord = -Double.compare(this.price, other.getPrice());
        }

        return ord;
    }

    @Override
    public String toString() {
        return order + "\t" + price;
    }
}

package com.ffl.study.hadoop.mr.orderprice;

import com.ffl.study.hadoop.pojo.OrderBean;
import org.apache.hadoop.io.WritableComparable;
import org.apache.hadoop.io.WritableComparator;

/**
 * @author lff
 * @datetime 2020/05/23 23:53
 */
public class OrderGroupingComparator extends WritableComparator {

    protected OrderGroupingComparator() {
        // 此处一定要穿true
        super(OrderBean.class, true);
    }

    @Override
    public int compare(WritableComparable a, WritableComparable b) {
        // 只要id相同，就认为是同一个对象
        OrderBean aBean = (OrderBean) a;
        OrderBean bBean = (OrderBean) b;

        return aBean.getOrder().compareTo(bBean.getOrder());
    }
}

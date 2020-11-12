package com.ffl.study.hadoop.mapreduce.mr.join.reducesideJoin;

import com.ffl.study.hadoop.mapreduce.pojo.TableBean;
import com.google.common.collect.Lists;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

/**
 * @author lff
 * @datetime 2020/05/24 03:35
 */
public class TableReducer extends Reducer<Text, TableBean, TableBean, NullWritable> {

    @Override
    protected void reduce(Text key, Iterable<TableBean> values, Context context) throws IOException, InterruptedException {

        List<TableBean> orderBeans = Lists.newArrayList();

        TableBean pdBean = new TableBean();

        for (TableBean tableBean : values) {
            if ("order".equals(tableBean.getFlag())) {
                TableBean tmpBean = new TableBean();
                orderBeans.add(tmpBean);
                try {
                    BeanUtils.copyProperties(tmpBean, tableBean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    BeanUtils.copyProperties(pdBean, tableBean);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }

        for (TableBean tableBean : orderBeans) {
            tableBean.setPName(pdBean.getPName());

            context.write(tableBean, NullWritable.get());
        }
    }
}

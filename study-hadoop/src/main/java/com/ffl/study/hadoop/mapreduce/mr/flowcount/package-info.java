/**
 * @author lff
 * @datetime 2020/11/14 11:50
 *
 * 需求
 * 统计每一个手机号耗费的总上行流量、下行流量、总流量
 * （1）输入数据
 *      resouces中flow_input
 * （2）输入数据格式：
 *      7 	13560436666	    120.196.100.99	 1116		954			200
 *      id	手机号码		    网络ip			 上行流量    下行流量     网络状态码
 * （3）期望输出数据格式
 *          13560436666 	1116		     954 		2070
 *          手机号码		    上行流量          下行流量		总流量
 *
 *
 *  FlowBean 为 编写好的序列化对象
 */
package com.ffl.study.hadoop.mapreduce.mr.flowcount;
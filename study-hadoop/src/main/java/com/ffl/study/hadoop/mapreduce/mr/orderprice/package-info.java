/**
 * @author lff
 * @datetime 2020/11/15 15:19
 *
 * 需求：有如下订单信息 需要输出订单ID和最大的订单金额，同一个订单ID只输出一次
 *
 * 订单ID    商品ID   金额
 *
 * 000001	pdt_01	222.8
 * 000001	pdt_02	33.8
 * 000002	pdt_03	522.8
 * 000002	pdt_04	122.8
 * 000002	pdt_05	722.8
 * 000003	pdt_06	232.8
 * 000003	pdt_02	33.8
 * 000002	pdt_03	522.8
 * 000002	pdt_04	122.8
 *
 * 2．需求分析
 * （1）利用“订单id和成交金额”作为key，可以将Map阶段读取到的所有订单数据按照id升序排序，如果id相同再按照金额降序排序，发送到Reduce。
 * （2）在Reduce端利用groupingComparator将订单id相同的kv聚合成组，然后取第一个即是该订单中最贵商品
 */
package com.ffl.study.hadoop.mapreduce.mr.orderprice;
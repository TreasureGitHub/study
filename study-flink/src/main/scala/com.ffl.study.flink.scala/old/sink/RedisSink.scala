package com.ffl.study.flink.scala.old.sink


import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.redis.RedisSink
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig
import org.apache.flink.streaming.connectors.redis.common.mapper.{RedisCommand, RedisCommandDescription, RedisMapper}



/**
  * @author lff
  * @datetime 2020/04/18 23:28
  */
object RedisSink {

    // 需求：把netcat作为数据源，并且统计每隔单词的次数，统计数据结果写入redis中
    def main(args: Array[String]): Unit = {


        //1. 初始化流计算环境
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        // 修改并行度
        env.setParallelism(1)

        // 2.导入隐式转换
        import org.apache.flink.streaming.api.scala._

        // 3.读取数据，读取socket中的数据
        val stream: DataStream[String] = env.socketTextStream("localhost", 8888)

        // 4.转换和处理数据
        val result: DataStream[(String, Int)] = stream.flatMap(_.split(" "))
          .map((_, 1)).setParallelism(2)
          .keyBy(0)   // 分组算子
          .sum(1).setParallelism(2)   // 聚合算子

        // 打印结果
        result.print("result")


        val config: FlinkJedisPoolConfig = new FlinkJedisPoolConfig.Builder().setDatabase(3).setHost("localhost").setPort(6379).build()

        // 设置redis sink
        result.addSink(new RedisSink[(String, Int)](config, new RedisMapper[(String, Int)] {

            // 设置Redis 的命令
            override def getCommandDescription: RedisCommandDescription = {
                new RedisCommandDescription(RedisCommand.HSET, "t_wc") // 类似于表名
            }

            // 从数据库中获得 Key
            override def getKeyFromData(data: (String, Int)): String = {
                data._1
            }

            // 从数据库中获得 value
            override def getValueFromData(data: (String, Int)): String = {
                data._2 + ""
            }
        }))

        env.execute("redisSink")
    }

}

package com.ffl.study.flink.scala.cep

import java.util

import org.apache.flink.cep.PatternSelectFunction
import org.apache.flink.cep.scala.{CEP, PatternStream}
import org.apache.flink.cep.scala.pattern.Pattern
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.windowing.time.Time

/**
  * @author lff
  * @datetime 2020/05/09 20:08
  */

/**
  *
  * @param id        登录日志ID
  * @param userName  userName
  * @param eventType 登录类型：失败和成功
  * @param eventTime 登录时间 精确到秒
  */
case class LoginEvent(id: Long, userName: String, eventType: String, eventTime: Long)

object TestCepByLogin {


    // 从一堆日志中,匹配一个恶意登录的模式(如果一个用户连续失败三次，则是恶意登录)，从而找到哪些用户是恶意登录
    def main(args: Array[String]): Unit = {
        val streamEnv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        streamEnv.setParallelism(1)

        import org.apache.flink.streaming.api.scala._

        streamEnv.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)

        val stream: DataStream[LoginEvent] = streamEnv.fromCollection(List(
            LoginEvent(1, "张三", "fail", 1587395110L),
            LoginEvent(2, "张三", "fail", 1587395112L),
            LoginEvent(3, "张三", "fail", 1587395115L),
            LoginEvent(4, "张三", "fail", 1587395116L),
            LoginEvent(4, "李四", "fail", 1587395117L),
            LoginEvent(5, "李四", "success", 1587395118L),
            LoginEvent(6, "李四", "fail", 1587395118L),
            LoginEvent(5, "张三", "fail", 1587395124L)
        )
        ).assignAscendingTimestamps(_.eventTime * 1000) // 确保为时间戳，精确到毫秒

        // 定义模式(Pattern)
        val pattern: Pattern[LoginEvent, LoginEvent] =
            Pattern.begin[LoginEvent]("start").where(_.eventType.equals("fail"))
              .next("fail2").where(_.eventType.equals("fail"))
              .next("fail3").where(_.eventType.equals("fail"))
              .within(Time.seconds(10)) // 时间限制，10s

        // 检测模式
        val patternSteam: PatternStream[LoginEvent] = CEP.pattern(stream.keyBy(_.userName), pattern)

        // 选择结果输出
        val result: DataStream[String] = patternSteam.select(new PatternSelectFunction[LoginEvent, String] {

            override def select(map: util.Map[String, util.List[LoginEvent]]): String = {
                val keyIter: util.Iterator[String] = map.keySet().iterator()
                val e1: LoginEvent = map.get(keyIter.next()).iterator().next()
                val e2: LoginEvent = map.get(keyIter.next()).iterator().next()
                val e3: LoginEvent = map.get(keyIter.next()).iterator().next()

                "用户名：" + e1.userName + "登录时间：" + e1.eventTime + ":" + e2.eventTime + ":" + e3.eventTime
            }
        })

        result.print()

        streamEnv.execute()
    }
}

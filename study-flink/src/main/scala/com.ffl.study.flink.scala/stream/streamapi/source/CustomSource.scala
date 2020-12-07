package com.ffl.study.flink.scala.stream.streamapi.source

import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.api.scala._

import scala.collection.immutable
import scala.util.Random

/**
  * @author lff
  * @datetime 2020/12/05 16:05
  */
object CustomSource {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val stream: DataStream[SensorReading] = env.addSource(new MySourceFunction)
        stream.print()

        env.execute("CustomSource")
    }

}

class MySourceFunction extends SourceFunction[SensorReading] {

    /**
      * 定义flag，用来表示数据源是否正常运行发出数据
      */
    var running: Boolean = true;

    override def run(ctx: SourceFunction.SourceContext[SensorReading]): Unit = {

        val rand = new Random()

        // 初始值
        var currTemp: immutable.IndexedSeq[(String, Double)] = 1.to(10).map(i => ("sensor_" + i, rand.nextDouble() * 100))

        // 无限循环不停产生数据，除非被cancel掉
        while (running) {
            currTemp = currTemp.map(data => (data._1, data._2 + rand.nextGaussian()))

            // 获取当前时间戳加入到数据中
            currTemp.foreach(data => ctx.collect(SensorReading(data._1, System.currentTimeMillis(), data._2)))

            Thread.sleep(1000);
        }
    }

    override def cancel(): Unit = running = false
}

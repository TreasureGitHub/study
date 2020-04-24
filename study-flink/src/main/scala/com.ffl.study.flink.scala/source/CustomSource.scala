package com.ffl.study.flink.scala.source

import org.apache.flink.streaming.api.functions.source.SourceFunction
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

import scala.util.Random

/**
  * @author lff
  * @datetime 2020/04/18 22:18
  *
  *           自定义source，每个2秒钟生成10条随机的基站通话数据
  */
class MyCustomSource extends SourceFunction[StationLog] {


    // 是否终止数据流
    var flag = true

    /**
      * 主要的方法，启动一个source，并且从source中读取数据
      * 如果run方法停止，则数据流终止
      *
      * @param ctx
      */
    override def run(ctx: SourceFunction.SourceContext[StationLog]): Unit = {
        val random = new Random
        var types= Array("fail","busy","success","barring")

        while (flag) {
            1.to(10).map(i => {
                // 主叫号码
                var callOut = "1860000%04d".format(random.nextInt(10000))

                // 被叫号码
                var callIn = "1890000%04d".format(random.nextInt(10000))
                // 生成数据
                new StationLog("station_" + random.nextInt(10),callOut,callIn,types(random.nextInt(4)),System.currentTimeMillis(),random.nextInt(20))

            }).foreach(ctx.collect(_))

            Thread.sleep(2000)
        }
    }

    // 终止任务
    override def cancel(): Unit = {
        flag = false
    }
}

object CustomSource {

    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        import org.apache.flink.api.scala._


        val stream: DataStream[StationLog] = env.addSource(new MyCustomSource)

        env.addSource(new MyCustomSource)

        stream.print()

        env.execute("CustomSource")

    }

}

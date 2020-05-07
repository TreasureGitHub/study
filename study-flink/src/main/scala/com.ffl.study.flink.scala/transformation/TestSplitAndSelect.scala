package com.ffl.study.flink.scala.transformation

import com.ffl.study.flink.scala.source.{MyCustomSource, StationLog}
import org.apache.flink.streaming.api.scala.{DataStream, SplitStream, StreamExecutionEnvironment}

/**
  * @author lff
  * @datetime 2020/04/20 22:17
  */
object TestSplitAndSelect {


    // 从自定义的数据流中读取基站日志，将成功的和失败的分离出来
    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(1)

        import org.apache.flink.api.scala._

        val stream: DataStream[StationLog] = env.addSource(new MyCustomSource)

        // 并没有真正切割，只是打了个标签
        val splitStream: SplitStream[StationLog] = stream.split(
            log => {
                if (log.callType.equals("success")) Seq("Success") else Seq("No Success")
            }
        )

        val stream1: DataStream[StationLog] = splitStream.select("Success")

        val stream2: DataStream[StationLog] = splitStream.select("No Success")

        stream1.print("成功")
        stream2.print("失败")

        //stream1.print()

        env.execute("TestSplitAndSelect")
    }

}

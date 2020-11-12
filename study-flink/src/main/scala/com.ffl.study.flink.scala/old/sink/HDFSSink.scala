package com.ffl.study.flink.scala.old.sink

import com.ffl.study.flink.scala.old.source.{MyCustomSource, StationLog}
import org.apache.flink.api.common.serialization.SimpleStringEncoder
import org.apache.flink.core.fs.Path
import org.apache.flink.streaming.api.functions.sink.filesystem.StreamingFileSink
import org.apache.flink.streaming.api.functions.sink.filesystem.rollingpolicies.DefaultRollingPolicy
import org.apache.flink.streaming.api.scala.{StreamExecutionEnvironment, _}

/**
  * @author lff
  * @datetime 2020/04/18 22:48
  */
object HDFSSink {

    // 需求：把自定义的source作为数据源，把基站日志数据写入HDFS并每隔两秒钟生成一个文件
    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

        env.setParallelism(1)

        import org.apache.flink.api.scala._

        val stream: DataStream[StationLog] = env.addSource(new MyCustomSource)

        // 默认一个小时一个目录(分桶)
        // 设置一个滚动策略
        val rolling: DefaultRollingPolicy[StationLog, String] = DefaultRollingPolicy
          .create()
          .withInactivityInterval(2000) // 不活动分桶时间
          .withRolloverInterval(2000)   // 生成文件的间隔时间，每隔两秒生成文件
          .build()


        //// 创建HDFS sink
        val hdfsSink: StreamingFileSink[StationLog] = StreamingFileSink.forRowFormat[StationLog](
            new Path("hdfs://localhost:9000/mysink001/"),
            new SimpleStringEncoder[StationLog]("UTF-8"))
          .withRollingPolicy(rolling)
          .build()
        //
        stream.addSink(hdfsSink)
        //
        env.execute("HDFSSink")
    }

}

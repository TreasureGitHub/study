package com.ffl.study.flink.scala.stream.streamapi.sink

import com.ffl.study.common.constants.{PathConstants, StringConstants}
import com.ffl.study.common.utils.FileUtils
import com.ffl.study.flink.scala.stream.streamapi.source.SensorReading
import org.apache.flink.streaming.api.scala._

/**
  * @author lff
  * @datetime 2020/12/05 18:23
  */
object SinkTest {

    def main(args: Array[String]): Unit = {

        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val filePath: String = PathConstants.FLINK_RES + "/sensor_input"
        val filePathOut: String = PathConstants.FLINK_RES + "/sensor_output/sensor.txt"
        val stream: DataStream[SensorReading] = env.readTextFile(filePath).map(data => {
            val arr: Array[String] = data.split(StringConstants.COMMA)
            SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
        })

        FileUtils.deleteDir(filePathOut)
        stream.print()

        // 1.写文件
        stream.writeAsCsv(filePathOut)
        //stream.addSink(
        //    StreamingFileSink.forRowFormat(
        //        new Path(filePathOut),
        //        new SimpleStringEncoder[SensorReading]()
        //    ).build()
        //)

        env.execute("SinkTest")


    }

}

package com.ffl.study.flink.scala.stream.streamapi.transformation

import com.ffl.study.common.constants.{PathConstants, StringConstants}
import com.ffl.study.flink.scala.stream.streamapi.source.SensorReading
import org.apache.flink.api.common.functions.ReduceFunction
import org.apache.flink.streaming.api.scala._

/**
  * @author lff
  * @datetime 2020/12/05 16:27
  */
object TransformTest {

    private val HIGH = "high"

    private val LOW = "low"

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)


        val filePath: String = PathConstants.FLINK_RES + "/sensor_input"
        val stream: DataStream[String] = env.readTextFile(filePath)


        // 1.max
        val stream1 = stream.map(data => {
            val arr: Array[String] = data.split(StringConstants.COMMA)
            SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
        })

        // 2.分组聚合
        //stream1.keyBy("id").max("temperature").print()
        //stream1.keyBy("id").maxBy("temperature").print()

        // 时间戳取最新值，温度取最小值
        val stream2: DataStream[SensorReading] = stream1
          .keyBy("id")
          .reduce((curState, newData) => SensorReading(curState.id, newData.timestamp, curState.temperature.min(newData.temperature)))

        //stream2.print()

        // 3.split/select
        val stream3: SplitStream[SensorReading] = stream1.split(data => {
            if (data.temperature > 30) Seq(HIGH) else Seq(LOW)
        })

        val highStream: DataStream[SensorReading] = stream3.select(HIGH)
        val lowStream: DataStream[SensorReading] = stream3.select(LOW)
        val allStream: DataStream[SensorReading] = stream3.select(HIGH, LOW)

        //highStream.print(HIGH)
        //lowStream.print(LOW)
        //allStream.print("all")

        // 4.connect
        val warningStream: DataStream[(String, Double)] = highStream.map(data => (data.id, data.temperature))
        val connectStream: ConnectedStreams[(String, Double), SensorReading] = warningStream.connect(lowStream)

        val stream4: DataStream[Product] = connectStream.map(warningData => (warningData._1, warningData._2,"high"), lowData => (lowData.id,"health"))
        //stream4.print()

        // 5.union
        val stream5: DataStream[SensorReading] = highStream.union(lowStream)

        stream5.print()

        env.execute("TransformTest")
    }

}


// 自定义函数
class MyReduceFunction extends ReduceFunction[SensorReading] {

    override def reduce(value1: SensorReading, value2: SensorReading): SensorReading = {
        SensorReading(value1.id, value2.timestamp, value1.temperature.min(value2.temperature))
    }
}

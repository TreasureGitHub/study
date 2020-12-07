package com.ffl.study.flink.scala.stream.streamapi.function

import com.ffl.study.common.constants.{PathConstants, StringConstants}
import com.ffl.study.flink.scala.stream.streamapi.source.SensorReading
import org.apache.flink.api.common.functions.{FilterFunction, IterationRuntimeContext, RichMapFunction, RuntimeContext}
import org.apache.flink.configuration.Configuration
import org.apache.flink.streaming.api.scala._

/**
  * @author lff
  * @datetime 2020/12/05 17:57
  */
object FunctionTest {

    def main(args: Array[String]): Unit = {
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val filePath: String = PathConstants.FLINK_RES + "/sensor_input"
        val stream: DataStream[SensorReading] = env.readTextFile(filePath).map(data => {
            val arr: Array[String] = data.split(StringConstants.COMMA)
            SensorReading(arr(0), arr(1).toLong, arr(2).toDouble)
        })

        // 1.函数类
        //1.1 定义函数类 继承 FilterFunction
        val stream1: DataStream[SensorReading] = stream.filter(new MyFilter)
        stream1.print()

        // 1.2 匿名函数 内部类
        val stream2: DataStream[SensorReading] = stream.filter(new FilterFunction[SensorReading] {
            override def filter(value: SensorReading): Boolean = value.id.startsWith("sensor_1")
        })
        //stream2.print()

        // 2.lambda表达式
        val stream3: DataStream[SensorReading] = stream.filter(_.id.startsWith("sensor_1"))
        //stream3.print()



        env.execute("FilterFuncTest")
    }

}


class MyFilter extends FilterFunction[SensorReading] {

    override def filter(value: SensorReading): Boolean = value.id.startsWith("sensor_1")

}

// 可以获取到运行时上下文
class MyRichMap extends RichMapFunction[SensorReading,String] {

    override def setRuntimeContext(t: RuntimeContext): Unit = super.setRuntimeContext(t)

    override def getRuntimeContext: RuntimeContext = super.getRuntimeContext

    override def getIterationRuntimeContext: IterationRuntimeContext = super.getIterationRuntimeContext

    // 做初始化操作，比如数据库连接
    override def open(parameters: Configuration): Unit = super.open(parameters)

    // 做收尾工作，比如关闭数据库连接或者清空状态
    override def close(): Unit = super.close()

    override def map(value: SensorReading): String = ???
}
package com.ffl.study.flink.scala.old.tableAndSql

import org.apache.flink.api.common.typeinfo.TypeInformation
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.table.api.{EnvironmentSettings, Table, Types}
import org.apache.flink.table.api.scala.StreamTableEnvironment
import org.apache.flink.table.functions.TableFunction
import org.apache.flink.types.Row

/**
  * @author lff
  * @datetime 2020/05/07 14:15
  */
object TestUDFByWordCount {

    // 使用table api 实现 word count
    def main(args: Array[String]): Unit = {
        val streamEnv: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        val settings: EnvironmentSettings = EnvironmentSettings.newInstance().useOldPlanner().inStreamingMode().build()
        val tableEnv: StreamTableEnvironment = StreamTableEnvironment.create(streamEnv, settings)

        import org.apache.flink.api.scala._
        import org.apache.flink.table.api.scala._

        // 读取数据源
        val stream: DataStream[String] = streamEnv.socketTextStream("localhost", 8888)

        val table: Table = tableEnv.fromDataStream(stream,'line)

        //使用tableApi切割单词
        val my_func = new MyFlatMapFunction // 创建一个udf
        val result: Table = table.flatMap(my_func('line)).as('word, 'cnt)
          .groupBy('word)
          .select('word, 'cnt.sum as 'cnt)

        tableEnv.toRetractStream[Row](result).filter(_._1== true).print()

        tableEnv.execute("tableApi")
    }

    class MyFlatMapFunction extends TableFunction[Row] {

        //定义函数处理之后的返回类型，输出单词和1
        override def getResultType: TypeInformation[Row] = Types.ROW(Types.STRING(), Types.INT())

        def eval(str: String): Unit = {
            str.trim
              .split(" ")
              .foreach(word => {
                  val row = new Row(2)
                  row.setField(0, word)
                  row.setField(1, 1)
                  collect(row)
              })

        }
    }

}

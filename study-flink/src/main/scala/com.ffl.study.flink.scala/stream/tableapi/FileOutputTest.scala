package com.ffl.study.flink.scala.stream.tableapi

import com.ffl.study.common.constants.PathConstants
import com.ffl.study.common.utils.FileUtils
import org.apache.flink.streaming.api.scala._
import org.apache.flink.table.api.{DataTypes, Table}
import org.apache.flink.table.api.scala._
import org.apache.flink.table.descriptors.{Csv, FileSystem, Schema}

/**
  * @author lff
  * @datetime 2020/12/06 20:07
  */
object FileOutputTest {

    def main(args: Array[String]): Unit = {
        // 1.创建环境变量
        val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment
        env.setParallelism(1)

        val tableEnv: StreamTableEnvironment = StreamTableEnvironment.create(env)

        val filePath: String = PathConstants.FLINK_RES + "/sensor_input"

        tableEnv.connect(new FileSystem().path(filePath))
          //.withFormat(new OldCsv) // 老版本
          .withFormat(new Csv) // 新版本
          .withSchema(new Schema()
          .field("id", DataTypes.STRING())
          .field("timestamp", DataTypes.BIGINT())
          .field("temp", DataTypes.DOUBLE()))
          .createTemporaryTable("inputTable")


        // 3.1简单转换
        val sensorTable: Table = tableEnv.from("inputTable")
        val resultTable: Table = sensorTable.select('id, 'temp)
          .filter('id === "sensor_1")

        // 3.2聚合转换
        val aggTable: Table = sensorTable
          .groupBy('id) // 基于ID分组
          .select('id, 'id.count as 'count)

        resultTable.toAppendStream[(String,Double)].print("resultTable")
        aggTable.toRetractStream[(String,Long)].print("agg")

        // 4. 输出到文件
        val outfilePath: String = PathConstants.FLINK_RES + "/sensor_output/test.txt"

        FileUtils.deleteDir(PathConstants.FLINK_RES + "/sensor_output")
        tableEnv.connect(new FileSystem().path(outfilePath))
          //.withFormat(new OldCsv) // 老版本
          .withFormat(new Csv) // 新版本
          .withSchema(new Schema()
          .field("id", DataTypes.STRING())
          //.field("timestamp", DataTypes.BIGINT())
          .field("temp", DataTypes.DOUBLE()))
          .createTemporaryTable("outputTable")

        resultTable.insertInto("outputTable")

        env.execute("FileOutputTest")
    }

}

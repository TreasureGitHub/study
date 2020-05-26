package com.ffl.study.spark.scala.input

import java.sql.{Connection, DriverManager, PreparedStatement}

import org.apache.spark.rdd.{JdbcRDD, RDD}
import org.apache.spark.{SparkConf, SparkContext}


/**
  * @author lff
  * @datetime 2020/05/24 23:16
  */

object MySqlInput {

    def main(args: Array[String]): Unit = {

        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("MySqlInput")

        // 创建上下文
        val sc = new SparkContext(config)

        val driver = "com.mysql.jdbc.Driver"
        val url = "jdbc:mysql://localhost:3306/test"
        val userName = "test"
        val pwd = "test"

        val sql = "select name,age from user where id >= ? and id <= ?"

        // 创建JDBC rdd 访问数据库
        val jdbcRdd = new JdbcRDD(sc,
            () => {
                // 获取数据连接对象
                Class.forName(driver)
                DriverManager.getConnection(url, userName, pwd)
            },
            sql,
            1,
            3,
            2,
            (rs) => {
                println(rs.getString(1) + ", " + rs.getInt(2))
            }
        )

        jdbcRdd.collect()

        // 保存数据
        val dataRDD: RDD[(String, Int)] = sc.makeRDD(List(("张三", 20), ("李四", 34), ("王五", 23)))

        dataRDD.foreachPartition(datas => {
            Class.forName(driver)
            val connection: Connection = DriverManager.getConnection(url, userName, pwd)
            datas.foreach {
                case (userName, age) => {

                    val sql = "insert into user(name,age) values (?,?)"
                    val statement: PreparedStatement = connection.prepareStatement(sql)
                    statement.setString(1, userName)
                    statement.setInt(2, age)

                    statement.execute()

                    statement.close()
                }
            }
            connection.close()
        })

        sc.stop()

    }

}

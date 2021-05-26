package com.ffl.study.flink12.flinksql

/**
  * @author lff
  * @datetime 2021/05/08 23:52
  */
object Ddl {

    val CREATE_DATABASE: String =
        s"""
           |create database if not exists db
        """.stripMargin


    val SOURCE_TABLE: String =
        s"""
           |create table db.mytest (
           |    name  varchar
           |  , sex   varchar
           |  , age   bigint
           |) with (
           |    'connector' = 'kafka'
           |   , 'topic' = 'mytest'
           |   , 'properties.bootstrap.servers' = 'localhost:9092'
           |   , 'properties.group.id' = 'testGroup'
           |   , 'scan.startup.mode' = 'earliest-offset'
           |   , 'format' = 'json'
           |)
         """.stripMargin


    val INSERT_SQL: String =
        s"""
           |select t.name
           |     , t.sex
           |     , t.age
           |     , t1.age as age1
              from (
           |select name
           |     , sex
           |     , age
           |     , row_number() over(partition by name order by age desc) rn
           |  from db.mytest
           |  )t
           |  left join (
           |select name
           |     , sex
           |     , age
           |     , row_number() over(partition by name order by age desc) rn
           |  from db.mytest
           |  )t1
           | on t.name = t1.name
           | and t1.rn = 1
           |
           | where t.rn = 1
         """.stripMargin

}

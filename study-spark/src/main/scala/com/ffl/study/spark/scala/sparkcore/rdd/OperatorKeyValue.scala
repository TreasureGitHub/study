package com.ffl.study.spark.scala.sparkcore.rdd

import com.ffl.study.common.constants.StringConstants
import org.apache.spark.rdd.RDD
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}

/**
  * @author lff
  * @datetime 2020/11/28 11:12
  */
object OperatorKeyValue {

    def main(args: Array[String]): Unit = {


        val config: SparkConf = new SparkConf().setMaster("local[*]").setAppName("Operator2Value")

        val sc = new SparkContext(config)

        val rdd1: RDD[(Int, String)] = sc.makeRDD(Array((1, "aaa"), (2, "bbb"), (3, "ccc"), (4, "ddd")), 4)

        // 1.partitionBy
        //对 pairRDD 进行分区操作，如果原有的 partionRDD 和现有的 partionRDD 是一致 的话就不进行分区，
        // 否则会生成 ShuffleRDD，即会产生 shuffle 过程。
        val rdd2: RDD[(Int, String)] = rdd1.partitionBy(new HashPartitioner(2))

        rdd2.glom().collect().foreach(x => println(x.mkString(StringConstants.TAB)))

        println("------------------------partitionBy------------------------")

        // 2.reduceByKey
        // 在一个(K,V)的 RDD 上调用，返回一个(K,V)的 RDD，使用指定的 reduce 函数，将相同 key 的值聚合到一起，reduce 任务的个数可以通过第二个可选的参数来设置
        val rdd3 = sc.makeRDD(List(("female", 1), ("male", 5), ("female", 5), ("male", 2)), 2)

        val rdd4: RDD[(String, Int)] = rdd3.reduceByKey(_ + _)

        rdd4.glom().collect().foreach(x => println(x.mkString(StringConstants.TAB)))

        println("------------------------reduceByKey------------------------")


        // 3.groupByKey
        // groupByKey 也是对每个 key 进行操作，但只生成一个 seq
        val rdd5: RDD[(String, Int)] = sc.makeRDD(List("one", "two", "two", "three", "three", "three")).map(word => (word, 1))
        val rdd6: RDD[(String, Iterable[Int])] = rdd5.groupByKey(new HashPartitioner(2))
        rdd6.glom().collect().foreach(x => println(x.mkString(StringConstants.TAB)))

        // 需要额外进行计算
        rdd6.map(t => (t._1, t._2.sum)).collect().foreach(println)

        println("------------------------groupByKey------------------------")


        //4.aggregateByKey
        //作用:在 kv 对的 RDD 中，，按 key 将 value 进行分组合并，合并时，将每个 value 和初 始值作为 seq 函数的参数，
        // 进行计算，返回的结果作为一个新的 kv 对，然后再将结果按照 key 进行合并，
        // 最后将每个分组的 value 传递给 combine 函数进行计算(先将前两个 value进行计算，将返回结果和下一个 value 传给 combine 函数，以此类推)，
        // 将 key 与计算结果 作为一个新的 kv 对输出。

        val rdd7: RDD[(String, Int)] = sc.makeRDD(List(("a", 3), ("a", 2), ("c", 4), ("b", 3), ("c", 6), ("c", 8)), 2)
        val rdd8: RDD[(String, Int)] = rdd7.aggregateByKey(0)(Math.max(_, _), _ + _)

        rdd8.glom().collect().foreach(x => println(x.mkString(StringConstants.TAB)))

        println("------------------------aggregateByKey------------------------")


        //5.foldByKey
        //aggregateByKey 的简化操作，seqop 和 combop 相同

        val rdd9: RDD[(Int, Int)] = sc.makeRDD(List((1, 3), (1, 2), (1, 4), (2, 3), (3, 6), (3, 8)), 3)
        val rdd10: RDD[(Int, Int)] = rdd9.foldByKey(0)(_ + _)

        rdd10.glom().collect().foreach(x => println(x.mkString(StringConstants.TAB)))

        println("------------------------foldByKey------------------------")

        //6.combineByKey
        // 针对相同 K，将 V 合并成一个集合
        // 求平均值，先
        val rdd11: RDD[(String, Int)] = sc.parallelize(Array(("a", 88), ("b", 95), ("a", 91), ("b", 93), ("a", 95), ("b", 98)), 2)
        val rdd12 = rdd11.combineByKey((_,1), // 初始化累加器
            (acc:(Int,Int),v)=>(acc._1+v,acc._2+1),  // 局部聚合
            (acc1:(Int,Int),acc2:(Int,Int))=> (acc1._1+acc2._1,acc1._2+acc2._2)) // 全局聚合

        val rdd13: RDD[(String, Double)] = rdd12.map{case (key,value) => (key,value._1/value._2.toDouble)}

        rdd13.glom().collect().foreach(x => println(x.mkString(StringConstants.TAB)))
        println("------------------------combineByKey------------------------")


        //7.sortByKey
        val rdd14: RDD[(Int, String)] = sc.makeRDD(Array((3,"aa"),(6,"cc"),(2,"bb"),(1,"dd")),2)
        val rdd15: RDD[(Int, String)] = rdd14.sortByKey(true)
        rdd15.glom().collect().foreach(x => println(x.mkString(StringConstants.TAB)))

        rdd14.sortBy(x => x._1,true).glom().collect().foreach(x => println(x.mkString(StringConstants.TAB)))
        rdd14.sortBy(x => x._2,true).glom().collect().foreach(x => println(x.mkString(StringConstants.TAB)))

        println("------------------------sortByKey------------------------")

        //8.mapValues
        val rdd16: RDD[(Int, String)] = sc.makeRDD(Array((1,"a"),(1,"d"),(2,"b"),(3,"c")))
        val values: RDD[(Int, String)] = rdd16.mapValues(_ + "|||")
        values.collect().foreach(println)

        println("------------------------mapValues------------------------")

        //9.join
        val joinRDD1: RDD[(Int, String)] = sc.makeRDD(Array((1,"a"),(2,"b"),(3,"c")))
        val joinRDD2: RDD[(Int, Int)] = sc.makeRDD(Array((1,4),(2,5),(3,6),(3,10)))

        val joinRDD3: RDD[(Int, (String, Int))] = joinRDD1.join(joinRDD2)

        joinRDD3.collect().foreach(println)

        println("------------------------join------------------------")

        //10.cogroup
        val groupRDD: RDD[(Int, (Iterable[String], Iterable[Int]))] = joinRDD1.cogroup(joinRDD2)
        groupRDD.collect().foreach(println)

        println("------------------------cogroup------------------------")

        sc.stop
    }
}

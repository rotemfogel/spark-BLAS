package me.rotemfo

import java.util.concurrent.ThreadLocalRandom

import org.apache.spark.ml.linalg.DenseMatrix
import org.apache.spark.sql.SparkSession

import scala.collection.mutable

/**
  * project: spark-blas-test
  * package: me.rotemfo
  * file:    SparkBLASApp
  * created: 2019-01-09
  * author:  rotem
  */
object SparkBLASApp {
  private def genDouble(n: Int = 100): Array[Double] = {
    require(n > 1, "n must be greater than 1")
    val list = mutable.Buffer[Double]()
    for (i <- 0 to n-1)
      list += ThreadLocalRandom.current().nextDouble(40, 50)
    list.toArray
  }

  def main(args: Array[String]): Unit = {
    val spark = SparkSession
      .builder()
      .appName("blas-test")
      .master("local[*]")
      .config("spark.executor.cores", 4)
      .config("spark.executor.memory", "8g")
      .getOrCreate()

    val d1 = genDouble()

    val m1: DenseMatrix = new DenseMatrix(1, d1.length, d1)
    val m2 = m1.transpose
    val m3 = m1.multiply(m2)
    spark.stop()
  }
}

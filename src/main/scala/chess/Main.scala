package chess

import scala.util.control.Exception

object Main {
  def main(args: Array[String]) {
    val start = System.currentTimeMillis()
    try {
      println("Overall size is: " + new SafePositions(7, 7, 2, 2, 2, 2, 1).size)
    } catch {
      case e: Exception => e.printStackTrace 
    }
    val end = System.currentTimeMillis()
    val timeInMs = end - start
    println("Time: " + (timeInMs / 1000) + "s")
  }
}
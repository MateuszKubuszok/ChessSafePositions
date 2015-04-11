package chess

import scopt.OptionParser

object Main {
  case class ParserConfig(
    n: Int = 0,
    m: Int = 0,
    kings: Int = 0,
    knights: Int = 0,
    rooks: Int = 0,
    bishops: Int = 0,
    queens: Int = 0,
    sizeOnly: Boolean = false,
    showTime: Boolean = false)

  def parser: OptionParser[ParserConfig] = {
    new OptionParser[ParserConfig]("Chess Safe Positions") {
      head("Chess safe positions")
      opt[Int]('n', "height") required () action
        { (v, c) => c.copy(n = v) } text
        "board height" validate
        { x => if (x > 0) success else failure("Height must be positive") }
      opt[Int]('m', "width") required () action
        { (v, c) => c.copy(m = v) } text
        "board width" validate
        { x => if (x > 0) success else failure("Width must be positive") }
      opt[Int]('K', "kings") optional () action { (v, c) => c.copy(kings = v) } text
        "kings number" validate
        { x => if (x >= 0) success else failure("Kings number must be non-negative") }
      opt[Int]('N', "knights") optional () action
        { (v, c) => c.copy(knights = v) } text
        "knights number" validate
        { x => if (x >= 0) success else failure("Knights number must be non-negative") }
      opt[Int]('R', "rooks") optional () action
        { (v, c) => c.copy(rooks = v) } text
        "rooks number" validate
        { x => if (x >= 0) success else failure("Rooks number must be non-negative") }
      opt[Int]('B', "bishops") optional () action
        { (v, c) => c.copy(bishops = v) } text
        "bishops number" validate
        { x => if (x >= 0) success else failure("Bishops number must be non-negative") }
      opt[Int]('Q', "queens") optional () action
        { (v, c) => c.copy(queens = v) } text
        "queens number" validate
        { x => if (x >= 0) success else failure("Queens number must be non-negative") }
      opt[Unit]('s', "size-only") optional () action
        { (_, c) => c.copy(sizeOnly = true) } text
        "display solution size only"
      opt[Unit]('t', "show-time") optional () action
        { (_, c) => c.copy(showTime = true) } text
        "show execution time"
      help("help") text "prints this useful test"
    }
  }

  def showSolutions(safePositions: SafePositions) = 
    for (positions <- safePositions.solution)
      println(safePositions.formatPositions(positions))

  def showSolutionSize(safePositions: SafePositions) =
    println("Solution size: " + safePositions.size)

  def calculateSolution(safePositions: SafePositions, sizeOnly: Boolean, showTime: Boolean) {
    val startTime = System.currentTimeMillis()

    if (sizeOnly) showSolutionSize(safePositions) else showSolutions(safePositions)

    val endTime = System.currentTimeMillis()
    val timeInMs = endTime - startTime

    if (showTime) println("Time: " + (timeInMs.toFloat / 1000.0) + "s")
  }

  def main(args: Array[String]) {
    val arguments = parser.parse(args, new ParserConfig)
    arguments match {
      case Some(ParserConfig(n, m, kings, knights, rooks, bishops, queens, sizeOnly, showTime)) =>
        calculateSolution(new SafePositions(n, m, kings, knights, rooks, bishops, queens), sizeOnly, showTime)

      case None =>
    }
  }
}
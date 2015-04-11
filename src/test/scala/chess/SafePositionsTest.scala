package chess

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SafePositionsTests extends FunSuite {
  class SafePositionsTest(n: Int, m: Int, kings: Int, knights: Int, rooks: Int, bishops: Int, queens: Int)
    extends SafePositions(n, m, kings, knights, rooks, bishops, queens) {
    def boardSize: Int = board.size

    def piecesSize: Int = piecesLeft.size

    def validateSolution: Boolean = {
      val requiredSafePieces = kings + knights + rooks + bishops + queens
      lazy val combinationsMatchingRequirements = solution.filter(_.size == requiredSafePieces)
      combinationsMatchingRequirements.size == size
    }
  }

  test("On 2x2 board sole King can occupy any of 4 positions") {
    new SafePositionsTest(2, 2, 1, 0, 0, 0, 0) {
      assert(boardSize == 4)
      assert(piecesSize == 1)
      assert(size == 4)
      assert(validateSolution)
    }
  }

  test("On 2x2 board 5 King have 0 possible positions combinations") {
    new SafePositionsTest(2, 2, 5, 0, 0, 0, 0) {
      assert(boardSize == 4)
      assert(piecesSize == 5)
      assert(size == 0)
      assert(validateSolution)
    }
  }

  test("On 2x2 board 2 Knights can occupy one of 6 position combinations") {
    new SafePositionsTest(2, 2, 0, 2, 0, 0, 0) {
      assert(boardSize == 4)
      assert(piecesSize == 2)
      assert(size == 6)
      assert(validateSolution)
    }
  }

  test("On 3x3 board 2 Kings and 1 Rook can occupy one of 4 position combinations") {
    new SafePositionsTest(3, 3, 2, 0, 1, 0, 0) {
      assert(boardSize == 9)
      assert(piecesSize == 3)
      assert(size == 4)
      assert(validateSolution)
    }
  }

  test("On 4x4 board 2 Rooks and 4 Knights can occupy one of 8 position combinations") {
    new SafePositionsTest(4, 4, 0, 4, 2, 0, 0) {
      assert(boardSize == 16)
      assert(piecesSize == 6)
      assert(size == 8)
      assert(validateSolution)
    }
  }
}

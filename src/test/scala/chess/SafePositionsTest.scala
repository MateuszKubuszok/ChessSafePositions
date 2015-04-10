package chess

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class SafePositionsTests extends FunSuite {
  test("On 2x2 board sole King can occupy any of 4 positions") {
    new SafePositions(2, 2, 1, 0, 0, 0, 0) {
      assert(board.length == 4)
      assert(solution.length == 4)
    }
  }
  
  test("On 2x2 board 2 Knights can occupy one of 6 position combinations") {
    new SafePositions(2, 2, 0, 2, 0, 0, 0) {
      assert(board.length == 4)
      assert(solution.length == 6)
    }
  }
}

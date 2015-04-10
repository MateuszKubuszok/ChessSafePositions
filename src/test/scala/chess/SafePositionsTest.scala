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
  
  test("On 2x2 board 5 King have 0 possible positions combinations") {
    new SafePositions(2, 2, 5, 0, 0, 0, 0) {
      assert(board.length == 4)
      assert(solution.length == 0)
    }
  }
  
  test("On 2x2 board 2 Knights can occupy one of 6 position combinations") {
    new SafePositions(2, 2, 0, 2, 0, 0, 0) {
      assert(board.length == 4)
      assert(solution.length == 6)
    }
  }
  
  test("On 7x7 board 2 Kings, 2 Queens, 2 Bishops and 1 Knights can occupy one of 357 position combinations") {
    new SafePositions(7, 7, 2, 2, 2, 2, 1) {
      assert(board.length == 49)
      assert(solution.length == 357)
    }
  }
}

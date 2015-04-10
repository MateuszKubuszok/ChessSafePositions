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
      assert(validateSolution)
    }
  }
  
  test("On 2x2 board 5 King have 0 possible positions combinations") {
    new SafePositions(2, 2, 5, 0, 0, 0, 0) {
      assert(board.length == 4)
      assert(solution.length == 0)
      assert(validateSolution)
    }
  }
  
  test("On 2x2 board 2 Knights can occupy one of 6 position combinations") {
    new SafePositions(2, 2, 0, 2, 0, 0, 0) {
      assert(board.length == 4)
      assert(solution.length == 6)
      assert(validateSolution)
    }
  }
  
  test("On 3x3 board 2 Kings and 1 Rook can occupy one of 4 position combinations") {
    new SafePositions(3, 3, 2, 0, 1, 0, 0) {
      assert(board.length == 9)
      assert(solution.length == 4)
      assert(validateSolution)
    }
  }
  
  test("On 4x4 board 2 Rooks and 4 Knights can occupy one of 8 position combinations") {
    new SafePositions(4, 4, 0, 4, 2, 0, 0) {
      assert(board.length == 16)
      assert(solution.length == 8)
      assert(validateSolution)
    }
  }
  
//  test("On 7x7 board 2 Kings, 2 Queens, 2 Bishops and 1 Knights can occupy one of 357 position combinations") {
//    new SafePositions(7, 7, 2, 2, 2, 2, 1) {
//      assert(board.length == 49)
//      assert(solution.length == 357) // I'm not sure of this result, but I had to put something here to run the test. 
//      assert(validateSolution)       // Then I copied result to remember what I got here. 
//    }
//  }
}

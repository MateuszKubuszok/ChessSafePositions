package chess

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

@RunWith(classOf[JUnitRunner])
class PiecesTests extends FunSuite {
  val position5x5y = new Position(5, 5)

  class AttackTest(piece: Piece) {
    def pos(x: Int, y: Int): Position = new Position(x, y)

    def isAttackingAll(positions: List[Position]): Boolean =
      positions.find(!piece.isAttacking(_)).isEmpty

    def isAttackingNone(positions: List[Position]): Boolean =
      positions.find(piece.isAttacking(_)).isEmpty
  }

  test("King attack only neighbours") {
    new AttackTest(new King(position5x5y)) {
      val attacking = List(pos(5, 4), pos(4, 5), pos(4, 4))
      val notAttacking = List(pos(5, 3), pos(3, 5), pos(3, 3))
      assert(isAttackingAll(attacking))
      assert(isAttackingNone(notAttacking))
    }
  }

  test("Knight attack only fields with abs diff (2,1) and (1,2)") {
    new AttackTest(new Knight(position5x5y)) {
      val attacking = List(pos(3, 4), pos(4, 3), pos(4, 7), pos(7, 4))
      val notAttacking = List(pos(5, 4), pos(4, 5), pos(4, 4))
      assert(isAttackingAll(attacking))
      assert(isAttackingNone(notAttacking))
    }
  }

  test("Rook attack only fields with abs diff (0,_) and (_,0)") {
    new AttackTest(new Rook(position5x5y)) {
      val attacking = List(pos(3, 5), pos(2, 5), pos(5, 3), pos(5, 2))
      val notAttacking = List(pos(4, 4), pos(3, 3), pos(2, 2))
      assert(isAttackingAll(attacking))
      assert(isAttackingNone(notAttacking))
    }
  }

  test("Bishop attack only fields with abs diff where diff.x == diff.y") {
    new AttackTest(new Bishop(position5x5y)) {
      val attacking = List(pos(3, 3), pos(2, 2), pos(6, 6), pos(4, 6))
      val notAttacking = List(pos(5, 4), pos(4, 5), pos(6, 5))
      assert(isAttackingAll(attacking))
      assert(isAttackingNone(notAttacking))
    }
  }

  test("Queen attack only fields which either Bishop or Rook would attack") {
    new AttackTest(new Queen(position5x5y)) {
      val attacking = List(pos(3, 5), pos(2, 5), pos(5, 3), pos(5, 2), pos(3, 3), pos(2, 2), pos(6, 6), pos(4, 6))
      val notAttacking = List(pos(3, 4), pos(4, 3), pos(4, 7), pos(7, 4))
      assert(isAttackingAll(attacking))
      assert(isAttackingNone(notAttacking))
    }
  }
}
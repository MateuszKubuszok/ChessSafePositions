package chess

case class Position(x: Int, y: Int) {
  def xDiff(position: Position): Int = (x - position.x).abs
    
  def yDiff(position: Position): Int = (y - position.y).abs
}

abstract class Piece(private val occupies: Position) {
  def isAttacking(position: Position): Boolean
  
  def isAttackingOrOccupies(position: Position): Boolean =
    occupies == position || isAttacking(position)
    
  def isConflictedWith(piece: Piece): Boolean =
    isAttackingOrOccupies(piece.occupies)
}

class King(occupies: Position) extends Piece(occupies) {
  def isAttacking(position: Position): Boolean =
    occupies.xDiff(position) <= 1 && occupies.yDiff(position) <= 1
}

class Knight(occupies: Position) extends Piece(occupies) {
  def isAttacking(position: Position): Boolean =
    (occupies.xDiff(position) == 1 && occupies.yDiff(position) == 2) ||
    (occupies.xDiff(position) == 2 && occupies.yDiff(position) == 1)
}

trait RookLike {
  def isAttackingLikeRook(occupies: Position, position: Position): Boolean =
    occupies.xDiff(position) == 0 || occupies.yDiff(position) == 0
}

class Rook(occupies: Position) extends Piece(occupies) with RookLike {
  def isAttacking(position: Position): Boolean =
    isAttackingLikeRook(occupies, position)
}

trait BishopLike {
  def isAttackingLikeBishop(occupies: Position, position: Position): Boolean =
    occupies.xDiff(position) == occupies.yDiff(position)
}

class Bishop(occupies: Position) extends Piece(occupies) with BishopLike {
  def isAttacking(position: Position): Boolean =
    isAttackingLikeBishop(occupies, position)
}

class Queen(occupies: Position) extends Piece(occupies) with RookLike with BishopLike {
  def isAttacking(position: Position): Boolean =
    isAttackingLikeRook(occupies, position) || isAttackingLikeBishop(occupies, position)
}
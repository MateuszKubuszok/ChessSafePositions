package chess

case class Position(x: Int, y: Int) {
  def xDist(position: Position): Int = (x - position.x).abs
    
  def yDist(position: Position): Int = (y - position.y).abs
}

abstract class Piece(private val occupies: Position) {
  def isAttacking(position: Position): Boolean
  
  def isAttackingOrOccupies(position: Position): Boolean =
    occupies == position || isAttacking(position)
    
  def isAttackingOrOccupies(piece: Piece): Boolean =
    isAttackingOrOccupies(piece.occupies)
}

class King(occupies: Position) extends Piece(occupies) {
  def isAttacking(position: Position): Boolean =
    occupies.xDist(position) <= 1 && occupies.yDist(position) <= 1
}

class Knight(occupies: Position) extends Piece(occupies) {
  def isAttacking(position: Position): Boolean =
    (occupies.xDist(position) == 1 && occupies.yDist(position) == 2) ||
    (occupies.xDist(position) == 2 && occupies.yDist(position) == 1)
}

trait RookLike {
  def isAttackingLikeRook(occupies: Position, position: Position): Boolean =
    occupies.xDist(position) == 0 || occupies.yDist(position) == 0
}

class Rook(occupies: Position) extends Piece(occupies) with RookLike {
  def isAttacking(position: Position): Boolean =
    isAttackingLikeRook(occupies, position)
}

trait BishopLike {
  def isAttackingLikeBishop(occupies: Position, position: Position): Boolean =
    occupies.xDist(position) == occupies.yDist(position)
}

class Bishop(occupies: Position) extends Piece(occupies) with BishopLike {
  def isAttacking(position: Position): Boolean =
    isAttackingLikeBishop(occupies, position)
}

class Queen(occupies: Position) extends Piece(occupies) with RookLike with BishopLike {
  def isAttacking(position: Position): Boolean =
    isAttackingLikeRook(occupies, position) || isAttackingLikeBishop(occupies, position)
}
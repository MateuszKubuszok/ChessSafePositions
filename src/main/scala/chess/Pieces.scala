package chess

case class Position(x: Int, y: Int) extends Ordered[Position] {
  def xDist(that: Position): Int = (x - that.x).abs
    
  def yDist(that: Position): Int = (y - that.y).abs
  
  def compare(that: Position) = if (x != that.x) x.compare(that.x) else y.compare(that.y)
  
  override def toString(): String = "[" + x + ',' + y + ']'
}

abstract class Piece(private val occupies: Position, private val name: String) extends Ordered[Piece] {
  def isAttacking(position: Position): Boolean
  
  def isAttackingOrOccupies(position: Position): Boolean =
    occupies == position || isAttacking(position)
    
  def isAttackingOrOccupies(piece: Piece): Boolean =
    isAttackingOrOccupies(piece.occupies)
  
  def compare(that: Piece) = occupies.compare(that.occupies)
  
  def isSameTypeAs(that: Piece) = name.equals(that.name)
  
  def position = occupies
    
  override def toString(): String = name + occupies
}

class King(occupies: Position) extends Piece(occupies, "King") {
  def isAttacking(position: Position): Boolean =
    occupies.xDist(position) <= 1 && occupies.yDist(position) <= 1
}

class Knight(occupies: Position) extends Piece(occupies, "Knight") {
  def isAttacking(position: Position): Boolean =
    (occupies.xDist(position) == 1 && occupies.yDist(position) == 2) ||
    (occupies.xDist(position) == 2 && occupies.yDist(position) == 1)
}

trait RookLike {
  def isAttackingLikeRook(occupies: Position, position: Position): Boolean =
    occupies.xDist(position) == 0 || occupies.yDist(position) == 0
}

class Rook(occupies: Position) extends Piece(occupies, "Rook") with RookLike {
  def isAttacking(position: Position): Boolean =
    isAttackingLikeRook(occupies, position)
}

trait BishopLike {
  def isAttackingLikeBishop(occupies: Position, position: Position): Boolean =
    occupies.xDist(position) == occupies.yDist(position)
}

class Bishop(occupies: Position) extends Piece(occupies, "Bishop") with BishopLike {
  def isAttacking(position: Position): Boolean =
    isAttackingLikeBishop(occupies, position)
}

class Queen(occupies: Position) extends Piece(occupies, "Queen") with RookLike with BishopLike {
  def isAttacking(position: Position): Boolean =
    isAttackingLikeRook(occupies, position) || isAttackingLikeBishop(occupies, position)
}
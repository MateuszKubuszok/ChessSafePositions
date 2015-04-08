package chess

import scala.collection.immutable.List

object SafePositions {
  type PieceFactory = Position => Piece
  
  def makeFactories(kings: Int, knights: Int, rooks: Int, bishops: Int, queens: Int): List[PieceFactory] =
    List.fill(kings)(new King(_)) :::
    List.fill(knights)(new Knight(_)) :::
    List.fill(rooks)(new Rook(_)) :::
    List.fill(bishops)(new Bishop(_)) :::
    List.fill(queens)(new Queen(_))
    
  def safeForCurrentSetup(pieces: Set[Piece], allowedPositions: Stream[Position], pieceFactory: PieceFactory): Stream[Piece] = {
    def isAllowed(piece: Piece): Boolean = !pieces.exists(piece.isConflictedWith(_))
    
    allowedPositions.map(pieceFactory).filter(isAllowed)
  }
}
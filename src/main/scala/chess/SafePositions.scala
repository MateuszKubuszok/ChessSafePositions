package chess

import scala.collection.immutable.List

object SafePositions {
  type PieceFactory = Position => Piece
  type SafePieces = Set[Piece]
  type AllowedPositions = Stream[Position]
  type PiecesLeft = Stream[PieceFactory]
  
  def makeFactories(kings: Int, knights: Int, rooks: Int, bishops: Int, queens: Int): PiecesLeft =
    Stream.fill[PieceFactory](kings)(new King(_)) #:::
    Stream.fill[PieceFactory](knights)(new Knight(_)) #:::
    Stream.fill[PieceFactory](rooks)(new Rook(_)) #:::
    Stream.fill[PieceFactory](bishops)(new Bishop(_)) #:::
    Stream.fill[PieceFactory](queens)(new Queen(_))
    
  def safePositionsForNextPiece(pieceFactory: PieceFactory)(piecesPositionsPair: (SafePieces, AllowedPositions)): Stream[(SafePieces, AllowedPositions)] = {
    val safePieces = piecesPositionsPair._1
    val allowedPositions = piecesPositionsPair._2
    
    def isAllowed(piece: Piece): Boolean = !safePieces.exists(piece.isAttackingOrOccupies)
    def pieceToPartial(piece: Piece): (SafePieces, AllowedPositions) =
      (safePieces + piece, allowedPositions.filterNot(piece.isAttackingOrOccupies))
    
    allowedPositions.map(pieceFactory).filter(isAllowed).map(pieceToPartial)
  }
  
  def addNextPieceToPartial(partial: Stream[(SafePieces, AllowedPositions)], piecesLeft: PiecesLeft): Stream[SafePieces] =
    if (piecesLeft.isEmpty) partial.map(_._1)
    else Stream() #::: addNextPieceToPartial(partial.map(safePositionsForNextPiece(piecesLeft.head)).flatten, piecesLeft.tail)
}
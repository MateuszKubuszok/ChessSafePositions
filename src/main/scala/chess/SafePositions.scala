package chess

import scala.collection.immutable.SortedSet
import scala.collection.immutable.List

object SafePositions {
  type PieceFactory = Position => Piece
  type SafePieces = SortedSet[Piece]
  type AllowedPositions = Stream[Position]
  type PiecesLeft = Stream[PieceFactory]
  
  def makeBoard(n: Int, m: Int): AllowedPositions =
    Stream.from(0).take(n * m).map(i => new Position(i / m, i % m))
  
  def makeFactories(kings: Int, knights: Int, rooks: Int, bishops: Int, queens: Int): PiecesLeft =
    Stream.fill[PieceFactory](kings)(new King(_)) #:::
    Stream.fill[PieceFactory](knights)(new Knight(_)) #:::
    Stream.fill[PieceFactory](rooks)(new Rook(_)) #:::
    Stream.fill[PieceFactory](bishops)(new Bishop(_)) #:::
    Stream.fill[PieceFactory](queens)(new Queen(_))
    
  def safePositionsForNextPiece(pieceFactory: PieceFactory)(piecesPositionsPair: (SafePieces, AllowedPositions)): Stream[(SafePieces, AllowedPositions)] = {
    val safePieces = piecesPositionsPair._1
    val allowedPositions = piecesPositionsPair._2
    
    def pieceTypeLastPosition(newPiece: Piece) = {
      val sameTypePieces = safePieces.filter(newPiece.isSameTypeAs)
      if (sameTypePieces.isEmpty) new Position(-1, -1) else sameTypePieces.last.position
    }
    def isAllowed(newPiece: Piece): Boolean =
      !safePieces.exists(newPiece.isAttackingOrOccupies) && (pieceTypeLastPosition(newPiece) < newPiece.position)
    
    def pieceToPartial(newPiece: Piece): (SafePieces, AllowedPositions) =
      (safePieces + newPiece, allowedPositions.filterNot(newPiece.isAttackingOrOccupies))
    
    allowedPositions.map(pieceFactory).filter(isAllowed).map(pieceToPartial)
  }
  
  def addNextPieceToPartial(partial: Stream[(SafePieces, AllowedPositions)], piecesLeft: PiecesLeft): Stream[(SafePieces, AllowedPositions)] = {
    if (piecesLeft.isEmpty) partial
    else Stream() #::: addNextPieceToPartial(partial.map(safePositionsForNextPiece(piecesLeft.head)).flatten, piecesLeft.tail)
  }
    
  def solve(allowedPositions: AllowedPositions, piecesLeft: PiecesLeft): Stream[SafePieces] =
    addNextPieceToPartial(Stream((SortedSet(), allowedPositions)), piecesLeft).map(_._1)
}

class SafePositions(n: Int, m: Int, kings: Int, knights: Int, rooks: Int, bishops: Int, queens: Int) {
  lazy val board = SafePositions.makeBoard(n, m)
  lazy val piecesLeft = SafePositions.makeFactories(kings, knights, rooks, bishops, queens)
  lazy val solution = SafePositions.solve(board, piecesLeft)
  
  def validateSolution: Boolean = {
    val requiredSafePieces = kings + knights + rooks + bishops + queens 
    lazy val combinationsMatchingRequirements = solution.filter(_.size == requiredSafePieces)
    combinationsMatchingRequirements.size == solution.size
  }
}
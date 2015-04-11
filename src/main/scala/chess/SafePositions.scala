package chess

import scala.collection.immutable.SortedSet
import scala.collection.immutable.List

object SafePositions {
  type PieceFactory = Position => Piece
  type SafePieces = SortedSet[Piece]
  type AllowedPositions = Stream[Position]
  type PiecesLeft = Stream[PieceFactory]

  val lastPositionNotFound = new Position(-1, -1)

  def makeBoard(n: Int, m: Int): AllowedPositions =
    Stream.from(0).take(n * m).map(i => new Position(i / m, i % m))

  def makeFactories(kings: Int, knights: Int, rooks: Int, bishops: Int, queens: Int): PiecesLeft =
    Stream.fill[PieceFactory](kings)(new King(_)) #:::
      Stream.fill[PieceFactory](knights)(new Knight(_)) #:::
      Stream.fill[PieceFactory](rooks)(new Rook(_)) #:::
      Stream.fill[PieceFactory](bishops)(new Bishop(_)) #:::
      Stream.fill[PieceFactory](queens)(new Queen(_))

  def safePositionsForNextPiece(pieceFactory: PieceFactory)(piecesPositionsPair: (SafePieces, AllowedPositions)): Stream[(SafePieces, AllowedPositions)] = {
    def safePieces = piecesPositionsPair._1
    def allowedPositions = piecesPositionsPair._2

    def pieceTypeLastPosition(newPiece: Piece) = {
      val sameTypePieces = safePieces.filter(newPiece.isSameTypeAs)
      if (sameTypePieces.isEmpty) lastPositionNotFound else sameTypePieces.last.position
    }
    def isAllowed(newPiece: Piece): Boolean =
      !safePieces.exists(newPiece.isAttackingOrOccupies) && (pieceTypeLastPosition(newPiece) < newPiece.position)

    def pieceToPartial(newPiece: Piece): (SafePieces, AllowedPositions) =
      (safePieces + newPiece, allowedPositions.filterNot(newPiece.isAttackingOrOccupies))

    allowedPositions.map(pieceFactory).filter(isAllowed).map(pieceToPartial)
  }

  @scala.annotation.tailrec
  def addNextPieceToPartial(partial: Iterator[(SafePieces, AllowedPositions)], piecesLeft: PiecesLeft): Iterator[(SafePieces, AllowedPositions)] =
    if (piecesLeft.isEmpty) partial
    else addNextPieceToPartial(partial.map(safePositionsForNextPiece(piecesLeft.head)).flatten, piecesLeft.tail)

  def initializeStream(allowedPositions: AllowedPositions): Stream[(SafePieces, AllowedPositions)] =
    Stream((SortedSet(), allowedPositions))

  def solve(allowedPositions: AllowedPositions, piecesLeft: PiecesLeft): Iterator[SafePieces] =
    addNextPieceToPartial(initializeStream(allowedPositions).toIterator, piecesLeft).map(_._1)
}

class SafePositions(n: Int, m: Int, kings: Int, knights: Int, rooks: Int, bishops: Int, queens: Int) {
  def board = SafePositions.makeBoard(n, m)
  def piecesLeft = SafePositions.makeFactories(kings, knights, rooks, bishops, queens)
  def solution = SafePositions.solve(board, piecesLeft)
  
  def formatPositions(pieces: SafePositions.SafePieces): String = {
    def positionToString(position: Position): String =
      pieces.find(_.position == position).map(_.toString).getOrElse(" ")
    
    def makeRow(x: Int): String =
      (0 until m).map(y => new Position(x, y)).map(positionToString).reduce(_ + _)
   
     (0 until n).map(_ => "-").reduce(_ + _) + '\n' + (0 until n).map(makeRow).reduce(_ + '\n' + _)
  }

  def size: Int = solution.size
}
package entities_chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import entities_board.Board;
import entities_board.Piece;
import entities_board.Position;
import entities_chess.pieces.Bishop;
import entities_chess.pieces.King;
import entities_chess.pieces.Knight;
import entities_chess.pieces.Pawn;
import entities_chess.pieces.Queen;
import entities_chess.pieces.Rook;

public class ChessMatch {

	private Board board;
	private int turn;
	private Color currentPlayer;
	private List<Piece> piecesOnTheBoard = new ArrayList<>();
	private List<Piece> capturedPieces = new ArrayList<>();
	private boolean check;
	private boolean checkMate;
	private ChessPiece enPassantVulnerable;
	private ChessPiece promoted;

	public ChessMatch() {
		board = new Board(8, 8);
		initialSetup();
		turn = 1;
		currentPlayer = Color.WHITE;
		// capturedPieces; podia instancia aqui!!
		// check = false;

	}

	public int getTurn() {
		return turn;
	}

	public Color getCurrentPlayer() {
		return currentPlayer;
	}

	public boolean getCheck() {
		return check;
	}

	public boolean getCheckMate() {
		return checkMate;
	}

	public ChessPiece getEnPassantVulnerable() {
		return enPassantVulnerable;
	}
	
	public ChessPiece getPromoted() {
		return promoted;
	}

	public ChessPiece[][] getPieces() { // para conhecer apenas a camda de xadrez e nao tab
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getCols()];
		for (int i = 0; i < board.getRows(); i++) {
			for (int j = 0; j < board.getCols(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i, j); // peca de xadrez e nao comum!
			}

		}
		return mat;
	}

	public boolean[][] possibleMoves(ChessPosition sourcePosition) {
		Position position = sourcePosition.toPosition();
		validateSourcePosition(position);

		return board.piece(position).possibleMoves(); // que foi override no Rook

	}

	public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition) {

		Position source = sourcePosition.toPosition();
		Position target = targetPosition.toPosition();
		validateSourcePosition(source);
		validateTargetPosition(source, target);
		Piece pecaCapturada = makeMove(source, target);
		
		if (testCheck(currentPlayer)) {
			undoMove(source, target, pecaCapturada);
			throw new ChessException("You can't put yourself in check!");
		}

		ChessPiece movedPiece = (ChessPiece) board.piece(target);

		// Special move - promotion
		promoted = null;
		if (movedPiece instanceof Pawn) {
			if ((movedPiece.getColor() == Color.WHITE && target.getRow()==0) || (movedPiece.getColor() == Color.BLACK && target.getRow()==7)) {
				promoted = (ChessPiece) board.piece(target);
				promoted = replacePromotedPiece("Q"); // default
			}
		}
		
		
		
		check = (testCheck(opponent(currentPlayer))) ? true : false; // verificar se o oponente ficou em check

		if (testCheckMate(opponent(currentPlayer))) {
			checkMate = true;
		} else {
			nextTurn();
		}

		// SPECIAL MOVE _ EN PASSANT

		if (movedPiece instanceof Pawn
				&& (target.getRow() == source.getRow() - 2 || target.getRow() == source.getRow() + 2)) {
			enPassantVulnerable = movedPiece;

		} else {
			enPassantVulnerable = null;
		}

		return (ChessPiece) pecaCapturada;

	}
	
	public ChessPiece replacePromotedPiece(String type) {
		if (promoted==null) {
			throw new IllegalStateException("Tehre is no promoted piece");
			
		}
		if (!type.equals("B") && !type.equals("N") && !type.equals("Q") && !type.equals("R")) {
		//	throw new InvalidParameterException("Invalid type for promotion");
			return promoted;
		}
		
		Position ps = promoted.getChessPosition().toPosition();
		Piece p = board.removePiece(ps);
		piecesOnTheBoard.remove(p);
		ChessPiece newPiece = newPiece(type, promoted.getColor());
		board.placePiece(newPiece, ps);
		piecesOnTheBoard.add(newPiece);
		return newPiece;
	}
	
	private ChessPiece newPiece(String type, Color color) {
		if (type.equals("B")) return new Bishop(board,color);
		if (type.equals("N")) return new Knight(board,color);
		if (type.equals("Q")) return new Queen(board,color);
		return new Rook(board,color);
	}
	

	private void nextTurn() {
		turn++;
		currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
		// if (turn%2==0) {
		// quem joga é o Black --> currentPlayer = Color.BLACK;

		// }
		// else {
		// qume jogar é o White
		// }

	}

	private void validateSourcePosition(Position position) {
		if (!board.thereIsAPiece(position)) {
			throw new ChessException("There is no piece on source position");
		}
		if (currentPlayer != ((ChessPiece) board.piece(position)).getColor()) { // color porpriedade do chesspiece dai o
																				// casting
			throw new ChessException("The chosen piece is not yours!");
		}
		if (!board.piece(position).isThereAnyPossibleMove()) {
			throw new ChessException("There is no possible moves for the chosen piece");
		}

	}

	private void validateTargetPosition(Position source, Position target) {
		if (!board.piece(source).possibleMove(target)) {
			throw new ChessException("The chosen piece can't move to target position");
		}
	}

	private Piece makeMove(Position source, Position target) {
		ChessPiece piece = (ChessPiece) board.removePiece(source);
		piece.increaseMoveCount();
		Piece capturedPiece = board.removePiece(target);
		board.placePiece(piece, target);

		if (capturedPiece != null) {
			piecesOnTheBoard.remove(capturedPiece);
			capturedPieces.add(capturedPiece);
		}

		// SPECIAL MODE- castling kingside rook
		if (piece instanceof King && target.getCol() == source.getCol() + 2) {
			Position sourceT = new Position(source.getRow(), source.getCol() + 3);
			Position targetT = new Position(source.getRow(), source.getCol() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}

		if (piece instanceof King && target.getCol() == source.getCol() - 2) {
			Position sourceT = new Position(source.getRow(), source.getCol() - 4);
			Position targetT = new Position(source.getRow(), source.getCol() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(sourceT);
			board.placePiece(rook, targetT);
			rook.increaseMoveCount();
		}

		// SPECIAL MOVE - EN PASSANT

		if (piece instanceof Pawn) {
			if (source.getCol() != target.getCol() && capturedPiece == null) { // se peao andou na diagonal e nao
																				// captuoru foi en passant
				Position pawnPosition;
				if (piece.getColor() == Color.WHITE) {
					pawnPosition = new Position(target.getRow() + 1, target.getCol());
				} else {
					pawnPosition = new Position(target.getRow() - 1, target.getCol());
				}

				capturedPiece = board.removePiece(pawnPosition);
				capturedPieces.add(capturedPiece);
				piecesOnTheBoard.remove(capturedPiece);
			}

		}

		return capturedPiece;
	}

	private void undoMove(Position source, Position target, Piece capturedPiece) {
		ChessPiece piece = (ChessPiece) board.removePiece(target);
		piece.decreaseMoveCount();
		board.placePiece(piece, source);

		if (capturedPiece != null) {
			board.placePiece(capturedPiece, target);

			capturedPieces.remove(capturedPiece);
			piecesOnTheBoard.add(capturedPiece);
		}

		// SPECIAL MODE- castling kingside rook
		if (piece instanceof King && target.getCol() == source.getCol() + 2) {
			Position sourceT = new Position(source.getRow(), source.getCol() + 3);
			Position targetT = new Position(source.getRow(), source.getCol() + 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}

		if (piece instanceof King && target.getCol() == source.getCol() - 2) {
			Position sourceT = new Position(source.getRow(), source.getCol() - 4);
			Position targetT = new Position(source.getRow(), source.getCol() - 1);
			ChessPiece rook = (ChessPiece) board.removePiece(targetT);
			board.placePiece(rook, sourceT);
			rook.decreaseMoveCount();
		}

		// SPECIAL MOVE - EN PASSANT

		if (piece instanceof Pawn) {
			if (source.getCol() != target.getCol() && capturedPiece == enPassantVulnerable) { 
										
				ChessPiece pawn = (ChessPiece)board.removePiece(target);
				Position pawnPosition;
				if (piece.getColor() == Color.WHITE) {
					pawnPosition = new Position(3, target.getCol());
				} else {
					pawnPosition = new Position(4, target.getCol());
				}
				board.placePiece(pawn, pawnPosition);

				
			}

		}

	}

	private Color opponent(Color color) {
		return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
	}

	private ChessPiece king(Color color) {
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			if (p instanceof King) {
				return (ChessPiece) p;
			}
		}
		throw new IllegalStateException("There is no " + color + " king on the board"); // se esta excecao der ta algo
																						// mal pq tem sempre de haver o
																						// rei ate final!!

	}

	private boolean testCheck(Color color) {
		Position kingPosition = king(color).getChessPosition().toPosition();
		List<Piece> opponentPieces = piecesOnTheBoard.stream()
				.filter(x -> ((ChessPiece) x).getColor() == opponent(color)).collect(Collectors.toList());
		for (Piece p : opponentPieces) {
			boolean[][] mat = p.possibleMoves();
			if (mat[kingPosition.getRow()][kingPosition.getCol()]) {
				return true;
			}
		}

		return false;

	}

	private boolean testCheckMate(Color color) {
		if (!testCheck(color)) {
			return false;
		}
		List<Piece> list = piecesOnTheBoard.stream().filter(x -> ((ChessPiece) x).getColor() == color)
				.collect(Collectors.toList());
		for (Piece p : list) {
			boolean[][] mat = p.possibleMoves();
			for (int i = 0; i < board.getRows(); i++) {
				for (int j = 0; j < board.getCols(); j++) {
					if (mat[i][j]) {
						Position source = ((ChessPiece) p).getChessPosition().toPosition();
						Position target = new Position(i, j);
						Piece capturedPiece = makeMove(source, target);
						boolean testCheck = testCheck(color); // testa se o meu rei(da minha cor ainda esta em check
						undoMove(source, target, capturedPiece); // desfazer mov
						if (!testCheck) {
							return false; // nao esta em checkMat
						}
					}
				}
			}
		}
		return true;
	}

	private void placeNewPiece(char column, int row, ChessPiece piece) {

		ChessPosition position = new ChessPosition(column, row);
		board.placePiece(piece, position.toPosition()); // toPosition convertemos para matrix
		piecesOnTheBoard.add(piece);
	}

	private void initialSetup() {
		placeNewPiece('a', 1, new Rook(board, Color.WHITE));
		placeNewPiece('b', 1, new Knight(board, Color.WHITE));
		placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('d', 1, new Queen(board, Color.WHITE));
		placeNewPiece('e', 1, new King(board, Color.WHITE, this)); // this é a partida
		placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
		placeNewPiece('g', 1, new Knight(board, Color.WHITE));
		placeNewPiece('h', 1, new Rook(board, Color.WHITE));
		placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
		placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

		placeNewPiece('a', 8, new Rook(board, Color.BLACK));
		placeNewPiece('b', 8, new Knight(board, Color.BLACK));
		placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('d', 8, new Queen(board, Color.BLACK));
		placeNewPiece('e', 8, new King(board, Color.BLACK, this));
		placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
		placeNewPiece('g', 8, new Knight(board, Color.BLACK));
		placeNewPiece('h', 8, new Rook(board, Color.BLACK));
		placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
		placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
	}

}

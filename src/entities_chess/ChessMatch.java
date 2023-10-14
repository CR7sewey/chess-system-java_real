package entities_chess;

import java.util.ArrayList;
import java.util.List;

import entities_board.Board;
import entities_board.Position;
import entities_chess.pieces.Rook;

public class ChessMatch {
	
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8,8);
		initialSetup();
	}
	
	public ChessPiece[][] getPieces() {  // para conhecer apenas a camda de xadrez e nao tab
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getCols()];
		for (int i = 0; i<board.getRows(); i++) {
			for (int j = 0; j<board.getCols(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i,j); // peca de xadrez e nao comum!
			}
			
		}
		return mat;
	}
	
	private void initialSetup() {
		board.placePiece(new Rook(board, Color.WHITE), new Position(2,1));
		board.placePiece(new Rook(board, Color.BLACK), new Position(0,4));
		board.placePiece(new Rook(board, Color.WHITE), new Position(7,4));
	}
	

}

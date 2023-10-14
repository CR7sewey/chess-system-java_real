package entities_chess;

import java.util.ArrayList;
import java.util.List;

import entities_board.Board;

public class ChessMatch {
	
	
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8,8);
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
	
	

}

package entities_chess.pieces;

import entities_board.Board;
import entities_chess.ChessPiece;
import entities_chess.Color;

public class Rook extends ChessPiece {

	public Rook(Board board, Color color) {
		super(board, color);
		
	}
	
	@Override
	public String toString() {
		return "R";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];
		
		
		return mat;
	}

}

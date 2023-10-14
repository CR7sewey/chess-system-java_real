package entities_chess.pieces;

import entities_board.Board;
import entities_chess.ChessPiece;
import entities_chess.Color;

public class King extends ChessPiece {

	public King(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String toString() {
		return "K";
	}

}

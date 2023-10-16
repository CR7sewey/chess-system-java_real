package entities_chess;

import entities_board.Board;
import entities_board.Piece;

public abstract class ChessPiece extends Piece {

	private Color color;
	//private int moveCount;
	
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
		//this.moveCount = moveCount;
	}

	public Color getColor() {
		return color;
	}


	


	


	
	
	
	
	
	
	
	

}

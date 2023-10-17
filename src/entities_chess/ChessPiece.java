package entities_chess;

import entities_board.Board;
import entities_board.Piece;
import entities_board.Position;

public abstract class ChessPiece extends Piece {

	private Color color;
	private int moveCount;
	
	public ChessPiece(Board board, Color color) {
		super(board);
		this.color = color;
		//this.moveCount = moveCount;
	}

	public Color getColor() {
		return color;
	}
	
	public Integer getMoveCount() {
		return moveCount;
	}
	
	protected boolean isThereOpponentPiece(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p.getColor() != color && p!=null; 
		}
	
	
	public ChessPosition getChessPosition() {
		return ChessPosition.fromPosition(position);
	}
	
	protected void increaseMoveCount() {
		moveCount++;
	}
	
	protected void decreaseMoveCount() {
		moveCount--;
	}
	


}

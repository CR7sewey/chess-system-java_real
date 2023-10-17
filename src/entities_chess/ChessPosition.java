package entities_chess;

import java.util.List;

import entities_board.Position;

public class ChessPosition {

	private int row;
	private char column;
	private char[] caracter = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h' };

	public ChessPosition(char column, int row) {
		if (row < 0 || row > 8) {
			throw new ChessException("Row not allowed!!");
		} else if (column < 'a' || column > 'h') {
			throw new ChessException("Column not allowed!!");
		}

		this.column = column;
		this.row = row;
		
	}

	public int getRow() {
		return row;
	}

	public char getColumn() {
		return column;
	}

	protected Position toPosition() { // converter para posicao comum!! a,1 -> 0,0
		int rowP = Math.abs(row - 8);
		// int colP = -1;
		// for (int i = 0; i<caracter.length;i++) {
		// if (caracter[i] == column) {
		// colP = i;
		// break;
		/// }
		// }
		int colP = column - 'a'; // ex: 'a'-'a' = 1, 'b'-'a'=2
		Position position = new Position(rowP, colP);
		return position;

	}
	
	protected static ChessPosition fromPosition(Position position) {
		int row = -position.getRow()+8;
		int col = position.getCol();
		return new ChessPosition((char) ('a'+col),row);
		
		
	}
	
	@Override
	public String toString() {
		return ""+column +row; // b5 etc etc
	}

}

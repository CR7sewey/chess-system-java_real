package entities_chess.pieces;

import entities_board.Board;
import entities_board.Position;
import entities_chess.ChessPiece;
import entities_chess.Color;

public class Knight extends ChessPiece {

	public Knight(Board board, Color color) {
		super(board, color);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "N";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];

		Position p = new Position(0, 0);

		// above
		p.setValues(position.getRow() - 2, position.getCol() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		p.setValues(position.getRow() - 2, position.getCol() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}

		// below
		p.setValues(position.getRow() + 2, position.getCol() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		p.setValues(position.getRow() + 2, position.getCol() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}

		// right
		p.setValues(position.getRow() + 1, position.getCol() + 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		p.setValues(position.getRow() - 1, position.getCol() + 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}

		// left
		p.setValues(position.getRow() + 1, position.getCol() - 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}
		p.setValues(position.getRow() - 1, position.getCol() - 2);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}

		return mat;
	}

	private boolean canMove(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().piece(position);

		return piece == null || piece.getColor() != getColor();

	}

}

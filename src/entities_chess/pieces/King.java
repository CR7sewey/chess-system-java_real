package entities_chess.pieces;

import entities_board.Board;
import entities_board.Position;
import entities_chess.ChessMatch;
import entities_chess.ChessPiece;
import entities_chess.Color;

public class King extends ChessPiece {

	private ChessMatch chessMatch;

	public King(Board board, Color color,ChessMatch chessMatch) {
		super(board, color);
		// TODO Auto-generated constructor stub
		this.chessMatch = chessMatch;
	}

	@Override
	public String toString() {
		return "K";
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];

		Position p = new Position(0, 0);

		// above
		p.setValues(position.getRow() - 1, position.getCol());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}

		// below
		p.setValues(position.getRow() + 1, position.getCol());
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}

		// right
		p.setValues(position.getRow(), position.getCol() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}

		// left
		p.setValues(position.getRow(), position.getCol() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}

		// above right
		p.setValues(position.getRow() - 1, position.getCol() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}

		// below right
		p.setValues(position.getRow() + 1, position.getCol() + 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}

		// left above
		p.setValues(position.getRow() - 1, position.getCol() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}

		// left below
		p.setValues(position.getRow() + 1, position.getCol() - 1);
		if (getBoard().positionExists(p) && canMove(p)) {
			mat[p.getRow()][p.getCol()] = true;
		}

		// CASTLING _ SPECIAL MOVE

		if (getMoveCount() == 0 && !chessMatch.getCheck()) {
			// kingside rook
			Position pt1 = new Position(position.getRow(), position.getCol() + 3);
			if (testRookCastling(pt1)) {
				Position p1 = new Position(position.getRow(), position.getCol() + 1);
				Position p2 = new Position(position.getRow(), position.getCol() + 2);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null) {
					mat[position.getRow()][position.getCol() + 2] = true;
				}
			}

			// queenside rook
			Position pt2 = new Position(position.getRow(), position.getCol() -4);
			if (testRookCastling(pt2)) {
				Position p1 = new Position(position.getRow(), position.getCol() -1);
				Position p2 = new Position(position.getRow(), position.getCol() -2);
				Position p3 = new Position(position.getRow(), position.getCol() -3);
				if (getBoard().piece(p1) == null && getBoard().piece(p2) == null && getBoard().piece(p3) == null) {
					mat[position.getRow()][position.getCol() - 2] = true;
				}
			}

		}

		return mat;
	}

	private boolean canMove(Position position) {
		ChessPiece piece = (ChessPiece) getBoard().piece(position);

		return piece == null || piece.getColor() != getColor();

	}

	private boolean testRookCastling(Position position) {
		ChessPiece p = (ChessPiece) getBoard().piece(position);
		return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
	}

}

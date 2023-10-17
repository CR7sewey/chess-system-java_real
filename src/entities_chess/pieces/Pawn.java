package entities_chess.pieces;

import entities_board.Board;
import entities_board.Position;
import entities_chess.ChessMatch;
import entities_chess.ChessPiece;
import entities_chess.Color;

public class Pawn extends ChessPiece {

	private ChessMatch chessMatch;

	public Pawn(Board board, Color color, ChessMatch chessMatch) {
		super(board, color);
		this.chessMatch = chessMatch;
	}

	@Override
	public boolean[][] possibleMoves() {
		boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getCols()];

		Position p = new Position(0, 0);

		if (getColor() == Color.WHITE) {
			p.setValues(position.getRow() - 1, position.getCol());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}
			p.setValues(position.getRow() - 2, position.getCol());
			Position p2 = new Position(position.getRow() - 1, position.getCol());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2)
					&& !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getCol()] = true;
			}
			p.setValues(position.getRow() - 1, position.getCol() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}
			p.setValues(position.getRow() - 1, position.getCol() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}

			// SPECIAL MOVE EN PASSNT white

			if (position.getRow() == 3) {
				Position left = new Position(position.getRow(), position.getCol() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
						&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow() - 1][left.getCol()] = true;
				}
				Position right = new Position(position.getRow(), position.getCol() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
						&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow() - 1][right.getCol()] = true;
				}

			}

		} else {
			p.setValues(position.getRow() + 1, position.getCol());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}
			p.setValues(position.getRow() + 2, position.getCol());
			Position p2 = new Position(position.getRow() + 1, position.getCol());
			if (getBoard().positionExists(p) && !getBoard().thereIsAPiece(p) && getBoard().positionExists(p2)
					&& !getBoard().thereIsAPiece(p2) && getMoveCount() == 0) {
				mat[p.getRow()][p.getCol()] = true;
			}
			p.setValues(position.getRow() + 1, position.getCol() - 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}
			p.setValues(position.getRow() + 1, position.getCol() + 1);
			if (getBoard().positionExists(p) && isThereOpponentPiece(p)) {
				mat[p.getRow()][p.getCol()] = true;
			}

			// SPECIAL MOVE EN PASSNT Black

			if (position.getRow() == 4) {
				Position left = new Position(position.getRow(), position.getCol() - 1);
				if (getBoard().positionExists(left) && isThereOpponentPiece(left)
						&& getBoard().piece(left) == chessMatch.getEnPassantVulnerable()) {
					mat[left.getRow() + 1][left.getCol()] = true; // anda para baixo dai mais 1!!
				}
				Position right = new Position(position.getRow(), position.getCol() + 1);
				if (getBoard().positionExists(right) && isThereOpponentPiece(right)
						&& getBoard().piece(right) == chessMatch.getEnPassantVulnerable()) {
					mat[right.getRow() + 1][right.getCol()] = true;
				}

			}
		}
		return mat;
	}

	@Override
	public String toString() {
		return "P";
	}

}
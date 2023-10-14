package entities_board;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private int rows;
	private int cols;

	private Piece[][] p;

	public Board(int rows, int cols) {

		if (rows < 1 || cols < 1) {
			throw new BoardException("Error creating board: therre must be ate leats 1 r/c!");
		}
		this.rows = rows;
		this.cols = cols;
		p = new Piece[rows][cols];

	}

	public int getRows() {
		return rows;
	}

	public int getCols() {
		return cols;
	}

	public Piece piece(int row, int col) { // retorna uma peça!
		if (!positionExists(row, col)) {
			throw new BoardException("This position doesn't exist!!");
		}
		return p[row][col];
	}

	public Piece piece(Position position) { // retorna uma peça pela posicao!!

		if (!positionExists(position)) {
			throw new BoardException("This position doesn't exist!!");
		}
		return p[position.getRow()][position.getCol()];
	}

	public void placePiece(Piece piece, Position position) throws BoardException {
		
		if (thereIsAPiece(position)) {
			throw new BoardException("This position is occupied!!");
		}
		p[position.getRow()][position.getCol()] = piece;
		piece.position = position;
	}

	public boolean positionExists(int row, int col) {
		return row >= 0 && row <= 8 && col >= 0 && col <= 8;
	}

	public boolean positionExists(Position position) {

		return positionExists(position.getRow(), position.getCol());
	}

	public boolean thereIsAPiece(Position position) throws BoardException {
		
		if (!positionExists(position)) {
			throw new BoardException("This position doesn't exist!!");
		}
		

		return piece(position) != null;
	}

}

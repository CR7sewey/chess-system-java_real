package entities_board;

import java.util.ArrayList;
import java.util.List;

public class Board {

	private int rows;
	private int cols;
	
	private Piece[][] p;
	
	

	public Board(int rows, int cols) {
	
		this.rows = rows;
		this.cols = cols;
		p = new Piece[rows][cols];
		
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public int getCols() {
		return cols;
	}

	public void setCols(int cols) {
		this.cols = cols;
	}

	public Piece piece(int row, int col) {  // retorna uma peça!
		return p[row][col];
	}
	
	public Piece piece(Position position) {  // retorna uma peça pela posicao!!
		return p[position.getRow()][position.getCol()];
	}
	

	
	
	
	
	
	
	
	
	
	
	
}

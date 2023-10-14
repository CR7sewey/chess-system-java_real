package application;

import entities_board.Board;
import entities_board.Position;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Position p = new Position(0,0);
		System.out.println(p);
		p.setValues(5, 5);
		System.out.println(p);
		
		Board board = new Board(8,8);
		
	}

}

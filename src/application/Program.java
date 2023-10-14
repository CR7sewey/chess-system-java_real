package application;

import entities_board.Board;
import entities_board.Position;
import entities_chess.ChessMatch;

public class Program {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ChessMatch chessMatch = new ChessMatch();
		UI.printBoard(chessMatch.getPieces());
		
	}

}

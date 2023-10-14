package application;

import entities_chess.ChessPiece;

public class UI {

	
	public static void printBoard(ChessPiece[][] cp) {
		for (int i = 0; i<cp.length; i++) {
			System.out.print(Math.abs(i-8)+" ");
			for (int j = 0; j<cp[i].length; j++) {
				printPiece(cp[i][j]);
				
			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}
	
	private static void printPiece(ChessPiece cp) {
		if (cp == null) {
			System.out.print('-');
		}
		else {
			System.out.print(cp);
		}
		System.out.print(" ");
	}
}

package application;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import entities_chess.ChessMatch;
import entities_chess.ChessPiece;
import entities_chess.ChessPosition;
import entities_chess.Color;

public class UI {

	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";

	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	// https://stackoverflow.com/questions/2979383/java-clear-the-console
	public static void clearScreen() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}

	public static ChessPosition readChessPosition(Scanner sc) {
		try {
			String s = sc.nextLine();
			char c = s.charAt(0);
			int i = Integer.parseInt(s.substring(1));
			return new ChessPosition(c, i);
		} catch (RuntimeException e) {
			throw new InputMismatchException("Error reading!");
		}
	}

	private static void printPiece(ChessPiece piece, boolean background) {
		if (background) {
			System.out.print(ANSI_BLUE_BACKGROUND);
		}
		if (piece == null) {
			System.out.print("-" + ANSI_RESET);
		}

		else {
			if (piece.getColor() == Color.WHITE) {
				System.out.print(ANSI_WHITE + piece + ANSI_RESET);
			} else {
				System.out.print(ANSI_YELLOW + piece + ANSI_RESET);
			}
		}
		System.out.print(" ");
	}

	public static void printMatch(ChessMatch chessmatch,List<ChessPiece> captured) {
		printBoard(chessmatch.getPieces());
		System.out.println();
		printCapturedPieces(captured);
		System.out.println();
		System.out.println("Turn: " + chessmatch.getTurn());
		System.out.println("Waiting player: " + chessmatch.getCurrentPlayer());
	}

	public static void printBoard(ChessPiece[][] cp) {
		for (int i = 0; i < cp.length; i++) {
			System.out.print(Math.abs(i - 8) + " ");
			for (int j = 0; j < cp[i].length; j++) {
				printPiece(cp[i][j], false); // para nao imprimir as posibiliidadases

			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	public static void printBoard(ChessPiece[][] cp, boolean[][] possibleMoves) {
		for (int i = 0; i < cp.length; i++) {
			System.out.print(Math.abs(i - 8) + " ");
			for (int j = 0; j < cp[i].length; j++) {
				printPiece(cp[i][j], possibleMoves[i][j]);

			}
			System.out.println();
		}
		System.out.println("  a b c d e f g h");
	}

	public static void printCapturedPieces(List<ChessPiece> captured) {
		List<ChessPiece> white = captured.stream().filter(x -> x.getColor()==Color.WHITE).collect(Collectors.toList());	
		List<ChessPiece> black = captured.stream().filter(x -> x.getColor()==Color.BLACK).collect(Collectors.toList());	
		System.out.println("Captured pieces: ");
		System.out.print("White: ");
		System.out.print(ANSI_WHITE);
		System.out.print(Arrays.toString(white.toArray()));
		System.out.print(ANSI_RESET);
		//for (ChessPiece p: white) {
		//	System.out.print(p + ", ");
		//}
		System.out.println();
		System.out.print("Black: ");
		System.out.print(ANSI_YELLOW);
		System.out.print(Arrays.toString(black.toArray()));
		System.out.print(ANSI_RESET);

	}

}

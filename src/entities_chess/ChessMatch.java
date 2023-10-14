package entities_chess;



import entities_board.Board;
import entities_board.Position;
import entities_chess.pieces.Rook;

public class ChessMatch {
	
	
	private Board board;
	
	public ChessMatch() {
		board = new Board(8,8);
		initialSetup();
	}
	
	public ChessPiece[][] getPieces() {  // para conhecer apenas a camda de xadrez e nao tab
		ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getCols()];
		for (int i = 0; i<board.getRows(); i++) {
			for (int j = 0; j<board.getCols(); j++) {
				mat[i][j] = (ChessPiece) board.piece(i,j); // peca de xadrez e nao comum!
			}
			
		}
		return mat;
	}
	
	private void placeNewPiece(char column,int row, ChessPiece piece) {
		
		ChessPosition position = new ChessPosition(column,row);
		board.placePiece(piece, position.toPosition()); // toPosition convertemos para matrix
		
	}
	
	private void initialSetup() {
		
		placeNewPiece('b',6,new Rook(board, Color.WHITE));
		placeNewPiece('e',8,new Rook(board, Color.BLACK));
		placeNewPiece('e',1,new Rook(board, Color.WHITE));
	}
	

}

package entities_board;

public abstract class Piece {

	protected Position position; //nao quero que seja visivel na camada do xadrez!
	private Board board;
	
	
	
	public Piece(Board board) { // passamos so isto pq inicialmente posicao da peca é nula!
		
		this.board = board;
		position = null;
	}
	
	
	protected Board getBoard() { // tabuleiro nao é acessivel pela outra camada!!
		return board;
	}

	
	public abstract boolean[][] possibleMoves(); // nao sei de uma peca generica
	
	public boolean possibleMove(Position position) {
		
		return possibleMoves()[position.getRow()][position.getCol()];
		// if false nao pode, if true pode
			
	}

	public boolean isThereAnyPossibleMove() {
		boolean[][] matriz = possibleMoves();
		for (int i = 0; i<matriz.length; i++) {
			for (int j = 0; j<matriz.length; j++) {
				if (matriz[i][j]) {
					return true;
				}
				
	
			}
		}
		return false;
		
	}
		
}

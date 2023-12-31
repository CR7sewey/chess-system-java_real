package entities_board;

public abstract class Piece {

	protected Position position; // nao quero que seja visivel na camada do xadrez!
	private Board board;

	public Piece(Board board) { // passamos so isto pq inicialmente posicao da peca é nula!

		this.board = board;
		position = null;
	}

	protected Board getBoard() { // tabuleiro nao é acessivel pela outra camada!!
		return board;
	}

	// public abstract boolean[][] possibleMoves(); // nao sei de uma peca generica
	public abstract boolean[][] possibleMoves();

	public boolean possibleMove(Position position) {
		return possibleMoves()[position.getRow()][position.getCol()];
	}

	public boolean isThereAnyPossibleMove() {
		boolean[][] mat = possibleMoves();
		for (int i = 0; i < mat.length; i++) {
			for (int j = 0; j < mat.length; j++) {
				if (mat[i][j]) {
					return true;
				}
			}
		}
		return false;
	}

}

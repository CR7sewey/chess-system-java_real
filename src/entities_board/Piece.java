package entities_board;

public class Piece {

	protected Position position; //nao quero que seja visivel na camada do xadrez!
	private Board board;
	
	
	
	public Piece(Board board) { // passamos so isto pq inicialmente posicao da peca é nula!
		
		this.board = board;
		position = null;
	}
	
	
	protected Board getBoard() { // tabuleiro nao é acessivel pela outra camada!!
		return board;
	}

	

		
}

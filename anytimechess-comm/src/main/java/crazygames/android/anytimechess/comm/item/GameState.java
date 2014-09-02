package crazygames.android.anytimechess.comm.item;

import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.pieces.Piece;

public class GameState extends Item {
	
	private Piece[][] map = new Piece[8][8];

	public GameState(Game game) {
		this.map = game.getBoard().getMap();
	}
	
	public GameState(String messageContext, int index) {
		createMap(getValue(messageContext, index));
	}
	
	@Override
	public int size() {
		return 68;
	}

	@Override
	public String build() {
		StringBuilder builder = new StringBuilder();
		
		for(int l = 0; l < 8; l++)
			for(int c = 0; c < 8; c++)
				builder.append(map[l][c] == null ? '-' : map[l][c].getMessageCode());
		
		return builder.toString();
	}

	public Piece[][] getMap() {
		return map;
	}

	private void createMap(String value) {
		PieceGenerator gen = new PieceGenerator();
		String kingBuffer = "";
		int row = 0;
		int col = 0;
		
		for (char code : value.toCharArray()) {
			Piece piece;
			
			if (gen.isKing(code) || !kingBuffer.isEmpty()) {
				kingBuffer += code;
				
				if (kingBuffer.length() != 3)
					continue;
				
				piece = gen.generateKing(kingBuffer);
				kingBuffer = "";
			} else
				piece = gen.generatePiece(code);
			
			map[row][col] = piece;
			col++;
			if (col == 8) {
				col = 0;
				row++;
			}
		}
	}
}

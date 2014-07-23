package crazygames.android.anytimechess.comm.message;

import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.pieces.Piece;

class GameState extends MessageItem {
	
	private Piece[][] map = new Piece[8][8];

	GameState(Game game) {
		this.map = game.getBoard().getMap();
	}
	
	GameState(String messageContext, int index) {
		createMap(getValue(messageContext, index));
	}
	
	@Override
	protected int size() {
		return 68;
	}

	@Override
	protected String build() {
		StringBuilder builder = new StringBuilder();
		
		for(int l = 0; l < 8; l++)
			for(int c = 0; c < 8; c++)
				builder.append(map[l][c] == null ? '-' : map[l][c].getMessageCode());
		
		return builder.toString();
	}

	Piece[][] getMap() {
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

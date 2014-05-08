package crazygames.android.anytimechess.comm.message;

import java.util.ArrayList;
import java.util.List;

public class GameState extends MessageItem {

	private List<List<String>> matrix = new ArrayList<List<String>>();
	
	GameState(String messageContext, int index) {
		createMatrix(getValue(messageContext, index));
	}
	
	@Override
	protected int size() {
		return 64;
	}

	@Override
	protected String build() {
		StringBuilder builder = new StringBuilder();
		
		for(List<String> line : matrix)
			for(String man : line)
				builder.append(man);
		
		return builder.toString();
	}

	private void createMatrix(String value) {
		int count = 0;
		List<String> line = new ArrayList<String>();
		
		for (char charac : value.toCharArray()) {
			line.add(String.valueOf(charac));
			count++;
			if (count == 7) {
				matrix.add(line);
				line = new ArrayList<String>();
			}
		}
	}

}

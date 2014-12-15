package crazygames.android.anytimechess.layouts;

import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import crazygames.android.anytimechess.BoardAdapter;
import crazygames.android.anytimechess.PieceView;
import crazygames.android.anytimechess.Square;
import crazygames.android.anytimechess.comm.message.State;
import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.game.Move;
import crazygames.android.anytimechess.engine.game.response.MoveResponse;
import crazygames.android.anytimechess.engine.pieces.Piece;

public class MainLayout extends LinearLayout {

	private BoardLayout boardLayout;
	private BoardAdapter boardAdapter;
	private GameStatusLayout gameStatusLayout;

	public MainLayout(final Context context) {
		super(context);
		setBackgroundColor(Color.WHITE);
		
		setOrientation(LinearLayout.VERTICAL);
		
		boardLayout = new BoardLayout(context);
		gameStatusLayout = new GameStatusLayout(context);
		addView(boardLayout);
		addView(gameStatusLayout);
	}
	
	private BoardAdapter getAdapter() {
		return boardAdapter;
	}
	
	private View findViewAtPosition(View parent, float x, float y) {
	    if (parent instanceof ViewGroup) {
	        ViewGroup viewGroup = (ViewGroup)parent;
	        for (int i=0; i<viewGroup.getChildCount(); i++) {
	            View child = viewGroup.getChildAt(i);
	            View viewAtPosition = findViewAtPosition(child, x, y);
	            if (viewAtPosition != null) {
	                return viewAtPosition;
	            }
	        }
	        return null;
	    } else {
	        Rect rect = new Rect();
	        parent.getGlobalVisibleRect(rect);
	        if (rect.contains((int)x, (int)y)) {
	            return parent;
	        } else {
	            return null;
	        }
	    }
	}
	
	public static boolean isPointInsideView(float x, float y, View view) {
	    int location[] = new int[2];
	    view.getLocationOnScreen(location);
	    int viewX = location[0];
	    int viewY = location[1];

	    //point is inside view bounds
	    if(( x > viewX && x < (viewX + view.getWidth())) &&
	            ( y > viewY && y < (viewY + view.getHeight()))){
	        return true;
	    } else {
	        return false;
	    }
	}
	
	public void load(State state) {
		boardAdapter = new BoardAdapter(getContext());
		boardLayout.setAdapter(boardAdapter);
		boardLayout.start(state.getGame());
		boardLayout.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
	            case MotionEvent.ACTION_CANCEL:
//	            	View viewSquare = null;
//	            	for(Square square : boardAdapter.getAllItems()) {
//	            		Rect rect = new Rect();
//	        	        square.getHitRect(rect);
//	            		viewSquare = rect.contains((int)event.getRawX(), (int)event.getRawY()) ? square : null;
////	            		System.out.println(String.format("%s - %s - %s %s", event.getRawX(), event.getRawY(), square, rect));
////	            		viewSquare = isPointInsideView(event.getRawX(), event.getRawY(), square) ? square : null;
//
//	            		if(viewSquare != null)
//	            			break;
//	            	}
//	            	
//	            	if(viewSquare == null)
//	            		return true;
//	            	System.out.println("CANCEL =========================================> " + event.getRawX() + ", " + event.getRawY());
//	            	System.out.println("CANCEL =========================================> " + viewSquare);
//	            	
//	            	if(viewSquare instanceof PieceView) {
//	            		System.out.println("=================> bah: " + viewSquare);
//	            		viewSquare = (View) viewSquare.getParent();
//	            	}
//	            	
//	            	move((Square) viewSquare, game);
	                break;
	            case MotionEvent.ACTION_MOVE:
//	            	System.out.println("MOVE =========================================> " + event.getRawX() + ", " + event.getRawY());
//	            	View viewAtPosition = findViewAtPosition(boardLayout, event.getRawX(), event.getRawY());
//	            	if(viewAtPosition == null)
//	            		return true;
//	            	final PieceView child = (PieceView) viewAtPosition;
//	            	
//	            	System.out.println("MOVE =========================================> " + viewAtPosition.getParent());
//	            	
//	            	boardLayout.setSelectedPiece(child);
//	            	final Piece piece = child.getPiece();
////	            	highlightPossibleMoves(child, piece);
//	            	
//	            	DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(viewAtPosition);
//	            	viewAtPosition.startDrag(null, shadowBuilder, viewAtPosition, 0);
	            	break;
				}
				return true;
			}
		});
		
//		boardLayout.newGame(game);
//		
//		for(int i = 0; i < getAdapter().getCount(); i++) {
//			final Square item = (Square) getAdapter().getItem(i);
//			item.setOnClickListener(new OnClickListener() {
//				@Override public void onClick(final View v) {
//					if(boardLayout.hasSelectedPiece())
//						move((Square) v, game);
//
//					final PieceView child = (PieceView) ((Square)v).getChildAt(0);
//					boardLayout.setSelectedPiece(child);
//
//					if(child == null) {
//						boardLayout.setBackgroundOriginalColor();
//						return;
//					}
//
//					final Piece piece = child.getPiece();
//					if(!game.getTurn().equals(piece.color())) {
//						boardLayout.setBackgroundOriginalColor();
//						return;
//					}
//
//					highlightPossibleMoves(child, piece);
//				}
//
//
//			});
//		}
	}

	private void highlightPossibleMoves(final PieceView child, final Piece piece) {
//		boardLayout.setBackgroundOriginalColor();
//		
//		final List<Move> moves = piece.moves(child.getColumn(), child.getRow(), game);
//		for(final Move move : moves) {
//			final MoveResponse moveResponse = piece.canMove(move, game, game.getBoard().get(move.getColTo(), move.getRowTo()));
//			if(moveResponse.getMoveType().equals(Move.Type.CANTMOVE))
//				continue;
//			
//			for(int i = 0; i < boardLayout.getChildCount(); i++) {
//				final Square s = (Square)boardLayout.getChildAt(i);
//				if(s.getRow() == move.getRowTo() && s.getColumn() == move.getColTo())
//					s.setBackgroundColor(Color.CYAN);
//			}
//			
//		}
	}
	
	private void move(final Square square, Game game) {
		final PieceView selectedPiece = boardLayout.getSelectedPiece();
		
		System.out.println("==================================> start moving: " + selectedPiece.getColumn() + selectedPiece.getRow() + square.getColumn() + square.getRow());
		
		MoveResponse move = game.move(selectedPiece.getColumn(), selectedPiece.getRow(), square.getColumn(), square.getRow());
//		if(move.isCheck())
//			Notifications.displayMessage("Check");
//		if(move.isCheckmate())
//			Notifications.displayMessage("Checkmate");
//		if(move.getMoveType().equals(Move.Type.CANTMOVE))
//			Notifications.displayMessage("Illegal move");
		
		boardLayout.refresh(game);
		gameStatusLayout.refresh(game.getTurn().name());
	}
}

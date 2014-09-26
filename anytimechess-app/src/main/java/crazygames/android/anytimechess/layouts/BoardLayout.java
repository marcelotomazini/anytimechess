package crazygames.android.anytimechess.layouts;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import crazygames.android.anytimechess.BoardAdapter;
import crazygames.android.anytimechess.PieceView;
import crazygames.android.anytimechess.Square;
import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.game.response.MoveResponse;
import crazygames.android.anytimechess.engine.pieces.Piece;

public class BoardLayout extends GridView {

	private Game game;
	
	private final List<PieceView> pieces = new ArrayList<PieceView>();
	private PieceView selectedPiece;

	public BoardLayout(Context context) {
		super(context);
		setBackgroundColor(Color.WHITE);
		setNumColumns(8);
	}

	public void newGame() {
		game = new Game();
		createPieces(game.getBoard().getMap());
		refresh(game);
	}

	public void refresh(Game game) {
		for (int i = 0; i < getAdapter().getCount(); i++) {
			final Square item = (Square) getAdapter().getItem(i);
			item.removeAllViews();

			Piece piece = game.getBoard().get(item.getPosition().getCol(),
					item.getPosition().getRow());
			if (piece != null) {
				PieceView pieceView = pieceViewCorrespondingTo(piece);
				final Square parent = (Square) pieceView.getParent();
				if (parent != null)
					parent.removeAllViews();
				item.addView(pieceView);
			}
		}
	}

	private void createPieces(Piece[][] pieces) {
		for (Piece[] p1 : pieces)
			for (Piece piece : p1)
				if (piece != null)
					addNewPiece(piece);
	}

	private void addNewPiece(Piece piece) {
		final PieceView pieceView = new PieceView(getContext(), piece);
		
		pieceView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				System.out.println(String.format("xxxxxxxxxxxxxxxxxxxxxxxxxxxx > %s %s", pieceView, v));
				DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            	v.startDrag(null, shadowBuilder, v, 0);
            	
            	setSelectedPiece((PieceView) v);
				return true;
			}
		});
		
		pieceView.setOnDragListener(new OnDragListener() {
			@Override
			public boolean onDrag(View v, DragEvent event) {
				switch (event.getAction()) {
				case DragEvent.ACTION_DRAG_STARTED:
//					return false;
					break;

				case DragEvent.ACTION_DRAG_ENTERED:
					break;

				case DragEvent.ACTION_DRAG_EXITED:
					System.out.println(String.format("opa, exited %s %s %s %s", v, event.getX(), event.getY(), event.getLocalState()));
					View viewSquare = null;
					for(Square square : ((BoardAdapter)getAdapter()).getAllItems()) {
	            		Rect rect = new Rect();
	        	        square.getHitRect(rect);
	            		viewSquare = rect.contains((int)event.getX(), (int)event.getY()) ? square : null;

	            		if(viewSquare != null)
	            			break;
	            	}
					move((Square)viewSquare, game);
					break;

				case DragEvent.ACTION_DROP:
					System.out.println("opa, dropô");
					break;

				case DragEvent.ACTION_DRAG_ENDED:
//					PieceView view = (PieceView) event.getLocalState();
//					System.out.println("view : " + view + " v: " + v);
//					System.out.println(v + "2=========================================================");
//	    			Square from = (Square) view.getParent();
//	    			System.out.println(v + "3=========================================================");
//	    			from.removeView(view);
//	    			System.out.println(v + "4=========================================================");
//	    			PieceView to = (PieceView) v;
//	    			to.addView(view);
//	    			view.setVisibility(View.VISIBLE);
//					((Square) v.getParent()).setBackgroundColor(Color.RED);
				}

				return true;

			}
		});
		getPieces().add(pieceView);
	}

	public List<PieceView> getPieces() {
		return pieces;
	}

	public void setBackgroundOriginalColor() {
		for (int i = 0; i < getChildCount(); i++) {
			final Square s = (Square) getChildAt(i);
			s.setOriginalBackgroundColor();
		}
	}

	public PieceView getSelectedPiece() {
		return selectedPiece;
	}

	public void setSelectedPiece(final PieceView selectedPiece) {
		this.selectedPiece = selectedPiece;
	}

	public boolean hasSelectedPiece() {
		return getSelectedPiece() != null;
	}

	private PieceView pieceViewCorrespondingTo(Piece piece) {
		for (PieceView pieceView : getPieces())
			if (piece.equals(pieceView.getPiece()))
				return pieceView;
		return null;
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
	
	private void move(final Square square, Game game) {
		final PieceView selectedPiece = getSelectedPiece();
		
		System.out.println("==================================> start moving: " + selectedPiece.getColumn() + selectedPiece.getRow() + square.getColumn() + square.getRow());
		
		MoveResponse move = game.move(selectedPiece.getColumn(), selectedPiece.getRow(), square.getColumn(), square.getRow());
//		if(move.isCheck())
//			Notifications.displayMessage("Check");
//		if(move.isCheckmate())
//			Notifications.displayMessage("Checkmate");
//		if(move.getMoveType().equals(Move.Type.CANTMOVE))
//			Notifications.displayMessage("Illegal move");
		
		refresh(game);
	}
}

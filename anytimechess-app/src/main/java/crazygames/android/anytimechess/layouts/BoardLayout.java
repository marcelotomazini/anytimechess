package crazygames.android.anytimechess.layouts;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import crazygames.android.anytimechess.BoardAdapter;
import crazygames.android.anytimechess.PieceView;
import crazygames.android.anytimechess.Square;
import crazygames.android.anytimechess.comm.message.State;
import crazygames.android.anytimechess.engine.game.Game;
import crazygames.android.anytimechess.engine.game.Move;
import crazygames.android.anytimechess.engine.game.response.MoveResponse;
import crazygames.android.anytimechess.engine.pieces.EmptyPiece;
import crazygames.android.anytimechess.engine.pieces.Piece;
import crazygames.android.anytimechess.state.StateManager;

public class BoardLayout extends GridView {

	private State state;
	private Game game;
	
	private final List<PieceView> pieces = new ArrayList<PieceView>();
	private PieceView selectedPiece;

	public BoardLayout(Context context) {
		super(context);
		setBackgroundColor(Color.WHITE);
		setNumColumns(8);
	}
	
	public void load(State state) {
		this.state = state;
		this.game = state.getGame();
	}

	public void start() {
		createPieces();
		refresh();
	}

	public void refresh() {
		for (int i = 0; i < getAdapter().getCount(); i++) {
			final Square item = (Square) getAdapter().getItem(i);
			item.removeAllViews();

			Piece piece = game.getBoard().get(item.getPosition().getCol(),
					item.getPosition().getRow());
			PieceView pieceView = pieceViewCorrespondingTo(piece);
			final Square parent = (Square) pieceView.getParent();
			if (parent != null)
				parent.removeAllViews();
			item.addView(pieceView);
		}
	}

	private void createPieces() {
		for (Piece[] p1 : game.getBoard().getMap())
			for (Piece piece : p1)
				if (piece != null)
					getPieces().add(createPieceView(piece));
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
			if (pieceView.getPiece().equals(piece))
				return pieceView;
		PieceView pieceView = createPieceView(new EmptyPiece().white());
		return pieceView;
	}
	
	private PieceView createPieceView(Piece piece) {
		final PieceView pieceView = new PieceView(getContext(), piece);
		pieceView.setOnTouchListener(new PieceTouchListener());
		pieceView.setOnDragListener(new PieceDragListener());
		return pieceView;
	}
	
	private void move(final Square square) {
		final PieceView selectedPiece = getSelectedPiece();
		
		System.out.println("==================================> start moving: " + selectedPiece.getColumn() + selectedPiece.getRow() + square.getColumn() + square.getRow());
		
		MoveResponse move = game.move(selectedPiece.getColumn(), selectedPiece.getRow(), square.getColumn(), square.getRow());
		
		if (!move.getMoveType().equals(Move.Type.CANTMOVE))
			new StateManager(getContext()).send(state.getVisit(), game);
//		if(move.isCheck())
//			Notifications.displayMessage("Check");
//		if(move.isCheckmate())
//			Notifications.displayMessage("Checkmate");
//		if(move.getMoveType().equals(Move.Type.CANTMOVE))
//			Notifications.displayMessage("Illegal move");
		
		refresh();
	}
	
	private class PieceTouchListener implements OnTouchListener {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        	v.startDrag(null, shadowBuilder, v, 0);
        	
        	setSelectedPiece((PieceView) v);
			return true;
		}
	}
	
	private class PieceDragListener implements OnDragListener {

		@Override
		public boolean onDrag(View destinyView, DragEvent event) {
			switch (event.getAction()) {
				case DragEvent.ACTION_DROP:
					move(getDestinySquare(destinyView));
					break;
			}

			return true;
		}

		private Square getDestinySquare(View destinyView) {
			for(Square square : ((BoardAdapter)getAdapter()).getAllItems()) {
				Rect rect = new Rect();
			    square.getHitRect(rect);
			    
			    Rect destinyRect = new Rect();
			    destinyView.getGlobalVisibleRect(destinyRect);
			    
			    int centerX = destinyRect.centerX();
			    int centerY = destinyRect.top - 100; // I fucking don't fucking know why the fucking fuck axis y is fucking displaced
			    
				if (rect.contains(centerX, centerY))
			    	return square;
			}
			
			return null;
		}
	}
}

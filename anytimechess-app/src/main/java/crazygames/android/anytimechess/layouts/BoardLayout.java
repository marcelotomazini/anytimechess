package crazygames.android.anytimechess.layouts;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import crazygames.android.anytimechess.comm.message.State;
import crazygames.android.anytimechess.engine.game.response.MoveResponse;
import crazygames.android.anytimechess.engine.pieces.EmptyPiece;
import crazygames.android.anytimechess.engine.pieces.Piece;
import crazygames.android.anytimechess.engine.pieces.Piece.Color;
import crazygames.android.anytimechess.state.StateManager;
import crazygames.android.anytimechess.utils.Alerts;
import crazygames.android.anytimechess.utils.Messages;

public class BoardLayout extends GridView {

	private State state;
	private String player;
	
	private final List<PieceView> pieces = new ArrayList<PieceView>();
	private PieceView selectedPiece;
	private StateManager stateManager;

	public BoardLayout(Context context) {
		super(context);
		stateManager = new StateManager(getContext());
		
		setBackgroundColor(android.graphics.Color.WHITE);
		setNumColumns(8);
	}
	
	public void load(String player) {
		this.player = player;
		this.state = stateManager.get(player);
		
		setAdapter(getBoardAdapter());
		createPieces();
		refresh();
	}

	private BoardAdapter getBoardAdapter() {
		return stateManager.getMyColor(player) == Color.WHITE ? new ReverseBoardAdapter(getContext()) : new BoardAdapter(getContext());
	}

	public void refresh() {
		for (int i = 0; i < getAdapter().getCount(); i++) {
			final Square item = (Square) getAdapter().getItem(i);
			item.removeAllViews();

			Piece piece = state.getGame().getBoard().get(item.getPosition().getCol(),
					item.getPosition().getRow());
			PieceView pieceView = pieceViewCorrespondingTo(piece);
			final Square parent = (Square) pieceView.getParent();
			if (parent != null)
				parent.removeAllViews();
			item.addView(pieceView);
		}
		
		if(state.getGame().isCheckmate())
			new Alerts(getContext()).displayMessage("Checkmate");
		else if(state.getGame().isCheck())
			new Alerts(getContext()).displayMessage("Check");
	}

	private void createPieces() {
		for (Piece[] p1 : state.getGame().getBoard().getMap())
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
		if(!stateManager.isMyTurn(player, state))
			return;
		
		final PieceView selectedPiece = getSelectedPiece();
		
		MoveResponse move = state.getGame().move(selectedPiece.getColumn(), selectedPiece.getRow(), square.getColumn(), square.getRow());
		
		switch (move.getMoveType()) {
		case CANTMOVE:
			break;
			
		case KINGISUNDERATTACK:
			new Alerts(getContext()).displayMessage("Não cara! Seu rei está sob ataque!");
			break;

		default:
			state = stateManager.send(player, state);
			new Alerts(getContext()).displayMessage(Messages.getString("move.sent"));
		}
		
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
					move((Square) destinyView.getParent());
					break;
			}

			return true;
		}
	}
}

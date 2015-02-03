package crazygames.android.anytimechess.layouts;

import static crazygames.android.anytimechess.engine.pieces.Piece.Color.WHITE;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.GridView;
import br.com.pilovieira.ermacs.Ermacs;
import crazygames.android.anytimechess.comm.message.State;
import crazygames.android.anytimechess.engine.game.response.MoveResponse;
import crazygames.android.anytimechess.engine.pieces.Piece;
import crazygames.android.anytimechess.engine.pieces.Piece.Color;
import crazygames.android.anytimechess.state.StateManager;
import crazygames.android.anytimechess.utils.Alerts;

public class BoardLayout extends GridView {

	private State state;
	private String player;
	private GameStatusLayout status;
	
	private final List<PieceView> pieces = new ArrayList<PieceView>();
	private PieceView selectedPiece;
	private StateManager stateManager;
	private Alerts alerts;

	public BoardLayout(Context context) {
		super(context);
		stateManager = new StateManager(getContext());
		alerts = new Alerts(getContext());
		
		setBackgroundColor(android.graphics.Color.WHITE);
		setNumColumns(8);
	}
	
	public void load(String player) {
		this.player = player;
		this.state = stateManager.get(player);
		
		setAdapter(getBoardAdapter());
		createPieces();
		refresh();
		validateWarnings();
	}

	private BoardAdapter getBoardAdapter() {
		return getMyColor() == WHITE ? new ReverseBoardAdapter(getContext()) : new BoardAdapter(getContext());
	}

	private void validateWarnings() {
		if(state.getGame().isCheckmate(getMyColor()))
			alerts.displayBundleMessage("check");
		else if(state.getGame().isCheck(getMyColor()))
			alerts.displayBundleMessage("checkmate");
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
		
		status.refresh();
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
		PieceView pieceView = createPieceView(null);
		return pieceView;
	}
	
	private PieceView createPieceView(Piece piece) {
		final PieceView pieceView = new PieceView(getContext(), piece);
		pieceView.setOnTouchListener(new PieceTouchListener());
		pieceView.setOnDragListener(new PieceDragListener());
		return pieceView;
	}
	
	private void move(Square square) {
		try {
			move2(square);			
		} catch (Exception e) {
			Ermacs.addErmac(getContext(), e);
			alerts.displayMessage("ERMAC!");
		}
	}
	
	private void move2(Square square) {
		if(!stateManager.isMyTurn(player, state))
			return;
		
		final PieceView selectedPiece = getSelectedPiece();
		
		MoveResponse move = state.getGame().move(selectedPiece.getColumn(), selectedPiece.getRow(), square.getColumn(), square.getRow());
		
		switch (move.getMoveType()) {
		case CANTMOVE:
			break;
			
		case KINGISUNDERATTACK:
			alerts.displayBundleMessage("king.under.attack");
			break;

		default:
			state = stateManager.send(player, state);
			alerts.displayBundleMessage("move.sent");
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

	public void setStatus(GameStatusLayout status) {
		this.status = status;
	}

	private Color getMyColor() {
		return stateManager.getMyColor(player);
	}
}

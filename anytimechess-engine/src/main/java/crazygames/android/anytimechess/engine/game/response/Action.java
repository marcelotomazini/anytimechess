package crazygames.android.anytimechess.engine.game.response;

import crazygames.android.anytimechess.engine.game.Move;

public class Action {
  private final Position from;
  private final Position to;

  public Action(Position from, Position to) {
    this.from = from;
    this.to = to;
  }

  public Action(Move move){
    this(new Position(move.getColFrom(), move.getRowFrom()),
        new Position(move.getColTo(), move.getRowTo()));
  }

  public Position getFrom() {
    return from;
  }

  public Position getTo() {
    return to;
  }

  public String toString() {
    return from.toString() + to.toString();
  }
}
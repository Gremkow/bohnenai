package utilities;

import ai.MinMaxAI;
import core.GameState;

public class Test {

  public Test() {
    // TODO Auto-generated constructor stub
  }

  public static void main(String[] args) {
    GameState state = new GameState((byte)6);
    state.doMove((byte)1);
    System.err.println(state);
    state.doMove((byte)12);
    System.err.println(state);
    state.doMove((byte)5);
    System.err.println(state);
  }

}

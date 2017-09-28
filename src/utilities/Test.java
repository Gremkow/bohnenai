package utilities;

import core.GameState;

public class Test {

  public Test() {
    // TODO Auto-generated constructor stub
  }

  public static void main(String[] args) {
    GameState state = new GameState((byte) 6);
    System.out.println(state.getSeedsInHouse((byte)1));
    GameState clone = state.clone();
    System.out.println(clone.getSeedsInHouse((byte)1));
    state.doMove((byte) 10);
    System.out.println(state.getSeedsInHouse((byte)1));
    System.out.println(clone.getSeedsInHouse((byte)1));
  }

}

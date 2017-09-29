package ai;

import java.util.Random;

import core.GameState;

public class RandomAI implements I_AI {

  Random random;
  GameState state;
  
  public RandomAI() {
    random = new Random();
    state = new GameState((byte)6);
  }

  @Override
  public int getMove(int enemyIndex) {
    int offset = (enemyIndex > 0 && enemyIndex <= 6) ? 6 : 0;

    state.doMove((byte)enemyIndex);
    int move = random.nextInt(6) + 1;
    state.doMove((byte)move);
    return move + offset;
  }

}

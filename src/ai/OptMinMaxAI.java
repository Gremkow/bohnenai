package ai;

import java.time.Instant;

import core.GameState;

public class OptMinMaxAI implements I_AI {

  GameState state;
  byte nextMove;
  int desiredDepth;
  int calcsDone;
  long calcTime;
  
  public OptMinMaxAI() {
    this.state = new GameState((byte) 6);
    calcTime = 2000; //Init to save value
    desiredDepth = 10;
  }
  
  /**
   * @param enemyIndex The index that refers to the field chosen by the enemy in the last action.If
   * this value is -1, than the AI is the starting player and has to specify the first move.
   * @return Return The index that refers to the field of the action chosen by this AI.
   */
  public int getMove(int enemyIndex) {
    //Adjust depth
    if (calcTime < 10) {
      desiredDepth += 8;
    } else if (calcTime < 100) {
      desiredDepth += 2;
    } else if(calcTime < 800) {
      desiredDepth++;
    } else if (calcTime > 3000){
      desiredDepth = Math.max(10, desiredDepth - 1);
    }
    
    Instant start = Instant.now();

    int offset = (enemyIndex > 0 && enemyIndex <= 6) ? 6 : 0;

    // not first move --> execute
    if (enemyIndex != -1) {
      //Execute enemy's move
      state.doMove((byte) (enemyIndex + offset)); //enemy's move
    }
    //System.out.println("Before:\n" + state);

    //start calculating own move
    byte move = calculateMove(state.clone());
    state.doMove(move);
    Instant stop = Instant.now();
    //System.out.println("MinMax: " + Duration.between(start, stop) + " --> " +  (move + offset));
    //System.out.println("After:\n" + state);
    
    calcsDone++;
    calcTime = stop.toEpochMilli() - start.toEpochMilli();
    System.out.println(calcTime + ";");
    
    return move + offset;
  }

  /**
   * Calculates next index (move)
   *
   * @param aiState last state
   * @param player false=me, true=enemy
   * @param depth maximum search depth
   * @return index of next move
   */
  private byte calculateMove(GameState startState) {
    //start calculation
    nextMove = 0;
    max(startState, desiredDepth);

    if (nextMove == 0) {
      try {
        throw new Exception("No move found");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return nextMove;
  }

  private int max(GameState aiState, int currDepth) {
    if (currDepth == 0 || aiState.noMovePossible(true)) {
      return aiState.assessment(); // last stage
    }

    int max = Integer.MIN_VALUE; //init max value

    //Check each move
    for (byte i = 1; i <= 6; i++) {
      if (aiState.getSeedsInHouse(i) == 0) {
        continue; //No move possible
      }
      //do move
      GameState move = aiState.clone();
      move.doMove(i); //we do the move
      //other player
      int value = min(move, currDepth - 1);
      if (value > max) {
        max = value;
        if (currDepth == desiredDepth) {
          nextMove = i;
        }
      }
    }
    return max;
  }

  private int min(GameState aiState, int currDepth) {
    if (currDepth == 0 || aiState.noMovePossible(false)) {
      return aiState.assessment(); // last stage
    }

    int min = Integer.MAX_VALUE; //init max value

    //Check each move
    for (byte i = 7; i <= 12; i++) {
      if (aiState.getSeedsInHouse(i) == 0) {
        continue; //No move possible
      }
      //do move
      GameState move = aiState.clone();
      move.doMove(i); //enemy does the move
      //other player
      int value = max(move, currDepth - 1);
      if (value < min) {
        min = value;
      }
    }
    return min;
  }

}
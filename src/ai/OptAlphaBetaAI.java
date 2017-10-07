package ai;

import java.time.Duration;
import java.time.Instant;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

import core.GameState;

public class OptAlphaBetaAI implements I_AI {

  GameState state;
  byte nextMove;
  int desiredDepth;

  public OptAlphaBetaAI() {
    this.state = new GameState((byte) 6);
    desiredDepth = 13;
  }

  /**
   * @param enemyIndex The index that refers to the field chosen by the enemy in the last action.If
   * this value is -1, than the AI is the starting player and has to specify the first move.
   * @return Return The index that refers to the field of the action chosen by this AI.
   */
  public int getMove(int enemyIndex) {
    Instant start = Instant.now();

    int offset = (enemyIndex > 0 && enemyIndex <= 6) ? 6 : 0;

    // have to choose the first move
    if (enemyIndex == -1) {
      state.doMove((byte) 1);
      return 1; //return sth. between 1 and 6
    }
    //Execute enemy's move
    state.doMove((byte) (enemyIndex + offset)); //enemy's move
    //System.out.println("Before:\n" + state);

    //start calculating own move
    byte move = calculateMove(state.clone());
    state.doMove(move);
    Instant stop = Instant.now();
    System.out.println("OptAB: " + Duration.between(start, stop) + " --> " + (move + offset));
    //System.out.println("After:\n" + state);
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
    max(startState, Integer.MIN_VALUE, Integer.MAX_VALUE, desiredDepth);

    if (nextMove == 0) {
      try {
        throw new Exception("No move found");
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    return nextMove;
  }

  private int max(GameState aiState, int alpha, int beta, int currDepth) {
    if (currDepth == 0 || aiState.noMovePossible(true)) {
      return aiState.assessment(); // last stage
    }

    int max = alpha; // init max value

    // Check each move
    for (Entry<Byte, Integer> entry : getMovesOrdered(aiState, 0).entrySet()) {
      if (aiState.getSeedsInHouse(entry.getKey()) == 0) {
        continue; // No move possible
      }
      // do move
      GameState move = aiState.clone();
      move.doMove(entry.getKey()); // we do the move
      // other player
      int value = min(move, max, beta, currDepth - 1);
      if (value > max) {
        max = value;
        if (currDepth == desiredDepth) {
          nextMove = entry.getKey();
        }
        if (max >= beta) {
          break;
        }
      }
    }
    return max; // return max only if a move was done
  }

  private int min(GameState aiState, int alpha, int beta, int currDepth) {
    if (currDepth == 0 || aiState.noMovePossible(false)) {
      return aiState.assessment(); // last stage
    }

    int min = beta; // init max value

    // Check each move
    for (Entry<Byte, Integer> entry : getMovesOrdered(aiState, 7).entrySet()) {
      if (aiState.getSeedsInHouse(entry.getKey()) == 0) {
        continue; // No move possible
      }
      // do move
      GameState move = aiState.clone();
      move.doMove(entry.getKey()); // enemy does the move
      // other player
      int value = max(move, alpha, min, currDepth - 1);
      if (value < min) {
        min = value;
        if (min <= alpha) {
          break;
        }
      }
    }
    // No move possible (all 0)
    return min; // return min only if a move was done
  }
  
  /**
   * Pre assess moves
   * 
   * @param currState
   * @param start
   * @return
   */
  private Map<Byte, Integer> getMovesOrdered(GameState currState, int start){
    Map<Byte, Integer> assesed = new HashMap<Byte, Integer>();
    
    for(int i = start; i < start + 6; i++){
      assesed.put((byte)i, preMax(currState, Integer.MIN_VALUE, Integer.MAX_VALUE, 2));
    }
            
    return assesed.entrySet().stream()
        .sorted(Map.Entry.<Byte, Integer>comparingByValue().reversed())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
  }

  private int preMax(GameState aiState, int alpha, int beta, int currDepth) {
    if (currDepth == 0 || aiState.noMovePossible(true)) {
      return aiState.assessment(); // last stage
    }

    int max = alpha; // init max value

    // Check each move
    for (byte i = 1; i <= 6; i++) {
      if (aiState.getSeedsInHouse(i) == 0) {
        continue; // No move possible
      }
      // do move
      GameState move = aiState.clone();
      move.doMove(i); // we do the move
      // other player
      int value = preMin(move, max, beta, currDepth - 1);
      if (value > max) {
        max = value;
        if (currDepth == desiredDepth) {
          nextMove = i;
        }
        if (max >= beta) {
          break;
        }
      }
    }
    return max; // return max only if a move was done
  }

  private int preMin(GameState aiState, int alpha, int beta, int currDepth) {
    if (currDepth == 0 || aiState.noMovePossible(false)) {
      return aiState.assessment(); // last stage
    }

    int min = beta; // init max value

    // Check each move
    for (byte i = 7; i <= 12; i++) {
      if (aiState.getSeedsInHouse(i) == 0) {
        continue; // No move possible
      }
      // do move
      GameState move = aiState.clone();
      move.doMove(i); // enemy does the move
      // other player
      int value = preMax(move, alpha, min, currDepth - 1);
      if (value < min) {
        min = value;
        if (min <= alpha) {
          break;
        }
      }
    }
    // No move possible (all 0)
    return min; // return min only if a move was done
  }
  
}

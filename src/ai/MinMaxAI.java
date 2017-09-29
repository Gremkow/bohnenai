package ai;

import core.GameState;

public class MinMaxAI {
  
  GameState state;
  byte nextMove;
  
  public MinMaxAI() {
    this.state = new GameState((byte)6);
  }
  /**
   * @param enemyIndex The index that refers to the field chosen by the enemy in the last action.If this value is -1, than the AI is the starting player and has to specify the first move.
   * @return Return The index that refers to the field of the action chosen by this AI.
   */
   public int getMove(int enemyIndex) {
     int offset = (enemyIndex > 0 && enemyIndex <= 6) ? 6 : 0;

     // have to choose the first move
     if (enemyIndex == -1) {
       return 1; //return sth. between 1 and 6
     }
     //Execute enemy's move
     state.doMove((byte)(enemyIndex + offset)); //enemy's move
     
     //start calculating own move
     return calculateMove(state.clone(), 5) + offset;
   }
   
   /**
    * Calculates next index (move)
    * 
    * @param aiState last state
    * @param player false=me, true=enemy
    * @param depth maximum search depth
    * @return index of next move
    */
   private byte calculateMove(GameState startState, int depth) {
     //start calculation
     max(startState, depth, depth);
     
     return nextMove;
   }
  
   private int max(GameState aiState, int currDepth, int desiredDepth) {
     if(currDepth == 0) return aiState.assessment(); // last stage
     
     int max = Integer.MIN_VALUE; //init max value
     
     //Check each move
     for(byte i = 1; i <= 6; i++) {
       if(aiState.getSeedsInHouse(i) == 0) continue; //No move possible
       //do move
       GameState move = aiState.clone();
       move.doMove(i); //we do the move
       //other player
       int value = min(move, currDepth - 1, desiredDepth);
       if (value > max) {
         max = value;
         if (currDepth == desiredDepth);
            nextMove = i;
         }
     }
     return (max == Integer.MIN_VALUE) ? aiState.assessment() : max; //return max only if a move was done
   }
   
   private int min(GameState aiState, int currDepth, int desiredDepth) {
     if(currDepth == 0) return aiState.assessment(); // last stage
     
     int min = Integer.MAX_VALUE; //init max value
     
     //Check each move
     for(byte i = 7; i <= 12; i++) {
       if(aiState.getSeedsInHouse(i) == 0) continue; //No move possible
       //do move
       GameState move = aiState.clone();
       move.doMove(i); //enemy does the move
       //other player
       int value = max(move, currDepth - 1, desiredDepth);
       if (value < min) {
         min = value;
       }  
     }
     //No move possible (all 0)
     return (min == Integer.MAX_VALUE) ? aiState.assessment() : min; //return min only if a move was done
   }

}

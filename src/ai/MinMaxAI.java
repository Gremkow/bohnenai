package ai;

import core.GameState;

public class MinMaxAI {
  
  GameState state;
  
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
     state.doMove((byte)enemyIndex);
     
     //start calculating own move
     return calculateMove(state.clone(), false, 5);
   }
   
   /**
    * Calculates next index (move)
    * 
    * @param aiState last state
    * @param player false=me, true=enemy
    * @param depth maximum search depth
    * @return index of next move
    */
   private byte calculateMove(GameState aiState, boolean player, int depth) {
     
     return 0;
   }

}

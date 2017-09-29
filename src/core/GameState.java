package core;

import java.util.Arrays;

public class GameState {
  
  byte[] houses;
  byte mystore;
  byte enemystore;
  
  public GameState(byte seedcount) {
    seedcount = (byte) Math.min(21, seedcount); // Because 12 * 22 > 255
    houses = new byte[12];
    mystore = 0;
    enemystore = 0;
    
    Arrays.fill( houses, seedcount);
  }
  
  private GameState(GameState source) {
    houses = source.houses.clone();
    mystore = source.mystore;
    enemystore = source.enemystore;
  }
  
  
  // ------- FUNCTIONS --------
  
  /**
   * Distributes all seeds from index, counterclockwise
   * @param index the index from where to take seeds
   */
  public void doMove(byte index) {
    boolean inRow = true; //If this is true, a house with 2, 4 or 6 seeds will be collected
    byte owned = 0;
    
    if(index >= 1 && index <= 12) {
      index--;
      for (int i = index + houses[index]; i >= index + 1; i--) { //start from last index for easier point checking
        byte localIndex = (byte)(i % 12); //avoid multiple calculation
        houses[localIndex]++;
        //Check for points
        if(inRow && (houses[localIndex] == 2 || houses[localIndex] == 4 || houses[localIndex] == 6)){
          owned += houses[localIndex];
          houses[localIndex] = 0;
        } else {
          inRow = false;
        }
      }
      houses[index] = 0;
      if(index <= 6) {
        mystore += owned;
       } else {
       enemystore += owned;
       }
    }
    return;
  }
  
  /**
   * Method to asses this state wrt the heuristic. Has to return Integer.MAX_VALUE if this is a win for us
   * and has to return Integer.MIN_VALUE if this is a win for the enemy.
   * 
   * @return assesment of this state wrt heuristic
   */
  public int assessment() {
    //simplest heuristic.. 
    //TODO change to real heuristic
    return mystore;
  }
  
  // -------- GETTERS ---------
  
  public byte getSeedsInHouse(byte index) {
    if(index >= 1 && index <= 12) {
      return houses[index - 1]; //index starts at 0
    }
    return -1;
  }
  
  public byte getMyStore() {
    return mystore;
  }
  
  public byte getEnemyStore() {
    return enemystore;
  }
  
  @Override
  public GameState clone() {
    return new GameState(this);
  }
  
  @Override
  public String toString() {
    String ret = "";
    ret += houses[11] + "|" + houses[10] + "|" + houses[9] + "|" + houses[8] + "|" + houses[7] + "|" + houses[6] + "\n";
    ret += houses[0] + "|" + houses[1] + "|" + houses[2] + "|" + houses[3] + "|" + houses[4] + "|" + houses[5]+ "\n";
    ret += "My score: " + mystore + "\n";
    ret += "Enemy score " + enemystore;
    return ret;
  }

}

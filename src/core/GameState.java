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
   * @param player true = we, false = enemy
   * @param index the index from where to take seeds
   */
  public void doMove(boolean player, byte index) {
    if(index >= 1 && index <= 12) {
      index--;
      for (int i = index + 1; i <= index + houses[index]; i++) {
        houses[(i % 12)]++;
      }
      houses[index] = 0;
      //Check if someone gets beans
      processPoint(player);
    }
    return;
  }
  
  /**
   * Checks if a player has scored, removes seeds from houses and adds them to players store
   * @param player true = we, false = enemy
   */
  public void processPoint(boolean player) {
    byte owned = 0;
    
    //TODO check if player scored somewhere, remove those seeds and add them to owned variable.
    
    if(player) {
      mystore += owned;
    } else {
      enemystore += owned;
    }
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

}

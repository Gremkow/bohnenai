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
  
  public void doMove(byte index) {
    if(index >= 1 && index <= 12) {
      index--;
      for (int i = index + 1; i <= index + houses[index]; i++) {
        houses[(i % 12)]++;
      }
      houses[index] = 0;
    }
    return;
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

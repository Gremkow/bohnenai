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

    Arrays.fill(houses, seedcount);
  }

  private GameState(GameState source) {
    houses = source.houses.clone();
    mystore = source.mystore;
    enemystore = source.enemystore;
  }

  // ------- FUNCTIONS --------

  /**
   * Distributes all seeds from index, counterclockwise
   *
   * @param index the index from where to take seeds
   */
  public void doMove(byte index) {
    byte seedsGained = 0;
    int i = index + 240;
    int seedsToDistribute = houses[index - 1];
    int originalSeedCount = seedsToDistribute;

    // distributes seeds
    houses[index % 12] = 0;
    while (seedsToDistribute != 0) {
      houses[--i % 12]++;
      seedsToDistribute--;
    }

    // checks if move scored
    while (originalSeedCount != 0 && (houses[i % 12] == 2 || houses[i % 12] == 4
        || houses[i % 12] == 6)) {

      seedsGained += houses[i];
      originalSeedCount--;
      i--;
    }
    if (i <= 6) {
      mystore += seedsGained;
    } else {
      enemystore += seedsGained;
    }

  }


  /**
   * Method to asses this state wrt the heuristic. Has to return Integer.MAX_VALUE if this is a win
   * for us and has to return Integer.MIN_VALUE if this is a win for the enemy.
   *
   * @return assesment of this state wrt heuristic
   */
  public int assessment() {
    //simplest heuristic.. 
    //TODO change to real heuristic
    if (mystore >= 37) {
      return Integer.MAX_VALUE;
    } else if (enemystore >= 37) {
      return Integer.MIN_VALUE;
    }
    return mystore - enemystore;
  }

  // -------- GETTERS ---------

  public byte getSeedsInHouse(byte index) {
    if (index >= 1 && index <= 12) {
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

  /**
   * Get all seed on one side and removes them from the houses
   *
   * @param player true=we, false=enemy
   * @return true if a move is possible, false otherwise
   */
  public byte popSeeds(boolean player) {
    byte tmp;
    if (player) {
      tmp = (byte) (houses[0] + houses[1] + houses[2] + houses[3] + houses[4] + houses[5]);
      houses[0] = 0;
      houses[1] = 0;
      houses[2] = 0;
      houses[3] = 0;
      houses[4] = 0;
      houses[5] = 0;
      return tmp;
    } else {
      tmp = (byte) (houses[6] + houses[7] + houses[8] + houses[9] + houses[10] + houses[11]);
      houses[6] = 0;
      houses[7] = 0;
      houses[8] = 0;
      houses[9] = 0;
      houses[10] = 0;
      houses[11] = 0;
      return tmp;
    }
  }

  /**
   * Check if player can do a move
   *
   * @param player true=we, false=enemy
   * @return true if a move is possible, false otherwise
   */
  public boolean noMovePossible(boolean player) {
    if (player) {
      return (houses[0] + houses[1] + houses[2] + houses[3] + houses[4] + houses[5]) == 0;
    } else {
      return (houses[6] + houses[7] + houses[8] + houses[9] + houses[10] + houses[11]) == 0;
    }
  }

  @Override
  public GameState clone() {
    return new GameState(this);
  }

  @Override
  public String toString() {
    String ret = "";
    ret += houses[11] + "|" + houses[10] + "|" + houses[9] + "|" + houses[8] + "|" + houses[7] + "|"
        + houses[6] + "\n";
    ret += houses[0] + "|" + houses[1] + "|" + houses[2] + "|" + houses[3] + "|" + houses[4] + "|"
        + houses[5] + "\n";
    ret += "My score: " + mystore + "\n";
    ret += "Enemy score " + enemystore;
    return ret;
  }

}

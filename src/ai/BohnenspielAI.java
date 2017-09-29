package ai;

public class BohnenspielAI {

  I_AI ai;
  
  public BohnenspielAI() {
     ai = new MinMaxAI();
  }
	
	/**
	* @param enemyIndex The index that refers to the field chosen by the enemy in the last action.If this value is -1, than the AI is the starting player and has to specify the first move.
	* @return Return The index that refers to the field of the action chosen by this AI.
	*/
	public int getMove(int enemyIndex) {
		return ai.getMove(enemyIndex);
	}
	


}

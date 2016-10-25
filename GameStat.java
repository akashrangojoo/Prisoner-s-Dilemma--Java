/******************************************************************
  PROGRAM 2 -  PRISONER'S DILEMMA
  SUBMITTED BY : 
  1. RANGOJOO, AKASH - Z1717009
  2. JAGARLAPUDI, VENKATA SAI KARTHIK - Z1778702
  This program demonstrates example of games studies “Prisoner's Dilemma.”
  *CLASS: GameStats:
  * This class is a model for game statistics. It has a variables that track 
  * user's sentence, and opponent's sentence. It also has method to determine 
  * each game winner and update player's sentence after each round. It has various 
  * setters and getters to access its private variables.
 *****************************************************************/


package pdgame;

public class GameStat {
    private int userSentence;
    private int compSentence;
    private int rounds;
    private String strategy;
    
    /*************************************************************************
	CONSTRUCTOR 1 :
	Arguments : none
	Functionality : Initializes user and computer sentences, and round to 0
    *************************************************************************/
    public GameStat(){
        this.userSentence=0;
        this.compSentence=0;
        this.rounds=1;
    }
    
    /*************************************************************************
	Arguments : 2 integers
	Functionality : updates user and computer sentence years
    *************************************************************************/
    public void update(int userSentence, int compSentence) {
        this.userSentence+=userSentence;
        this.compSentence+=compSentence;
        this.rounds+=1;
    }
    
    /*************************************************************************
	Arguments : none 
	Functionality : Returns  the winner based on sentenced years
    *************************************************************************/
    public String getWinner(){
        if(userSentence<compSentence) return "Player";
        else if(compSentence<userSentence) return "Computer";
        else return "Its a Tie";
    }
    
    /*************************************************************************
	SETTER : setRounds
	Arguments : none 
	Functionality : Sets the rounds integer to passed argument
    *************************************************************************/
    public void setRounds(int rounds) {
        this.rounds = rounds;
    }
    
    /*************************************************************************
	SETTER : setStrategy
	Arguments : String
	Functionality : Sets strategy String to passed argument
    *************************************************************************/
    public void setStrategy(String strategy) {
        this.strategy = strategy;
    }
    
    /*************************************************************************
	GETTER : getRounds()
	Arguments : none 
	Functionality : Returns  the rounds integer
    *************************************************************************/
    public int getRounds() {
        return rounds;
    }
    
    /*************************************************************************
	GETTER : getStrategy()
	Arguments : none 
	Functionality : Returns strategy String
    *************************************************************************/
    public String getStrategy() {
        return strategy;
    }
    
    /*************************************************************************
	GETTER : getUserSentence()
	Arguments : none 
	Functionality : Returns the player sentence
    *************************************************************************/
    public int getUserSentence() {
        return userSentence;
    }
    
    /*************************************************************************
	GETTER : getCompSentence()
	Arguments : none 
	Functionality : Returns the computer sentence
    *************************************************************************/
    public int getCompSentence() {
        return compSentence;
    }
}
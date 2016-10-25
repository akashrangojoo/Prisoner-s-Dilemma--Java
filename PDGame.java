/******************************************************************
  PROGRAM 2 -  PRISONER'S DILEMMA
  SUBMITTED BY : 
  1. RANGOJOO, AKASH - Z1717009
  2. JAGARLAPUDI, VENKATA SAI KARTHIK - Z1778702
  This program demonstrates example of games studies “Prisoner's Dilemma.”
  CLASS: PDGame
  * This class has instance variables of a Game. It has setters and getters.
  * playRound method is the method that is called for each round. It calls 
  * cgetComputerDecision method to determine computer's move.
 *****************************************************************/
package pdgame;

import java.util.*;

public class PDGame {
    private ArrayList<Integer> userHistory = new ArrayList<>(); // arrayList for saving user history
    private static ArrayList<String> computerStrategies;// array list that holds computer strategies
    private GameStat stats =new GameStat(); // game stats object to update stats
    private int strategy; // strategy chosen  
    
    /*************************************************************************
	CONSTRUCTOR :
	Arguments : None
	Functionality : Initializes array list and adds all the strategies. 
    *************************************************************************/
    public PDGame(){
        computerStrategies = new ArrayList<>(); // add strategies to list
        computerStrategies.add("Tit-For-Two-Tat");
        computerStrategies.add("Tit-For-Tat");
        computerStrategies.add("Random Choice by Computer");
    }
    
    /*************************************************************************
	GETTER : getComputerStrategies()
	Arguments : none 
	Functionality : Returns  the strategies array list
    *************************************************************************/
    public static ArrayList<String> getComputerStrategies() {
        return computerStrategies;
    }
    
    /*************************************************************************
	GETTER : getStats()
	Arguments : none 
	Functionality : Returns  the statistics object
    *************************************************************************/
    public GameStat getStats() {
        return stats;
    }
    
    /*************************************************************************
        SETTER
	Arguments : 1 integer
	Functionality : sets the strategy chosen by the user
    *************************************************************************/
    public void setStrategy(int strategy) {
        this.strategy=strategy;
    }
        
    /*************************************************************************
	Arguments : 1 integer
	Functionality : updates gamestat object with user strategy. gets 
        * computer decision. compares and returns result.updates gamestat again 
        * with sentence years
    *************************************************************************/
    public String playRound(int userDecision){
        String result="";     
        if(strategy==1) stats.setStrategy("Tit-For-Two-Tat");
        else if(strategy==2) stats.setStrategy("Tit-For-Tat");
        else if(strategy==3) stats.setStrategy("Random Choice by Computer");
        
        int userSentence=0;
        int computerSentence=0;
         
        userHistory.add(userDecision);// current user decision is added to user history
        int computerDecision = getComputerDecision();// computer decision is obtained
        result = "----Computer Decision : " +computerDecision+"\n";
        // compare user and computer decision
        if(computerDecision== 1 && userDecision==1){
            userSentence=2;
            computerSentence=2;
            result += "You and your partner remain silent\n" +
            "You both get 2 years in prison.\n";
        }
        else if(computerDecision== 1 && userDecision==2){
            userSentence=1;
            computerSentence=5;
            result += "You testify against your partner and they remain silent.\n" +
            "You get 1 year in prison and they get 5.\n";
        }
        else if(computerDecision== 2 && userDecision==1){
            userSentence=5;
            computerSentence=1;
            result += "You remain silent and your partner testify against you .\n" +
            "You get 5 years in prison and they get 1.\n";
        }
        else if(computerDecision== 2 && userDecision==2){
            userSentence=3;
            computerSentence=3;
            result += "You and your partner testify against each other. \n" +
            "You both get 3 years in prison.\n";
        }
        stats.update(userSentence, computerSentence); // update sentence years in game stat object
        return result;
    }
   
    /*************************************************************************
	Arguments : none
	Functionality : this method returns the computers move depending on
        * the strategy chosen by the user. 
    *************************************************************************/
    public int getComputerDecision(){
        int computerDecision=1;
        
        if(strategy == 1){ // tit for two tat
            if(stats.getRounds()==1 || stats.getRounds() ==2){
                computerDecision=1;
            }
            else if(userHistory.get(stats.getRounds()-3)==2 && userHistory.get(stats.getRounds()-2)==2){
                computerDecision=2;
            }
            else{
                computerDecision=1;
            } // decision from int array that was populated from file is used
        }
        else if(strategy == 2){ // tit for tat
            if(stats.getRounds()==1){
                computerDecision = 1;
            }
            else{
                computerDecision = userHistory.get(stats.getRounds()-2);// user history from previos round used by computer
            }
        }
        else if(strategy == 3) //random decision
        {
            Random rand = new Random(); // random generator is intialized
            computerDecision = rand.nextInt(2) + 1; // random numbers between 0 and 1 are generated. hence 1 is added
        }
        return computerDecision;
    }    
}
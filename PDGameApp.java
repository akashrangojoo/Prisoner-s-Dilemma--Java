/******************************************************************
  PROGRAM 2 -  PRISONER'S DILEMMA
  SUBMITTED BY : 
  1. RANGOJOO, AKASH - Z1717009
  2. JAGARLAPUDI, VENKATA SAI KARTHIK - Z1778702
  This program demonstrates example of games studies “Prisoner's Dilemma.”
  CLASS PDGameApp :
  * This class provides an interface for user input. It has main which 
  * initializes the PDGame object. and iterates through each round. Finally 
  * game statistics are printed
 *****************************************************************/

package pdgame;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Scanner;
import java.util.Set;

public class PDGameApp {
    public static void main (String [] args){
        boolean flag=true; // to loop until false
        Scanner scan = new Scanner(System.in); // to scan user inputs
        HashMap<String,GameStat> gameStatHash = new HashMap<>(); // to save game stats history
        int strategy=0; 
        
        while(flag){ // loop until flag is false
            PDGame newGame = new PDGame(); // new PDGame object created
            ArrayList<String> strategyList = new ArrayList<>(); // array list to hold strategies
            System.out.println("***Starting A Game of Prisoner's Dilemma ***  "
                    + "5 rounds in each game");
            System.out.println("HERE ARE STRATEGIES AVAILABLE FOR THE "
                    + "COMPUTER");
            
            strategyList=PDGame.getComputerStrategies(); // get strategies
           
            for(int i=0;i<strategyList.size();i++){
                System.out.println(i+1+". "+strategyList.get(i)); //display strategies
            }
            
            System.out.print("Select a strategy from above for the Computer "
                    + "to use in the 5 rounds :  ");
            boolean tempFlag=true; // to eliminate illegal inputs
            while(tempFlag){
                String strat = scan.next();  // get selected trategy from user
                if(strat.equals("1") || strat.equals("2")|| strat.equals("3")){
                tempFlag=false; // correct strategy, exit this loop
                strategy = Integer.parseInt(strat); // parse into integer
                }
                else{ // ask user to re-enter correct strategy
                    System.out.print("Illegal Choice - Select 1 or 2 or 3 : ");
                }
            }
            GameStat gameStats= new GameStat(); // new gamestats obj to hold this present game stats
            newGame.setStrategy(strategy); // set strategy
            
            //begin round
            for(int k=0;k<5;k++){
                System.out.println("\nBEGIN ROUND "+ (k+1) +" - Here are your 2 choices");
                System.out.println("1. Cooperate and remain silent.");
                System.out.println("2. Betray and testify against.");
                System.out.print(" ----What is your decision this round?  ");
                int userInput=1;
                tempFlag=true;
                while(tempFlag){ //to eliminate illegal inputs
                    String tempInput = scan.next();
                    if(tempInput.equals("1") || tempInput.equals("2")){
                        tempFlag=false; // correct strategy, exit this loop
                        userInput = Integer.parseInt(tempInput); // parse into integer
                    }
                    else{ // ask user to re-enter correct strategy
                        System.out.print("Illegal Choice - Select 1 or 2 : ");  
                        tempFlag = true; // if illegal entry.. loop again to get new value
                    }                    
                }
                
                String roundResult=newGame.playRound(userInput); // call playround og PDGame 
                System.out.println(roundResult); // print this round result
            }   //rounds ended         
            gameStats=newGame.getStats();// gets stats
            String currentTime= (new Date()).toString(); // get time and convert to string
            gameStatHash.put(currentTime,gameStats); // put stats in a hash map
            System.out.println("END OF ROUNDS, GAME OVER\n --GAME STATS --\n"
                    + "------Your prison sentence is: "+
                    gameStats.getUserSentence()+"\n" +
                    "------Your partner's prison sentence is: "+
                    gameStats.getCompSentence());
            
            tempFlag=true;
            System.out.print("\n\n--Would you like to play another game (y/n)? ");
            while(tempFlag){ //to eliminate illegal inputs
                String inAnswerString = scan.next();  //read in the letter
         
                if(inAnswerString.equalsIgnoreCase("y")){
                    flag=true; // if true new game is sarted
                    tempFlag=false;
                }
                else if(inAnswerString.equalsIgnoreCase("n")){
                    flag=false; // if false final stats are printed 
                    tempFlag=false;
                }               
                else{
                    System.out.print("Illegal Choice - Choose y/n : ");
                    tempFlag=true;
                }
            }               
        } 
        
        System.out.println("\n\nFinal Stats:");
        
        Set<String> keySet= gameStatHash.keySet(); // get keyset
        
        ArrayList<String> keyList = new ArrayList<>(); // copy ket set to array list
        for (String key : keySet) { 
            keyList.add(key);
        }
        int temp=1;
        Collections.sort(keyList); // sort arrray list according to time
        for(String key: keyList){ //print game stats according to time
            GameStat tempStat = gameStatHash.get(key);
            String time=key.substring(4,20);
            System.out.println("Game "+ (temp++) +" at "+time+
                    "winner is --"+tempStat.getWinner()+". The computer used "
                    +tempStat.getStrategy());
        }
    }
}
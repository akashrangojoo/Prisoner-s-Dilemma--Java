/******************************************************************
  PROGRAM 3 -  PRISONER'S DILEMMA GUI
  SUBMITTED BY : 
  1. RANGOJOO, AKASH - Z1717009
  2. JAGARLAPUDI, VENKATA SAI KARTHIK - Z1778702
  This program demonstrates example of games studies “Prisoner's Dilemma.”
  CLASS PDGameGUI :
  * This class provides a GUI interface for user input. It has main which 
  * initializes the PDGame object.It has two main panels. Left panel has 
  * previous games in a scrollable list.When one item is selected, corresponding
  * game's values are shown below. In the right panel, we have a combo box
  * to select the strategy for computer and button to start game. There are two
  * decision buttons (Silent and Testify). These are disabled before game starts.
  * Once we click on new game, new game button is disabled and decision buttons are
  * enabled. Game ends when all rounds end.At this time new game button is enabled.
 *****************************************************************/

package pdgame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.HashMap;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class PDGameGUI extends JFrame implements ActionListener,ListSelectionListener 
{    
    private final DefaultListModel<String> listModel;
    private final JList<String> finishedGamesList;
    //Jtext area to display current game play
    private final JTextArea gameResultsTA = new JTextArea(15, 30);
    //three JButtons to start a game and to continue game play
    private final JButton startButton = new JButton("Start New Game");
    private final JButton silentButton = new JButton("Remain Silent");
    private final JButton testifyButton = new JButton("Testify");
    //JComboBox to hold strategies available for the computer
    private final JComboBox<Object> computerStrategyCB;
    // Text fields to hold game stats values
    private final JTextField roundsPlayedTF;
    private final JTextField compStratTF;
    private final JTextField playerSentTF;
    private final JTextField compSentTF;
    private final JTextField winnerTF;
    //Hashmap to save game stat objects
    private final HashMap<String, GameStat> stats = new HashMap<>();
    //Time when a game is started to use a a key in hash map
    private String gameStartTime = null;
    //References to PDGame and GameStat
    private PDGame currentPDGame = null;
    private GameStat gameStats = null;
    //Fixed number of rounds per game
    private final int rounds=5;
     
    /*************************************************************************
	CONSTRUCTOR :
	Arguments : None
	Functionality : Sets title, Initializes a PDGame object. Panels are
        * created and all GUI components are added to the Frame. Background colors
        * are set.
    *************************************************************************/
    
    public PDGameGUI() {
        super("Prisoner's Dilemma");
        this.listModel = new DefaultListModel<>();
        currentPDGame = new PDGame();
        setLayout(new BorderLayout()); //set layout to BorderLayout
        //Labels to display on screen
        JLabel computerStrategyLabel = new JLabel("Computer Strategy ");
        JLabel decisionLabel = new JLabel("Your decision this round?");
        //Various panels used to add components
        JPanel panel1 = new JPanel(new GridLayout(2,1));//outer left panel-contains list of games
        JPanel panel2 = new JPanel(new BorderLayout());//outer right panel for game play
        JPanel panel3 = new JPanel(new GridLayout(5,2));// left lower to display stats
        JPanel panel4 = new JPanel(new GridLayout(2,1));// right top, contains buttons and combo box
        JPanel panel5 = new JPanel();//added to panel4 in the top - contains combo box and new game button
        JPanel panel6 = new JPanel();//added to panel4 in the bottom - contains decision buttons
        JScrollPane panel7 = new JScrollPane();// right bottom pane to which text area is added
        
        //seting borders
        panel1.setBorder(new TitledBorder(new EtchedBorder(), "List of Games"));
        panel2.setBorder(new TitledBorder(new EtchedBorder(), "Current Game"));
        //adding outere panels to frame first
        add(panel1, BorderLayout.WEST); 
        add(panel2, BorderLayout.EAST);
        //List of previous games
        finishedGamesList = new JList<>(listModel);
        finishedGamesList.setFont(new Font("SansSerif", Font.BOLD, 12));
        finishedGamesList.setVisibleRowCount(10);
        finishedGamesList.setFixedCellWidth(400);
        finishedGamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        // Labels for left lower panel
        JLabel roundsPlayedLabel = new JLabel("Rounds Played");
        JLabel compStratLabel = new JLabel("Computer Strategy");
        JLabel playerSentLabel = new JLabel("Player Sentence");
        JLabel compSentLabel = new JLabel("Computer Sentence");
        JLabel winnerLabel = new JLabel("Winner");
        // Text fields for lower left panel
        roundsPlayedTF = new JTextField();
        compStratTF = new JTextField();
        playerSentTF = new JTextField();
        compSentTF = new JTextField();
        winnerTF = new JTextField();
        //add labels and text fields to panel3
        panel3.add(roundsPlayedLabel);
        panel3.add(roundsPlayedTF);
        panel3.add(compStratLabel);
        panel3.add(compStratTF);
        panel3.add(playerSentLabel);
        panel3.add(playerSentTF);
        panel3.add(compSentLabel);
        panel3.add(compSentTF);
        panel3.add(winnerLabel);
        panel3.add(winnerTF);
        
        //add list and panel 3 to panel1
        panel1.add(new JScrollPane(finishedGamesList));
        panel1.add(panel3);
        
        //create combo box by getting strategies
        Object[] strategyArray = PDGame.getComputerStrategies().toArray();//convert AL to array
        computerStrategyCB = new JComboBox<>(strategyArray);
        computerStrategyCB.setEditable(false);
        computerStrategyCB.setSelectedIndex(0);
        
        //add startegy label, combo box and start button to panel 5
        panel5.add(computerStrategyLabel);
        panel5.add(computerStrategyCB);
        panel5.add(this.startButton);
        
        //add decision label and buttons to panel 6
        panel6.add(decisionLabel);
        panel6.add(this.silentButton);
        panel6.add(this.testifyButton);
        
        //add panel5, panel6 to panel 4
        panel4.add(panel5);
        panel4.add(panel6);
        //add panel4 on "north" of panel2
        panel2.add(panel4,BorderLayout.NORTH);
        panel7.add(gameResultsTA); // add text area to scrollable panel 7
        panel2.add(new JScrollPane(gameResultsTA),BorderLayout.SOUTH);
        //disable decision buttons. these are enables when new game button is clicked
        silentButton.setEnabled(false);
        testifyButton.setEnabled(false);
        //set all text areas and fields as non editable
        gameResultsTA.setEditable(false);
        roundsPlayedTF.setEditable(false);
        winnerTF.setEditable(false);
        compStratTF.setEditable(false);
        compSentTF.setEditable(false);
        playerSentTF.setEditable(false);
        //set backgroud color for aa text field as "white"
        roundsPlayedTF.setBackground(Color.WHITE);
        winnerTF.setBackground(Color.WHITE);
        compStratTF.setBackground(Color.WHITE);
        compSentTF.setBackground(Color.WHITE);
        playerSentTF.setBackground(Color.WHITE);
        
        Color c2 = new Color(156, 180, 193);
        //set bg color to all panels
        panel5.setBackground(c2);
        panel4.setBackground(c2);
        panel6.setBackground(c2);
        panel2.setBackground(c2);
        panel1.setBackground(c2);
        panel3.setBackground(c2);
    } //end constructor
    
    /*************************************************************************
	Method : main
        Arguments : String[]
	Functionality : Execution starts here. Calls static method to create GUI
    *************************************************************************/
    public static void main(String[] args) 
    {
        createAndShowGUI();// call static method createandShowGUI()    
    } 
    
    /*************************************************************************
	Method : createAndShowGUI()
        Arguments : None
	Functionality : Creates a new PDGameGUI obj which calls the class 
        * constructor. The constructor add all panels and components to the 
        * screen. Then listeners are added and setVisible is set true.
    *************************************************************************/
    public static void createAndShowGUI() 
    {
        PDGameGUI pdg1 = new PDGameGUI(); //call constructor above to set the window to user
        pdg1.addListeners();     //method will add listeners to buttons
        pdg1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//sets default close operation
        pdg1.pack();// Display the window and pack together all the panels, etc
        pdg1.setVisible(true);      
    }  
    
    /*************************************************************************
	Method : addListeners()
        Arguments : None
	Functionality : Adds all listeners to interacting components
    *************************************************************************/
    public void addListeners() 
    {
        //adding listeners to all buttons
        startButton.addActionListener(this); 
        silentButton.addActionListener(this);
        testifyButton.addActionListener(this);
        //adding listener to Listselection
        finishedGamesList.addListSelectionListener(this);
    }
    
    /*************************************************************************
	Method : actionPerformed()
        Arguments : ActionEvent
	Functionality : Decides which button is clicked and acts accordingly.
        * If start is clicked, a new game is started
        * If Silent or Testify  is clicked a new round is played.
    *************************************************************************/
    @Override
    public void actionPerformed(ActionEvent e) 
    {
        if(e.getSource()== silentButton)// if silen button is clicked
        {
            continueGame(1);
        }
        
        else if(e.getSource()== testifyButton)
        {
            continueGame(2);
        }
        else if(e.getSource()== startButton) // if new game button is clicked
        {
            startGame();
        }
    }

    /*************************************************************************
	Method : valueChanged()
        Arguments : ListenerSelectionEvent
	Functionality : The new selected value is copied into a string and 
        * corresponding gameStat object is retrieved from the hash map.Then 
        * various values are obtained from that object and set to Text fields
    *************************************************************************/
    @Override
    public void valueChanged(ListSelectionEvent e) // when another list element is selected 
    {
        if(!finishedGamesList.isSelectionEmpty()){
            String currTime= finishedGamesList.getSelectedValue().toString(); // get element selected
            GameStat tempStats = stats.get(currTime); //get game stats using above as key
            int compSent = tempStats.getCompSentence();
            int playerSent = tempStats.getCompSentence();
            //set all the text fields in the lower left panel
            roundsPlayedTF.setText(String.valueOf(tempStats.getRounds()-1)+ " rounds"); 
            winnerTF.setText(tempStats.getWinner());
            compSentTF.setText(String.format("%d %s", compSent, 
                 ((compSent > 1) ? " years" : " year")));
            playerSentTF.setText(String.format("%d %s", playerSent, 
                 ((playerSent > 1) ? " years" : " year")));
            compStratTF.setText(tempStats.getStrategy());
        }
    }
    
    
    /*************************************************************************
	Method : continueGame()
        Arguments : integer
	Functionality : Called when any decision button is clicked. present 
        * round number is checked and if its less than total rounds player is 
        * prompted for new round decision. if not, game is ended.
    *************************************************************************/
    public void continueGame(int playerDecision){
        String result=currentPDGame.playRound(playerDecision); // round is played
        gameResultsTA.append(result); // result is added to text area
        gameStats=currentPDGame.getStats();//gets stats
        if(gameStats.getRounds()<=rounds) //if curr round less than fixed rounds-continue 
        {
            gameResultsTA.append("\nBEGIN ROUND - "+gameStats.getRounds()+
                " Here are your 2 choices\n"+
                "1. Cooperate and remain silent.\n"+
                "2. Betray and testify against.\n"+
                " ----What is your decision this round? \n ");
        }
        else // else end game
        {
            endGame();
        }
    }
    
    /*************************************************************************
	Method : endGame()
        Arguments : none
	Functionality : Called when rounds end. stats are put in a hashmap. also
        * the game start time is pushed into a list so that it appears in the 
        * completed games list. start button is re enabled and decision buttons 
        * are disabled
    *************************************************************************/
    public void endGame(){
        stats.put(gameStartTime,gameStats); // put stats in a hash map
        listModel.addElement(gameStartTime); // add time to list
        gameResultsTA.append("\nEND OF ROUNDS, GAME OVER\n -- GAME STATS --\n"
            + "------Your prison sentence is: "+
            gameStats.getUserSentence()+"\n" +
            "------Your partner's prison sentence is: "+
            gameStats.getCompSentence());
        startButton.setEnabled(true); // re-enable start button
        computerStrategyCB.setEnabled(true);//re-enable strategy combo box
        silentButton.setEnabled(false);//disable decision boxes
        testifyButton.setEnabled(false);
    }
    
    /*************************************************************************
	Method : startGame()
        Arguments : none
	Functionality : Called when start new game button is clicked. New pdgame
        * object is created and strategy is set. decision buttons are now enabled 
        * and start button is disabled.and player is prompted for decision.
    *************************************************************************/
    public void startGame(){
        currentPDGame=new PDGame(); // create new PDGame object
        gameStats=currentPDGame.getStats(); // reset game stats obj
        gameResultsTA.setText("***Starting A Game of Prisoner's Dilemma ***\n\n");
        gameStartTime = (new Date()).toString(); // get time and convert to string
        currentPDGame.setStrategy(computerStrategyCB.getSelectedIndex()+1); //get the strategy
        startButton.setEnabled(false); // disable start button- will be enabled after end game
        computerStrategyCB.setEnabled(false);// disable strategy combo box
        silentButton.setEnabled(true); // enable decision buttons
        testifyButton.setEnabled(true);
            
        gameResultsTA.append("\nBEGIN ROUND - "+gameStats.getRounds()+" Here are your 2 choices\n"+
            "1. Cooperate and remain silent.\n"+
            "2. Betray and testify against.\n"+
            " ----What is your decision this round? \n ");
    }
}

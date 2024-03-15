package model;

import java.util.ArrayList;

public class MasterMindGame
{
    private int currentRound = 0; //Current round
    private int nbRoud; //Number of rounds to be played
    private int score = 0; //Global score of the game
    private int nbTry; //Number of try to guess the secret combination
    private int lineSize; //Number of cells in a combination
    private int colorCount; //Number of colors available
    private String playerName; //Player name
    private CluesMode cluesMode = CluesMode.EASY_MODE; //Clues mode
    private MasterMindBoard masterMindBoard; //The MasterMind board
    private ArrayList<GameColor> availableColors; //List of the available colors

    public MasterMindGame(String playerName, int nbRoud,int lineSize,int colorCount,int nbTry,CluesMode cluesMode)
    {
        this.nbRoud=nbRoud;
        this.playerName=playerName;
        this.lineSize=lineSize;
        this.colorCount = colorCount;
        this.cluesMode=cluesMode;

        this.nbTry=nbTry;

        this.availableColors = new ArrayList<GameColor>();

        generateListAvailableGameColor();
        generateNewRound();
    }

    //***********************
    //*** All the getters ***
    //***********************

    //The current round
    public int getCurrentRound() {
        return this.currentRound;
    }
    //Total's round
    public int getNbRoud(){
        return this.nbRoud;
    }
    //Score
    public int getScore() {
        return this.score;
    }
    //Number of try per round
    public int getNbTry(){
        return this.nbTry;
    }
    //Le lenght of a line (= size the combination)
    public int getLineSize() {
        return lineSize;
    }
    //Number of color available
    public int getColorCount() {
        return colorCount;
    }
    //Player name
    public String getPlayerName() {
        return playerName;
    }
    //Clues mode
    public CluesMode getCluesMode() {
        return this.cluesMode;
    }
    //MasterMindBoard
    public MasterMindBoard getMasterMindBoard() {
        return this.masterMindBoard;
    }
    //The list of available color
    public ArrayList<GameColor> getAvailableColors() {
        return availableColors;
    }
    //***********************
    //***** End getters *****
    //***********************


    //Method that creates a new round of the game by creating a whole new board
    public boolean generateNewRound()
    {
        this.currentRound++;

        if(currentRound<=nbRoud)
        {
            this.masterMindBoard = new MasterMindBoard(this.lineSize, nbTry, availableColors);
        }
        else
        {
            return false;
        }
        return true;
    }


    //Method that generates a list with all the colors available in the current game
    public void generateListAvailableGameColor()
    {
        for(int i=0;i<this.colorCount;i++)
        {
            this.availableColors.add(GameColor.values()[i]);
        }
    }

    //Method that update the score after each round
    public void updateScore()
    {
        MasterMindLine line=this.getMasterMindBoard().getLastLine();

        this.score += line.getWellChosen() + line.getWellPlaced() * 3;

        if(this.cluesMode == CluesMode.CLASSIC_MODE)
        {
            this.score += 4;
        }
    }

    //Method that print informations about the game on the terminal
    public void printInfoAboutGame()
    {
        System.out.println("    --> Jeu de Mastermind en ligne de commande\t <--");
        System.out.println("    --> Voici les paramêtres\t\t\t\t\t <--");
        System.out.println("    --> MasterMind Info\t\t\t\t\t\t\t <--");
        System.out.println("    --> Nom joueur : "+this.playerName+"\t\t\t\t\t\t <--");
        System.out.println("    --> Nb manches : "+this.nbRoud+"\t\t\t\t\t\t\t <--");
        System.out.println("    --> Nb tentatives/manches : "+this.nbTry+"\t\t\t\t <--");
        System.out.println("    --> Nb total couleur : " +this.colorCount+"\t\t\t\t\t <--");
        System.out.println("    --> Tour actuel : "+this.currentRound+"\t\t\t\t\t\t\t <--");
        System.out.println("    --> Score : "+this.score+"\t\t\t\t\t\t\t\t <--");
        printAvailableColor();
    }

    //Method that print all the available color on the terminal
    public void printAvailableColor()
    {
        System.out.println("Couleur disponible : ");
        for(int i=0;i<this.availableColors.size();i++)
            System.out.print(this.availableColors.get(i)+" ");
    }
}

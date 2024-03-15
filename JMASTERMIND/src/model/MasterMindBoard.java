package model;

import helpersLib.Helpers;

import java.util.ArrayList;
import java.util.Random;

public class MasterMindBoard
{
    private int lineCount; //Number of lines in the board
    private int lineSize; //Size of a board line
    private int currentLine=0; //Index of the currentLine
    private MasterMindLine secretCombination; //Secret combination of the current board
    private ArrayList<MasterMindLine> board; //Board that contains all the lines
    private ArrayList<GameColor> availableColors; //Available colors

    public MasterMindBoard(int lineSize, int lineCount, ArrayList<GameColor> availableColors)
    {
        this.lineSize = lineSize;
        this.lineCount = lineCount;

        this.availableColors = availableColors;
        this.board = new ArrayList<MasterMindLine>();
        this.secretCombination = new MasterMindLine(this.lineSize);

        generateBoardLines();
        generateSecretCombination();

        //printSecretCombination();
    }

    //Getters
    //The current line
    public MasterMindLine getCurrentLine()
    {
        return this.board.get(this.currentLine);
    }
    //The index of the current line
    public int getIndexCurrentLine()
    {
        return this.currentLine;
    }
    //The secret combination
    public MasterMindLine getSecretCombination()
    {
        return this.secretCombination;
    }
    //The board
    public ArrayList<MasterMindLine> getBoard()
    {
        return this.board;
    }
    //Get the last line (the last of the board or the last with the combination found)
    public MasterMindLine getLastLine()
    {
        int index=this.currentLine;
        if(currentLine>=this.board.size())
            index=this.board.size()-1;
        return this.board.get(index);
    }

    //Function that select the next line of the board
    //the function returns true if the next line exists
    public boolean nextLine()
    {
        this.currentLine++;

        return this.currentLine < this.lineCount;
    }

    //Method that generate all the empty lines of the board
    private void generateBoardLines()
    {
        for(int i=0; i<this.lineCount; i++)
        {
            this.board.add(new MasterMindLine(this.lineSize));
        }
    }

    //Method that fills the secretCombination with random colors
    //within the available colors list
    private void generateSecretCombination()
    {
        int value;

        Random rand = new Random();

        for(int i=0; i<this.lineSize; i++)
        {
            value= rand.nextInt(this.availableColors.size());
            this.secretCombination.setCellColor(this.availableColors.get(value),i);
        }
    }

    //Function used to verify the state of the current line
    //returns true if the line is equal to the secret combination
    public boolean verifyCurrentLine()
    {
        return this.board.get(currentLine).verify(this.secretCombination);
    }

    //Method that prints the secret combination in the terminal
    public void printSecretCombination()
    {
        for(int i=0; i<this.lineSize;i++)
        {
            System.out.print(this.secretCombination.getCellColor(i) + " ");
        }

        System.out.println();
    }

    //Method that returns a String with the color of the secret combination in french separate by space
    public String secretCombinaisonToString()
    {
        String secret="";
        for(GameColor c : this.secretCombination.getCells())
            secret+= Helpers.translateColorToFrench(c).toUpperCase()+" ";
        return secret;
    }
}

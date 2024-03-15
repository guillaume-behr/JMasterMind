package controller;

import model.CluesMode;
import model.MasterMindGame;
import view.EndWindow;
import view.GameWindow;
import view.StartWindow;

public class GameMasterController
{
    private StartWindow startWindow; //Start window where the player select the game parameters
    private GameWindow gameWindow; //Game window where the mastermind is played
    private EndWindow endWindow; //End window where the score is displayed
    private MasterMindGame game; //The game (=the models of our MVC)

    //The constructor is empty
    public GameMasterController(){}

    //Method used by the main class to start a new game
    //the method creates a new start window for the player to select the game parameters
    public void startNewGame()
    {
        this.startWindow = new StartWindow(this);
        if(this.endWindow!=null)
            this.endWindow.dispose();
    }

    //Method that is used by the view to launch the game
    //with the selected parameters
    public void launchGame(String playerName,int nbRound,int lineSize,int lineCount,int colorCount,CluesMode cluesMode)
    {
        startWindow.dispose();
        if(gameWindow!=null)
             gameWindow.dispose();//Si relance une partie alors que déjà en cours
        this.game = new MasterMindGame(playerName,nbRound,lineSize,colorCount,lineCount,cluesMode);
        this.gameWindow=new GameWindow(this,game,playerName,nbRound,lineSize,lineCount,colorCount);
    }

    //Method that create a new round
    //(Different from lauchGame() because launche game is to launch the game at the beginning and newRound() is at the end of every round)
    public void newRound(String playerName,int nbRound,int lineSize,int lineCount,int colorCount, boolean abandond)
    {
        if(!abandond)
            game.updateScore();

        if(game.generateNewRound())
        {
            this.gameWindow.dispose();
            this.gameWindow = new GameWindow(this, game,playerName, nbRound, lineSize, lineCount, colorCount);
        }
        else
        {
            this.endGame();
        }
    }

    //Method that ends the current game
    public void endGame()
    {
        this.gameWindow.dispose();
        this.endWindow = new EndWindow(this.game,this);
    }

    //Function used to verify the state of the current line
    //returns true if the line is equal to the secret combination
    public void verifyCurrentLine()
    {
        if(game.getMasterMindBoard().verifyCurrentLine())
        {
            printFoundToPlayer();
            this.newRound(this.game.getPlayerName(), this.game.getNbRoud(), this.game.getLineSize(), this.game.getNbTry(), this.game.getColorCount(),false);
            return;
        }
        game.getMasterMindBoard().getCurrentLine().printAllInformationsAboutTheLine(game.getMasterMindBoard().getSecretCombination());

        this.gameWindow.updateCombBox();
        this.gameWindow.updateClues();

        this.nextLine();
    }

    //Function that selects the next line in the MasterMinBoard
    //return true if the next line exists
    public void nextLine()
    {
        if(!game.getMasterMindBoard().nextLine())
        {
            printFailToPlayer();
            this.newRound(this.game.getPlayerName(), this.game.getNbRoud(), this.game.getLineSize(), this.game.getNbTry(), this.game.getColorCount(),false);
        }
    }

    //Method that calls another method of the view to make it close
    public void exitStartWindow()
    {
        this.startWindow.closeWindow();
    }

    //Method that calls anoter method of the view to inform it to show a fail message to the player
    public void printFailToPlayer()
    {
        gameWindow.showFailToPlayer();
    }

    //Method that calls anoter method of the view to inform it to show a victory message to the player
    public void printFoundToPlayer()
    {
        gameWindow.showFoundToPlayer();
    }

    //Method that shut down the app
    public void shutDownApp()
    {
        if(this.startWindow!=null)
            this.startWindow.dispose();
        if(this.gameWindow!=null)
            this.gameWindow.dispose();
        if(this.endWindow!=null)
            this.endWindow.dispose();
    }
}

package view;

import controller.GameMasterController;
import view.startPanels.StartPanel;

import javax.swing.*;
import java.awt.*;

//Start window were the player choses the game options
public class StartWindow extends JFrame//Remember to extend JFrame
{
    private GameMasterController controller;

    public StartWindow(GameMasterController gmc)
    {
        super("Menu Principal");//or setTitle("Menu Principal");
        this.setSize( 600, 920 );
        this.setLocationRelativeTo(null);//Window spawn in center screen
        //Change default icone of the window
        ImageIcon icon = new ImageIcon("./images/icon_start_game.png");
        this.setIconImage(icon.getImage());

        this.controller=gmc;

        //A minimum size
        this.setMinimumSize(new Dimension(600,1000));
        this.setResizable(false);//Cannot resize window in order to avoid strange display

        JPanel startPanel=new StartPanel(gmc);

        this.setContentPane(startPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);//Close the program when we click on the cross
        this.setVisible(true);
    }

    //Method that close this windows
    public void closeWindow()
    {
        this.dispose();
    }
}

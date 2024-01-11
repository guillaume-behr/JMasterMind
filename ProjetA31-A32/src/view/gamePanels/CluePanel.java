package view.gamePanels;

import controller.GameMasterController;
import model.CellInfo;
import model.CluesMode;
import model.MasterMindGame;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

//The clue Panel
public class CluePanel extends JPanel
{
    private GameMasterController controller;
    private MasterMindGame game;
    private ArrayList<Component> clues;

    public CluePanel(int lineCount, int lineSize, GameMasterController controller,MasterMindGame game)
    {
        this.controller = controller;
        this.game=game;
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        this.clues = new ArrayList<Component>();

        //If we are in numeric mode, we printf dash that will be replaced by the text that says the number of found and good place
        if(game.getCluesMode()==CluesMode.NUMERIC_MODE)
        {
            for (int i = 0; i < lineCount; i++)
            {
                JLabel lbl = new JLabel("----------------------");
                lbl.setOpaque(true);
                lbl.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,30));
                lbl.setAlignmentX(CENTER_ALIGNMENT);
                this.add(Box.createVerticalStrut(10));//Empty vertcal space between

                clues.add(lbl);
                this.add(lbl);
            }
        }
        //Else (classic mode or easy mode)
        //We create small label but with an emty texte that we color in black or white for the clues
        else
        {
            for (int i = 0; i < lineCount; i++)
            {
                JPanel clue = new JPanel();
                clue.setLayout(new FlowLayout());
                for (int j = 0; j < lineSize; j++)
                {
                    JLabel lbl = new JLabel("  ");
                    lbl.setOpaque(false);//Default, we don't see the color of the label
                    lbl.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,30));
                    clue.add(lbl);
                }
                clue.setBackground(new Color(140, 218, 218));

                clues.add(clue);
                this.add(clue);
            }
        }
    }

    //Method that update the clue of the index of the line get in parameter
    public void updateClues(int line)
    {
        Component c = this.clues.get(line+1);

        //In numeric mode, we print the text
        if(this.game.getCluesMode() == CluesMode.NUMERIC_MODE)
        {
            JLabel clue=(JLabel)c;
            clue.setText(
                        "Bien placé : "
                        + this.game.getMasterMindBoard().getCurrentLine().getWellPlaced()
                        + " | Mal placé : "
                        + this.game.getMasterMindBoard().getCurrentLine().getWellChosen());
        }
        //Else (classic and easy mode)
        //We color it in with or black
        else
        {
            JPanel p=(JPanel) c;
            ArrayList<CellInfo> info=game.getMasterMindBoard().getCurrentLine().getCellInfos();
            int i=0;

            //In easy mod, the white pawn are at the good place
            if (this.game.getCluesMode()==CluesMode.EASY_MODE)
            {
                for (Component cc : p.getComponents()) {
                    JLabel l = (JLabel) cc;
                    l.setOpaque(true);
                    if (info.get(i) == CellInfo.WELL_PLACED)
                        l.setBackground(Color.WHITE);
                    else
                        l.setBackground(Color.BLACK);
                    i++;
                }
            }
            //In classic mode, we print first the white, than the black
            else
            {
                i=0;
                int nbWellPlace=game.getMasterMindBoard().getCurrentLine().getWellPlaced();
                for (Component cc : p.getComponents())
                {
                    JLabel l = (JLabel) cc;
                    l.setOpaque(true);
                    if(i>=nbWellPlace)
                        l.setBackground(Color.BLACK);
                    else
                        l.setBackground(Color.WHITE);
                    i++;
                }
            }
        }
    }
}

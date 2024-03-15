package view.gamePanels;

import helpersLib.Helpers;
import model.MasterMindGame;

import javax.swing.*;
import java.awt.*;

//Top panel of the game window
public class TopPanel extends JPanel
{
    public TopPanel(String playerName,int nbRound, MasterMindGame masterMindGame)
    {
        JLabel lblTitle=new JLabel("MASTERMIND");
        lblTitle.setMinimumSize(new Dimension(50,50));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(Color.RED);
        lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));

        FlowLayout flowLayout=new FlowLayout();
        flowLayout.setHgap(100);//Horizontal space between components
        JPanel pnlInfoPlayer=new JPanel(flowLayout);

        JPanel pnlTitle=new JPanel(new FlowLayout());
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));
        pnlTitle.add(lblTitle);
        this.add(pnlTitle);

        JPanel pnlInfoGame=new JPanel();
        pnlInfoPlayer.setLayout(new GridLayout(3,2));
        this.add(pnlInfoGame);

        JLabel lblPlayerName=constructTopBigLabel("Pseudo : "+playerName);
        JLabel lblScore=constructTopBigLabel("Score : "+masterMindGame.getScore());
        JLabel lblRound=constructTopBigLabel("Tour "+masterMindGame.getCurrentRound()+" / "+nbRound);

        pnlInfoGame.add(lblPlayerName);
        pnlInfoGame.add(lblScore);
        pnlInfoGame.add(lblRound);

        pnlInfoGame.setLayout(new GridLayout(2,2));

        JLabel lblNbPiece=constructTopSmallLabel("Taille d'une combinaison : "+masterMindGame.getLineSize());
        JLabel lblNbColor=constructTopSmallLabel("Nombre de couleurs : "+masterMindGame.getColorCount());
        JLabel lblCluesMode=constructTopSmallLabel("Mode des indices : "+
                Helpers.translateCluesModeToFrench(masterMindGame.getCluesMode()));

        pnlInfoGame.add(lblNbPiece);
        pnlInfoGame.add(lblNbColor);
        pnlInfoGame.add(lblCluesMode);

        pnlTitle.setBackground(new Color(216, 222, 152));
        pnlInfoGame.setBackground(new Color(216, 222, 152));

        this.add(pnlInfoGame);
    }

    //Construct a big label with the text in parameter
    private JLabel constructTopBigLabel(String texte)
    {
        JLabel bigLabel=new JLabel(texte);
        bigLabel.setMinimumSize(new Dimension(50,50));
        bigLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        bigLabel.setForeground(Color.BLACK);
        bigLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        bigLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return bigLabel;
    }

    //Construct a small Label with the text in parameter
    private JLabel constructTopSmallLabel(String texte)
    {
        JLabel smallLabel=new JLabel(texte);
        smallLabel.setAlignmentX(CENTER_ALIGNMENT);
        smallLabel.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,20));
        smallLabel.setHorizontalAlignment(SwingConstants.CENTER);
        return smallLabel;
    }
}

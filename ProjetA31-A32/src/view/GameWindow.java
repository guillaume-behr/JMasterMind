package view;

import controller.GameMasterController;
import model.GameColor;
import model.MasterMindGame;
import model.MasterMindGameObserver;
import view.GamePanels.*;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameWindow extends JFrame implements MasterMindGameObserver
{
    //------------------------------------------------------------------
    private GameMasterController controller;
    private MasterMindGame masterMindGame;
    private int lineSize;
    private int lineCount;
    private int colorCount;
    private int nbRound;
    private int activeLine;
    private String playerName;

    private JPanel boardPanel;
    private JPanel pnlModeIndice;
    private JPanel pnlIndice;
    private JPanel pnlNumeric;
    private JPanel pnlEasyClassicMode;
    private JLabel lblRound;
    private CluePanel pnlClue;
    //------------------------------------------------------------------

    public GameWindow(GameMasterController controller,MasterMindGame game,String playerName,
                      int nbRound,int lineSize,int lineCount, int colorCount)
    {
        super("MasterMind");
        setSize(1100,900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

        this.activeLine = lineCount-1;
        this.controller = controller;
        this.masterMindGame = game;
        this.playerName = playerName;
        this.nbRound = nbRound;
        this.lineCount = lineCount;
        this.lineSize = lineSize;
        this.colorCount = colorCount;

        JPanel backPanel=new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel,BoxLayout.Y_AXIS));

        MainPanel mainPanel = new MainPanel();
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;


        //backPanel.add(pnlInfoPlayer);
        backPanel.add(new TopPanel(this.playerName,this.nbRound,this.masterMindGame));
        backPanel.add(mainPanel);

        constraints.gridx = 0;
        constraints.gridy = 0;

        boardPanel= new BoardPanel(this.lineCount,this.lineSize,this.controller);
        mainPanel.add(boardPanel,constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        pnlClue = new CluePanel(this.lineCount,this.lineSize,this.controller,this.masterMindGame);

        mainPanel.add(pnlClue,constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        JPanel pnlChoiceColor=new JPanel();
        pnlChoiceColor.setLayout(new FlowLayout());

        constructAvailableColor(pnlChoiceColor);
        mainPanel.add(pnlChoiceColor,constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        //pnlModeIndice=new ClueModePanel(this.controller);

        //mainPanel.add(pnlModeIndice,constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        JButton btnValidate=new JButton("Valider");
        btnValidate.setMaximumSize(new Dimension(200,100));

        btnValidate.addActionListener(ActionEvent->{

            if(!activeLineFilled()){return;}

            GameColor[] lineColor=colorOfTheLine();
            System.out.println("Couleur récupére");
            for (int i=0;i<lineColor.length;i++)
            {
                System.out.print(lineColor[i]+" ");
                controller.setCurrentLineCellColor(lineColor[i],i);
            }
            controller.verifyCurrentLine();



        });
        mainPanel.add(btnValidate,constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        JButton btnReset=new JButton("Réinitialiser");
        btnReset.setMaximumSize(new Dimension(200,100));
        mainPanel.add(btnReset,constraints);

        constraints.gridx = 0;
        constraints.gridy = 4;
        JButton btnRestart=new JButton("Relancer une nouvelle partie");
        btnRestart.addActionListener(ActiveEvent->{
            controller.launchGame(this.playerName,this.nbRound,this.lineSize,this.lineCount,this.colorCount,this.masterMindGame.getCluesMode());
        });
        mainPanel.add(btnRestart,constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        JButton btnPassTurn=new JButton("Abandonner la manche actulle");
        btnPassTurn.addActionListener(ActionEvent->{
            newGame();
        });
        mainPanel.add(btnPassTurn,constraints);


        add(backPanel);
        setVisible(true);
    }
    private void constructAvailableColor(JPanel pnlChoiceColor)
    {
        List<GameColor>lstAvailableColor=this.controller.getAvailableColors();
        for(int i=0;i<this.colorCount;i++)
        {
            JLabel lblOneColor=new JLabel("Color "+lstAvailableColor.get(i).toString());
            pnlChoiceColor.add(lblOneColor);
        }
    }

    private boolean activeLineFilled()
    {
        for(Component component : boardPanel.getComponents())
        {
            LinePanel linePanel = (LinePanel) component;

            if(linePanel.getTag() == activeLine) {

                for (Component component1 : linePanel.getComponents())
                {
                    JComboBox comboBox = (JComboBox) component1;
                    if(comboBox.getSelectedIndex() < 0)
                    {
                        return false;
                    }
                }
            }
        }

        return true;
    }
    public void updateCombBox()
    {
        for(Component pnl:boardPanel.getComponents())
        {
            if(pnl.getClass()== LinePanel.class)
            {
                LinePanel pnlTheTry=(LinePanel) pnl;
                for(Component cbo : pnlTheTry.getComponents())
                {
                    cbo.setEnabled(false);
                }
            }
        }
        GameColor[] lineColor=colorOfTheLine();

        this.activeLine--;
        int i=0;
        for(Component pnl:boardPanel.getComponents())
        {
            if(pnl.getClass()== LinePanel.class)
            {
                LinePanel pnlTheTry=(LinePanel) pnl;
                if(pnlTheTry.getTag()==activeLine) {

                    for (Component cbo : pnlTheTry.getComponents())
                    {
                        cbo.setEnabled(true);
                        //On fait en sorte de remplir les ComboBox de l'essai prochain avec celles qu'il vient de mettre
                        JComboBox the_cbo=(JComboBox)cbo;
                        the_cbo.setSelectedIndex(masterMindGame.getAvailableColors().indexOf(lineColor[i]));
                        i++;
                    }
                }
            }
        }
    }
    private GameColor[] colorOfTheLine()
    {
        GameColor[] lineColor=new GameColor[this.lineSize];

        for (Component pnl : boardPanel.getComponents())
        {
            if (pnl.getClass() == LinePanel.class)
            {
                LinePanel pnlTheTry = (LinePanel) pnl;
                if (pnlTheTry.getTag() == activeLine)
                {
                    Component[] components = pnlTheTry.getComponents();
                    for (int i = 0; i < components.length; i++)
                    {
                        JComboBox cbo = (JComboBox) components[i];
                        lineColor[i]=GameColor.values()[cbo.getSelectedIndex()];
                    }

                }
            }
        }
        return lineColor;
    }

    private void updateAllIndicesMode()
    {

    }

    private void newGame()
    {
        controller.newRound(this.playerName, this.nbRound, this.lineSize, this.lineCount, this.colorCount);
    }


    @Override
    public void updateActualRound(int actualRound)
    {
        //controller.newRound(this.playerName, this.nbRound, this.lineSize, this.lineCount, this.colorCount);
    }
    @Override
    public void updateEndGame(int score)
    {
        controller.endGame();
    }
    public void updateClues()
    {
        pnlClue.updateClues(activeLine);
    }
}









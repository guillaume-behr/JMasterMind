package view;

import controller.GameMasterController;
import model.CluesMode;
import model.GameColor;
import model.MasterMindGame;
import model.MasterMindGameObserver;


import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GameWindow extends JFrame implements MasterMindGameObserver
{
    private GameMasterController controller;
    private MasterMindGame masterMindGame;
    private int lineSize;
    private int lineCount;
    private int colorCount;
    private int nbRound;
    private int activeLine = 0;
    private String playerName;

    private JPanel boardPanel;
    private JPanel pnlModeIndice;
    private JPanel pnlIndice;

    private JPanel pnlNumeric;
    private JPanel pnlEasyClassicMode;
    private JLabel lblRound;

    public GameWindow(GameMasterController controller,MasterMindGame game,String playerName,
                      int nbRound,int lineSize,int lineCount, int colorCount)
    {
        super("MasterMind");
        setSize(1100,900);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);//Fentêre qui apprait au milieu de l'écran

        this.activeLine=lineCount-1;
        this.controller=controller;
        this.masterMindGame=game;
        this.playerName=playerName;
        this.nbRound=nbRound;
        this.lineCount=lineCount;
        this.lineSize=lineSize;
        this.colorCount=colorCount;

        JPanel mainPanel = new JPanel();
        JPanel backPanel=new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel,BoxLayout.Y_AXIS));

        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints constraints=new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitle=new JLabel("MasterMind");
        lblTitle.setMinimumSize(new Dimension(50,50));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(Color.RED);
        lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));

        JPanel pnlInfoPlayer=new JPanel(new FlowLayout());
        JPanel pnlTitle=new JPanel(new FlowLayout());
        JPanel pnlTop=new JPanel();
        pnlTop.setLayout(new BoxLayout(pnlTop,BoxLayout.Y_AXIS));
        pnlTitle.add(lblTitle);
        pnlTop.add(pnlTitle);
        pnlTop.add(pnlInfoPlayer);

        JLabel lblPlayerName=new JLabel("Pseudo : "+playerName);
        lblPlayerName.setMinimumSize(new Dimension(50,50));
        lblPlayerName.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblPlayerName.setForeground(Color.BLACK);
        lblPlayerName.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        JLabel lblScore=new JLabel("Score : "+masterMindGame.getScore());
        lblScore.setMinimumSize(new Dimension(50,50));
        lblScore.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lblScore.setForeground(Color.BLACK);
        lblScore.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        lblRound=new JLabel("Tour "+masterMindGame.getCurrentRound()+" / "+this.nbRound);
        lblRound.setMinimumSize(new Dimension(50,50));
        lblRound.setAlignmentX(Component.RIGHT_ALIGNMENT);
        lblRound.setForeground(Color.BLACK);
        lblRound.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));

        pnlInfoPlayer.add(lblPlayerName);
        pnlInfoPlayer.add(lblScore);
        pnlInfoPlayer.add(lblRound);
        //backPanel.add(pnlInfoPlayer);
        backPanel.add(pnlTop);
        backPanel.add(mainPanel);

        constraints.gridx = 0;
        constraints.gridy = 0;
        boardPanel=new JPanel();
        //boardPanel.setBorder(BorderFactory.createLineBorder(Color.RED,5,true));
        //boardPanel.setSize(300,600);
        boardPanel.setLayout(new BoxLayout(boardPanel,BoxLayout.Y_AXIS));
        constructTry(boardPanel);
        mainPanel.add(boardPanel,constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        pnlIndice=new JPanel();
        pnlEasyClassicMode=new JPanel();
        pnlEasyClassicMode.setLayout(new BoxLayout(pnlEasyClassicMode,BoxLayout.Y_AXIS));
        pnlNumeric=new JPanel();
        pnlNumeric.setLayout(new BoxLayout(pnlNumeric,BoxLayout.Y_AXIS));
        constructPanelForIndices();
        pnlIndice.add(pnlEasyClassicMode);

        mainPanel.add(pnlIndice,constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        JPanel pnlChoiceColor=new JPanel();
        pnlChoiceColor.setLayout(new FlowLayout());

        constructAvailableColor(pnlChoiceColor);
        mainPanel.add(pnlChoiceColor,constraints);

        constraints.gridx = 1;
        constraints.gridy = 2;
        pnlModeIndice=new JPanel();
        pnlModeIndice.setLayout(new BoxLayout(pnlModeIndice,BoxLayout.Y_AXIS));
        JRadioButton rdbEasyMode=new JRadioButton("Mode facile");
        JRadioButton rdbClassicMode=new JRadioButton("Mode classique");
        JRadioButton rdbNumeric=new JRadioButton("Mode numérique");
        rdbEasyMode.addActionListener(ActionEvent->{
            updateIndiceMode(rdbEasyMode, CluesMode.EASY_MODE);
        });
        rdbClassicMode.addActionListener(ActionEvent->{
            updateIndiceMode(rdbClassicMode,CluesMode.CLASSIC_MODE);
        });
        rdbNumeric.addActionListener(ActionEvent->{
            updateIndiceMode(rdbNumeric,CluesMode.NUMERIC_MODE);
        });
        pnlModeIndice.add(rdbEasyMode);
        pnlModeIndice.add(rdbClassicMode);
        pnlModeIndice.add(rdbNumeric);
        mainPanel.add(pnlModeIndice,constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        //JPanel pnlValidate=new JPanel();
        JButton btnValidate=new JButton("Valider");
        btnValidate.setMaximumSize(new Dimension(200,100));

        btnValidate.addActionListener(ActionEvent->{
            GameColor[] lineColor=colorOfTheLine();
            System.out.println("Couleur récupére");
            for (int i=0;i<lineColor.length;i++)
            {
                System.out.print(lineColor[i]+" ");
                controller.setCurrentLineCellColor(lineColor[i],i);
            }
            System.out.println();

            boolean find=controller.verifyCurrentLine();

            if(find)
            {
                newGame();
                return;
            }
            else if(controller.nextLine() == false)
            {
                newGame();
                return;
            }
                    //System.out.println(controller.verifyCurrentLine());
                    //System.out.println("Bien placé : " + this.controller.getCurentLineWellPlaced() + " "
                    //+ "Mal placé : " + this.controller.getCurrentLineWrongColor()+" "
                    //+ "Couleur présente : "+this.controller.getCurrentLineWellChosen());

            //System.out.println(controller.verifyCurrentLine());
            //System.out.println("Bien placé : " + this.controller.getCurrentLineWellPlaced() + " " + "Mal placé : " + this.controller.getCurrentLineWrongColor());
            //controller.nextLine();
            updateCombBox();
            updateAllIndicesMode();
            //updateIndiceMode(rdbNumeric,CluesMode.NUMERIC_MODE);
        });
        //pnlValidate.add(btnValidate);
        mainPanel.add(btnValidate,constraints);
        //mainPanel.add(pnlValidate);

        constraints.gridx = 1;
        constraints.gridy = 3;
        //JPanel pnlReset=new JPanel();
        JButton btnReset=new JButton("Réinitialiser");
        btnReset.setMaximumSize(new Dimension(200,100));
        //pnlReset.add(btnReset);
        mainPanel.add(btnReset,constraints);
        //mainPanel.add(pnlReset);

        constraints.gridx = 0;
        constraints.gridy = 4;
        JButton btnRestart=new JButton("Relancer une nouvelle partie");
        mainPanel.add(btnRestart,constraints);

        constraints.gridx = 1;
        constraints.gridy = 4;
        JButton btnPassTurn=new JButton("Abandonner la manche actulle");
        mainPanel.add(btnPassTurn,constraints);


        add(backPanel);
        setVisible(true);
    }

    private void constructTry(JPanel pnlTry)
    {
        for(int i=0;i<this.lineCount;i++)
        {
            LinePanel pnlOneTry=new LinePanel(i);
            pnlOneTry.setLayout(new GridLayout(1,this.lineSize+1));
            constructOneTryLine(pnlOneTry);
            //je mets en actif juste la 1ère ligne
            if(i!=this.lineCount-1)
                for(Component cbo:pnlOneTry.getComponents())
                    cbo.setEnabled(false);

            pnlTry.add(pnlOneTry);
        }
    }
    private void constructOneTryLine(JPanel pnlOneTry)
    {
        List<GameColor>lstAvailableColor=this.controller.getAvailableColors();
        for(int i=0;i<this.lineSize;i++)
        {
            JComboBox cboOnePiece=new JComboBox<>();
            cboOnePiece.setSize(new Dimension(200,40));
            for(int j=0;j<this.colorCount;j++)
            {
                cboOnePiece.addItem(lstAvailableColor.get(j).toString());
            }

            pnlOneTry.add(cboOnePiece);
        }
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
    private void updateCombBox()
    {
        for(Component pnl:boardPanel.getComponents())
        {
            if(pnl.getClass()==LinePanel.class)
            {
                LinePanel pnlTheTry=(LinePanel) pnl;
                for(Component cbo : pnlTheTry.getComponents())
                {
                    cbo.setEnabled(false);
                }
            }
        }
        this.activeLine--;
        for(Component pnl:boardPanel.getComponents())
        {
            if(pnl.getClass()== LinePanel.class)
            {
                LinePanel pnlTheTry=(LinePanel) pnl;
                if(pnlTheTry.getTag()==activeLine) {

                    for (Component cbo : pnlTheTry.getComponents()) {
                        cbo.setEnabled(true);
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
                        //controller.setCurrentLineCellColor(GameColor.values()[cbo.getSelectedIndex()], i);
                        lineColor[i]=GameColor.values()[cbo.getSelectedIndex()];
                    }

                }
            }
        }
        return lineColor;
    }
    private void updateIndiceMode(JRadioButton rdbActual,CluesMode indicesMode)
    {
        for (Component c: pnlModeIndice.getComponents())
        {
            JRadioButton rdb=(JRadioButton)c;
            rdb.setSelected(false);
        }
        rdbActual.setSelected(true);

        if(indicesMode==CluesMode.NUMERIC_MODE)
        {
            pnlIndice.remove(pnlEasyClassicMode);
            pnlIndice.add(pnlNumeric);
        }
        else
        {
            pnlIndice.remove(pnlNumeric);
            pnlIndice.add(pnlEasyClassicMode);
        }
        this.repaint();
    }

    private void constructPanelForIndices()
    {
        for(int i=0;i<this.lineCount;i++)
        {
            JPanel pnlOneIndice=new JPanel();
            pnlOneIndice.setLayout(new FlowLayout());
            for(int j=0;j<this.lineSize;j++)
            {
                JLabel lbl=new JLabel(" ");
                lbl.setOpaque(true);
                lbl.setBackground(Color.BLACK);
                //lbl.setMaximumSize(new Dimension(20,20));
                pnlOneIndice.add(lbl);
            }
            pnlEasyClassicMode.add(pnlOneIndice);
        }
        //pour mode numérique
        for(int i=0;i<this.lineCount;i++)
        {
            JPanel pnlOneNumIndice=new JPanel();
            pnlOneNumIndice.setLayout(new FlowLayout());
            JLabel lblGoodPlace=new JLabel("Bien placé : ");
            JLabel lblBadPlace=new JLabel("Mal placé : ");

            pnlOneNumIndice.add(lblGoodPlace);
            pnlOneNumIndice.add(lblBadPlace);
            pnlNumeric.add(pnlOneNumIndice);
        }
    }

    private void updateAllIndicesMode()
    {

    }

    private void newGame()
    {
        controller.newRound(this.playerName, this.nbRound, this.lineSize, this.lineCount, this.colorCount);
        //controller.endRound();
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
}
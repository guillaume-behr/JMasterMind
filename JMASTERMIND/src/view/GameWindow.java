package view;

import controller.GameMasterController;
import model.GameColor;
import helpersLib.Helpers;
import model.MasterMindGame;
import view.gamePanels.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class GameWindow extends JFrame
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
    private CluePanel pnlClue;
    private Color originalCBOBackColor;
    private Color originalColorButton;
    //------------------------------------------------------------------

    public GameWindow(GameMasterController controller,MasterMindGame game,String playerName,
                      int nbRound,int lineSize,int lineCount, int colorCount)
    {
        super("MasterMind");
        this.setSize(1200,1000);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);//Spawn center screen
        ImageIcon icon = new ImageIcon("./images/icon_mastermind.png");
        this.setIconImage(icon.getImage());

        //I have look to find the good default color
        this.originalCBOBackColor=new Color(238,238,238);

        //We put everything we need
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

        backPanel.add(new TopPanel(this.playerName,this.nbRound,this.masterMindGame));
        backPanel.add(mainPanel);

        constraints.gridx = 0;
        constraints.gridy = 0;

        boardPanel= new BoardPanel(this.lineCount,this.lineSize,this.controller,this.masterMindGame);
        mainPanel.add(boardPanel,constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        pnlClue = new CluePanel(this.lineCount,this.lineSize,this.controller,this.masterMindGame);

        mainPanel.add(pnlClue,constraints);

        constraints.gridx = 0;
        constraints.gridy = 2;
        JPanel pnlChoiceColor=new JPanel();
        pnlChoiceColor.setLayout(new FlowLayout());
        pnlChoiceColor.setBackground(new Color(140, 218, 218));

        constructAvailableColor(pnlChoiceColor);
        mainPanel.add(pnlChoiceColor,constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        JButton btnValidate=new JButton("Valider");
        btnValidate.setMaximumSize(new Dimension(200,100));
        btnValidate.setFont(new Font(Font.SANS_SERIF,Font.BOLD,40));
        btnValidate.addActionListener(ActionEvent->{
            //Verify if all ComboBoxs of the line are filled
            if(!activeLineFilled())
            {
                return;
            }

            GameColor[] lineColor=colorOfTheLine();
            for (int i=0;i<lineColor.length;i++)
            {
                masterMindGame.getMasterMindBoard().getCurrentLine().setCellColor(lineColor[i],i);
            }
            controller.verifyCurrentLine();
        });
        this.originalColorButton=btnValidate.getBackground();
        createMyGameButtonMouseHoverEvent(btnValidate);
        mainPanel.add(btnValidate,constraints);

        constraints.gridx = 1;
        constraints.gridy = 3;
        JButton btnReset=new JButton("Réinitialiser");
        btnReset.setFont(new Font(Font.SANS_SERIF,Font.BOLD,40));
        btnReset.setMaximumSize(new Dimension(200,100));
        btnReset.addActionListener(ActiveEvent->{
            //Event of the button to reset all ComboBox of the current line
            int choice=JOptionPane.showConfirmDialog(null,
                    "Voulez-vous réinitialiser la ligne ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if(choice==JOptionPane.YES_OPTION)
                resetComboBox();
        });
        createMyGameButtonMouseHoverEvent(btnReset);
        mainPanel.add(btnReset,constraints);

        JButton btnPassTurn=new JButton("Abandonner la manche actuelle");
        btnPassTurn.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
        btnPassTurn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnPassTurn.addActionListener(ActionEvent->{
            //Event of the button to abandon the round
            int choice=JOptionPane.showConfirmDialog(null,
                    "Voulez-vous abandonner la manche ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if(choice==JOptionPane.YES_OPTION)
                newRound();
        });
        createMyGameButtonMouseHoverEvent(btnPassTurn);
        backPanel.add(Box.createVerticalStrut(15));

        backPanel.add(btnPassTurn);

        backPanel.add(Box.createVerticalStrut(15));

        JButton btnRestart=new JButton("Relancer une nouvelle partie");
        btnRestart.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
        btnRestart.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnRestart.addActionListener(ActiveEvent->{
            //Event of the button to restart a game with the same game options
            int choice=JOptionPane.showConfirmDialog(null,
                    "Voulez-vous relancer une partie ?",
                    "Confirmation",
                    JOptionPane.YES_NO_OPTION);

            if(choice==JOptionPane.YES_OPTION)
                controller.launchGame(this.playerName,this.nbRound,this.lineSize,this.lineCount,this.colorCount,this.masterMindGame.getCluesMode());
        });
        createMyGameButtonMouseHoverEvent(btnRestart);
        backPanel.add(btnRestart);
        backPanel.add(Box.createVerticalStrut(15));

        backPanel.setBackground(new Color(216, 222, 152));

        this.add(backPanel);
        this.setVisible(true);
    }

    //Construct the panel with all the available colors
    private void constructAvailableColor(JPanel pnlChoiceColor)
    {
        List<GameColor>lstAvailableColor=this.masterMindGame.getAvailableColors();
        JLabel lblInfoColor=new JLabel("Couleurs disponibles :");
        lblInfoColor.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
        pnlChoiceColor.add(lblInfoColor);
        for(int i=0;i<this.colorCount;i++)
        {
            JLabel lblOneColor=new JLabel(" "+
                    Helpers.translateColorToFrench(lstAvailableColor.get(i)).toUpperCase());
            lblOneColor.setFont(new Font(Font.SANS_SERIF,Font.BOLD,15));
            pnlChoiceColor.add(lblOneColor);
        }
    }

    //Put the hand's cursor and that the button changes colors when its hover by the mouse
    private void createMyGameButtonMouseHoverEvent(JButton button)
    {
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(201, 246, 117));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColorButton);
            }
        });
    }

    //Check if all the ComboBox of the current line are filled
    private boolean activeLineFilled()
    {
        for(Component component : boardPanel.getComponents())
        {
            LinePanel linePanel = (LinePanel) component;

            if(linePanel.getTag() == activeLine)
            {
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

    //Update ComboBox
    //Set active the current line and copy all precedent colors in the ComboBox of this line
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

    //Return an array with the colors of the current line
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

    //Launch a new round
    private void newRound()
    {
        this.controller.newRound(this.playerName, this.nbRound, this.lineSize, this.lineCount, this.colorCount,true);
    }

    //Update clue's panel
    public void updateClues()
    {
        pnlClue.updateClues(activeLine);
    }

    //Reset the ComboBoxs of the current line
    private void resetComboBox()
    {
        for(Component c:this.boardPanel.getComponents())
        {
            if (c.getClass() == LinePanel.class) {
                LinePanel panel = (LinePanel) c;
                if (panel.getTag() == activeLine) {
                    for(Component cc : panel.getComponents())
                    {
                        JComboBox cbo=(JComboBox) cc;
                        cbo.setSelectedIndex(-1);
                        cbo.setBackground(this.originalCBOBackColor);
                    }
                }
            }
        }
    }

    //Print a message when the player found the secret combination
    public void showFoundToPlayer()
    {
        JOptionPane.showMessageDialog(null,
        "Bravo !\nLa combinaison secrète était bien : "+
                masterMindGame.getMasterMindBoard().secretCombinaisonToString(),
                "Trouver",
                JOptionPane.INFORMATION_MESSAGE);
    }
    //Print a message when the player fail to found the secret combination
    public void showFailToPlayer()
    {
        JOptionPane.showMessageDialog(null,
                "Dommage !\nLa combinaison secrète était : "+
                        masterMindGame.getMasterMindBoard().secretCombinaisonToString(),
                "Perdu !",
                JOptionPane.OK_OPTION);
    }
}

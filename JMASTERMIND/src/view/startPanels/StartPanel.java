package view.startPanels;

import controller.GameMasterController;
import model.CluesMode;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

//Panel with game option choice
public class StartPanel extends JPanel
{
    private JTextArea txtPlayerName;
    private GameMasterController controller;
    private JComboBox cboNbRound;
    private JComboBox cboNbTotalPiece;
    private JComboBox cboNbTry;
    private JComboBox cboNbPieceOfCombinaison;
    private ClueModePanel clueModePanel;
    private Color originalColorButton;

    public StartPanel(GameMasterController controller)
    {
        this.controller=controller;
        this.setAlignmentX(Component.CENTER_ALIGNMENT);
        this.setAlignmentY(Component.CENTER_ALIGNMENT);
        this.setBackground(new Color(195, 211, 250));
        //Put a small border around
        this.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(25,50,25,50),
                BorderFactory.createLineBorder(new Color(255, 36, 36, 168),10,true)));//Make a padding

        //BoxLayout, because we want all the component on top of each other (all place verticaly)
        this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));

        this.add(Box.createVerticalStrut(10));//Empty vertical space

        constructLabelPicture();//Label with picture

        this.add(Box.createVerticalStrut(10));//Empty vertical space

        constructMyLabel("Entrer votre pseudo :");//Label for pseudo
        constructPlayerTextBox();//TexteBox for pseudo

        this.add(Box.createVerticalStrut(20));

        constructMyLabel("Nombre de manche :");//Label number of round
        cboNbRound=constructMyComboBox(new String[]{"3", "4", "5"},0,cboNbRound);//ComboBox number of round
        this.add(cboNbRound);

        this.add(Box.createVerticalStrut(20));

        constructMyLabel("Nombre de couleurs :");//Label number of colors
        cboNbTotalPiece=constructMyComboBox(new String[]{"4", "5", "6","7","8"},4,cboNbTotalPiece);//ComboBox number of colors
        this.add(cboNbTotalPiece);

        this.add(Box.createVerticalStrut(20));

        constructMyLabel("Nombre de pions d'une combinaison :");//Label size combination
        cboNbPieceOfCombinaison=constructMyComboBox(new String[]{"4", "5", "6"},0,cboNbPieceOfCombinaison);//ComboBox size combinaison
        this.add(cboNbPieceOfCombinaison);

        this.add(Box.createVerticalStrut(20));

        constructMyLabel("Nombre de tentatives par manche :");//Label number of try
        cboNbTry=constructMyComboBox(new String[]{"10", "11", "12"},0,cboNbTry);//ComboBox number of try
        this.add(cboNbTry);

        this.add(Box.createVerticalStrut(20));

        constructMyLabel("Choissisez votre mode d'indices :");//Label clue mode
        this.clueModePanel=new ClueModePanel(controller);//Panel clue mode
        this.add(clueModePanel);

        this.add(Box.createVerticalStrut(20));

        constructLaunchGameButton();//Button to launch the game

        this.add(Box.createVerticalStrut(20));

        constructExitButton();//Button to exit

        this.add(Box.createVerticalStrut(10));
    }

    //Construct a ComboBox with values of the tab and set the default selected index on the startIndex int
    private JComboBox constructMyComboBox(String [] numberTab,int startIndex,JComboBox cbo)
    {
        cbo=new JComboBox(numberTab);
        cbo.setSelectedIndex(startIndex);
        cbo.setAlignmentX(Component.CENTER_ALIGNMENT);
        cbo.setFont(new Font("Sans-Serif", Font.PLAIN, 20));
        cbo.setMaximumSize(new Dimension(100,50));
        return cbo;
    }

    //Construct the title label
    private void constructTitleLabel()
    {
        JLabel lblTitle=new JLabel("Mastermind");
        lblTitle.setMinimumSize(new Dimension(50,50));
        lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblTitle.setForeground(Color.RED);
        lblTitle.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        this.add(lblTitle);
    }

    //Construct player name text box
    private void constructPlayerTextBox()
    {
        txtPlayerName=new JTextArea();
        txtPlayerName.setAlignmentX(Component.CENTER_ALIGNMENT);
        txtPlayerName.setFont(new Font(Font.DIALOG_INPUT,Font.PLAIN,20));
        txtPlayerName.setSize(new Dimension(400,30));
        txtPlayerName.setMaximumSize(new Dimension(400,30));

        //Add an event for the keyboard
        txtPlayerName.addKeyListener(new KeyListener() {
                                         @Override
                                         public void keyTyped(KeyEvent e) {
                                             //Pseudo needs to be less than 20 char
                                             //Limit place by us
                                             if(txtPlayerName.getText().length() > 20)
                                                 e.consume();

                                         }
                                         @Override
                                         public void keyPressed(KeyEvent e) {
                                             //No \n
                                             if(e.getKeyChar()=='\n')
                                                 e.consume();
                                         }
                                         @Override
                                         public void keyReleased(KeyEvent e) {}
                                     }
        );
        this.add(txtPlayerName);
    }

    //Construct a Label with the text in parameter and add it to the panel
    private void constructMyLabel(String text)
    {
        JLabel lblNbRound=new JLabel(text);
        lblNbRound.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblNbRound.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        this.add(lblNbRound);
    }

    //Construct launch game button
    private void constructLaunchGameButton()
    {
        JButton btnLaunchGame=new JButton("Lancer le jeu !");
        this.originalColorButton=btnLaunchGame.getBackground();//We collect the default button color

        createMyStartButtonMouseHoverEvent(btnLaunchGame);
        btnLaunchGame.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        btnLaunchGame.setAlignmentX(Component.CENTER_ALIGNMENT);//Center alignment

        btnLaunchGame.addActionListener(actionEvent -> {
            startTheGame();
        });

        this.add(btnLaunchGame);
    }

    //Construct exit button
    private void constructExitButton()
    {
        JButton btnExit=new JButton("Quitter");
        btnExit.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExit.addActionListener(actionEvent -> {
            controller.exitStartWindow();
        });

        createMyStartButtonMouseHoverEvent(btnExit);
        this.add(btnExit);
    }

    //Construct the Label with the picture
    private void constructLabelPicture()
    {
        try
        {
            //Load picture
            BufferedImage myPicture = ImageIO.read(new File("./images/mastermind_title.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            //size
            picLabel.setMaximumSize(new Dimension(415,225));
            //Alignment
            picLabel.setHorizontalAlignment(SwingConstants.CENTER);
            picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            this.add(picLabel);
        }
        catch (IOException e)
        {
            //If we don't find the picture, we put a simple title label instead
            constructTitleLabel();
        }
    }

    //Put the hand's cursor and that the button changes colors when its hover by the mouse
    private void createMyStartButtonMouseHoverEvent(JButton button)
    {
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));//Hand cursor

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setBackground(new Color(245, 175, 175));
            }

            public void mouseExited(MouseEvent e) {
                button.setBackground(originalColorButton);
            }
        });
    }

    //Method when we click on the button "Lancer le jeu"
    private void startTheGame()
    {
        String playerName=txtPlayerName.getText();
        int nbRound=Integer.parseInt(cboNbRound.getSelectedItem().toString());
        int nbTotalPiece=Integer.parseInt(cboNbTotalPiece.getSelectedItem().toString());
        int nbPieceOfCombinaison= Integer.parseInt(cboNbPieceOfCombinaison.getSelectedItem().toString());
        int nbTry=Integer.parseInt(cboNbTry.getSelectedItem().toString());
        CluesMode cluesMode=clueModePanel.getSelectedCluesMode();

        //Check if the payer text box is not empty
        if(!playerName.isEmpty())
            controller.launchGame(playerName,nbRound,nbPieceOfCombinaison,nbTry,nbTotalPiece,cluesMode);
        else
        {
            //Small information dialog message
            JOptionPane.showMessageDialog(new JFrame(),
                    "Vous devez entrer un nom de joueur",
                    "Entrer un nom",
                    JOptionPane.ERROR_MESSAGE);
            txtPlayerName.requestFocus();//Focus on the text box
        }
    }
}

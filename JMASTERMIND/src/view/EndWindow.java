package view;

import controller.GameMasterController;
import helpersLib.Helpers;
import model.MasterMindGame;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class EndWindow extends JFrame
{
    private MasterMindGame game;
    private GameMasterController controller;
    private Color originalColorButton;
    private JPanel backPanel;

    public EndWindow(MasterMindGame game, GameMasterController gmc)
    {
        super("Fin de la partie");
        this.setSize(700,950);
        this.setResizable(false);//Cannot resize window in order to avoid strange display (like StartWindows)
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        ImageIcon icon = new ImageIcon("./images/icon_end_game.png");
        this.setIconImage(icon.getImage());

        this.game = game;
        this.controller=gmc;

        backPanel=new JPanel();
        backPanel.setLayout(new BoxLayout(backPanel,BoxLayout.Y_AXIS));
        backPanel.setBackground(new Color(215, 222, 141));
        this.add(backPanel);

        backPanel.add(Box.createVerticalStrut(15));

        constructTitleLabel();

        constructEndScoreLabel();

        constructEndSmallRacepLabel("Votre pseudo : "+game.getPlayerName());

        constructEndSmallRacepLabel("Nombre de manches : "+game.getNbRoud());

        constructEndSmallRacepLabel("Tentatives par manche : "+game.getNbTry());

        constructEndSmallRacepLabel("Nombre de couleurs : "+game.getColorCount());

        constructEndSmallRacepLabel("Taille combinaison : "+game.getLineSize());

        constructEndSmallRacepLabel("Mode des indices : "+Helpers.translateCluesModeToFrench(game.getCluesMode()).toUpperCase());

        try
        {
            BufferedImage myPicture = ImageIO.read(new File("./images/homer_simpson.png"));
            JLabel picLabel = new JLabel(new ImageIcon(myPicture));
            picLabel.setMaximumSize(new Dimension(500,400));
            picLabel.setHorizontalAlignment(SwingConstants.CENTER);
            picLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            backPanel.add(picLabel);
        }
        //If we don't find the picture, we put a small simple label instead
        catch (IOException e)
        {
            constructSimpleLabel();
        }

        backPanel.add(Box.createVerticalStrut(15));

        contructRetryButton();

        constructQuitButton();

        this.setVisible(true);
    }

    //Put the hand's cursor and that the button changes colors when its hover by the mouse
    private void createMyEndGameButtonMouseHoverEvent(JButton button)
    {
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addMouseListener(new MouseAdapter() {
        public void mouseEntered(MouseEvent e) {
            button.setBackground(new Color(245, 175, 175));
        }

        public void mouseExited(MouseEvent e) {
            button.setBackground(originalColorButton);
        }
        });
    }

    //Construcut the title label
    private void constructTitleLabel()
    {
        JLabel lblRes=new JLabel("Résultat de la partie !");
        lblRes.setFont(new Font(Font.SANS_SERIF,Font.BOLD,40));
        lblRes.setAlignmentX(Component.CENTER_ALIGNMENT);
        lblRes.setHorizontalAlignment(SwingConstants.CENTER);
        backPanel.add(lblRes);
        backPanel.add(Box.createVerticalStrut(15));
    }

    //Construct the score label
    private void constructEndScoreLabel()
    {
        JLabel lblScore=new JLabel("Votre score : "+game.getScore());
        lblScore.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
        lblScore.setAlignmentX(Component.CENTER_ALIGNMENT);
        backPanel.add(lblScore);
        backPanel.add(Box.createVerticalStrut(15));
    }

    //Construct all the small end recap labels
    private void constructEndSmallRacepLabel(String txt)
    {
        JLabel lbl=new JLabel(txt);
        lbl.setFont(new Font(Font.SANS_SERIF,Font.PLAIN,22));
        lbl.setAlignmentX(Component.CENTER_ALIGNMENT);
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        this.backPanel.add(lbl);
        backPanel.add(Box.createVerticalStrut(15));
    }

    //Construct a simple label if the try/catch of the picture fail
    private void constructSimpleLabel()
    {
        JLabel label=new JLabel("MASTERMIND");
        label.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        backPanel.add(label);
    }

    //Construct the retry button
    private void contructRetryButton()
    {
        JButton btnNewGame=new JButton("Relancer une partie");
        btnNewGame.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
        btnNewGame.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnNewGame.addActionListener(ActionEvent->{
            controller.startNewGame();
        });
        this.originalColorButton=btnNewGame.getBackground();
        createMyEndGameButtonMouseHoverEvent(btnNewGame);
        backPanel.add(btnNewGame);
        backPanel.add(Box.createVerticalStrut(15));
    }

    //Construct the exit button
    private void constructQuitButton()
    {
        JButton btnExit=new JButton("Quitter");
        btnExit.setFont(new Font(Font.SANS_SERIF,Font.BOLD,30));
        btnExit.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnExit.addActionListener(ActionEvent->{
            controller.shutDownApp();
        });
        createMyEndGameButtonMouseHoverEvent(btnExit);
        backPanel.add(btnExit);
        backPanel.add(Box.createVerticalStrut(15));
    }
}

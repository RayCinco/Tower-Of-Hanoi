package main;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  
import Utility.*;

public class TitleFrame extends JFrame implements MouseListener {
    private JLabel play;
    private JLabel rules;
    private JLabel about;
    
    public TitleFrame() {
        setTitle("Tower Of Hanoi");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackgroundPanel backgroundPanel = new BackgroundPanel("image/Homepage.png");

        setSize(960, 540); 
        setResizable(false);


        //PLAY
        play = new JLabel("PLAY", SwingConstants.CENTER);
        play.setFont(new Font("Goudy", Font.BOLD, 30));
        play.setBounds(430, 323, 80, 50); 
        play.setForeground(Color.WHITE);
        play.addMouseListener(this);

        //RULES
        rules = new JLabel("HOW TO PLAY", SwingConstants.CENTER);
        rules.setFont(new Font("Goudy", Font.BOLD, 23));
        rules.setBounds(603, 323, 190, 50); 
        rules.setForeground(Color.WHITE);
        rules.addMouseListener(this);

        //ABOUT
        about = new JLabel("ABOUT", SwingConstants.CENTER);
        about.setFont(new Font("Goudy", Font.BOLD, 25));
        about.setBounds(200,323, 90, 50); 
        about.setForeground(Color.WHITE);
        about.addMouseListener(this);

        backgroundPanel.setLayout(null);


        backgroundPanel.add(play);
        backgroundPanel.add(rules);
        backgroundPanel.add(about);

        setContentPane(backgroundPanel);

        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == about) {;
            new About();
            dispose(); 
        }
        if (e.getSource() == rules) {;
            new Rules();
            dispose(); 
        }
        if (e.getSource() == play) {;
            new MainGame();
            dispose(); 
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}


    public static void main(String[] args) {
        TitleFrame frame = new TitleFrame();
    }

}

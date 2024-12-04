package main;



import javax.swing.*;
import java.awt.*;
import java.awt.event.*;  
import Utility.*;

public class About extends JFrame implements MouseListener {
    public About() {
        setTitle("Tower Of Hanoi");
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        BackgroundPanel backgroundPanel = new BackgroundPanel("image/About.png");

        setSize(960, 540); 
        setResizable(false);

        JLabel back = new JLabel("BACK", SwingConstants.CENTER);
        back.setFont(new Font("Goudy", Font.BOLD, 20));
        back.setBounds(830, 440, 80, 50); 
        back.setForeground(Color.WHITE);

        back.addMouseListener(this);
        backgroundPanel.setLayout(null);
        backgroundPanel.add(back);

        setContentPane(backgroundPanel);

        setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof JLabel) {;
            TitleFrame title = new TitleFrame();
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


}

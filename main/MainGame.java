package main;
import javax.swing.JFrame;

public class MainGame {
    
    public MainGame(){
        MyTower t = new MyTower();
    
        JFrame gameFrame = new JFrame();
        gameFrame.setTitle("Tower of Hanoi");
        gameFrame.setSize(960, 540); 
        gameFrame.setResizable(false);
        
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.getContentPane().add(t);
        gameFrame.revalidate();
        gameFrame.repaint();
        gameFrame.setVisible(true);
        
    }
    
}

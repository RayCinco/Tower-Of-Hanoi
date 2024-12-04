package main;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import Utility.BackgroundPanel;

import java.util.Stack;
import java.awt.geom.Rectangle2D;

public class MyTower extends JPanel implements MouseListener, MouseMotionListener {
    int diskCount = 5;
    Stack<Image> stack[] = new Stack[3];
    Stack<Integer> diskNumber[] = new Stack[3];
    int moveCount = 0;
    int prevTower = 0;
    Rectangle diskRect = null;

    Image top = null; // Disk currently being dragged
    int topNumber = 0; // Number of the disk currently being dragged
    double ax, ay, height, width; // Coordinates and dimensions for dragging
    boolean dragable = false; // Flag to indicate if a disk is draggable
   
    int moves = 1;
    boolean isPaused = false;
    

    //JLABEL
    private JLabel pause;
    private JLabel restart;
    private JLabel menu;

    //SUMMARY
    private JLabel totalTime;
    private JLabel totalMoves;
    private JLabel summaryMenu;
    private JLabel summaryExit;
    private JLabel summaryPlay;


    // TIMER
    private JLabel timerLabel;
    private GameTimer gameTimer;


    public MyTower() {
        this.setLayout(null);

        
        

        //PLAY
        pause = new JLabel("PAUSE", SwingConstants.CENTER);
        pause.setFont(new Font("Goudy", Font.BOLD, 20));
        pause.setBounds(560, 30, 100, 50); 
        pause.setForeground(Color.WHITE);
        pause.addMouseListener(this);
        

         //TIMER
         timerLabel = new JLabel("00:00:00");
         timerLabel.setFont(new Font("Goudy", Font.BOLD, 30));
         timerLabel.setBounds(50, 30, 130, 50); 
         timerLabel.setForeground(Color.WHITE);
         
         gameTimer = new GameTimer(timerLabel);  
         

        //RULES
        restart = new JLabel("RESTART", SwingConstants.CENTER);
        restart.setFont(new Font("Goudy", Font.BOLD, 19));
        restart.setBounds(640, 30, 190, 50); 
        restart.setForeground(Color.WHITE);
        restart.addMouseListener(this);

        //ABOUT
        menu = new JLabel("MENU", SwingConstants.CENTER);
        menu.setFont(new Font("Goudy", Font.BOLD, 20));
        menu.setBounds(817,30, 90, 50); 
        menu.setForeground(Color.WHITE);
        menu.addMouseListener(this);


        //SUMMARY PANEL


    totalTime = new JLabel();
    totalTime.setFont(new Font("Goudy", Font.BOLD, 27));
    totalTime.setBounds(285, 270, 130, 50); 
    totalTime.setForeground(Color.WHITE);
    totalTime.setVisible(false);

    totalMoves = new JLabel();
    totalMoves.setFont(new Font("Goudy", Font.BOLD, 27));
    totalMoves.setBounds(690, 270, 100, 50); 
    totalMoves.setForeground(Color.WHITE);
    totalMoves.setVisible(false);

    summaryMenu = new JLabel("MENU", SwingConstants.CENTER);
    summaryMenu.setFont(new Font("Goudy", Font.BOLD, 23));
    summaryMenu.setBounds(270, 372, 100, 50); 
    summaryMenu.setForeground(Color.WHITE);
    summaryMenu.addMouseListener(this);
    summaryMenu.setVisible(false);

    summaryExit = new JLabel("EXIT", SwingConstants.CENTER);
    summaryExit.setFont(new Font("Goudy", Font.BOLD, 23));
    summaryExit.setBounds(588, 372, 100, 50); 
    summaryExit.setForeground(Color.WHITE);
    summaryExit.addMouseListener(this);
    summaryExit.setVisible(false);

    summaryPlay = new JLabel("PLAY AGAIN", SwingConstants.CENTER);
    summaryPlay.setFont(new Font("Goudy", Font.BOLD, 21));
    summaryPlay.setBounds(415, 372, 130, 50); 
    summaryPlay.setForeground(Color.WHITE);
    summaryPlay.addMouseListener(this);
    summaryPlay.setVisible(false);

        this.add(summaryMenu);
        this.add(summaryPlay);
        this.add(summaryExit);
        this.add(totalMoves);
        this.add(totalTime);
        this.add(timerLabel);
        this.add(menu);
        this.add(restart);
        this.add(pause);

        this.addMouseListener(this);
        this.addMouseMotionListener(this);

        
        
        init();

       
       

    }

    public void init() {
        if(isPaused) return;
        
        stack[0] = new Stack<>();
        stack[1] = new Stack<>();
        stack[2] = new Stack<>();
        moveCount = 0;

        diskNumber[0] = new Stack<>();
        diskNumber[1] = new Stack<>();
        diskNumber[2] = new Stack<>();

        //diskNumber[2].add(1);
        //diskNumber[2].add(1);
       // diskNumber[2].add(1);
       // diskNumber[2].add(1);



        Image pegImages[] = new Image[5]; 

        pegImages[4] = new ImageIcon("image/disk1.png").getImage(); //  disk 1
        pegImages[3] = new ImageIcon("image/disk2.png").getImage(); //  disk 2
        pegImages[2] = new ImageIcon("image/disk3.png").getImage(); //  disk 3
        pegImages[1] = new ImageIcon("image/disk4.png").getImage(); //  disk 4
        pegImages[0] = new ImageIcon("image/disk5.png").getImage(); //  disk 5

        //PUSH
        for (int i = 0; i < diskCount; i++) {
            stack[0].push(pegImages[i]); 
            diskNumber[0].push(i); 

        }
        
        gameTimer.start();
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g1 = (Graphics2D) g;

        Image backgroundImage = new ImageIcon("image/GameBG.png").getImage();
        g1.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // RODS
        int baseX = getWidth() / 6;
        int baseY1 = getHeight() - 335;
        int baseY2 = getHeight() - 80;

        g1.setColor(Color.WHITE);
        g1.setStroke(new BasicStroke(5));
        g1.drawLine(baseX, baseY1, baseX, baseY2);
        g1.drawLine(3 * baseX, baseY1, 3 * baseX, baseY2);
        g1.drawLine(5 * baseX, baseY1, 5 * baseX, baseY2);
        g1.setStroke(new BasicStroke(1));

        // TOWERS
        drawTower(g1, 0);
        drawTower(g1, 1);
        drawTower(g1, 2);

        // DRAGABLE DISK
        if (dragable && top != null) {
            g1.drawImage(top, (int) ax - top.getWidth(null) / 2, (int) ay - top.getHeight(null) / 2, this);
        }
    }

    //FIND TOWER METHOD
    private int currentTower(Point p) {
        Rectangle2D.Double rA = new Rectangle2D.Double();
        Rectangle2D.Double rB = new Rectangle2D.Double();
        Rectangle2D.Double rC = new Rectangle2D.Double();

        rA.setFrame(0, 0, getWidth() / 3, getHeight());
        rB.setFrame(getWidth() / 3, 0, getWidth() / 3, getHeight());
        rC.setFrame(2 * getWidth() / 3, 0, getWidth() / 3, getHeight());

        if (rA.contains(p))
            return 0;
        else if (rB.contains(p))
            return 1;
        else if (rC.contains(p))
            return 2;
        else
            return -1;
    }

    //DRAW TOWER
    private void drawTower(Graphics2D g, int n) {
        Stack<Image> temp = new Stack<>();
    
        while (!stack[n].isEmpty()) {
            Image diskImage = stack[n].pop();  
            temp.push(diskImage); 
        }
        
        // Y POSITIONS
        int yOffset = -34 ;  
    
        int xPosition = (n == 0) ? getWidth() / 6 : (n == 1) ? getWidth() / 2 : 5 * getWidth() / 6;
        
        //INDEX OF DISKS
        int index = 0;
        while (!temp.isEmpty()) {
            

            Image diskImage = temp.pop();  
            
            g.drawImage(diskImage, xPosition - (diskImage.getWidth(null) / 2), yOffset, this);
            yOffset -= (50 - diskNumber[n].get(index)  * 7);
            index++;
           
            //PUSHING TO STACKS
            stack[n].push(diskImage);  
        }
    }
    


    // MOUSE LISTENER
    @Override
    public void mousePressed(MouseEvent e) {
        if (isPaused) return;

        Point p = e.getPoint();
        int towerIndex = currentTower(p);
    
        if (towerIndex != -1 && !stack[towerIndex].isEmpty()) {
            
            Image topDisk = stack[towerIndex].peek();
            int diskWidth = topDisk.getWidth(null);
            int diskHeight = topDisk.getHeight(null);
            
            // CALCULATE POSTION
            int xPosition = (towerIndex == 0) ? getWidth() / 6 : 
                            (towerIndex == 1) ? getWidth() / 2 : 
                            5 * getWidth() / 6;
            xPosition -= diskWidth / 2; 
            int yPosition = getHeight() - 335 - (stack[towerIndex].size() - 1) * 20; 
    
            Rectangle diskRect = new Rectangle(xPosition, yPosition, diskWidth, diskHeight);
    
            if (diskRect.contains(p)) {
                top = stack[towerIndex].pop();
                topNumber = diskNumber[towerIndex].pop();
                prevTower = towerIndex;
                ax = p.getX();
                ay = p.getY()-200;
                dragable = true;
                repaint();
            }
        }
    }
    

    @Override
    public void mouseDragged(MouseEvent e) {
        if (dragable) {
            ax = e.getX();
            ay = e.getY()-200;
            repaint();
        }
    }
    
    
    @Override
    public void mouseReleased(MouseEvent e) {
    
        if (dragable) {
            int towerIndex = currentTower(e.getPoint());
            
            if (towerIndex != -1 && (stack[towerIndex].isEmpty() || diskNumber[towerIndex].peek() < topNumber)) {
                stack[towerIndex].push(top);
                diskNumber[towerIndex].push(topNumber);
                top = null;
                topNumber = -1;
                dragable = false;
                moves++;
                repaint();

                   
            if(stack[2].size() == 5) {
            isPaused = true;
            gameTimer.stop();
            summaryPanel();
            System.out.println(stack[2].size());

        }
            } 
            else {   
                stack[prevTower].push(top);
                diskNumber[prevTower].push(topNumber);
                top = null;
                topNumber = -1;
                dragable = true;
                repaint();
            }
        }
    }

   
    //SUMMARY PANEL
    public void summaryPanel() {
   
    JPanel summaryPanel = new JPanel() {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            
            Image backgroundImage = new ImageIcon("image/Summary.png").getImage();
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    };

    
    summaryPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
    summaryPanel.setOpaque(false);  
    
    
    removeVisibility();
    this.add(summaryPanel);

    revalidate();
    repaint();
    }

    


    @Override
    public void mouseMoved(MouseEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {
        if(e.getSource() == restart){
            isPaused = false;
            pause.setText("PAUSE");  
            gameTimer.reset();
            init();
        }
        if (e.getSource() == pause) {
            if (isPaused) {
                gameTimer.start();  
                pause.setText("PAUSE");  
            } else {
                gameTimer.stop();  
                pause.setText("RESUME");  
            }

            isPaused = !isPaused;
        }
        

        if(e.getSource() == menu){
            new TitleFrame();
            
            Window window = SwingUtilities.windowForComponent(this);  
            if (window != null) {
                window.dispose();  
            }
        }

        if(e.getSource() == summaryMenu){
            new TitleFrame();
            
            Window window = SwingUtilities.windowForComponent(this);  
            if (window != null) {
                window.dispose();  
            }
        }
        if(e.getSource() == summaryExit){
            Window window = SwingUtilities.windowForComponent(this);  
            if (window != null) {
                window.dispose();  
            }
        }
        if(e.getSource() == summaryPlay){
            System.out.println("CLICK");
            Window window = SwingUtilities.windowForComponent(this);  
            if (window != null) {
                window.dispose();  
            }
            new MainGame();
            
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}



    public void removeVisibility(){
        totalTime.setText(timerLabel.getText());
        totalMoves.setText(""+moves);

        timerLabel.setVisible(false);
        pause.setVisible(false);
        restart.setVisible(false);
        menu.setVisible(false);
        totalTime.setVisible(true);
        totalMoves.setVisible(true);
        summaryExit.setVisible(true);
        summaryPlay.setVisible(true);
        summaryMenu.setVisible(true);

       
    }
}

package main;
import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer {
    private Timer timer;
    private long elapsedTime;
    private JLabel timeLabel;
    private boolean isRunning;

    public GameTimer(JLabel timeLabel) {
        this.timeLabel = timeLabel;
        this.elapsedTime = 0;
        this.isRunning = false;
        this.timer = new Timer(); 
    }

    public void start() {
        if (!isRunning) {
            if (timer == null) {
                timer = new Timer();
            }
            isRunning = true;
            timer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    elapsedTime++;  
                    updateTimerLabel();
                }
            }, 1000, 1000);  
        }
    }

    public void stop() {
        if (timer != null) {
            timer.cancel(); 
            isRunning = false;  
            timer = null; 
        }
    }

    public void reset() {
        elapsedTime = 0;
        updateTimerLabel();
        if (timer == null) {
            timer = new Timer(); 
        }
    }

    private void updateTimerLabel() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                timeLabel.setText(String.format("%02d:%02d:%02d", elapsedTime / 3600, (elapsedTime % 3600) / 60, elapsedTime % 60));
            }
        });
    }

    public long getElapsedTime() {
        return elapsedTime;
    }
}

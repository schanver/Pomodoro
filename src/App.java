package src;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class App extends JFrame
{
    private JButton startButton, resetButton, skipButton;
    private JPanel timerPanel, statePanel;
    Timer timer;
    private JLabel sessionAmount, timerLabel, stateLabel;
    private int minutes = 25;
    private int seconds = 0;
    private int pomodoroCount = 0;
    public TIMER_STATES currentState;
    boolean isRunning = false;

    Font buttonFont = new Font("Arial", Font.BOLD, 11);

    public App()
    {
        
        final int WIDTH = 300;
        final int HEIGHT = 300;
        this.setResizable(false);
        this.setTitle("Pomodoro Timer");
        this.setSize(WIDTH, HEIGHT);
        this.getContentPane().setBackground(Color.black);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        // Set Current State to IDLE 
        currentState = TIMER_STATES.IDLE;

        // Timer and State Panels
        timerPanel = new JPanel();
        timerPanel.setBounds(50, 75, 200, 50);
        timerPanel.setBackground(Color.black);

        statePanel = new JPanel();
        statePanel.setBounds(50, 10, 200, 60);
        statePanel.setBackground(Color.black);

        // Timer Label
        timerLabel = new JLabel("25:00");
        timerLabel.setBounds(50, 75, 200, 50);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 34));
        timerLabel.setBackground(Color.black);
        timerLabel.setForeground(Color.white);
        timerPanel.add(timerLabel);

        // State Label
        stateLabel = new JLabel("SESSION");
        stateLabel.setBounds(50, 10, 200, 60);
        stateLabel.setBackground(Color.black);
        stateLabel.setForeground(Color.white);
        stateLabel.setFont(new Font("Arial", Font.BOLD, 27));
        statePanel.add(stateLabel);

        // Amount of pomodoros done
        sessionAmount = new JLabel("Pomodoros: "+ pomodoroCount);
        sessionAmount.setBounds(90, 220, 120, 30);
        sessionAmount.setBackground(Color.black);
        sessionAmount.setForeground(Color.white);
        sessionAmount.setFont(new Font("Arial", Font.BOLD, 13));
        this.add(sessionAmount);


        // Start Button
        startButton = new JButton("Start");
        startButton.setBounds(45, 150, 70, 30);
        startButton.setFocusPainted(false);
        startButton.setBackground(Color.black);
        startButton.setForeground(Color.white);
        startButton.setFont(buttonFont);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if( currentState == TIMER_STATES.IDLE )
                {
                    if( stateLabel.getText().equals("SESSION"))
                    {
                        currentState = TIMER_STATES.SESSION;
                        toggleTimer();
                        updateTimer();
                    }
                    else if( stateLabel.getText().equals("SHORT BREAK") )
                    {
                        currentState = TIMER_STATES.SHORT_BREAK;
                        toggleTimer();
                        updateTimer();
                    }
                    else if( stateLabel.getText().equals("LONG BREAK") )
                    {
                        currentState = TIMER_STATES.LONG_BREAK;
                        toggleTimer();
                        updateTimer();
                    }
                
                
                }
                timer.start();
        }});
        

        resetButton = new JButton("Reset");
        resetButton.setBounds(115, 150, 70, 30);
        resetButton.setFocusPainted(false);
        resetButton.setBackground(Color.black);
        resetButton.setForeground(Color.white);
        resetButton.setFont(buttonFont);
        if( currentState == TIMER_STATES.LONG_BREAK || currentState == TIMER_STATES.SHORT_BREAK)
        {
            resetButton.setEnabled(false);
        }
        else
        {
            resetButton.setEnabled(true);
        }
        resetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                resetTimer();
            }
            
        });

        // Skip Button (only for break times)
        skipButton =  new JButton("Skip");
        skipButton.setBounds(185, 150, 70, 30);
        skipButton.setFocusPainted(false);
        skipButton.setBackground(Color.black);
        skipButton.setForeground(Color.white);
        skipButton.setFont(buttonFont);
        if( currentState != TIMER_STATES.SHORT_BREAK || currentState != TIMER_STATES.LONG_BREAK )
        {
            skipButton.setEnabled(false);
        }
        else
        {
            skipButton.setEnabled(true);
        }
        
        skipButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                switchStates();
            }
            
        });

        
        timer = new Timer(1000, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //if( currentState != TIMER_STATES.IDLE && currentState != TIMER_STATES.PAUSED )
                //{
                    updateTimer();
               // }
              
                
            }
            
        });
        timer.setRepeats(false);
            if( currentState != TIMER_STATES.IDLE )
            {
                timer.start();
            }



        this.add(startButton);
        this.add(resetButton);
        this.add(skipButton);
        this.add(timerPanel);
        this.add(statePanel);
        this.setVisible(true);
        
    }

    // It's the basic countdown method bro, what's there not to understand
    /*private void updateTimer()
    {
        if( currentState != TIMER_STATES.IDLE && currentState != TIMER_STATES.PAUSED)
        {

            if(minutes == 0 && seconds == 0)
            {
                if( currentState == TIMER_STATES.SESSION)
                {
                    pomodoroCount++;
                }
                switchStates();
            
            }
            else {
                if (seconds == 0) {
                    minutes--;
                    seconds = 59;
                } else {
                    seconds--;
                }
            }
            updateTimerLabel();
        }

    }*/
    private void updateTimer() {
        if (currentState != TIMER_STATES.IDLE ) {
            if (minutes == 0 && seconds == 0) {
                if (currentState == TIMER_STATES.SESSION) {
                    pomodoroCount++;
                }
                switchStates();
                timer.stop(); // Stop the timer when it reaches 0:00
            } else {
                if (seconds == 0) {
                    minutes--;
                    seconds = 59;
                } else {
                    seconds--;
                }
            }
            updateTimerLabel();
        }
    }
   

    // Formatting the timer label so it looks like 00:08 instead of 0:8
    private void updateTimerLabel() {
            String formattedTime = String.format("%02d:%02d", minutes, seconds);
            timerLabel.setText(formattedTime);
    }
    // Changing the name og the button according to the state 
    private void toggleTimer() // TODO This shit is causing me headaches. Find a way to fix it.
    {
        if ( currentState != TIMER_STATES.IDLE ) {
            currentState = TIMER_STATES.;
            startButton.setText("Resume");
            timer.stop();
        } else if( currentState == TIMER_STATES.PAUSED ) {
            currentState = TIMER_STATES.RUNNING;
            startButton.setText("Pause");
            timer.start();
        }
    }
    // Resetting the timer according to the state 
    private void resetTimer()
    {
        if( currentState == TIMER_STATES.SESSION )
        {
            minutes = 25;
            seconds = 0;
            toggleTimer();
            currentState = TIMER_STATES.IDLE;
        }
    }
    // Skipping the states or switching to break time after a session
    public void switchStates()
    {
        if( currentState == TIMER_STATES.SESSION )
        {
            resetButton.setEnabled(true);
            if( pomodoroCount % 4 == 0 )
            {
                currentState = TIMER_STATES.LONG_BREAK;
                stateLabel.setText("LONG BREAK");
                minutes = 20;
                seconds = 0;
            }
            else
            {
                currentState = TIMER_STATES.SHORT_BREAK;
                stateLabel.setText("SHORT BREAK");
                minutes = 5;
                seconds = 0;
            }
        }
        else if( currentState == TIMER_STATES.SHORT_BREAK || currentState == TIMER_STATES.LONG_BREAK )
        {
            currentState = TIMER_STATES.SESSION;
            stateLabel.setText("SESSION");
            minutes = 25;
            seconds = 0;
        }
    }
    
   
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
                
            }
        });
        }

}
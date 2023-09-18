package src;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.FloatControl;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;




public class App extends JFrame
{
    protected JButton startButton, resetButton, skipButton, audioStartButton, audioForwardButton, audioBackwardButton, rainButton;
    protected JPanel timerPanel, statePanel;
    protected JLabel sessionAmount, timerLabel, stateLabel, songName, progressBar;
    protected JSlider volumeSlider;
    

    
    public Log_Updater lUpdater = new Log_Updater();
   
    
    Icons icons = new Icons();
    TIMER_STATES currentState = TIMER_STATES.IDLE;
    TimerManager tm = new TimerManager(currentState, this);
    Audio audio = new Audio(this, tm);

    Font buttonFont = new Font("Arial", Font.BOLD, 10);
    

    public void makeUI() 
    {
        

        // The Frame
        final int WIDTH = 450;
        final int HEIGHT = 450;
        this.setResizable(false);
        this.setTitle("Pomodoro Timer");
        this.setSize(WIDTH, HEIGHT);
        this.getContentPane().setBackground(Color.black);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
       

        // Timer and State Panels
        timerPanel = new JPanel();
        timerPanel.setBounds(125, 75, 200, 50);
        timerPanel.setBackground(Color.black);

        statePanel = new JPanel();
        statePanel.setBounds(125, 10, 200, 60);
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
        stateLabel.setBounds(125, 10, 200, 60);
        stateLabel.setBackground(Color.black);
        stateLabel.setForeground(Color.white);
        stateLabel.setFont(new Font("Arial", Font.BOLD, 24));
        statePanel.add(stateLabel);

        // Amount of pomodoros done
        sessionAmount = new JLabel("Pomodoros: " + tm.sessionsCompleted);
        sessionAmount.setBounds(165, 200, 120, 30);
        sessionAmount.setHorizontalAlignment(JLabel.HORIZONTAL);
        sessionAmount.setBackground(Color.black);
        sessionAmount.setForeground(Color.white);
        sessionAmount.setFont(new Font("Arial", Font.BOLD, 13));
        this.add(sessionAmount);


        // Start Button
        startButton = new JButton("Start");
        startButton.setBounds(120, 150, 70, 30);
        startButton.setFocusPainted(false);
        startButton.setBackground(Color.black);
        startButton.setForeground(Color.white);
        startButton.setFont(buttonFont);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentState == TIMER_STATES.IDLE) {
                    tm.start();  // Start the timer
                    currentState = TIMER_STATES.SESSION;  // Update the timer state
                    startButton.setText("Pause");  // Change button text
                } else if (currentState == TIMER_STATES.SESSION) {
                    tm.stop();  // Pause the timer
                    currentState = TIMER_STATES.PAUSED;  // Update the timer state
                    startButton.setText("Resume");  // Change button text
                } else if (currentState == TIMER_STATES.PAUSED) {
                    tm.start();  // Resume the timer
                    currentState = TIMER_STATES.SESSION;  // Update the timer state
                    startButton.setText("Pause");  // Change button text
                }
        
                
                tm.checkButtonEnability();
                tm.updateTimer();
            }
        });
        
        
        
        
        
        
        // Reset Button 
        resetButton = new JButton("Reset");
        resetButton.setBounds(190, 150, 70, 30);
        resetButton.setFocusPainted(false);
        resetButton.setBackground(Color.black);
        resetButton.setForeground(Color.white);
        resetButton.setFont(buttonFont);
        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed( ActionEvent e )
            {
                if( currentState == TIMER_STATES.IDLE || currentState == TIMER_STATES.SESSION || currentState == TIMER_STATES.PAUSED ) 
                { tm.resetTimer(); }
                
            }
        });
        
        // Skip Button (only for break times)
        skipButton =  new JButton("Skip");
        skipButton.setBounds(260, 150, 70, 30);
        skipButton.setFocusPainted(false);
        skipButton.setBackground(Color.black);
        skipButton.setForeground(Color.white);
        skipButton.setFont(buttonFont);
        
        //Audio Backwards Button (This button restarts the audio from the beginning)
        audioBackwardButton = new JButton(icons.setIcon("resources/restart.png", 70, 40));
        audioBackwardButton.setBounds(105, 260, 70, 40);
        audioBackwardButton.setFocusPainted(false);
        audioBackwardButton.setBackground(Color.black);
        audioBackwardButton.setForeground(Color.white);
        audioBackwardButton.setFont(buttonFont);
        
        //Audio Forward Button (This button starts the next song )
        audioForwardButton = new JButton(icons.setIcon("resources/next_song.png", 70, 40));
        audioForwardButton.setBounds(275, 260, 70, 40);
        audioForwardButton.setFocusPainted(false);
        audioForwardButton.setBackground(Color.black);
        audioForwardButton.setForeground(Color.white);
        audioForwardButton.setFont(buttonFont);
        
        // Audio Start Button
        audioStartButton = new JButton(icons.setIcon("resources/start.png", 50, 40));
        audioStartButton.setBounds(175, 260, 50, 40);
        audioStartButton.setFocusPainted(false);
        audioStartButton.setBackground(Color.black);
        audioStartButton.setForeground(Color.white);
        audioStartButton.addActionListener(new ActionListener() {  //TODO fix this brah...

            @Override
            public void actionPerformed(ActionEvent e) {
               if ( audio.audioStartButtonPressed )
               {
                 audioStartButton.setIcon(icons.setIcon("resources/start.png", 50, 40));
               }
               else 
               {
                audioStartButton.setIcon(icons.setIcon("resources/pause.png", 50, 40));
               }
               audio.playAudio();
            }
            
        });
        
        // Rain Button
        rainButton = new JButton(icons.setIcon("resources/rainy.png", 50, 40));
        rainButton.setBounds(225, 260, 50, 40);
        rainButton.setBackground(Color.black);
        rainButton.setForeground(Color.white);
        rainButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              if( !audio.rainButtonPressed ) 
              {
                   audio.rainButtonPressed = true;  
                   rainButton.setIcon(icons.setIcon("resources/not_rainy.png", 50, 40));
              }
              else
              {
                audio.rainButtonPressed = false;
                rainButton.setIcon(icons.setIcon("resources/rainy.png", 50, 40));
              }
              audio.startRain();
            }
            
        });

        // Song Name Label
        songName = new JLabel(audio.song_Name);
        songName.setBounds(150,300,150,40);
        songName.setHorizontalAlignment(JLabel.HORIZONTAL);
        songName.setBackground(Color.black);
        songName.setForeground(Color.white);
        songName.setFont(new Font("Arial", Font.BOLD, 12));

        // Progress Bar 
        progressBar = new JLabel("");
        progressBar.setBounds(100,350, 250, 20);
        progressBar.setBackground(Color.black);
        progressBar.setForeground(Color.white);
        progressBar.setFont(new Font("Purisa", Font.BOLD, 15));
        progressBar.setHorizontalAlignment(JLabel.HORIZONTAL);
        
        // Volume Slider
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeSlider.setBounds(370, 0, 80, 50);
        volumeSlider.setBackground(Color.black);
        volumeSlider.setForeground(Color.white);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setMinorTickSpacing(5);
        volumeSlider.setPaintTicks(true);
        
        //volumeSlider.setPaintLabels(true);
        this.add(volumeSlider);
        volumeSlider.addChangeListener(new ChangeListener() {
        @Override
        public void stateChanged(ChangeEvent e) {
                if( audio.clip!= null )
                {
                int volume = volumeSlider.getValue(); // Get the current slider value (0-100)
                // Adjust the volume of the audio , using the 'volume' value
                FloatControl gainControl = (FloatControl) audio.clip.getControl(FloatControl.Type.MASTER_GAIN);
                float volumeLevel = volume / 100.0f; // Convert to a float between 0 and 1
                gainControl.setValue(volumeLevel); // Adjust the volume of the audio clip  
                }
            }
        });



       
        
        this.add(startButton);
        this.add(resetButton);
        this.add(skipButton);
        this.add(timerPanel);
        this.add(statePanel);
        this.add(audioBackwardButton);
        this.add(audioForwardButton);
        this.add(audioStartButton);
        this.add(songName);
        this.add(progressBar);
        this.add(rainButton);
        this.setVisible(true);
        
    }

    public void toggleTimer()
    {
        if( currentState == TIMER_STATES.SESSION )
        {
            startButton.setText("Pause");
            
        }
        else if( currentState == TIMER_STATES.PAUSED )
        {
            
            startButton.setText("Resume");
            
           
        }
        else if( currentState == TIMER_STATES.IDLE )
        {
            startButton.setText("Start");
            
        }
    }
    
   
     


}
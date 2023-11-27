package src;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.GridBagConstraints;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;




public class App extends JFrame
{
    protected JButton startButton, resetButton, skipButton, audioStartButton, audioForwardButton, audioBackwardButton, rainButton;
    protected JPanel timerPanel, buttonPanel;
    protected JLabel sessionAmount, timerLabel, stateLabel, songName, progressBar;
   
    // FIXED: implement a layout manager for GUI elements

    
    public Log_Updater lUpdater = new Log_Updater();
    Icons icons = new Icons();
    TIMER_STATES currentState = TIMER_STATES.IDLE;
    TimerManager tm = new TimerManager(currentState, this);
    Audio audio = new Audio(this, tm);
    

    Font buttonFont = new Font("Arial", Font.BOLD, 20);
    

    public void makeUI()
    {
        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        // The Frame
        final int WIDTH = (int) (screenWidth * 0.2);
        final int HEIGHT = (int) (screenHeight * 0.3);
        this.setResizable(true);
        this.setTitle("Pomodoro Timer 🍅 ");
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.getContentPane().setBackground(Color.black);
        this.setLayout(new GridBagLayout());
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

		GridBagConstraints gbc = new GridBagConstraints();
        
       

        //Button Panel 
        buttonPanel = new JPanel(new GridLayout(1,3));
        buttonPanel.setBackground(Color.black);
        gbc.gridx = 2;
		gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
		gbc.weightx = 0.2;
        gbc.insets = new Insets(0, 0, 5, 0);
        this.add(buttonPanel,gbc);

        // Timer Label
        tm.minutes  = TimerEditor.getSessionMinutes();
        timerLabel = new JLabel(tm.minutes+":"+tm.seconds);
        tm.updateTimerLabel();
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
		gbc.weightx = 0.5;
        gbc.insets = new Insets(5, 5, 10, 5);
		this.add(timerLabel,gbc);
        timerLabel.setFont(new Font("Arial", Font.BOLD, 54));
        timerLabel.setBackground(Color.black);
        timerLabel.setForeground(Color.white);
        //timerPanel.add(timerLabel);

        // State Label
        stateLabel = new JLabel("SESSION");
        stateLabel.setBackground(Color.black);
        stateLabel.setForeground(Color.white);
		gbc.gridx = 2;
		gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
		gbc.weightx = 1;
        gbc.insets = new Insets(5, 0, 10, 0);

		this.add(stateLabel,gbc);
        stateLabel.setFont(new Font("Arial", Font.BOLD, 35));
        //buttonPanel.add(stateLabel);

        // Amount of pomodoros done
        sessionAmount = new JLabel("Pomodoros: " + tm.sessionsCompleted);
        sessionAmount.setBackground(Color.black);
        sessionAmount.setForeground(Color.white);
		gbc.gridx = 2;
		gbc.gridy = 6;
		gbc.gridwidth = 2;
		gbc.fill = GridBagConstraints.CENTER;
		gbc.weightx = 0.5;
        gbc.insets = new Insets(15, 0, 0, 0);
		this.add(sessionAmount,gbc);
        sessionAmount.setFont(new Font("Arial", Font.BOLD, 18));


        // Start Button
        startButton = new JButton("Start");
        startButton.setFocusPainted(false);
        startButton.setBackground(Color.black);
        startButton.setForeground(Color.white);
        startButton.setFont(buttonFont);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentState == TIMER_STATES.IDLE) {
                    tm.startTime = new Date();
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
				buttonPanel.add(startButton);
        
        // Reset Button 
        resetButton = new JButton("Reset");
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
		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc.weightx = 0.2;
        gbc.insets = new Insets(0, 0, 5, 0);
		buttonPanel.add(resetButton);


        // Skip Button (only for break times)
        skipButton =  new JButton("Skip");
        skipButton.setFocusPainted(false);
        skipButton.setBackground(Color.black);
        skipButton.setForeground(Color.white);
        skipButton.setFont(buttonFont);
        skipButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
               tm.switchStates();
            }
            
        }); 
        tm.name = TimerEditor.getName();
		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.ABOVE_BASELINE;
		gbc.weightx = 0.2;
        gbc.insets = new Insets(0, 0, 5, 0);

		buttonPanel.add(skipButton);

        
        
        this.setVisible(true);
		this.pack();
        
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
    public void handleShutdown() {
        if (currentState == TIMER_STATES.SESSION) {
            // Update the log with the current minutes worked
            lUpdater.logSessionData(tm.startTime, tm.finishTime,tm.sessionsCompleted, tm.totalMinutesWorked,tm.name);
        }
    }
   
     


}

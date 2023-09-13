package src;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    protected int sessionsCompleted = 0;
    protected int totalMinutesWorked = 0;
    public Log_Updater lUpdater = new Log_Updater();
    public TIMER_STATES currentState;
    Logic logic = new Logic();
    

    Font buttonFont = new Font("Arial", Font.BOLD, 10);

    public App()
    {
        // The Frame
        final int WIDTH = 300;
        final int HEIGHT = 300;
        this.setResizable(false);
        this.setTitle("Pomodoro Timer");
        this.setSize(WIDTH, HEIGHT);
        this.getContentPane().setBackground(Color.black);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        
        // Set Initial State to IDLE 
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
        stateLabel.setFont(new Font("Arial", Font.BOLD, 24));
        statePanel.add(stateLabel);

        // Amount of pomodoros done
        sessionAmount = new JLabel("Pomodoros: " + sessionsCompleted);
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
        
        
       
        

        resetButton = new JButton("Reset");
        resetButton.setBounds(115, 150, 70, 30);
        resetButton.setFocusPainted(false);
        resetButton.setBackground(Color.black);
        resetButton.setForeground(Color.white);
        resetButton.setFont(buttonFont);
        

        // Skip Button (only for break times)
        skipButton =  new JButton("Skip");
        skipButton.setBounds(185, 150, 70, 30);
        skipButton.setFocusPainted(false);
        skipButton.setBackground(Color.black);
        skipButton.setForeground(Color.white);
        skipButton.setFont(buttonFont);
        
       

        


        this.add(startButton);
        this.add(resetButton);
        this.add(skipButton);
        this.add(timerPanel);
        this.add(statePanel);
        this.setVisible(true);
        
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
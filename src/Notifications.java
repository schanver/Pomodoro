package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;




public class Notifications 
{
    App app;
    JFrame frame;
    JLabel notificationLabel,frameLabel;
    Random random = new Random();

    protected void showNotification(App app)
    {   
        // TODO: find a way to get rid of the 
        if( frame != null && frame.isVisible() )
        {
            frame.dispose();
            showNotification(app);
        }
        this.app = app;
        final int NOTIFICATION_DELAY = 3000;
		int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
		int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
		final int SCREEN_WIDTH = (int)(screenWidth * 0.25);
		final int SCREEN_HEIGHT = (int)(screenHeight * 0.1);


        String[] sessionMessages =  {   "Let's get started! Your productive session begins now.",
                                        "Focus time begins! Time to tackle your tasks.",
                                        "Ready, set, work! Your Pomodoro session is underway.",
                                        "Work mode activated. Make the most of these 25 minutes.",
                                        "Productivity mode on! It's time to dive into your work.",
                                        "Get in the zone! Your Pomodoro session has begun.",
                                        "Start working now, and success will follow.",
                                        "You know what they say about success, it loves hardworkers.",
                                        "Early bird gets the worm! Let's get to work!",
                                        "Make your future self proud! Let's build a good life for you!" 
                                    };
    
        String[] breakMessages =    {
                                        "Great job! It's time for a well-deserved break.",
                                        "Take a breather! Your break has started.",
                                        "Relax and recharge. Your break is here.",
                                        "Enjoy your break! You earned it.",
                                        "Time to step away from work. Break mode initiated.",
                                        "You've earned this break. Make it count!",
                                        "Recharge your energy for the next session.",
                                        "Good work out there! Let's catch our breath a litte bit!"
                                    };
       
         int sessionSize = sessionMessages.length;
         int breakSize = breakMessages.length;
        // Notification  frame
        frame = new JFrame();
        frame.setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        frame.getContentPane().setBackground(Color.BLACK);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setResizable(false);
        frame.setUndecorated(true);


        int x = 0;
        int y = 0;

        frame.setLocation(x, y);

        // Create a custom title bar panel
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setSize(300,30);
        titleBar.setBackground(Color.GRAY);

        // Close button
        JButton closeButton = new JButton("X");
        closeButton.setForeground(Color.WHITE);
        closeButton.setBackground(Color.RED.brighter().brighter());
        titleBar.add(closeButton, BorderLayout.EAST);
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            disposeTheFrame();
            }
        });
       
        //Label
        notificationLabel  = new JLabel("TEST", JLabel.HORIZONTAL);
        notificationLabel.setForeground(Color.WHITE);
        notificationLabel.setBackground(Color.WHITE);
        notificationLabel.setFont(new Font("Arial",Font.BOLD,16));

        frameLabel = new JLabel("", JLabel.HORIZONTAL);
        frameLabel.setForeground(Color.WHITE);
        frameLabel.setBackground(Color.black);
        frameLabel.setFont(new Font("Arial",Font.BOLD,15));
        
        Timer frameTimer = new Timer(NOTIFICATION_DELAY, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
              disposeTheFrame();
            }
            
        });
        frameTimer.setRepeats(true);
        frameTimer.start();
        
    

        switch( app.currentState)
        {
            case SESSION: 
            {
                frameLabel.setText("Break is over");
                notificationLabel.setText(sessionMessages[random.nextInt(sessionSize - 1)]);
                break;
            }
            case SHORT_BREAK:
            {
                frameLabel.setText(" Session is over");
                notificationLabel.setText(breakMessages[random.nextInt(breakSize - 1)]);
                break;
            }
            case LONG_BREAK:
            {
                frameLabel.setText(" Session is over");
                notificationLabel.setText(breakMessages[random.nextInt(breakSize - 1)]);
                break;
            }
            default:
                break;
        }

        titleBar.add(frameLabel,BorderLayout.WEST);
        frame.getContentPane().add(titleBar,BorderLayout.NORTH);
        frame.getContentPane().add(notificationLabel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

		
    public void disposeTheFrame()
	{
		frame.dispose();
	}
}





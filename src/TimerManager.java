package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class TimerManager  {
    protected Timer timer;
    protected int minutes = 1;  //TODO: Change this to 25 after testing
    protected int seconds = 0;
    protected int sessionsCompleted = 0;
    protected int totalMinutesWorked = 0;
    protected int totalSecondsWorked = 0;
   
    Log_Updater lUpdater = new Log_Updater();
    Notifications notifications = new Notifications();
    App app;
    Audio audio = new Audio(app, this);


    public TimerManager(TIMER_STATES initialState, App app ) {
        this.app = app;
        app.currentState = initialState;
       
        // Initialize timer and other properties here
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateTimer();
            }
        });
        timer.setRepeats(true);
    }

    public void start() {
        // Start the timer
        timer.start();
        app.toggleTimer();
    }
    
    
   
    public void updateTimer() {  /* F: MAKE A FCKING LOGIC CHART FOR THIS FUNCTION DUMBASS, DON'T TRY TO BRUTE-FORCE IT
                                M: It works now though, my intelligence needs no logic chart! */ 
        if (minutes == 0 && seconds == 0) {
            if (app.currentState == TIMER_STATES.SESSION) {
                sessionsCompleted++;
                lUpdater.logSessionData(sessionsCompleted, totalMinutesWorked);
                //audio.ringTone(0);
                //notifications.showNotification("End of the Session", "Your session is over. Enjoy your hard earned break!", null);
                }
                switchStates();
    
        } else {
            if (seconds == 0) {
                seconds = 59;
                minutes--;
                totalMinutesWorked++;
            } else {
                seconds--;
            }
        }
        updateTimerLabel();
        app.sessionAmount.setText("Pomodoros:" + sessionsCompleted);
    }
    

    public void stop() {
        // Stop the timer
        timer.stop();
        app.toggleTimer();
    }

    public void resetTimer()
    {
        if( app.currentState == TIMER_STATES.SESSION || app.currentState == TIMER_STATES.IDLE || app.currentState == TIMER_STATES.PAUSED )
        {
            minutes = 25;
            seconds = 0;
            app.currentState = TIMER_STATES.IDLE;
            stop();
            updateTimerLabel();
            app.startButton.setText("Start");
        }
        
    }
    public void checkButtonEnability()  //  This function checks the availability of the buttons and disables them depending on the state.
    {
        if( app.currentState == TIMER_STATES.SESSION || app.currentState == TIMER_STATES.PAUSED || app.currentState == TIMER_STATES.IDLE )
        {
            app.startButton.setEnabled(true);
            app.resetButton.setEnabled(true);
            app.skipButton.setEnabled(false);
        }

        if( app.currentState == TIMER_STATES.SHORT_BREAK || app.currentState == TIMER_STATES.LONG_BREAK )
        {
            app.skipButton.setEnabled(true);
            app.startButton.setEnabled(false);
            app.resetButton.setEnabled(false);
        }
        
        
    }
    public void switchStates() 
    {
        if (app.currentState == TIMER_STATES.SESSION) {
            if (sessionsCompleted > 0 && sessionsCompleted % 4 == 0) {
                app.currentState = TIMER_STATES.LONG_BREAK;
                app.stateLabel.setText("LONG BREAK");
                minutes = 15;
                seconds = 0;
                
            } else {
                app.currentState = TIMER_STATES.SHORT_BREAK;
                app.stateLabel.setText("SHORT BREAK");
                minutes = 5;
                seconds = 0;
            }
        } else if (app.currentState == TIMER_STATES.SHORT_BREAK || app.currentState == TIMER_STATES.LONG_BREAK) {
            app.currentState = TIMER_STATES.SESSION;
            app.stateLabel.setText("SESSION");
            minutes = 1; //TODO change this after the tests
            seconds = 0;
        }
    
        // Call updateTimer only once after updating minutes and seconds
        updateTimer();
        checkButtonEnability();
        
    }
    
    
    public void updateTimerLabel() 
    {
        String formattedTime = String.format("%02d:%02d", minutes, seconds);
        app.timerLabel.setText(formattedTime);
    }

    
    
}

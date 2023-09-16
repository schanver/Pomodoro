package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class TimerManager  {
    protected Timer timer;
    protected int minutes = 25;
    protected int seconds = 0;
    protected int sessionsCompleted = 0;
    protected int totalMinutesWorked = 0;
   
    protected Log_Updater lUpdater = new Log_Updater();
    App app;


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
        timer.setRepeats(false);
    }

    public void start() {
        // Start the timer
        timer.start();
        app.toggleTimer();
    }
    
    

    public void updateTimer() {
        if (minutes == 0 && seconds == 0) {
            if (app.currentState == TIMER_STATES.SESSION) {
                sessionsCompleted++;
                lUpdater.logSessionData(sessionsCompleted, totalMinutesWorked);
            }
            
            // Check if it's a break, and stop the timer if it is
            if (app.currentState == TIMER_STATES.SHORT_BREAK || app.currentState == TIMER_STATES.LONG_BREAK) {
                stop();
            } else {
                switchStates();
            }
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
    }
    

    public void stop() {
        // Stop the timer
        timer.stop();
        app.toggleTimer();
    }

    public void resetTimer()
    {
        if( app.currentState == TIMER_STATES.SESSION || app.currentState == TIMER_STATES.IDLE )
        {
            minutes = 25;
            seconds = 0;
            updateTimerLabel();
            app.startButton.setText("Start");
        }
        
    }
    public void checkButtonEnability()
    {
        if( app.currentState == TIMER_STATES.SESSION )
        {
            app.startButton.setEnabled(true);
            app.resetButton.setEnabled(true);
            app.skipButton.setEnabled(false);
        }
        else if( app.currentState == TIMER_STATES.SHORT_BREAK )
        {
            app.skipButton.setEnabled(true);
            app.startButton.setEnabled(false);
            app.resetButton.setEnabled(false);
        }
        else if( app.currentState == TIMER_STATES.LONG_BREAK )
        {
            app.skipButton.setEnabled(true);
            app.startButton.setEnabled(false);
            app.resetButton.setEnabled(false);
        }
        else if( app.currentState == TIMER_STATES.PAUSED )
        {
            app.startButton.setEnabled(true);
            app.resetButton.setEnabled(true);
            app.skipButton.setEnabled(false);
        }
        else if( app.currentState == TIMER_STATES.IDLE )
        {
            app.startButton.setEnabled(true);
            app.resetButton.setEnabled(true);
            app.skipButton.setEnabled(false);
        }
    }
    public void switchStates() {
        if (app.currentState == TIMER_STATES.SESSION) {
            if (sessionsCompleted > 0 && sessionsCompleted % 4 == 0) {
                app.currentState = TIMER_STATES.LONG_BREAK;
                minutes = 15;
                seconds = 0;
            } else {
                app.currentState = TIMER_STATES.SHORT_BREAK;
                minutes = 5;
                seconds = 0;
            }
        } else if (app.currentState == TIMER_STATES.SHORT_BREAK || app.currentState == TIMER_STATES.LONG_BREAK) {
            app.currentState = TIMER_STATES.SESSION;
            minutes = 25;
            seconds = 0;
        }
    
        // Call updateTimerLabel() only once after updating minutes and seconds
        updateTimer();
    }
    
    
    public void updateTimerLabel() {
        String formattedTime = String.format("%02d:%02d", minutes, seconds);
        app.timerLabel.setText(formattedTime);
        }

    
    
}

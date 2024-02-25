package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

import javax.swing.Timer;

public class TimerManager  {
    protected Timer timer;

    
    int i = 0;
    Log_Updater lUpdater = new Log_Updater();
    Notifications notifications = new Notifications();
    App app;
    Audio audio = new Audio(app, this);
    protected Date startTime, finishTime;

    String name = "";
    int sessionMinutes;
    int sBreakMinutes;
    int lBreakMinutes;
    protected int minutes = 0;  
    protected int seconds = 0;
    protected int sessionsCompleted = 0;
    protected int totalMinutesWorked = 0;

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
    
    public void setTimer()
    {
        switch(app.currentState)
        {
            case SESSION:
            {
                minutes = TimerEditor.getSessionMinutes();
                break;
            }
            default:
            break;
        }
        
    }
    
  

    public void start() {
        // Start the timer
        timer.start();
        app.toggleTimer();
    }


    //
    //

    //
    //

    //
    //

    //
    //
    
    
   
    public void updateTimer() {  /* F: MAKE A  LOGIC CHART FOR THIS FUNCTION DUMBASS, DON'T TRY TO BRUTE-FORCE IT
                                    M: It works now though, my intelligence needs no logic chart! */ 
        while( i < 1)
        { setTimer(); i++;
           }
        if (minutes == 0 && seconds == 0) {
            if (app.currentState == TIMER_STATES.SESSION) {
                sessionsCompleted++;
                finishTime = new Date();

                // TRANSF

                sql SQL = new sql();

                // Initialisation of a SQLtable to save all informations related to the activity saved in the variable "name"

                SQL.create(name);

                //

                SQL.insert(startTime, finishTime, sessionsCompleted, totalMinutesWorked,name);
              
                lUpdater.logSessionData(startTime, finishTime, sessionsCompleted, totalMinutesWorked,name);
                //audio.ringTone(0);
                
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
            minutes = TimerEditor.getSessionMinutes();
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
                minutes = TimerEditor.getLongBreakMinutes();
                seconds = 0;
                
            } else {
                app.currentState = TIMER_STATES.SHORT_BREAK;
                app.stateLabel.setText("SHORT BREAK");
                minutes = TimerEditor.getShortBreakMinutes();   
                seconds = 0;
            }
        } else if (app.currentState == TIMER_STATES.SHORT_BREAK || app.currentState == TIMER_STATES.LONG_BREAK) {
            app.currentState = TIMER_STATES.SESSION;
            app.stateLabel.setText("SESSION");
            minutes = TimerEditor.getSessionMinutes(); 
            seconds = 0;
        }
        notifications.showNotification(app);
        audio.ringTone();
        updateTimer();
        checkButtonEnability();
        
    }
    
    
    public void updateTimerLabel() 
    {
        String formattedTime = String.format("%02d:%02d", minutes, seconds);
        app.timerLabel.setText(formattedTime);
    }

    

    
    
}

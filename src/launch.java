package src;

import javax.swing.SwingUtilities;


public class launch {
    
    public static void main(String[] args) {
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() 
            {   
                App app = new App();
                TimerManager tm = new TimerManager(TIMER_STATES.IDLE, app);
                TimerEditor.makeUI(app, tm);
             
                
            }
        });
         Runtime.getRuntime().addShutdownHook(new Thread() {
            
                @Override
                public void run() {
                    App app = new App();
                    app.handleShutdown();
                }
    
        });
        
    }
    

}


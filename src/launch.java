package src;









import javax.swing.SwingUtilities;


public class launch {
    
    public static void main(String[] args) {
        /*SwingUtilities.invokeLater(new Runnable() {
            App app = new App();
            @Override
            public void run() {
               
                app.makeUI();
                
              // Notifications notifications = new Notifications();
                //notifications.showNotification("Pomodoro", "Let's start the session", MessageType.INFO);
                
            }
        });
         Runtime.getRuntime().addShutdownHook(new Thread() {
            App app = new App();
                @Override
                public void run() {
                    app.handleShutdown();
                }
    
        });
    
    }*/
    App app = new App();
    app.currentState = TIMER_STATES.SESSION;
    Notifications notifications = new Notifications();
    notifications.showNotification(app);

}
}

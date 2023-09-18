package src;


import java.awt.TrayIcon.MessageType;

import javax.swing.SwingUtilities;

public class launch {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                /*App app = new App();
                app.makeUI();*/
                Notifications notifications = new Notifications();
                notifications.showNotification("End of the Session", "Your session is over." + '\n'+ "Enjoy your hard earned break!", MessageType.INFO);
         
            }
        });
        }
    
}

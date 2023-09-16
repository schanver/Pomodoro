package src;

import java.awt.*;
import java.awt.TrayIcon.MessageType;



public class Notifications 
{
    protected void showNotification(String title, String message, MessageType messageType) {
        
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            
            Image image = Toolkit.getDefaultToolkit().getImage("/src/resources/app_icon"); // Replace "icon.png" with your app's icon file path
    
            TrayIcon trayIcon = new TrayIcon(image, "Pomodoro Timer");
            trayIcon.setImageAutoSize(true);
    
            try {
                tray.add(trayIcon);
                trayIcon.displayMessage(title, message, messageType);
                
            } catch (AWTException e) {
                System.err.println("SystemTray could not be added.");
                e.printStackTrace();
            }
        }
    }
    
}

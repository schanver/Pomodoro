package src;

import java.awt.*;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;



public class Notifications 
{
    protected void showNotification(String title, String message, MessageType type)
    {
    if (SystemTray.isSupported()) {
        // Create a SystemTray instance
        SystemTray systemTray = SystemTray.getSystemTray();

        // Load your notification icon (replace with your own icon path)
        Image trayImage = Toolkit.getDefaultToolkit().getImage("resources/pomodoro.png");

        // Create a PopupMenu for the system tray
        PopupMenu popupMenu = new PopupMenu();

        // Create a MenuItem for the close action
        MenuItem closeItem = new MenuItem("Close");
        closeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0); // Close the application when the "Close" option is selected
            }
        });

        // Add the close MenuItem to the PopupMenu
        popupMenu.add(closeItem);

        // Create a TrayIcon with your icon and PopupMenu
        TrayIcon trayIcon = new TrayIcon(trayImage, "Pomodoro Timer", popupMenu);

        // Set the TrayIcon to automatically size itself based on the icon's dimensions
        trayIcon.setImageAutoSize(true);

        try {
            // Add the TrayIcon to the SystemTray
            systemTray.add(trayIcon);

            
            trayIcon.displayMessage(title, message, type);
            // Create a timer to close the notification after 3 seconds
            Timer timer = new Timer(5000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Remove the TrayIcon to close the notification
                    systemTray.remove(trayIcon);
                }
            });
            timer.setRepeats(false); // Only fire the timer once
            timer.start();
        } catch (AWTException ex) {
            System.err.println("SystemTray not supported.");
        }
    }
     else {
        System.err.println("SystemTray is not supported.");
       }   
    }
}





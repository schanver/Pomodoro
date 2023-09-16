package src;

import javax.swing.SwingUtilities;

public class launch {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                App app = new App();
                app.makeUI();
            }
        });
        }
    
}

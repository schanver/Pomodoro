package src;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TimerEditor {
        
       
        
        TimerManager tm;
        App app;
        static JFrame inputFrame;
        static JLabel sessionLabel, shortLabel, longLabel;
        static JTextField sessionField, shortField, longField;
        static JButton saveButton,resetButton;
        static Font labelFont = new Font("Arial", Font.PLAIN, 10);
        static Font buttonFont = new Font("Arial",Font.BOLD,8 );
    
   
    protected static void makeUI(App app, TimerManager tm)
    {
        
       // Create the input frame
        inputFrame = new JFrame();
        inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        inputFrame.setSize(400, 300);
        inputFrame.setResizable(false);
        inputFrame.setLocationRelativeTo(null);
        inputFrame.getContentPane().setBackground(Color.black);
        inputFrame.setTitle("Timer Editor ⏲️");
        inputFrame.setLayout(null);

        // This variable is used to filter the user input so that the user can only enter integer values.
         DocumentFilter filter = new DocumentFilter() {
            @Override
            public void insertString(DocumentFilter.FilterBypass fb, int offset, String text, AttributeSet attr) throws BadLocationException {
                if (text.matches("\\d+")) {
                    super.insertString(fb, offset, text, attr);
                }
            }

            @Override
            public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attr) throws BadLocationException {
                if (text.matches("\\d+")) {
                    super.replace(fb, offset, length, text, attr);
                }
            }
        };

        // Classical GUI implementation brah, not much to explain here. Nur eine große Zeitverschwendung.

        sessionLabel = new JLabel("Enter the duration of sessions : (default is 45 minutes)", JLabel.HORIZONTAL);
        shortLabel = new JLabel("Enter the duration of short breaks : (default is 5 minutes)", JLabel.HORIZONTAL);
        longLabel = new JLabel("Enter the duration of long breaks : (default is 15 minutes)", JLabel.HORIZONTAL);

        sessionLabel.setBounds(60, 10, 280, 20);
        sessionLabel.setBackground(Color.BLACK);
        sessionLabel.setForeground(Color.WHITE);
        sessionLabel.setFont(labelFont);
        
        shortLabel.setBounds(60,70,280, 20);
        shortLabel.setBackground(Color.BLACK);
        shortLabel.setForeground(Color.white);
        shortLabel.setFont(labelFont);

        longLabel.setBounds(60,130,280, 20);
        longLabel.setBackground(Color.BLACK);
        longLabel.setForeground(Color.white);
        longLabel.setFont(labelFont);

        sessionField = new JTextField();
        sessionField.setBounds(150, 40, 100, 20);
        AbstractDocument sessionDoc = (AbstractDocument) sessionField.getDocument();
        sessionDoc.setDocumentFilter(filter);
        
        
        shortField = new JTextField();
        shortField.setBounds(150, 100, 100, 20);
        AbstractDocument shortDoc = (AbstractDocument) shortField.getDocument();
        shortDoc.setDocumentFilter(filter);


        longField = new JTextField();
        longField.setBounds(150, 160, 100, 20);
        AbstractDocument longDoc = (AbstractDocument) shortField.getDocument();
        longDoc.setDocumentFilter(filter);

        saveButton = new JButton("Save Changes");
        saveButton.setBounds(100,200,100, 40);
        saveButton.setBackground(Color.BLACK);
        saveButton.setForeground(Color.white);
        saveButton.setFont(buttonFont);
        saveButton.setFocusPainted(false);
        saveButton.isFocusable();
        saveButton.addActionListener(new ActionListener() {

            /* This Action Listener is responsible for overwriting the duration of the session and breaks depending on 
            if the user filled the text fields.*/
            @Override
            public void actionPerformed(ActionEvent e) {
                tm.sessionMinutes = getSessionMinutes();
                tm.sBreakMinutes = getShortBreakMinutes();
                tm.lBreakMinutes = getLongBreakMinutes();
                
                System.out.println("Session: " + getSessionMinutes());
                System.out.println("Long Break: " + getLongBreakMinutes());
                System.out.println("Short Break: " + getShortBreakMinutes());
                app.makeUI();
                inputFrame.dispose();
            }
       

        });
        // This button clears the text fields
        resetButton = new JButton("Reset");
        resetButton.setBounds(200,200,100, 40);
        resetButton.setBackground(Color.black);
        resetButton.setForeground(Color.white);
        resetButton.setFont(buttonFont);
        resetButton.setFocusPainted(false);
        resetButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                sessionField.setText("");
                shortField.setText("");
                longField.setText("");
            }

        });
        KeyListener keyListener = new KeyListener() {

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) 
                {
                  saveButton.doClick();
                }  
            }

            @Override
            public void keyReleased(KeyEvent e) {
              
            }

            @Override
            public void keyTyped(KeyEvent e) {
                
            }
        };
        sessionField.addKeyListener(keyListener);
        shortField.addKeyListener(keyListener);
        longField.addKeyListener(keyListener);


        //Add the components to the input frame
        inputFrame.add(sessionLabel);
        inputFrame.add(sessionField);
        inputFrame.add(shortLabel);
        inputFrame.add(shortField);
        inputFrame.add(longLabel);
        inputFrame.add(longField);
        inputFrame.add(saveButton);
        inputFrame.add(resetButton);
        inputFrame.setVisible(true);
    
    }
    public static int getSessionMinutes() 
    {
        return (sessionField.getText().isEmpty())? 45 : Integer.parseInt(sessionField.getText());
    }
    public static int getShortBreakMinutes() 
    {
        return (shortField.getText().isEmpty())? 5 : Integer.parseInt(shortField.getText());
    }
    public static int getLongBreakMinutes() 
    {
        return (longField.getText().isEmpty())? 15 : Integer.parseInt(longField.getText());
    }
   
}

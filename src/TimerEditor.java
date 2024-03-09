package src;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class TimerEditor {
        
       
        
        TimerManager tm;
        App app;
        static JFrame inputFrame;
        static JPanel buttonPanel,namePanel,sesPanel,shortPanel,longPanel;
        static JLabel nameLabel,sessionLabel, shortLabel, longLabel;
        static JTextField nameField,sessionField, shortField, longField;
        static JButton saveButton,resetButton;
        static Font labelFont = new Font("Arial", Font.BOLD, 15);
        static Font buttonFont = new Font("Arial",Font.BOLD,12 );
        static Font fieldFont = new Font("Arial",Font.PLAIN, 55);
   
    protected static void makeUI(App app, TimerManager tm)
    {
        double screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        double screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;

        // The Frame
        final int WIDTH = (int) (screenWidth * 0.125);;
        final int HEIGHT = (int) (screenHeight * 0.25);
        
       // Create the input frame
        inputFrame = new JFrame();
        inputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        inputFrame.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        inputFrame.setResizable(true);
        inputFrame.setLocationRelativeTo(null);
        inputFrame.getContentPane().setBackground(Color.black);
        inputFrame.setTitle("Timer Editor  ");
        inputFrame.setLayout(new GridLayout(5,1));

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
        GridBagConstraints gbc = new GridBagConstraints();

        namePanel = new JPanel(new GridLayout(2,1));
        namePanel.setBackground(Color.black);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.fill = GridBagConstraints.NORTH;
        inputFrame.add(namePanel,gbc);

        nameLabel = new  JLabel("Enter the name of the activity: (default is Study Session)");	
        nameLabel.setBackground(Color.black);
		nameLabel.setForeground(Color.white);
		nameLabel.setFont(labelFont);

        nameField = new JTextField();
        nameField.setFont(buttonFont);

        namePanel.add(nameLabel);
        namePanel.add(nameField);

        
        sesPanel = new JPanel(new GridLayout(2,1));
        sesPanel.setBackground(Color.black);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputFrame.add(sesPanel,gbc);

        sessionLabel = new JLabel("Enter the duration of sessions : (default is 45 minutes)");
        sessionLabel.setBackground(Color.BLACK);
        sessionLabel.setForeground(Color.WHITE);
        sessionLabel.setFont(labelFont);

        sessionField = new JTextField();
        sessionField.setFont(buttonFont);

        AbstractDocument sessionDoc = (AbstractDocument) sessionField.getDocument();
        sessionDoc.setDocumentFilter(filter);

        sesPanel.add(sessionLabel);
        sesPanel.add(sessionField);
        
        shortPanel = new JPanel(new GridLayout(2,1));
        shortPanel.setBackground(Color.black);
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputFrame.add(shortPanel,gbc);

        shortLabel = new JLabel("Enter the duration of short breaks : (default is 5 minutes)");
        shortLabel.setBackground(Color.BLACK);
        shortLabel.setForeground(Color.white);
        shortLabel.setFont(labelFont);

        shortField = new JTextField();
        shortField.setFont(buttonFont);
        
        AbstractDocument shortDoc = (AbstractDocument) shortField.getDocument();
        shortDoc.setDocumentFilter(filter);

        shortPanel.add(shortLabel);
        shortPanel.add(shortField);

        longPanel = new JPanel(new GridLayout(2,1));
        longPanel.setBackground(Color.black);
        gbc.gridx = 0;
        gbc.gridy = 3; 
        gbc.fill = GridBagConstraints.HORIZONTAL;
        inputFrame.add(longPanel,gbc);

        longLabel = new JLabel("Enter the duration of long breaks : (default is 15 minutes)");
        longLabel.setBackground(Color.BLACK);
        longLabel.setForeground(Color.white);
        longLabel.setFont(labelFont);
		
        longField = new JTextField();
        longField.setFont(buttonFont);
        
        AbstractDocument longDoc = (AbstractDocument) shortField.getDocument();
        longDoc.setDocumentFilter(filter);

        longPanel.add(longLabel);
        longPanel.add(longField);

        saveButton = new JButton("Save Changes");
       
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
                
                
                app.makeUI();
                inputFrame.dispose();
            }
       

        });
        // This button clears the text fields
        resetButton = new JButton("Reset");
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
				nameField.setText("");
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
		nameField.addKeyListener(keyListener);

        buttonPanel = new JPanel(new GridLayout(1,2));
        gbc.gridx = 0;
        gbc.gridy = 3; 
        gbc.fill = GridBagConstraints.SOUTH;

        buttonPanel.add(saveButton);
        buttonPanel.add(resetButton);
        inputFrame.add(buttonPanel,gbc);
        //Add the components to the input frame
        
		
        
        inputFrame.pack();
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
	public static String getName()
	{
		return (nameField.getText().isEmpty())? "Study Session" : nameField.getText();
	}

}
package src;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Log_Updater  
{
   // FIXME: Make the logs override 
    protected void logSessionData(Date startTime, Date finishTime, int sessionsCompleted, int totalMinutesWorked, String name) {
        // Get the "Documents" directory path for the current user
        String documentsDirectory = System.getProperty("user.home") + "/Documents";
    
        // Create a "PomodoroLogs" directory within "Documents" if it doesn't exist
        String pomodoroLogsDirectory = documentsDirectory + "/Pomodoro Logs";
        Path logsDirectoryPath = Paths.get(pomodoroLogsDirectory);
        if (!logsDirectoryPath.toFile().exists()) {
            logsDirectoryPath.toFile().mkdirs();
        }
    
        // Create a log entry with timestamps, sessions completed, and total minutes worked
        SimpleDateFormat timestampFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String logEntry = name + " - Start Time: " + timestampFormat.format(startTime) +
                          " Finish Time: " + timestampFormat.format(finishTime) +
                          " - Pomodoros: " + sessionsCompleted +
                          ", Total Minutes Worked: " + totalMinutesWorked;
    
        try {
            // Open the log file in append mode (true)
            String logFilePath = pomodoroLogsDirectory + "/pomodoro_log.txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(logFilePath, true));
    
            // Write the log entry and a newline character to the file
            writer.write(logEntry);
            writer.newLine();
    
            // Close the file
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
    
    


   

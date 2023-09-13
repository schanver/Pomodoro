package src;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Log_Updater 
{
   protected void logSessionData(int sessionsCompleted, int totalMinutesWorked) {
        // Get the "Documents" directory path for the current user
        String documentsDirectory = System.getProperty("user.home") + "\\Documents";

        // Create a "PomodoroLogs" directory within "Documents" if it doesn't exist
        String pomodoroLogsDirectory = documentsDirectory + "\\PomodoroLogs";
        Path logsDirectoryPath = Paths.get(pomodoroLogsDirectory);
        if (!logsDirectoryPath.toFile().exists()) {
            logsDirectoryPath.toFile().mkdirs();
        }

        // Get the current date
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = dateFormat.format(calendar.getTime());

        // Create a log entry with the current date, sessions completed, and total minutes worked
        String logEntry = currentDate + " - Sessions: " + sessionsCompleted + ", Total Minutes Worked: " + totalMinutesWorked;

        try {
            // Open the log file in append mode (true)
            String logFilePath = pomodoroLogsDirectory + "\\pomodoro_log.txt";
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

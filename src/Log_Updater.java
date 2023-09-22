package src;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class Log_Updater  //TODO look into it and fix it
{
   
        protected void logSessionData(int sessionsCompleted, int totalMinutesWorked) {
            // Get the "Documents" directory path for the current user
            String documentsDirectory = System.getProperty("user.home") + "/Documents";
    
            // Create a "Pomodoro Logs" directory within "Documents" if it doesn't exist
            String pomodoroLogsDirectory = documentsDirectory + File.separator + "Pomodoro Logs";
            Path logsDirectoryPath = Paths.get(pomodoroLogsDirectory);
            if (!logsDirectoryPath.toFile().exists()) {
                logsDirectoryPath.toFile().mkdirs();
            }
    
            // Get the current date
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            String currentDate = dateFormat.format(calendar.getTime());
    
            // Create a log entry with the current date, sessions completed, and total minutes worked
            String logEntry = currentDate + " - Sessions: " + sessionsCompleted + ", Total Minutes Worked: " + totalMinutesWorked;
    
            try {
                // Build the full file path including the log file name
                String logFilePath = pomodoroLogsDirectory + File.separator + "pomodoro_log.txt";
    
                // Check if the log file exists
                File logFile = new File(logFilePath);
    
                if (logFile.exists()) {
                    // Read the existing log entries
                    List<String> existingLogEntries = Files.readAllLines(logFile.toPath());
    
                    // Iterate through existing entries to find the current date's entry
                    for (int i = 0; i < existingLogEntries.size(); i++) {
                        String line = existingLogEntries.get(i);
                        if (line.startsWith(currentDate)) {
                            // Split the line into parts using ": "
                            String[] parts = line.split(": ");
    
                            // Ensure that there are at least two parts
                            if (parts.length >= 2) {
                                String[] values = parts[1].split(", ");
    
                                // Ensure that there are at least two values
                                if (values.length >= 2) {
                                    int existingSessions = 0;
                                    int existingTotalMinutes = 0;
    
                                    // Extract the sessions and total minutes values
                                    for (String value : values) {
                                        if (value.startsWith("Sessions: ")) {
                                            existingSessions = Integer.parseInt(value.substring("Sessions: ".length()));
                                        } else if (value.startsWith("Total Minutes Worked: ")) {
                                            existingTotalMinutes = Integer.parseInt(value.substring("Total Minutes Worked: ".length()));
                                        }
                                    }
    
                                    // Combine the values with the new data
                                    sessionsCompleted += existingSessions;
                                    totalMinutesWorked += existingTotalMinutes;
    
                                    // Create the updated log entry
                                    String updatedLogEntry = currentDate + " - Sessions: " + sessionsCompleted + ", Total Minutes Worked: " + totalMinutesWorked;
    
                                    // Update the existing entry in the list
                                    existingLogEntries.set(i, updatedLogEntry);
    
                                    // Write the updated entries back to the file
                                    Files.write(logFile.toPath(), existingLogEntries, StandardCharsets.UTF_8);
    
                                    return; // Exit the method since the log entry has been updated
                                }
                            }
                        }
                    }
                }
    
                // If no log entry exists for the current day or there's no log file, create a new log entry
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
    
    


   

package src;

import java.sql.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.Date;

public class sql {

    private Connection connect;

    public sql() {
        connect = connect();
    }

    public Connection connect() {
        Connection connection = null;
        String password = "0000";
        String user = "root";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3305/S", user, password);
        } catch (ClassNotFoundException e) {
            System.out.println("Error: JDBC driver not found");
        } catch (SQLException e) {
            System.out.println("Error: Connection failed");
        }
        return connection;
    }

    public void insert(Date startTime, Date finishTime, int sessionsCompleted, int totalMinutesWorked, String name) {
        try {
            PreparedStatement sessioninfo = connect.prepareStatement(
                    "INSERT INTO Time (startTime, finishTime, sessionsCompleted, totalMinutesWorked, name) VALUES (?, ?, ?, ?, ?)"
            );
            sessioninfo.setTimestamp(1, new Timestamp(startTime.getTime()));
            sessioninfo.setTimestamp(2, new Timestamp(finishTime.getTime()));
            sessioninfo.setInt(3, sessionsCompleted);
            sessioninfo.setInt(4, totalMinutesWorked);
            sessioninfo.setString(5, name);

            sessioninfo.executeUpdate();
            sessioninfo.close();
        } catch (SQLException e) {
            System.out.println("Error: Failed to insert data");
            e.printStackTrace();
        }
    }


}



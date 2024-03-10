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

    public void create(String name){

        try {
            PreparedStatement sessioninfo = connect.prepareStatement(
                    "CREATE TABLE "+name+"Table (startTime datetime, finishTime datetime, sessionsCompleted int," +
                            " totalMinutesWorked int, name varchar(255)) " );

            sessioninfo.executeUpdate();
            sessioninfo.close();
        } catch (SQLException e) {
            System.out.println("Error: Failed to create Table");
            e.printStackTrace();
        }
    }

    public void insert(Date startTime, Date finishTime, int sessionsCompleted, int totalMinutesWorked, String name) {
        try {
            PreparedStatement insertion = connect.prepareStatement(
                    "INSERT INTO "+name+"Table (startTime, finishTime, sessionsCompleted, totalMinutesWorked, name) VALUES (?, ?, ?, ?, ?)"
            );
            insertion.setTimestamp(1, new Timestamp(startTime.getTime()));
            insertion.setTimestamp(2, new Timestamp(finishTime.getTime()));
            insertion.setInt(3, sessionsCompleted);
            insertion.setInt(4, totalMinutesWorked);
            insertion.setString(5, name);

            insertion.executeUpdate();
            insertion.close();
        } catch (SQLException e) {
            System.out.println("Error: Failed to insert data");
            e.printStackTrace();
        }
    }


}



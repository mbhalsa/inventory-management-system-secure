package database;

import java.sql.Connection;

public class TestConnection {
    public static void main(String[] args) {
        Connection connection = DBConnection.getConnection();

        if (connection != null) {
            System.out.println("Connected to database successfully.");
        } else {
            System.out.println("Connection failed.");
        }
    }
}
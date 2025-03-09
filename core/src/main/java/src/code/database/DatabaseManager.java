package src.code.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://127.0.0.1:3306/pacman?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    private Connection connection;

    public DatabaseManager() {
        try {
            // Load JDBC Driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Driver loaded successfully!");

            // Connect to Database
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to the database!");
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Connection failed: " + e.getMessage());
        }
    }

    public List<String[]> getHighscores() {
        List<String[]> highscores = new ArrayList<>();
        String query = "SELECT name, highscore FROM user ORDER BY highscore DESC LIMIT 10";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String name = rs.getString("name");
                String score = String.valueOf(rs.getInt("highscore"));
                highscores.add(new String[]{name, score});
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving highscores: " + e.getMessage());
        }
        return highscores;
    }

    public boolean insertHighscore(String name, int score) {
        String query = "INSERT INTO user (name, highscore) VALUES (?, ?) " +
            "ON DUPLICATE KEY UPDATE highscore = GREATEST(highscore, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, score);
            stmt.setInt(3, score); // Untuk membandingkan dengan skor yang sudah ada di database

            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Highscore inserted/updated successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error inserting/updating highscore: " + e.getMessage());
        }
        return false;
    }

    public int getHighestScore() {
        String query = "SELECT MAX(highscore) AS highest_score FROM user";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("highest_score");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving highest score: " + e.getMessage());
        }
        return -1; // Return -1 jika terjadi kesalahan
    }

    public void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.err.println("Error closing connection: " + e.getMessage());
        }
    }
}

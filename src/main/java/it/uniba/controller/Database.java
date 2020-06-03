package it.uniba.controller;
import java.sql.*;


public class Database {
    private Connection conn = null;

    public Database(String dbpath){
        connect(dbpath);
    }

    public static void main(String[] args) {
        Database db = new Database("users.db");
        db.getLogin("nicole500", "nicole123!");
    }


    public void getLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? and password=?";

        try (PreparedStatement pstmt  = this.conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                System.out.println(
                    rs.getString("id") +  "\t" +
                    rs.getString("username") + "\t" +
                    rs.getString("password") + "\t" +
                    rs.getString("email")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    private void connect(String dbpath) {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + dbpath;
            // create a connection to the database
            this.conn = DriverManager.getConnection(url);
            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

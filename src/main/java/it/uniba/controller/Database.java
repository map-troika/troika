package it.uniba.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Database {
    private Connection conn = null;

    public Database(String dbpath){
        connect(dbpath);
    }

    public static void main(String[] args) {
        Database db = new Database("users.db");
        db.getLogin("nicole500", "nicole123!");
    }

    public boolean getLogin(String username, String password) {
        String sql = "SELECT * FROM users WHERE username=? and password=?";
        boolean rc = false;

        try (PreparedStatement pstmt  = this.conn.prepareStatement(sql)){

            // set the value
            pstmt.setString(1,username);
            pstmt.setString(2,password);
            ResultSet rs  = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                rc = true;
                System.out.println(
                    "*** Login user: " +
                    rs.getString("username")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rc;
    }

    private void connect(String dbpath) {
        try {
            // db parameters
            String url = "jdbc:sqlite:" + dbpath;
            // create a connection to the database
            this.conn = DriverManager.getConnection(url);
            System.out.println("*** Connection to " + dbpath + " SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

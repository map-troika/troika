package it.uniba.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 * <p><code>Entity</code></p> La classe <code>Database</code> consente di accedere ad un database SQLite tramite il protocollo JDBC.
 *
 * @author Nicole Stolbovoi
 */

public final class Database {
    private Connection conn = null; // interazione tra il programma ed il DBMS

    public Database(final String dbpath) {
        connect(dbpath);
    }

/*
        public static void main(final String[] args) {
        Database db = new Database("users.db");
        db.getLogin("nicole500", "nicole123!");
    }

 */

    /**
     * Controlla se il login è valido
     *
     * @param username  stringa contenente username
     * @param password  stringa contenente password
     * @return          true se il login è valido, false altrimenti
     */

    public boolean getLogin(final String username, final String password) {
        String sql = "SELECT * FROM users WHERE username=? and password=?";
        boolean rc = false;

        try (PreparedStatement pstmt = this.conn.prepareStatement(sql)) {

            // Imposta il valore
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            while (rs.next()) {
                rc = true;
                System.out.println(
                        "*** Login user: "
                                + rs.getString("username")
                );
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return rc;
    }

    /**
     * Apre la connessione al database.
     *
     * @param dbpath path del file database
     */

    private void connect(final String dbpath) {
        try {
            // Parametri del database
            String url = "jdbc:sqlite:" + dbpath; // stringa di connessione
            // Crea una connessione con il database
            this.conn = DriverManager.getConnection(url);
            System.out.println("*** Connection to " + dbpath + " SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

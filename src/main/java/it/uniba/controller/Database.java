package it.uniba.controller;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.DriverManager;

/**
 * La classe <code>Database</code> consente di analizzare un flusso di token in ingresso e
 * determinarne la correttezza interpretando un pattern grazie ad una data espressione regolare. Questa classe
 * implementa metodi che restituiscono un valore booleano per identificare l'input, che traducono il contenuto di una
 * partita di scacchi in formato PGN dall'inglese all'italiano (permettendo un rapido test delle mosse) e per pulire il
 * contenuto del file da commenti e stringhe non necessarie al fine di giocare la partita.
 *
 * @author Nicole Stolbovoi
 */

public final class Database {
    private Connection conn = null;

    public Database(final String dbpath) {
        connect(dbpath);
    }

    public static void main(final String[] args) {
        Database db = new Database("users.db");
        db.getLogin("nicole500", "nicole123!");
    }

    public boolean getLogin(final String username, final String password) {
        String sql = "SELECT * FROM users WHERE username=? and password=?";
        boolean rc = false;

        try (PreparedStatement pstmt  = this.conn.prepareStatement(sql)) {

            // Imposta il valore
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            ResultSet rs  = pstmt.executeQuery();

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

    private void connect(final String dbpath) {
        try {
            // Parametri del database
            String url = "jdbc:sqlite:" + dbpath;
            // Crea una connessione con il database
            this.conn = DriverManager.getConnection(url);
            System.out.println("*** Connection to " + dbpath + " SQLite has been established.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}

package ru.daniilazarnov;

import java.sql.*;

public class SqlCheck {
    private Connection conn;

    public Connection SqlChecker() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        String FILE_DB = "serverData.db";
        conn = DriverManager.getConnection("jdbc:sqlite:" + FILE_DB);
        Statement stmt = conn.createStatement();
        stmt.execute("CREATE TABLE IF NOT EXISTS users (user_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, login varchar(255) NOT NULL, password varchar(255))");
        return conn;
    }

    public void closeConn() throws SQLException {
        conn.close();
    }
}

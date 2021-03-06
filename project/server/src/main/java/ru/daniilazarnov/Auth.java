package ru.daniilazarnov;

import java.sql.*;
import java.util.Locale;

public class Auth {
    private String login;
    private String password;
    private final Connection conn;
    private boolean isAuth = false;
    private int user_id = 0;

    public Auth() throws SQLException, ClassNotFoundException {
        SqlCheck connector = new SqlCheck();
        conn = connector.SqlChecker();
    }

    public void setLogin(String login) {
        this.login = login.toLowerCase(Locale.ROOT);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAuth() {
        return isAuth;
    }

    public void doLogin() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM users WHERE login = ?");
        ps.setString(1, this.login);
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            String password = rs.getString("password");
            if (password.equals(this.password)) {
                this.user_id = rs.getInt("user_id");
                this.isAuth = true;
            }
        } else {
            doRegistration();
        }
    }

    public void doRegistration() throws SQLException {
        PreparedStatement ps = conn.prepareStatement("INSERT INTO users (`login`, `password`) VALUES (?, ?)");
        ps.setString(1, this.login);
        ps.setString(2, this.password);
        int rs = ps.executeUpdate();
        if (rs > 0) {
            this.user_id = rs;
            this.isAuth = true;
        }
    }

    public boolean changePassword() throws SQLException {
        if (!isAuth || this.user_id < 1) {
            return false;
        }
        PreparedStatement ps = conn.prepareStatement("UPDATE users SET `password` = ? WHERE `user_id` = ?");
        ps.setInt(2, this.user_id);
        ps.setString(1, this.password);
        int rs = ps.executeUpdate();
        return rs > 0;
    }
}

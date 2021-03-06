package ru.daniilazarnov.Auth;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;


public class Auth {

    private Socket socket;
    private final String login;
    private final String password;
    private DataOutputStream out;
    private DataInputStream in;

    private boolean isLogin = false;

    public Auth(Socket socket, String login, String password) {
        this.login = login;
        this.password = password;
        this.socket = socket;
        isLogin = true;
    }

    public boolean isLogin() {
        return isLogin;
    }

    public void tryAuthToServer() {

    }

    public void tryRegistrationClient() {

    }

}

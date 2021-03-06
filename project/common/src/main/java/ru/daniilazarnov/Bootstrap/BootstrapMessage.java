package ru.daniilazarnov.Bootstrap;

public class BootstrapMessage {
    private String hash = "";
    private String command = "";
    private String data = "";

    public BootstrapMessage(String command, String data) {

        this.hash = hash;
        this.command = command;
        this.data = data;
    }

    public String getHash() {
        return hash;
    }

    public String getCommand() {
        return command;
    }

    public String getData() {
        return data;
    }

}

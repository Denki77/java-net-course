package ru.daniilazarnov.Bootstrap;

public enum InternalCommand {
    AUTH(1024),
    SERVER_PORT(8888);

    private int value;

    InternalCommand(int value) {
        this.value = value;
    }

    public int toInt() {
        return value;
    }
}

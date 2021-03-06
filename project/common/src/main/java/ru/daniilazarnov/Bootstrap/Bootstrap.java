package ru.daniilazarnov.Bootstrap;

import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Bootstrap {

    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private final Gson g = new Gson();

    public Bootstrap(Socket socket) {
        this.socket = socket;
    }

    public void sendMessageToChanel(BootstrapMessage message) throws IOException {
        out = new DataOutputStream(socket.getOutputStream());
        out.writeUTF(g.toJson(message));
    }

    public BootstrapMessage getMessageFromChanel() throws IOException {
        in = new DataInputStream(socket.getInputStream());
        return g.fromJson(in.readUTF(), BootstrapMessage.class);
    }

    public String[] getArrayFromJSON(String message) throws IOException {
        return g.fromJson(message, String[].class);
    }

    public String getJSONFromArray(String[] message) throws IOException {
        return g.toJson(message);
    }

}

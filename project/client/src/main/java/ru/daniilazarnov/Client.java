package ru.daniilazarnov;

import ru.daniilazarnov.Auth.Auth;
import ru.daniilazarnov.Bootstrap.Bootstrap;
import ru.daniilazarnov.Bootstrap.BootstrapMessage;
import ru.daniilazarnov.Bootstrap.InternalCommand;
import ru.daniilazarnov.Logger.Logger;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class Client {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private static final Logger log = new Logger("./Log-C/");
    private static String login = "";
    private static String password = "";


    public static void main(String[] args) throws IOException {

        log.setLogMessage("Start client app");

        if (args == null || args.length == 0) {
            login = "Deni";
            password = "1234";
        } else {
            login = args[0];
            password = args[1];
        }

        new Client();

    }

    public Client() throws IOException {
        try {
            socket = new Socket("localhost", InternalCommand.SERVER_PORT.toInt());
        } catch (IOException e){
            System.out.println("Cant connect");
            return;
        }
        log.setLogMessage("Connect to srv");

        //Auth auth = new Auth(socket, login, password);

        /*if (!auth.isLogin()) {
            log.setLogMessage("Client error auth to app");
            System.out.println("Error auth to app pls try again!");
            return;
        }*/

        Bootstrap bootstrap = new Bootstrap(socket);
        String[] authData = new String[2];
        authData[0] = "Deni";
        authData[1] = "1234";
        BootstrapMessage message = new BootstrapMessage(InternalCommand.AUTH.name(), bootstrap.getJSONFromArray(authData));

        bootstrap.sendMessageToChanel(message);



        //out = new DataOutputStream(socket.getOutputStream());
        //out.writeUTF("Client say: " + scanner.nextLine());


    }
}

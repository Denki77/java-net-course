package ru.daniilazarnov;

import ru.daniilazarnov.Bootstrap.Bootstrap;
import ru.daniilazarnov.Bootstrap.BootstrapMessage;
import ru.daniilazarnov.Bootstrap.InternalCommand;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import java.net.SocketException;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {
    private ServerSocket serverSocket;
    private Socket clientSocket;
    private DataInputStream in;
    private DataOutputStream out;

    private final String ANSI_RESET = "\u001B[0m";
    private final String ANSI_RED = "\u001B[31m";

    public Server() {

        try {
            System.out.println("Server is starting up...");
            serverSocket = new ServerSocket(InternalCommand.SERVER_PORT.toInt());

            System.out.println("Server is waiting for a connection...");

            AtomicBoolean isDrop = new AtomicBoolean(false);
            AtomicBoolean isServerRun = new AtomicBoolean(false);

            new Thread(() -> {
                try {
                    clientSocket = serverSocket.accept();
                    Bootstrap bootstrap = new Bootstrap(clientSocket);

                    // блокирующий accept запустил сервер - пришёл клиент
                    isServerRun.set(true);
                    Auth user = new Auth();
                    BootstrapMessage incomingMessage;

                    while (isServerRun.get()) {
                        try {
                            incomingMessage = bootstrap.getMessageFromChanel();
                        } catch (SocketException e) {
                            System.out.println("Client disconnect");
                            break;
                        }

                        // не авторизованный - сначала авторизуйся
                        if (!user.isAuth()) {

                            if (incomingMessage.getCommand().equals(InternalCommand.AUTH.name())) {
                                String authDataJSON = incomingMessage.getData();
                                String[] authData = bootstrap.getArrayFromJSON(authDataJSON);
                                user.setLogin(authData[0]);
                                user.setPassword(authData[1]);
                                user.doLogin();
                                System.out.println("Auth ok");
                            }
                            continue;
                        }

                        //BootstrapMessage stringMessage = bootstrap.getMessageFromChanel();

                        if (incomingMessage.getCommand().equals("exit") || !isServerRun.get()) {

                            //out.writeUTF("cmd Exit");
                            System.out.println("Socket shut down");
                            System.out.println("Please press enter to close command line...");
                            closeConnection();
                            isDrop.set(true);
                            isServerRun.set(false);
                            break;

                        }

                        System.out.println(ANSI_RED + incomingMessage.getData() + ANSI_RESET);

                    }

                    System.out.println("Correctly server stop...");

                } catch (IOException | ClassNotFoundException | SQLException e) {
                    e.printStackTrace();
                }
            }).start();

            /*new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                try {
                    while (true) {

                        if (isDrop.get()) {
                            System.out.println("Server stop in client side...");
                            break;
                        }

                        System.out.println("Please type in a message (or -exit for quit):");
                        String message = scanner.nextLine();

                        if (message.equals("-exit")) {
                            if (isServerRun.get()) {
                                out.writeUTF("ShutDown server");
                                out.writeUTF("cmd Exit");
                                System.out.println("Socket shut down");
                                System.out.println("Wait disconnect from client...");

                            } else {

                                // остановка сервера если нет подключения клиента
                                // сброс ожидания подключения
                                serverSocket.close();

                            }

                            isServerRun.set(false);
                            isDrop.set(true);
                            break;

                        }

                        if (isServerRun.get()) {
                            out.writeUTF("Server say: " + message);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();*/


        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void closeConnection() {
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        System.out.println("Start server!");
        new Server();
    }
}

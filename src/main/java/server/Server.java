package server;

import server.actors.Player;
import server.actors.Room;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Server {

    private ServerSocket serverSocket;

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                new ClientHandler(serverSocket.accept()).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try {
                PlayerConnector playerConnector = new PlayerConnector(clientSocket,
                        new PrintWriter(clientSocket.getOutputStream(), true),
                        new BufferedReader(new InputStreamReader(clientSocket.getInputStream())));

                Room.getInstance().addPlayer(new Player(
                        Integer.toString(Room.getInstance().getPlayers().size() +
                                ThreadLocalRandom.current().nextInt(1, 9999999 + 1)),
                        playerConnector
                ), Integer.parseInt(playerConnector.getInfoFromPlayer()));
                //), 1);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args){
        Room.getInstance().addTable(1);
        Server server = new Server();
        server.start(7777);
    }
}

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
    private int roomSize;

    public Server(int roomSize){
        this.roomSize = roomSize;
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while (true) {
            try {
                new ClientHandler(serverSocket.accept(), roomSize).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ClientHandler extends Thread {
        private Socket clientSocket;
        private int roomSize;

        public ClientHandler(Socket socket, int roomSize) {
            this.clientSocket = socket;
            this.roomSize = roomSize;
        }

        public void run() {
            PlayerConnector playerConnector = new PlayerConnector(clientSocket);

            if(!checkConnection(playerConnector)){
                playerConnector.closeConnection();
                return;
            }


            Room.getInstance().addPlayer(new Player(
                    Integer.toString(Room.getInstance().getPlayers().size() +
                            ThreadLocalRandom.current().nextInt(1, 9999999 + 1)),
                    playerConnector
            ), roomSize);
            //), 1);

        }

        public boolean checkConnection(PlayerConnector playerConnector){
            int randNumber = new Random().nextInt(50);

            playerConnector.sendInfoToPlayer(Integer.toString(randNumber));
            if(playerConnector.getInfoFromPlayer().equals(Integer.toString(randNumber + 1))){
                return true;
            }
            return false;
        }
    }

    public static void main(String[] args){
        //Room.getInstance().addTable(1);
        Server server = new Server(2);
        server.start(7777);
    }
}

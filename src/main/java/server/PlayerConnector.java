package server;

import server.game.actors.PlayerGameStateToSend;

import java.io.*;
import java.net.Socket;

public class PlayerConnector {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public PlayerConnector(Socket clientSocket, PrintWriter out, BufferedReader in) {
        this.clientSocket = clientSocket;
        this.out = out;
        this.in = in;
    }

    public String getInfoFromPlayer(){
        try {
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void sendInfoToPlayer(String message){
        out.println(message);
    }

    public void sendObjectToPlayer(PlayerGameStateToSend playerGameStateToSend){
        try {
            OutputStream outputStream = clientSocket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(playerGameStateToSend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

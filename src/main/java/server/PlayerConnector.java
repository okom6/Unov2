package server;

import error.ErrorCode;
import server.game.actors.PlayerGameStateToSend;

import java.io.*;
import java.net.Socket;

public class PlayerConnector {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectOutputStream objectOutputStream;


    public PlayerConnector(Socket clientSocket) {
        try {
            this.clientSocket = clientSocket;
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            this.in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            this.objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            /*OutputStream outputStream = clientSocket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);*/
            objectOutputStream.writeObject(playerGameStateToSend);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendErrorCodeToPlayer(ErrorCode errorCode){
        try {
            /*OutputStream outputStream = clientSocket.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);*/
            objectOutputStream.writeObject(errorCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection(){
        try {
            objectOutputStream.close();
            in.close();
            out.close();
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

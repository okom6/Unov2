package client.console;

import error.ErrorCode;
import server.game.actors.PlayerGameStateToSend;

import java.io.*;
import java.net.Socket;

public class ConnectionToServer {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public ConnectionToServer(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    public String reciveMessage() {
        String resp = null;
        try {
            resp = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return resp;
    }

    public PlayerGameStateToSend reciveObject() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            PlayerGameStateToSend playerGameStateToSend = (PlayerGameStateToSend) objectInputStream.readObject();
            //
            ClientGameOperations clientGameOperations = new ClientGameOperations();
            System.out.println("Początek odbioru");
            //clientGameOperations.showGameInfo(playerGameStateToSend);
            System.out.println("Koniec odbioru");
            //
            return playerGameStateToSend;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object reciveRawObject() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ErrorCode reciveErrorCode() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return (ErrorCode) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

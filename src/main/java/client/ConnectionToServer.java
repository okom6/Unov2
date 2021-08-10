package client;

import error.ErrorCode;
import server.game.actors.PlayerGameStateToSend;

import java.io.*;
import java.net.Socket;

public class ConnectionToServer {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private ObjectInputStream objectInputStream;

    public ConnectionToServer(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
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
            /*InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);*/
            return (PlayerGameStateToSend) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public ErrorCode reciveErrorCode() {
        try {
            /*InputStream inputStream = clientSocket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);*/
            return (ErrorCode) objectInputStream.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return null;
    }

    public void stopConnection() {
        try {
            objectInputStream.close();
            in.close();
            out.close();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package client;

import server.game.actors.PlayerGameStateToSend;

import java.io.*;
import java.net.Socket;

public class Client {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
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
            return (PlayerGameStateToSend) objectInputStream.readObject();
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

    public static void main(String[] args){
        Client client = new Client();
        client.startConnection("localhost", 7777);

        client.sendMessage(Integer.toString(Integer.parseInt(client.reciveMessage()) + 1));
        client.sendMessage("1");

        PlayerGameStateToSend playerGameStateToSend = client.reciveObject();
        System.out.println(playerGameStateToSend.getPlayersCardNumbers());
        System.out.println(playerGameStateToSend.getPlayerNumber());
        System.out.println(playerGameStateToSend.getCardOnTop().getCharacter() + " - " + playerGameStateToSend.getCardOnTop().getColour());
        System.out.println(client.reciveMessage());

        client.stopConnection();
    }
}

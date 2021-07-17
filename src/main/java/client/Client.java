package client;

public class Client {

    public static void main(String[] args){
        ClientGame clientGame = new ClientGame();
        clientGame.startGame(new ConnectionToServer("localhost", 7777));
    }
}
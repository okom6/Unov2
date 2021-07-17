package client;

public class Client2 {

    public static void main(String[] args){
        ClientGame clientGame = new ClientGame();
        clientGame.startGame(new ConnectionToServer("localhost", 7777));
    }
}

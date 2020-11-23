package client;

import server.game.actors.PlayerGameStateToSend;

import java.io.*;
import java.net.Socket;

public class Client {

    public static void main(String[] args){
        //Client client = new Client();
        //client.startConnection("localhost", 7777);
        //ConnectionToServer connectionToServer = new ConnectionToServer("localhost", 7777);
        ClientGame clientGame = new ClientGame();
        clientGame.startGame(new ConnectionToServer("localhost", 7777));


        //przyjęcie pierwszej informacji o stanie gry

        //przyjmowanie w pętli informacji o stanie gry i którego gracza jest obecna tura

        //jeśli tura jest obecnego gracza to wyslij odpowiedź
        //przyjmij oddpowiedź któa potwierdza że ruch był prawidłowy
        //jełśi ruch był nieprawidłowy to wyslij ponownie odpowiedź

        //poprawic serwer aby przesyłał jdnocześnie czy jest już koniec gry
        //zakończyć grę po odebraniu końca gry


        /*connectionToServer.sendMessage(Integer.toString(Integer.parseInt(connectionToServer.reciveMessage()) + 1));
        connectionToServer.sendMessage("1");

        PlayerGameStateToSend playerGameStateToSend = connectionToServer.reciveObject();
        System.out.println(playerGameStateToSend.getPlayersCardNumbers());
        System.out.println(playerGameStateToSend.getPlayerNumber());
        System.out.println(playerGameStateToSend.getCardOnTop().getCharacter() + " - " + playerGameStateToSend.getCardOnTop().getColour());
        System.out.println(connectionToServer.reciveMessage());*/

        //connectionToServer.stopConnection();
    }
}

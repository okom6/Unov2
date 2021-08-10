package client.console;

import client.ConnectionToServer;
import server.game.actors.Card;
import server.game.actors.PlayerGameStateToSend;

import java.util.Scanner;

public class ClientGameOperations {
    public PlayerGameStateToSend reciveGameStatus(ConnectionToServer connectionToServer){
        return connectionToServer.reciveObject();
    }

    public String recivePlaceInfo(ConnectionToServer connectionToServer){
        return connectionToServer.reciveMessage();
    }

    public void showGameInfo(PlayerGameStateToSend gameInfo){
        System.out.println();
        System.out.println("Is take battle: " + Boolean.toString(gameInfo.isTakeBattle()));
        System.out.println("Is stop battle: " + Boolean.toString(gameInfo.isStopBattle()));
        System.out.println("Card on top: " + gameInfo.getCardOnTop().getColour() + " " + gameInfo.getCardOnTop().getCharacter());
        if(gameInfo.getPlayerNumber() == gameInfo.isPlayerTurn()){
            System.out.println("It is your turn");
        } else{
            System.out.println("It is " + Integer.toString(gameInfo.isPlayerTurn() + 1) + " player turn");
        }
        if(gameInfo.getCardOnTop().getColour() == 's'){ System.out.println("Declarated colour: " + gameInfo.getDeclaratedColour());}
        System.out.println();
        for (int i = 0; i < gameInfo.getPlayersCardNumbers().size(); ++i) {
            if(gameInfo.getPlayerNumber() == i){
                System.out.println("Your cards: " + gameInfo.getPlayersCardNumbers().get(i));
            } else{
                System.out.println("Player " + Integer.toString(i + 1) + " cards: " + gameInfo.getPlayersCardNumbers().get(i));
            }
        }
        System.out.println();

        for (Card c: gameInfo.getHandDeck()) {
            System.out.println(c.getColour() + " " + c.getCharacter());
        }
    }

    public void showSummaryInfo(String placeInfo) {
        if (placeInfo.equals("-1")) {
            System.out.println("Jesteś ostatni. Przegrałeś!");
        } else{
            System.out.println("Miejsce " + placeInfo);
        }
    }

    public boolean turnVerify(PlayerGameStateToSend gameInfo){
        return gameInfo.getPlayerNumber() == gameInfo.isPlayerTurn();
    }

    public String playerInteraction(){
        Scanner scanner = new Scanner(System.in);
        String command = scanner.nextLine();
        return command;
    }
}

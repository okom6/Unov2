package client;

import error.ErrorCode;
import server.game.GameInfo;
import server.game.actors.PlayerGameStateToSend;

public class ClientGame {

    public void startGame(ConnectionToServer connectionToServer){
        ClientGameOperations clientGameOperations = new ClientGameOperations();

        /*walidacja połączenia (jest po stronie serwera)*/
        connectionToServer.sendMessage(Integer.toString(Integer.parseInt(connectionToServer.reciveMessage()) + 1));
        connectionToServer.sendMessage("2"); //wybór pokoju

        PlayerGameStateToSend gameInfo = clientGameOperations.reciveGameStatus(connectionToServer);
        //clientGameOperations.showGameInfo(gameInfo);

        while(!gameInfo.isEndGame()){
            //gameInfo = clientGameOperations.reciveGameStatus(connectionToServer);

            System.out.println();
            System.out.println("Is end game: " + Boolean.toString(gameInfo.isEndGame()));

            clientGameOperations.showGameInfo(gameInfo);

            if(clientGameOperations.turnVerify(gameInfo)){
                connectionToServer.sendMessage(
                        clientGameOperations.playerInteraction());

                ErrorCode info = connectionToServer.reciveErrorCode();
                while(info.getCode() != 0){
                    System.out.println(info.getInfo());
                    connectionToServer.sendMessage(
                            clientGameOperations.playerInteraction());
                    info = connectionToServer.reciveErrorCode();
                }
                System.out.println(info.getInfo());
            }

            gameInfo = clientGameOperations.reciveGameStatus(connectionToServer);
        }
        System.out.println("Po grze");

        clientGameOperations.showSummaryInfo(clientGameOperations.recivePlaceInfo(connectionToServer));

        connectionToServer.stopConnection();
    }
}

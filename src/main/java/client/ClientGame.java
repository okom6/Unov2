package client;

import server.game.GameInfo;
import server.game.actors.PlayerGameStateToSend;

public class ClientGame {

    public void startGame(ConnectionToServer connectionToServer){
        ClientGameOperations clientGameOperations = new ClientGameOperations();

        PlayerGameStateToSend gameInfo = clientGameOperations.reciveGameStatus(connectionToServer);
        clientGameOperations.showGameInfo(gameInfo);

        while(!gameInfo.isEndGame()){
            gameInfo = clientGameOperations.reciveGameStatus(connectionToServer);
            clientGameOperations.showGameInfo(gameInfo);

            if(clientGameOperations.turnVerify(gameInfo)){
                connectionToServer.sendMessage(
                        Integer.toString(clientGameOperations.playerInteraction()));

                while(!connectionToServer.reciveMessage().equals("good")){
                    connectionToServer.sendMessage(
                            Integer.toString(clientGameOperations.playerInteraction()));
                }
            }
        }

        clientGameOperations.showSummaryInfo(clientGameOperations.recivePlaceInfo(connectionToServer));

        connectionToServer.stopConnection();
    }
}

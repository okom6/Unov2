package client;

import server.game.actors.PlayerGameStateToSend;

public class ClientGame {

    public void startGame(ConnectionToServer connectionToServer){
        ClientGameOperations clientGameOperations = new ClientGameOperations();

        connectionToServer.sendMessage(Integer.toString(Integer.parseInt(connectionToServer.reciveMessage()) + 1));
        connectionToServer.sendMessage("2");

        PlayerGameStateToSend gameInfo = clientGameOperations.reciveGameStatus(connectionToServer);

        while(!gameInfo.isEndGame()){

            System.out.println();
            System.out.println("Is end game: " + Boolean.toString(gameInfo.isEndGame()));

            clientGameOperations.showGameInfo(gameInfo);

            if(clientGameOperations.turnVerify(gameInfo)){
                connectionToServer.sendMessage(
                        clientGameOperations.playerInteraction());

                while(!connectionToServer.reciveMessage().equals("good")){
                    connectionToServer.sendMessage(
                            clientGameOperations.playerInteraction());
                }
            }

            gameInfo = clientGameOperations.reciveGameStatus(connectionToServer);
        }
        System.out.println("Po grze");

        clientGameOperations.showSummaryInfo(clientGameOperations.recivePlaceInfo(connectionToServer));

        connectionToServer.stopConnection();
    }
}

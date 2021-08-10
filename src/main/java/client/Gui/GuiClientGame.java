package client.Gui;

import client.ConnectionToServer;
import error.ErrorCode;
import server.game.actors.PlayerGameStateToSend;

public class GuiClientGame {
    private ConnectionToServer connectionToServer;
    private ClientGuiMediator clientGuiMediator;
    private boolean endGame = false;
    private boolean downloadDataFromServer = true;

    public GuiClientGame(ConnectionToServer connectionToServer) {
        this.connectionToServer = connectionToServer;
    }

    public void setClientGuiMediator(ClientGuiMediator clientGuiMediator) {
        this.clientGuiMediator = clientGuiMediator;
    }

    public void reciveMessagesAbautGameState(){
        /*walidacja połączenia (jest po stronie serwera)*/
        connectionToServer.sendMessage(Integer.toString(Integer.parseInt(connectionToServer.reciveMessage()) + 1));
        connectionToServer.sendMessage("2"); //wybór pokoju

        while(!endGame){
            //System.out.println(downloadDataFromServer);
            if(downloadDataFromServer) {
                PlayerGameStateToSend playerGameStateToSend = reciveGameStatus();

                endGame = playerGameStateToSend.isEndGame();

                clientGuiMediator.updateGui(playerGameStateToSend);

                if(clientGuiMediator.isThisPlayerTurn()) {downloadDataFromServer = false;}
            }

            /*
            dopisać zarządzanie ostatnią wiadomością
             */
        }
    }

    public ErrorCode sendMoveToServer(String command){
        sendMessageToServer(command);
        ErrorCode errorCode = reciveErrorCode();
        if(errorCode.getCode() == 0) {downloadDataFromServer = true;}
        return errorCode;
    }

    public void sendMessageToServer(String command){
        connectionToServer.sendMessage(command);
    }

    public void setEndGame(boolean endGame) {
        this.endGame = endGame;
    }

    public PlayerGameStateToSend reciveGameStatus(){
        return connectionToServer.reciveObject();
    }

    public String recivePlaceInfo(){
        return connectionToServer.reciveMessage();
    }

    public ErrorCode reciveErrorCode(){
        return connectionToServer.reciveErrorCode();
    }
}

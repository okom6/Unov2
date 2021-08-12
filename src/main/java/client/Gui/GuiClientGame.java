package client.Gui;

import client.console.ConnectionToServer;
import error.ErrorCode;
import server.actors.Player;
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

        while(true){

            /*try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/

            System.out.println("Pobrano wiadomość");
            Object recivedObject = reciveRawObject();

            if(verifyTypePlayerGameStateToSend(recivedObject)){
                PlayerGameStateToSend playerGameStateToSend = (PlayerGameStateToSend) recivedObject;

                endGame = playerGameStateToSend.isEndGame();

                if(endGame){break;}

                clientGuiMediator.updateGui(playerGameStateToSend);
            }
            if(verifyTypeErrorCode(recivedObject)){
                ErrorCode errorCode = (ErrorCode) recivedObject;

                clientGuiMediator.updateGuiWithErrorCode(errorCode);
            }

            /*
            dopisać zarządzanie ostatnią wiadomością
             */
        }
        System.out.println("Koniec gry");

        clientGuiMediator.showEndgameStatus(recivePlaceInfo());

        connectionToServer.stopConnection();
    }



    public void sendMoveToServer(String command){
        sendMessageToServer(command);
    }


    public void sendMessageToServer(String command){
        connectionToServer.sendMessage(command);
    }

    public String recivePlaceInfo(){
        return connectionToServer.reciveMessage();
    }

    public boolean verifyTypePlayerGameStateToSend(Object object){
        try{
            PlayerGameStateToSend playerGameStateToSend = (PlayerGameStateToSend) object;
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public boolean verifyTypeErrorCode(Object object){
        try{
            ErrorCode errorCode = (ErrorCode) object;
            return true;
        } catch (Exception e){
            return false;
        }
    }

    public Object reciveRawObject(){
        return connectionToServer.reciveRawObject();
    }
}

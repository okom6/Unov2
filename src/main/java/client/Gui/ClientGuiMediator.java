package client.Gui;

import client.Gui.comandBuilder.CommandBuilder;
import client.Gui.comandBuilder.CommandBuilderDirector;
import client.console.ClientGameOperations;
import error.ErrorCode;
import server.game.actors.PlayerGameStateToSend;

import javax.swing.*;

public class ClientGuiMediator {
    private Gui gui;
    private GuiClientGame guiClientGame;
    boolean thisPlayerTurn;

    public ClientGuiMediator(Gui gui, GuiClientGame guiClientGame) {
        this.gui = gui;
        this.guiClientGame = guiClientGame;
        gui.setClientGuiMediator(this);
        guiClientGame.setClientGuiMediator(this);
    }

    public void updateGui(PlayerGameStateToSend playerGameStateToSend){
        thisPlayerTurn = turnVerify(playerGameStateToSend);
        System.out.println("Update");

        //
        /*ClientGameOperations clientGameOperations = new ClientGameOperations();
        clientGameOperations.showGameInfo(playerGameStateToSend);*/
        //

        gui.updateButtons(playerGameStateToSend.getHandDeck());
        gui.updatePlayersInfo(playerGameStateToSend.getPlayersCardNumbers(),
                playerGameStateToSend.getPlayerNumber());
        gui.updadeGameBoardInfo(playerGameStateToSend.getCardOnTop(), playerGameStateToSend.isStopBattle(),
                playerGameStateToSend.isTakeBattle(), thisPlayerTurn,
                playerGameStateToSend.getDeclaratedColour(), playerGameStateToSend.isPlayerTurn());
        gui.repaintButtonsAndPlayerInfo();
    }

    public void sendMoveToServer(CommandBuilderDirector commandBuilderDirector, ButtonGroup cardButtonsGroup,
                                 ButtonGroup colourRequestButtonsGroup){
        if(!thisPlayerTurn){
            gui.updadeErrorCodeInfo("Nie twoja tura");
            return;
        }
        String command = commandBuilderDirector.createCommand(cardButtonsGroup, colourRequestButtonsGroup);
        System.out.println("Komenda: " + command);
        guiClientGame.sendMoveToServer(command);

        //updateGuiWithErrorCode(guiClientGame.sendMoveToServer(command));
    }

    public void updateGuiWithErrorCode(ErrorCode errorCode){
        gui.updadeErrorCodeInfo(errorCode);
    }

    public boolean turnVerify(PlayerGameStateToSend gameInfo){
        return gameInfo.getPlayerNumber() == gameInfo.isPlayerTurn();
    }

    public boolean isThisPlayerTurn() {
        return thisPlayerTurn;
    }

    public void showEndgameStatus(String placeInfo){
        gui.setEndgameInfo(placeInfo);
        /*if (placeInfo.equals("-1")) {
            System.out.println("Jesteś ostatni. Przegrałeś!");
        } else{
            System.out.println("Miejsce " + placeInfo);
        }*/
    }
}

package server.game.actors;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerGameStateToSend implements Serializable {
    private Card cardOnTop;
    private ArrayList<Integer> playersCardNumbers = new ArrayList<>();
    private ArrayList<Card> handDeck = new ArrayList();
    private int playerNumber = 0;
    private int playerTurn;
    private char declaratedColour;
    private boolean stopBattle;
    private boolean endGame;

    public PlayerGameStateToSend(Card cardOnTop, char declaratedColour, boolean stopBattle, boolean endGame) {
        this.cardOnTop = cardOnTop;
        this.declaratedColour = declaratedColour;
        this.stopBattle = stopBattle;
        this.endGame = endGame;
    }

    public void setHandDeck(ArrayList<Card> handDeck) {
        this.handDeck = handDeck;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public void setPlayerTurn(int playerTurn) {
        this.playerTurn = playerTurn;
    }

    public ArrayList<Integer> getPlayersCardNumbers() {
        return playersCardNumbers;
    }

    public ArrayList<Card> getHandDeck() {
        return handDeck;
    }

    public boolean isStopBattle() {
        return stopBattle;
    }

    public boolean isEndGame() {
        return endGame;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Card getCardOnTop() {
        return cardOnTop;
    }

    public char getDeclaratedColour() {
        return declaratedColour;
    }

    public int isPlayerTurn() {
        return playerTurn;
    }
}

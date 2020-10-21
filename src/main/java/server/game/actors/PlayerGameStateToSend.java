package server.game.actors;

import java.io.Serializable;
import java.util.ArrayList;

public class PlayerGameStateToSend implements Serializable {
    private Card cardOnTop;
    private ArrayList<Integer> playersCardNumbers = new ArrayList<>();
    private ArrayList<Card> handDeck = new ArrayList();
    private int playerNumber = 0;

    public PlayerGameStateToSend(Card cardOnTop) {
        this.cardOnTop = cardOnTop;
    }

    public void setHandDeck(ArrayList<Card> handDeck) {
        this.handDeck = handDeck;
    }

    public void setPlayerNumber(int playerNumber) {
        this.playerNumber = playerNumber;
    }

    public ArrayList<Integer> getPlayersCardNumbers() {
        return playersCardNumbers;
    }

    public ArrayList<Card> getHandDeck() {
        return handDeck;
    }

    public int getPlayerNumber() {
        return playerNumber;
    }

    public Card getCardOnTop() {
        return cardOnTop;
    }
}

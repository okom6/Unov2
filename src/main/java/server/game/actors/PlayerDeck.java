package server.game.actors;

import server.actors.Player;
import server.game.actors.Card;

import java.util.ArrayList;

public class PlayerDeck {
    private ArrayList<Card> handDeck = new ArrayList();
    private Player player;

    public PlayerDeck(Player player) {
        this.player = player;
    }

    public ArrayList<Card> getHandDeck() {
        return handDeck;
    }

    public Player getPlayer() {
        return player;
    }

    public void addCard(Card card){
        handDeck.add(card);
    }

    public Card remove(int cardIndex){
        return handDeck.remove(cardIndex);
    }
}

package server.game.actors;

import server.actors.Player;
import server.actors.Table;

import java.util.ArrayList;
import java.util.Collections;

public class Croupier {
    public ArrayList<PlayerDeck> deal(Table table, ArrayList<Card> deck){
        ArrayList<PlayerDeck> playerDeckArrayList = new ArrayList<>();

        for(Player p: table.getPlayers()){
            PlayerDeck playerDeck = new PlayerDeck(p);
            for(int i = 0; i < 5; i++){
                playerDeck.addCard(deck.remove(0));
            }
            playerDeckArrayList.add(playerDeck);
        }

        return playerDeckArrayList;
    }

}

package server.game;

import server.actors.Player;
import server.actors.Table;
import server.game.actors.Croupier;
import server.game.actors.DeckFactory;
import server.game.actors.PlayerDeck;

import java.util.ArrayList;

public class Game {
    public static void startGame(Table table){
        DeckFactory deckFactory = new DeckFactory();
        ArrayList deck = deckFactory.produceShuffledDeck();
        Croupier croupier = new Croupier();
        ArrayList<PlayerDeck> playerDeckArrayList = croupier.deal(table, deck);


        for (PlayerDeck pd: playerDeckArrayList) {
            pd.getPlayer().getPlayerConnector().sendInfoToPlayer("gra rozpoczeta");
            pd.getPlayer().disconnect();
        }
    }
}

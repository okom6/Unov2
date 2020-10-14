package gameTests.actorsTests;

import org.junit.Test;
import server.PlayerConnector;
import server.actors.Player;
import server.actors.Table;
import server.game.actors.Card;
import server.game.actors.Croupier;
import server.game.actors.DeckFactory;
import server.game.actors.PlayerDeck;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class CroupierTests {

    @Test
    public void croupierDealSuccessfull(){
        Table table = new Table(2);
        table.addPlayer(new Player("1", new PlayerConnector(null, null, null)));
        table.addPlayer(new Player("2", new PlayerConnector(null, null, null)));
        DeckFactory deckFactory = new DeckFactory();
        ArrayList deck = deckFactory.produceShuffledDeck();
        Croupier croupier = new Croupier();
        ArrayList<PlayerDeck> playerDeckArrayList = croupier.deal(table, deck);

        assertEquals(98, deck.size());
        assertEquals(5, playerDeckArrayList.get(0).getHandDeck().size());
        assertEquals(5, playerDeckArrayList.get(1).getHandDeck().size());
    }
}

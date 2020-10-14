package gameTests.actorsTests;

import org.junit.Test;
import server.PlayerConnector;
import server.actors.Player;
import server.game.actors.Card;
import server.game.actors.DeckFactory;
import server.game.actors.PlayerDeck;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class PlayerDeckTests {

    @Test
    public void createdPlayerDeckSuccessfull(){
        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        assertNotNull(playerDeck);
        assertEquals("1", playerDeck.getPlayer().getHashID());
    }

    @Test
    public void addCardToPlayerDeckSuccessfull(){
        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        playerDeck.addCard(new Card('r', '0'));
        assertEquals(1, playerDeck.getHandDeck().size());
    }

    @Test
    public void removeCardFromPlayerDeckSuccessfull(){
        PlayerDeck playerDeck = new PlayerDeck(new Player("1",
                new PlayerConnector(null, null, null)));
        Card card = new Card('r', '0');
        playerDeck.addCard(card);
        assertEquals(card, playerDeck.remove(0));
        assertEquals(0, playerDeck.getHandDeck().size());
    }
}

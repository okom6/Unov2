package gameTests.actorsTests;

import org.junit.Test;
import server.game.actors.Card;

import static org.junit.Assert.assertEquals;

public class CardTests {

    @Test
    public void createdCardSuccessfull(){
        Card card = new Card('r', '0');
        assertEquals('r', card.getColour());
        assertEquals('0', card.getCharacter());
    }
}

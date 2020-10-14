package gameTests.actorsTests;

import org.junit.Test;
import server.game.actors.Card;
import server.game.actors.DeckFactory;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class DeckFactoryTests {

    @Test
    public void createdDeckFactorySuccessfull(){
        DeckFactory deckFactory = new DeckFactory();
        assertNotNull(deckFactory);
    }

    @Test
    public void createdDeckSuccessfull(){
        DeckFactory deckFactory = new DeckFactory();
        ArrayList<Card> deck = deckFactory.produceShuffledDeck();
        assertEquals(108, deck.size());
    }

    @Test
    public void createdDeckCardsColourSuccessfull() {
        DeckFactory deckFactory = new DeckFactory();
        ArrayList<Card> deck = deckFactory.produceShuffledDeck();

        assertEquals(25, deck.stream().filter(e -> e.getColour() == 'r').count());
        assertEquals(25, deck.stream().filter(e -> e.getColour() == 'y').count());
        assertEquals(25, deck.stream().filter(e -> e.getColour() == 'g').count());
        assertEquals(25, deck.stream().filter(e -> e.getColour() == 'b').count());
        assertEquals(8, deck.stream().filter(e -> e.getColour() == 's').count());
    }

    @Test
    public void createdDeckCardsCharactersSuccessfull() {
        DeckFactory deckFactory = new DeckFactory();
        ArrayList<Card> deck = deckFactory.produceShuffledDeck();

        assertEquals(4, deck.stream().filter(e -> e.getCharacter() == '0').count());
        assertEquals(8, deck.stream().filter(e -> e.getCharacter() == '1').count());
        assertEquals(8, deck.stream().filter(e -> e.getCharacter() == '2').count());
        assertEquals(8, deck.stream().filter(e -> e.getCharacter() == '3').count());
        assertEquals(8, deck.stream().filter(e -> e.getCharacter() == '4' && e.getColour() != 's').count());
        assertEquals(8, deck.stream().filter(e -> e.getCharacter() == '5').count());
        assertEquals(8, deck.stream().filter(e -> e.getCharacter() == '6').count());
        assertEquals(8, deck.stream().filter(e -> e.getCharacter() == '7').count());
        assertEquals(8, deck.stream().filter(e -> e.getCharacter() == '8').count());
        assertEquals(8, deck.stream().filter(e -> e.getCharacter() == '9').count());
        assertEquals(8, deck.stream().filter(e -> e.getCharacter() == 's').count());
        assertEquals(8, deck.stream().filter(e -> e.getCharacter() == 't').count());
        assertEquals(8, deck.stream().filter(e -> e.getCharacter() == 'g').count());
        assertEquals(8, deck.stream().filter(e -> e.getCharacter() == 'g').count());
        assertEquals(8, deck.stream().filter(e -> e.getCharacter() == 'g').count());
        assertEquals(4, deck.stream().filter(e -> e.getCharacter() == '4' && e.getColour() == 's').count());
        assertEquals(4, deck.stream().filter(e -> e.getCharacter() == 'c').count());
    }
}

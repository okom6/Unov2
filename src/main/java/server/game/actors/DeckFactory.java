package server.game.actors;

import server.game.actors.Card;

import java.util.ArrayList;
import java.util.Collections;

public class DeckFactory {
    public ArrayList<Card> produceShuffledDeck(){
        ArrayList<Card> deck = new ArrayList<>();
        deck.addAll(produceCardsWithOneColor('r'));
        deck.addAll(produceCardsWithOneColor('g'));
        deck.addAll(produceCardsWithOneColor('b'));
        deck.addAll(produceCardsWithOneColor('y'));
        //deck.addAll(produceBlackCards());
        Collections.shuffle(deck);
        return deck;
    }

    private ArrayList<Card> produceCardsWithOneColor(char colour){
        ArrayList<Card> cardsWithOneColour = new ArrayList<>();
        cardsWithOneColour.add(new Card(colour, '0'));
        for(int i = 0; i < 2; i++){
            cardsWithOneColour.add(new Card(colour, '1'));
            cardsWithOneColour.add(new Card(colour, '2'));
            cardsWithOneColour.add(new Card(colour, '3'));
            cardsWithOneColour.add(new Card(colour, '4'));
            cardsWithOneColour.add(new Card(colour, '5'));
            cardsWithOneColour.add(new Card(colour, '6'));
            cardsWithOneColour.add(new Card(colour, '7'));
            cardsWithOneColour.add(new Card(colour, '8'));
            cardsWithOneColour.add(new Card(colour, '9'));
            cardsWithOneColour.add(new Card(colour, 's'));
            cardsWithOneColour.add(new Card(colour, 't'));
            //cardsWithOneColour.add(new Card(colour, 'g'));
        }
        return cardsWithOneColour;
    }

    private ArrayList<Card> produceBlackCards(){
        ArrayList<Card> specialCards = new ArrayList<>();
        for(int i = 0; i < 4; i++) {
            specialCards.add(new Card('s', '4'));
            specialCards.add(new Card('s', 'c'));
        }
        return specialCards;
    }
}

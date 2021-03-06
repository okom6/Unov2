package server.game.actors;

import java.io.Serializable;

public class Card implements Serializable {
    char colour;
    char character;

    public Card(char colour, char character) {
        this.colour = colour;
        this.character = character;
    }

    public char getColour() {
        return colour;
    }

    public char getCharacter() {
        return character;
    }
}

package client.Gui;

import server.game.actors.Card;

import javax.swing.*;

public class CardsImageAdder {

    public void addImageToButton(JToggleButton button, Card card){
        button.setIcon(new ImageIcon(createImageFileName(card)));
    }

    public void addImageToLabel(JLabel label, Card card){
        label.setIcon(new ImageIcon(createImageFileName(card)));
    }

    public String createImageFileName(Card card){
        return "card_images/" + card.getColour() + "_" + card.getCharacter() + ".jpg";
    }
}

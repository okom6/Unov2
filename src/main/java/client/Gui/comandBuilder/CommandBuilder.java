package client.Gui.comandBuilder;

import javax.swing.*;
import java.util.Enumeration;

public abstract class CommandBuilder {
    protected String command;

    public abstract void buildCommandMove();

    public abstract void buildCommandChoosenCard(ButtonGroup cardButtonsGroup);

    public abstract void buildCommandChoosenColour(ButtonGroup colourRequestButtonsGroup);

    public String getComand() {
        return command;
    }

    public String getValueFromButtonGroup(ButtonGroup buttonGroup){
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getName();
            }
        }

        return null;
    }
}

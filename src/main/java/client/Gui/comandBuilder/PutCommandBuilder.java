package client.Gui.comandBuilder;

import javax.swing.*;
import java.util.Enumeration;

public class PutCommandBuilder extends CommandBuilder{

    @Override
    public void buildCommandMove() {
        command = "p";
    }

    @Override
    public void buildCommandChoosenCard(ButtonGroup cardButtonsGroup) {
        String buttonGroupValue = getValueFromButtonGroup(cardButtonsGroup);
        if(buttonGroupValue != null){
            command += "-" + buttonGroupValue;
        } else {
            command += "-0";
        }
    }

    @Override
    public void buildCommandChoosenColour(ButtonGroup colourRequestButtonsGroup) {
        String buttonGroupValue = getValueFromButtonGroup(colourRequestButtonsGroup);
        if(buttonGroupValue != null){
            command += "-" + buttonGroupValue;
        } else {
            command += "-*";
        }
    }
}

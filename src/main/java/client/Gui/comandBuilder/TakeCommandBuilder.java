package client.Gui.comandBuilder;

import javax.swing.*;

public class TakeCommandBuilder extends CommandBuilder{

    @Override
    public void buildCommandMove() {
        command = "t";
    }

    @Override
    public void buildCommandChoosenCard(ButtonGroup cardButtonsGroup) {
        command += "-0";
    }

    @Override
    public void buildCommandChoosenColour(ButtonGroup colourRequestButtonsGroup) {
        command += "-*";
    }
}

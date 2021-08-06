package client.Gui.comandBuilder;

import javax.swing.*;

public class CommandBuilderDirector {
    private CommandBuilder commandBuilder;

    public CommandBuilderDirector(CommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
    }

    public void setCommandBuilder(CommandBuilder commandBuilder) {
        this.commandBuilder = commandBuilder;
    }

    public String createCommand(ButtonGroup cardButtonsGroup, ButtonGroup colourRequestButtonsGroup){
        commandBuilder.buildCommandMove();
        commandBuilder.buildCommandChoosenCard(cardButtonsGroup);
        commandBuilder.buildCommandChoosenColour(colourRequestButtonsGroup);
        return commandBuilder.getComand();
    }
}

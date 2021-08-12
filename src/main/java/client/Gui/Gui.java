package client.Gui;

import client.console.ConnectionToServer;
import client.Gui.comandBuilder.CommandBuilderDirector;
import client.Gui.comandBuilder.PutCommandBuilder;
import client.Gui.comandBuilder.TakeCommandBuilder;
import error.ErrorCode;
import server.game.actors.Card;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Gui extends JFrame {
    private ClientGuiMediator clientGuiMediator;

    private ButtonGroup cardButtonsGroup = new ButtonGroup();
    private ArrayList<JToggleButton> cardsButtons = new ArrayList<>();

    private ButtonGroup colourRequestButtonsGroup = new ButtonGroup();
    private JToggleButton yellowButton = new JToggleButton();
    private JToggleButton blueButton = new JToggleButton();
    private JToggleButton redButton = new JToggleButton();
    private JToggleButton greenButton = new JToggleButton();

    private JButton takeButton = new JButton("Pobierz karty");

    private JButton sendButton = new JButton("Wyślij");

    private ArrayList<JLabel> playersInfo = new ArrayList<>();

    private JLabel errorCodeInfo = new JLabel();
    private JLabel gameBoardInfo = new JLabel();
    private JLabel cardOnTopInfo = new JLabel();

    public Gui(){
        setTitle("UNO");
        setSize(1920,1040);
        setLocation(new Point(0,0));
        setLayout(null);
        setResizable(false);

        initComponents();
        initEvent();
    }

    public void setClientGuiMediator(ClientGuiMediator clientGuiMediator) {
        this.clientGuiMediator = clientGuiMediator;
    }

    private void initComponents(){
        this.getContentPane().removeAll();

        sendButton.setBounds(1650,900,200,40);
        takeButton.setBounds(1650,850,200,40);

        add(sendButton);
        add(takeButton);

        initColourButtons();

        //
        //addTestButtons();
        //
        initCardButtons();


        initLabels();

        //
        //addTestPlayersInfo();
        //
        initPlayersInfo();
    }

    public void repaintButtonsAndPlayerInfo(){
        initCardButtons();
        initPlayersInfo();
        this.repaint();
    }

    private void initColourButtons(){
        blueButton.setBounds(1700,550,150,80);
        greenButton.setBounds(1540,550,150,80);
        yellowButton.setBounds(1700,460,150,80);
        redButton.setBounds(1540,460,150,80);

        blueButton.setBackground(Color.blue);
        greenButton.setBackground(Color.green);
        yellowButton.setBackground(Color.yellow);
        redButton.setBackground(Color.red);

        blueButton.setName("b");
        greenButton.setName("g");
        yellowButton.setName("y");
        redButton.setName("r");

        colourRequestButtonsGroup.add(blueButton);
        colourRequestButtonsGroup.add(greenButton);
        colourRequestButtonsGroup.add(yellowButton);
        colourRequestButtonsGroup.add(redButton);

        add(blueButton);
        add(greenButton);
        add(yellowButton);
        add(redButton);
    }

    private void initCardButtons(){
        int x = 150;
        int xFreeSpace = 10;
        int buttonWidth = 100;
        int maxCardsBeforeResize = 12;

        if(cardsButtons.size() > maxCardsBeforeResize){
            int lastPositionButton = x + (maxCardsBeforeResize * (buttonWidth + xFreeSpace));
             buttonWidth =  (lastPositionButton - x) / cardsButtons.size() - xFreeSpace;
        }

        for (JToggleButton b:
                cardsButtons) {
            b.setBounds(x, 750, buttonWidth, 200);
            add(b);
            x = x + buttonWidth + xFreeSpace;
        }
    }

    private void initPlayersInfo(){
        int x = 150;
        int xFreeSpace = 10;
        int labelWidth = 250;

        for (JLabel l:
                playersInfo) {
            l.setBounds(x, 100, labelWidth, 200);
            add(l);
            x = x + labelWidth + xFreeSpace;
        }
    }

    private void initLabels(){
        errorCodeInfo.setBounds(1550,200,250,40);
        errorCodeInfo.setText("error code info");
        add(errorCodeInfo);

        gameBoardInfo.setBounds(860,450,250,40);
        gameBoardInfo.setText("game board info");
        add(gameBoardInfo);

        cardOnTopInfo.setBounds(600,450,250,40);
        cardOnTopInfo.setText("card on top");
        add(cardOnTopInfo);
    }

    private void initEvent(){

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(1);
            }
        });

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendPutResponse(e);
            }
        });

        takeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendTakeResponse(e);
            }
        });
    }

    private void sendPutResponse(ActionEvent e){
        clientGuiMediator.sendMoveToServer(new CommandBuilderDirector(new PutCommandBuilder()),
                cardButtonsGroup, colourRequestButtonsGroup);
        cardButtonsGroup.clearSelection();
        colourRequestButtonsGroup.clearSelection();
    }

    private void sendTakeResponse(ActionEvent e){
        clientGuiMediator.sendMoveToServer(new CommandBuilderDirector(new TakeCommandBuilder()),
                cardButtonsGroup, colourRequestButtonsGroup);
        cardButtonsGroup.clearSelection();
        colourRequestButtonsGroup.clearSelection();
    }

    public void updateButtons(ArrayList<Card> handDeck){
        deleteOldButtons();

        for(int i = 0 ; i < handDeck.size(); i++){
            JToggleButton button = new JToggleButton(
                    handDeck.get(i).getColour() + "-" + handDeck.get(i).getCharacter());
            button.setName(Integer.toString(i));
            System.out.println("Button name: " + button.getName());
            cardsButtons.add(button);
            cardButtonsGroup.add(button);
        }
    }

    private void deleteOldButtons(){
        for (JToggleButton button: cardsButtons) {
            cardButtonsGroup.remove(button);
            remove(button);
        }
        cardsButtons.clear();
    }

    public void updatePlayersInfo(ArrayList<Integer> playersCardNumbers, int playerNumber){
        deleteOldPlayersInfo();

        for(int i = 0 ; i < playersCardNumbers.size(); i++){
            JLabel l = new JLabel();
            if(i != playerNumber) {
                l.setText("Player " + Integer.toString(i + 1) + "\n\n" +
                        "Cards" + Integer.toString(playersCardNumbers.get(i)));
            } else {
                l.setText("You " + "\n\n" +
                        "Cards" + Integer.toString(playersCardNumbers.get(i)));
            }
            l.setName(Integer.toString(i));
            playersInfo.add(l);
        }
    }

    private void deleteOldPlayersInfo(){
        for (JLabel playerInfo: playersInfo) {
            remove(playerInfo);
        }
        playersInfo.clear();
    }

    public void updadeGameBoardInfo(Card cardOnTop, boolean stopBattle, boolean takeBattle,
                                    boolean thisPlayerTurn, char declaratedColour, int playerTurn){
        this.cardOnTopInfo.setText(cardOnTop.getColour() + "-" + cardOnTop.getCharacter());
        this.gameBoardInfo.setText("Is stop battle: " + Boolean.toString(stopBattle)
                + "\n" + "Is take battle: " + Boolean.toString(takeBattle)
                + "\n" + ((thisPlayerTurn) ? "It is your turn" : "It is " + Integer.toString(playerTurn + 1)) + " player turn"
                + ((cardOnTop.getColour() == 's') ? "\nDeclarated colour: " + declaratedColour : ""));
        this.cardOnTopInfo.repaint();
        this.gameBoardInfo.repaint();
    }

    public void updadeErrorCodeInfo(ErrorCode errorCode){
        errorCodeInfo.setText(errorCode.getInfo());
        errorCodeInfo.repaint();
    }

    public void setEndgameInfo(String placeInfo){
        if (placeInfo.equals("-1")) {
            this.gameBoardInfo.setText("Jesteś ostatni. Przegrałeś!");
        } else{
            this.gameBoardInfo.setText("Miejsce " + placeInfo);
        }
        this.gameBoardInfo.repaint();

        deleteOldButtons();
        deleteOldPlayersInfo();
        this.repaint();
    }

    public static void main(String[] args){
        Gui gui = new Gui();
        gui.setVisible(true);

        /*ClientGame clientGame = new ClientGame();
        clientGame.startGame(new ConnectionToServer("localhost", 7777));*/
        GuiClientGame guiClientGame = new GuiClientGame(new ConnectionToServer("localhost", 7777));
        ClientGuiMediator clientGuiMediator = new ClientGuiMediator(gui, guiClientGame);

        guiClientGame.reciveMessagesAbautGameState();
    }

}

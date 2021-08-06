package client.Gui;

import client.Gui.comandBuilder.CommandBuilderDirector;
import client.Gui.comandBuilder.PutCommandBuilder;
import client.Gui.comandBuilder.TakeCommandBuilder;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Gui extends JFrame {
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

    public Gui(){
        setTitle("UNO");
        setSize(1920,1040);
        setLocation(new Point(0,0));
        setLayout(null);
        setResizable(false);

        initComponent();
        initEvent();
    }

    private void initComponent(){
        sendButton.setBounds(1650,900,200,40);
        takeButton.setBounds(1650,850,200,40);

        add(sendButton);
        add(takeButton);

        initColourButtons();

        addTestButtons();
        initCardButtons();

        errorCodeInfo.setBounds(1550,200,250,40);
        errorCodeInfo.setText("error code info");
        add(errorCodeInfo);

        gameBoardInfo.setBounds(750,450,250,40);
        gameBoardInfo.setText("game board info");
        add(gameBoardInfo);

        addTestPlayersInfo();
        initPlayersInfo();

        /*putCardButton.setBounds(100,10,100,20);
        takeCardButton.setBounds(100,35,100,20);


        btnTutup.setBounds(300,130, 80,25);
        btnTambah.setBounds(300,100, 80,25);

        txtA.setBounds(100,10,100,20);
        txtB.setBounds(100,35,100,20);
        txtC.setBounds(100,65,100,20);

        lblA.setBounds(20,10,100,20);
        lblB.setBounds(20,35,100,20);
        lblC.setBounds(20,65,100,20);*/

        //txtA.setName();

        /*add(lblA);
        add(lblB);
        add(lblC);

        add(txtA);
        add(txtB);
        add(txtC);*/
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
        CommandBuilderDirector commandBuilderDirector = new CommandBuilderDirector(new PutCommandBuilder());
        System.out.println(commandBuilderDirector.createCommand(cardButtonsGroup, colourRequestButtonsGroup));
        cardButtonsGroup.clearSelection();
        colourRequestButtonsGroup.clearSelection();
    }

    private void sendTakeResponse(ActionEvent e){
        CommandBuilderDirector commandBuilderDirector = new CommandBuilderDirector(new TakeCommandBuilder());
        System.out.println(commandBuilderDirector.createCommand(cardButtonsGroup, colourRequestButtonsGroup));
        cardButtonsGroup.clearSelection();
        colourRequestButtonsGroup.clearSelection();
    }

    public static void main(String[] args){
        Gui gui = new Gui();
        gui.setVisible(true);
    }

    private void addTestButtons(){
        for(int i = 0 ; i < 10; i++){
            JToggleButton b = new JToggleButton(Integer.toString(i + 1));
            b.setName(Integer.toString(i));
            cardsButtons.add(b);
            cardButtonsGroup.add(b);
        }
    }

    private void addTestPlayersInfo(){
        for(int i = 0 ; i < 5; i++){
            JLabel l = new JLabel();
            l.setText("Player " + Integer.toString(i + 1));
            l.setName(Integer.toString(i));
            playersInfo.add(l);
        }
    }

}

package server.serverGui;

import server.PlayerConnector;
import server.Server;
import server.actors.Player;
import server.actors.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BasicServerGui extends JFrame {
    private ButtonGroup radioButtonGroup = new ButtonGroup();
    private ArrayList<JRadioButton> radioButtons = new ArrayList<>();
    private JButton runButton = new JButton("Start");
    private JLabel infoLabel = new JLabel("Serwer wyłączony");

    private boolean serverRunned = false;

    private Thread serverThread;

    public BasicServerGui(){
        setTitle("SERWER UNO");
        setSize(500,300);
        setLocation(new Point(800,350));
        setLayout(null);
        setResizable(false);

        initElements();
        initEvent();
    }

    private void initElements(){
        runButton.setBounds(250, 50, 200, 50);
        add(runButton);

        initRadioButtons();

        infoLabel.setBounds(260, 150, 200, 50);
        add(infoLabel);
    }

    private void initRadioButtons(){
        int height = 50;
        int freeSpace = 10;
        int buttonSize = 20;

        for(int i = 2; i < 7; ++i){
            JRadioButton button = new JRadioButton(Integer.toString(i));
            button.setName(Integer.toString(i));
            radioButtons.add(button);
            radioButtonGroup.add(button);
        }

        for(JRadioButton button: radioButtons){
            button.setBounds(50, height, 100, buttonSize);
            height = height + buttonSize + freeSpace;
            add(button);
        }
    }

    private void initEvent(){

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(1);
            }
        });

        runButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                runServer(e);
            }
        });
    }

    private void runServer(ActionEvent e){
        String valueFromButtonGroup = getValueFromButtonGroup(radioButtonGroup);

        if(serverRunned || valueFromButtonGroup == null) {return;}

        serverRunned = true;

        infoLabel.setText("Serwer włączony");
        infoLabel.repaint();

        serverThread = new Thread(new ServerThreat(Integer.parseInt(valueFromButtonGroup)));
        serverThread.start();
    }

    private String getValueFromButtonGroup(ButtonGroup buttonGroup){
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getName();
            }
        }

        return null;
    }

    private static class ServerThreat implements Runnable{
        private int roomSize;

        public ServerThreat(int roomSize) {
            this.roomSize = roomSize;
        }

        public void run() {
            Server server = new Server(roomSize);
            server.start(7777);
        }
    }

    public static void main(String[] args){
        BasicServerGui basicServerGui = new BasicServerGui();
        //basicServerGui.pack();
        basicServerGui.setVisible(true);
    }
}

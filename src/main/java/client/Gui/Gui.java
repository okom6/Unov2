package client.Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class Gui extends JFrame {
    private ArrayList<JToggleButton> cardsButtons = new ArrayList<>();

    private JToggleButton yellowButton = new JToggleButton();
    private JToggleButton blueButton = new JToggleButton();
    private JToggleButton redButton = new JToggleButton();
    private JToggleButton greenButton = new JToggleButton();

    private JToggleButton takeButton = new JToggleButton("Pobierz karty");
    private JToggleButton putButton = new JToggleButton("Połóż kartę");

    private JButton sendButton = new JButton("Wyślij");

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

    private void initEvent(){

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e){
                System.exit(1);
            }
        });

        sendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                sendResponse(e);
            }
        });
    }

    private void sendResponse(ActionEvent e){

    }

    public static void main(String[] args){
        Gui gui = new Gui();
        gui.setVisible(true);
    }
}

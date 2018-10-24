package model;

import model.abitur.netz.Client;
import view.framework.DrawTool;
import view.framework.DrawableObject;
import view.framework.DrawingPanel;

import javax.crypto.Cipher;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.util.Scanner;

/**
 * Klasse des Clients zum TicTacToe spielen.
 */
public class TicTacToeClient extends Client implements DrawableObject {

    /** Spieler-Nummer (Kreis(1) oder Kreuz(2)) */
    private int playerNumber;

    /**
     * click: Mousereleased boolean
     * turn: Ob man am Zug ist
     * win: Ob das Spiel zu Ende ist
     * restart: Ob man ein Rematch haben will
     */
    private boolean click,turn,win,restart;

    /**
     * Das Spielfeld, das auf Grund von höherer Nützlichkeit in einem 1-Dimensionalem Array gespeichert wird.
     */
    private Field[] map;
    private PublicKey publicKey;
    private PrivateKey privateKey;
    private Scanner scanner;

    private DrawingPanel dp;
    private JTextField textField;

    /**
     * Konstrukor der Klasse TicTacToeClient
     * Erstellt einen Client womit auf den Servern TicTacToe spielen kann
     *
     * @param pServerIP IPv4 Adresse des Servers um sich zu verbinden
     * @param pServerPort +der benötigte Port
     * @param map Eine Map die übergeben werden muss (Array aus Field-Objekten)
     */
    public TicTacToeClient(String pServerIP, int pServerPort, Field[] map, DrawingPanel dp) {
        super(pServerIP, pServerPort);
        if(isConnected())
            System.out.println("running Client");
        this.map = map;
        this.dp = dp;

        scanner = new Scanner(System.in);
        textField = new JTextField(5);
        dp.add(textField);
        textField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    System.out.println("> "+e.getActionCommand());
                    send("CHAT"+encryptMessage(e.getActionCommand()));
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
    }


    private byte[] encryptMessage(String message) throws Exception{
        byte[] encryptedMessage = null;
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE,publicKey);
        encryptedMessage = cipher.doFinal(message.getBytes());

        return encryptedMessage;
    }


    private void buildPrivateKey(String message){
        String[] temp = message.split("PRIVATE");
        temp = temp[1].split("NEXT");
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PrivateKey prK = factory.generatePrivate(new RSAPrivateKeySpec(new BigInteger(temp[0].getBytes()),new BigInteger(temp[1].getBytes())));
            privateKey = prK;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildPublicKey(String message){
        String[] temp = message.split("PUBLIC");
        temp = temp[1].split("NEXT");
        try {
            KeyFactory factory = KeyFactory.getInstance("RSA");
            PublicKey puK = factory.generatePublic(new RSAPublicKeySpec(new BigInteger(temp[0].getBytes()),new BigInteger(temp[1].getBytes())));
            publicKey = puK;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Felder werden gezeichnet
     * Rematch-Button wird nach Bedarf gezeichnet
     *
     * @param drawTool DrawTool lol
     */
    @Override
    public void draw(DrawTool drawTool) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                drawTool.drawRectangle(i*200+100,j*200+80, 200,200);
            }
        }
        if (win) {
            if (restart) drawTool.setCurrentColor(0,255,0,255);
            else drawTool.setCurrentColor(0,0,0,255);
            drawTool.drawRectangle(350,30,100,30);
            drawTool.drawRectangle(351,31,98,28);
            drawTool.drawRectangle(352,32,96,26);
            drawTool.drawText(370,50,"Rematch?");
        }
    }


    //Irrelevant
    @Override
    public void update(double dt) {

    //    System.out.println((dp.getTextField().getText()));
        //if(scanner.hasNext()) {
     //       send(scanner.next());
        //     System.out.println("sended "+scanner.next());

    }

    @Override
    public void keyPressed(int key) {

    }

    @Override
    public void keyReleased(int key) {


    }
    //Irrelevant


    /**
     * Aktionen des Clients auf Nachrichten des Servers
     *
     * @param pMessage Die Nachricht
     */
    @Override
    public void processMessage(String pMessage) {
        if(pMessage.contains("PUBLIC")) {
            buildPublicKey(pMessage);
        }else if(pMessage.contains("PRIVATE")) {
            buildPrivateKey(pMessage);
        } else if(pMessage.contains("TEXT")){
            String[] message = pMessage.split("TEXT");
            for(int i=1;i<message.length;i++){
                System.out.println(message[i]);
            }
            if (message[1].equalsIgnoreCase("   ")) {
                win = false;
                restart = false;
            }
        }else if (pMessage.contains("SPIELER")){
            /**
             * Es wird festgelegt ob der Client Spieler 1 oder Spieler 2 ist
             */
            String[] message = pMessage.split("SPIELER");
            playerNumber = Integer.parseInt(message[1]);
        }else if (pMessage.contains("KREUZ")){
            /**
             * Es wird festgelegt wer als Erster startet
             * Man fängt an, wenn man:
             *      - Spieler 1 ist und das Symbol Kreuz hat
             *      - Spieler 2 ist und das Symbol Kreis hat
             */
            if (playerNumber == 1) {
                turn = false;
            }
            if (playerNumber == 2) {
                turn = true;
            }
            System.out.println(playerNumber+" "+turn);
        }else if (pMessage.contains("KREIS")){
            if (playerNumber == 1) {
                turn = true;
            }
            if (playerNumber == 2) {
                turn = false;
            }
            System.out.println(playerNumber+" "+turn);
        }else if (pMessage.contains("UPDATE")){
            /**
             * Das Spielfeld wid aktualisiert
             */
            String[] toUpdate =  pMessage.split("UPDATE");
            if (toUpdate[1].contains("NEXT")){
                String[] fields = toUpdate[1].split("NEXT");
                String[][] data = new String[9][3];
                for (int i = 0; i < fields.length; i++) {
                    if (fields[i].contains("FIELD")){
                        data[i] = fields[i].split("FIELD");
                    }
                }
                updateField(data);
            }
        }else if (pMessage.contains("WIN")){
            turn = false;
            win = true;
        }else if (pMessage.contains("RESTART")){
            win = false;
            restart = false;

        }
    }

    /**
     * Mausklick-Überprüfung
     *
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        if (!click) {
            click = true;
            if (turn) {
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        if (e.getX() >= i * 200 + 100 && e.getX() < i * 200 + 300 && e.getY() >= j * 200 + 80 && e.getY() < j * 200 + 280) {
                            String message = "ACTIONNEXT" + i + "FIELD" + j + "FIELD" + playerNumber + "";
                            System.out.println(message);
                            send(message);
                        }
                    }
                }
            }else if (win) {
                if (e.getX() >= 350 && e.getX() <= 450 && e.getY() >= 30 && e.getY() <= 60) {
                    if (!restart) {
                        send("RESTART");
                        restart = true;
                    }
                }
            }
        }else{
            click = false;
        }
    }

    /**
     * Aktualisieren des Feldes:
     * Das üebrgebene "map"-Array bekommt die entsprechenden Werte zugeschrieben die es momentan aufweisen soll.
     *
     * @param data
     */
    private void updateField(String[][] data){
        for (int i = 0; i < data.length; i++) {
            int wert = Integer.parseInt(data[i][2]);
            if (wert == 1) map[i].setCircle();
            if (wert == 2) map[i].setCross();
        }
    }
}

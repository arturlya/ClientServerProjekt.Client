package model;

import model.abitur.netz.Client;
import model.framework.GraphicalObject;
import view.framework.DrawTool;
import view.framework.DrawableObject;

import java.awt.event.MouseEvent;

public class TicTacToeClient extends Client implements DrawableObject {

    private int playerNumber;
    private boolean click,turn;
    private Field[] map;

    public TicTacToeClient(String pServerIP, int pServerPort, Field[] map) {
        super(pServerIP, pServerPort);
        if(isConnected())
            System.out.println("running Client");
        this.map = map;
    }



    //Irrelevant
    @Override
    public void draw(DrawTool drawTool) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                drawTool.drawRectangle(i*200+100,j*200+80, 200,200);
            }
        }
    }

    @Override
    public void update(double dt) {

    }

    @Override
    public void keyPressed(int key) {

    }

    @Override
    public void keyReleased(int key) {

    }
    //Irrelevant



    @Override
    public void processMessage(String pMessage) {
        if(pMessage.contains("TEXT")){
            String[] message = pMessage.split("TEXT");
            for(int i=1;i<message.length;i++){
                System.out.println(message[i]);
            }
        }else if (pMessage.contains("SPIELER")){
            String[] message = pMessage.split("SPIELER");
            playerNumber = Integer.parseInt(message[1]);
        }else if (pMessage.contains("KREUZ")){
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
        }
    }

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
            }
        }else{
            click = false;
        }
    }

    private void updateField(String[][] data){
        for (int i = 0; i < data.length; i++) {
            int wert = Integer.parseInt(data[i][2]);
            if (wert == 1) map[i].setCircle();
            if (wert == 2) map[i].setCross();
        }
    }
}

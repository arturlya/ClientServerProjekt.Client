package view.framework;

import control.Config;

import javax.swing.*;
import java.util.ArrayList;

/** Entspricht einem Fenster, das DrawingPanels beinhalten kann.
 *  Vorgegebene Klasse des Frameworks. Modifikation auf eigene Gefahr.
 */
public class DrawFrame extends JFrame {

    // Attribute

    // Referenzen
    private DrawingPanel activePanel;           // Das im Moment sichtbare DrawingPanel
    private ArrayList<DrawingPanel> panels;     // Die Liste aller DrawingPanels

    /**
     * Konstruktor
     * @param name Der Titel des Fensters
     * @param x Die obere linke x-Koordinate des Fensters bzgl. des Bildschirms
     * @param y Die obere linke y-Koordinaite des Fensters bzgl. des Bildschirms
     * @param width Die Breite des Fensters
     * @param height Die Höhe des Fensters
     */
    public DrawFrame(String name, int x, int y, int width, int height) {
        panels = new ArrayList<>();
        activePanel = new DrawingPanel();
        panels.add(activePanel);
        add(activePanel);
        addKeyListener(activePanel);
        setLocation(x,y);
        setSize(width,height);
        setTitle(name);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        if ( Config.INFO_MESSAGES) System.out.println("  > DrawFrame: Ich wurde erzeugt. Das erste DrawingPanel wurde angelegt.");
    }

    /**
     * Liefert das aktuell vom DrawFrame angezeigte DrawingBoard zurück
     * @return Das aktuelle DrawingPanel
     */
    public DrawingPanel getActiveDrawingPanel(){
        return activePanel;
    }

    /**
     * Fügt dem DrawFrame ein neues DrawingPanel hinzu. Dieses wird nicht zum
     * aktuellen DrawingPanel!
     */
    public void addNewDrawingPanel(){
        DrawingPanel tempDB = new DrawingPanel();
        panels.add(tempDB);
        if ( Config.INFO_MESSAGES) System.out.println("  > DrawFrame: DrawingPanel mit dem Index: "+(panels.size()-1)+"angelegt.");
    }

    /**
     * Ändert das aktuell vom DrawFrame gezeigte DrawingPanel.
     * @param index Der Index des neuen zu zeigenden DrawingPanels (angefangen bei 0).
     */
    public void setActiveDrawingPanel(int index){
        if (index < panels.size()){
            remove(activePanel);
            removeKeyListener(activePanel);
            activePanel = panels.get(index);
            add(activePanel);
            revalidate();
            addKeyListener(activePanel);
            if ( Config.INFO_MESSAGES) System.out.println("  > DrawFrame: Aktives Panel auf Index: "+index+" gesetzt.");
        } else {
            if ( Config.INFO_MESSAGES) System.out.println("  > DrawFrame: Programmierfehler DrawingPanel mit dem Index: "+index+" exitiert nicht.");
        }
    }

    @Override
    /**
     * Gibt die Breite des im Fenster liegenden DrawingPanels zurück
     */
    public int getWidth(){
        return activePanel.getWidth();
    }

    @Override
    /**
     * Gibt die Höhe des im Fenster liegenden DrawingPanels zurück
     */
    public int getHeight(){
        return activePanel.getHeight();
    }

    public DrawingPanel getPanelByIndex(int index){
        if ( panels.get(index) != null) return panels.get(index);
        return null;
    }
}


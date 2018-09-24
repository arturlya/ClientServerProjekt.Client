package control.framework;

import control.Config;
import view.framework.DrawFrame;
import java.awt.*;

/**
 * Diese Klasse enthält die main-Methode. Von ihr wird als erstes ein Objekt innerhalb der main-Methode erzeugt,
 * die zu Programmstart aufgerufen wird und kein Objekt benötigt, da sie statisch ist.
 * Die erste Methode, die also nach der main-Methode aufgerufen wird, ist der Konstruktor dieser Klasse. Aus ihm
 * wird alles weitere erzeugt.
 * Vorgegebene Klasse des Frameworks. Modifikation auf eigene Gefahr.
 */
public class MainController {

    // Attribute

    // Referenzen

    /**
     * Die Main-Methode von Java startet das Programm. Sie ist einzigartig im ganzen Projekt.
     * Sie erzeugt mit dem nachfolgenden Code genau ein Objekt der Klasse MainController und ist
     * dann abgeschlossen.
     */
    public static void main (String[] args){
        if ( Config.INFO_MESSAGES) System.out.println("***** PROGRAMMSTART *****.");
        if ( Config.INFO_MESSAGES) System.out.println("Benötigte Objekte des Frameworks werden erzeugt:");
        if ( Config.INFO_MESSAGES) System.out.println("  > Main-Methode: Programmstart. Erzeuge ein Objekt der Main-Controller-Klasse.");
        EventQueue.invokeLater(
                new Runnable() {
                    @Override
                    public void run() {
                        new MainController();
                    }
                });
    }

    /**
     * Der Konstruktor der Klasse-MainController ist die erste Methode, die nach der Main-Methode
     * aufgerufen wird. Hier wird der Programmfluss geregelt.
     */
    public MainController(){
        if ( Config.INFO_MESSAGES) System.out.println("  > MainController: Ich wurde erzeugt. Erstelle Fenster (Drawframe-Objekt)...");
        // Berechne Mitte des Bildschirms
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
        int x = width / 2;
        int y = height / 2;
        // Berechne die beste obere linke Ecke für das Fenster so, dass es genau mittig erscheint
        x = x - Config.WINDOW_WIDTH / 2;
        y = y - Config.WINDOW_HEIGHT / 2;
        // Erzeuge ein neues Fenster zum Zeichnen
        DrawFrame drawFrame = new DrawFrame(Config.WINDOW_TITLE, x, y, Config.WINDOW_WIDTH, Config.WINDOW_HEIGHT);
        drawFrame.setResizable(false);
        // Übergibt den weiteren Programmfluss an das neue Objekt der Klasse UIController
        if ( Config.INFO_MESSAGES) System.out.println("  > MainController: Erzeuge UIController und übergebe Drawframe-Objekt-Referenz.");
        new UIController(drawFrame);
    }

}

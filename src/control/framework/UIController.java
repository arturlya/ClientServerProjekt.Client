package control.framework;

import control.Config;
import control.ProgramController;
import view.framework.DrawFrame;
import view.framework.DrawableObject;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Diese Klasse kontrolliert die DrawingPanels einer ihr zugewiesenen DrawingFrame.
 * Sie kann verschiedene Objekte erzeugen und den Panels hinzufuegen.
 * Vorgegebene Klasse des Frameworks. Modifikation auf eigene Gefahr.
 */
public class UIController implements ActionListener {

    // Referenzen
    private DrawFrame drawFrame;    // das Fenster des Programms
    private ProgramController gameController; // das Objekt, das das Programm steuern soll
    private javax.swing.Timer spielProzess;

    // Attribute
    private int dt;
    private long lastLoop, elapsedTime;

    /**
     * Erzeugt ein Objekt zur Kontrolle des Programmflusses. Die uebergebene DrawFrame ist
     * das aktive Fenster fuer das Programm.
     * @param drawFrame Das Fenster des Programms wird für einen neuen UIController benoetigt
     */
    public UIController(DrawFrame drawFrame){
        this.drawFrame = drawFrame;
        // Waehlt das erste (standardmaeßig vorhandene) drawingPanel
        selectDrawingPanel(0);
        // Setzt die Ziel-Zeit zwischen zwei aufeinander folgenden Frames in Millisekunden
        dt = 35; //Vernuenftiger Startwert
        if ( Config.INFO_MESSAGES) System.out.println("  > UIController: Erzeuge ProgramController und starte Spielprozess (Min. dt = "+dt+"ms)...");
        if ( Config.INFO_MESSAGES) System.out.println("-------------------------------------------------------------------------------------------------\n");
        gameController = new ProgramController(this);
        gameController.startProgram();
        // Starte nebenlaeufigen Prozess, der Zeichnen und Animation uebernimmt
        lastLoop = System.nanoTime();
        spielProzess = new javax.swing.Timer(dt, this);
        spielProzess.start();
    }

    /**
     * Setzt das angezeigte Drawingpanel auf das Panel mit dem angegebenen Index.
     * @param index Gibt die Nummer des gewünschten Drawing-Panel-Objekts an.
     */
    public void selectDrawingPanel(int index){
        // Setze das gewuenschte DrawingPanel und lege eine Referenz darauf an.
        drawFrame.setActiveDrawingPanel(index);
    }

    /**
     * Erzeugt ein neues, leeres Drawingpanel, das noch nicht angezeigt wird.
     */
    public void createNewDrawingPanel(){
        drawFrame.addNewDrawingPanel();
    }

    public void drawObjectOnPanel(DrawableObject dO, int index){
        if ( drawFrame.getPanelByIndex(index) != null && dO != null){
            drawFrame.getPanelByIndex(index).addObject(dO);
        }
    }

    /**
     * Abkuerzende Methode, um ein Objekt auf das gerade aktuelle DrawingPanel zu zeichnen. Zusaetzlich
     * wird update vom Objekt aufgerufen.
     * @param dO Das zu zeichnende Objekt.
     */
    public void drawObject(DrawableObject dO){
        if ( drawFrame.getActiveDrawingPanel() != null && dO != null){
            drawFrame.getActiveDrawingPanel().addObject(dO);
        }
    }

    /**
     * Abkuerzende Methode, um ein Objekt vom aktuellen DrawingPanel zu entfernen. Dann wird auch
     * update vom Objekt nicht mehr aufgerufen.
     * @param dO Das zu entfernende Objekt.
     */
    public void removeObject(DrawableObject dO){
        if ( drawFrame.getActiveDrawingPanel() != null && dO != null){
            drawFrame.getActiveDrawingPanel().removeObject(dO);
        }
    }

    /**
     * Entfernt ein Objekt aus einem DrawingPanel. Die Update- und Draw-Methode des Objekts
     * wird dann nicht mehr aufgerufen.
     * @param dO Das zu entfernende Objekt
     * @param index Der Index des DrawingPanel-Objekts von dem entfernt werden soll
     */
    public void removeObjectFromPanel(DrawableObject dO, int index){
        if ( drawFrame.getPanelByIndex(index) != null && dO != null){
            drawFrame.getPanelByIndex(index).removeObject(dO);
        }
    }

    /**
     * Ueberprueft ob eine bestimmte Taste im Moment gedrueckt ist. Eignet sich hervorragend, um fluessige
     * Steuerungen durch einen Spieler zu realisieren.
     * @param key Der Zahlencode fuer die Taste. Kann aus der Klasse KeyEvent erhalten werden.
     * @return True, falls die Taste im Moment gedrueckt ist, sonst false.
     */
    public boolean isKeyDown(int key){
        return drawFrame.getActiveDrawingPanel().isKeyDown(key);
    }

    /**
     * Wird vom Timer-Thread aufgerufen. Es wird dafuer gesorgt, dass das aktuelle Drawing-Panel
     * alle seine Objekte zeichnet und deren Update-Methoden aufruft.
     * Zusaetzlich wird die updateProgram-Methode des GameControllers regelmaeßig nach jeder Frame
     * aufgerufen.
     * @param e Das uebergebene Action-Event-Objekt
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        elapsedTime = System.nanoTime() - lastLoop;
        lastLoop = System.nanoTime();
        dt = (int) ((elapsedTime / 1000000L)+0.5);
        if ( dt == 0 ) dt = 1;
        drawFrame.getActiveDrawingPanel().updateDrawingPanel(dt);
        gameController.updateProgram((double)dt/1000);
    }

}

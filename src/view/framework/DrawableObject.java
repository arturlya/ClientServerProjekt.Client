package view.framework;

import java.awt.event.MouseEvent;

/**
 * Interface für Objekte, die man zeichnen und steuern kann.
 * Vorgegebene Klasse des Frameworks. Modifikation auf eigene Gefahr.
 */
public interface DrawableObject{

    void draw(DrawTool drawTool);

    void update(double dt);

    void keyPressed(int key);

    void keyReleased(int key);

    void mouseReleased(MouseEvent e);

}

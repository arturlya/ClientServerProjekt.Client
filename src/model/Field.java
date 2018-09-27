package model;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

/**
 * Klasse Field
 */
public class Field extends GraphicalObject {

    private int value; //   0 = "nicht belegt"    1 = "Kreis"   2 = "Kreuz"

    /**
     * Konstruktor der Klasse Field
     */
    public Field(){
        value = 0;
    }

    /**
     * Kreis oder Kreuz zeichnen
     *
     * @param drawTool DrawTool lol
     */
    @Override
    public void draw(DrawTool drawTool) {
        if (value == 1) {
            drawTool.drawCircle(x*200+120,y*200+100,160);
        }else if (value == 2) {
            drawTool.drawLine(x*200+120,y*200+100,x*200+280,y*200+260);
            drawTool.drawLine(x*200+280,y*200+100,x*200+120,y*200+260);
        }
    }

    /**
     * @return value zurückgeben
     */
    public int getValue() {
        return value;
    }

    /**
     * Kreuz setzen
     */
    public void setCross() {
        this.value = 2;
    }

    /**
     * Kreis setzen
     */
    public void setCircle() {
        this.value = 1;
    }

    /**
     * Leer setzen
     */
    public void setEmpty() {
        this.value = 0;
    }

    /**
     * @return Fragt ab, ob das Feld leer ist.
     */
    public boolean isEmpty(){
        if(value == 0){
            return true;
        }
        return false;
    }
}

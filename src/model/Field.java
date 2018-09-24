package model;

import model.framework.GraphicalObject;
import view.framework.DrawTool;

public class Field extends GraphicalObject {

    private int value; //   0 = "nicht belegt"    1 = "Kreis"   2 = "Kreuz"

    public Field(){
        value = 0;
    }

    @Override
    public void draw(DrawTool drawTool) {
        if (value == 1) {
            drawTool.drawCircle(x*200+120,y*200+100,160);
        }else if (value == 2) {
            drawTool.drawLine(x*200+120,y*200+100,x*200+280,y*200+260);
            drawTool.drawLine(x*200+280,y*200+100,x*200+120,y*200+260);
        }
    }

    public int getValue() {
        return value;
    }

    public void setCross() {
        this.value = 2;
    }

    public void setCircle() {
        this.value = 1;
    }

    public boolean isEmpty(){
        if(value == 0){
            return true;
        }
        return false;
    }
}

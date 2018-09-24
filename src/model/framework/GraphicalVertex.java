package model.framework;

import model.VertexContent;
import model.framework.GraphicalObject;

import java.awt.geom.Rectangle2D;

/**
 * Created by braun on 09.06.2018.
 */
public class GraphicalVertex extends GraphicalObject implements VertexContent {

    private String name;

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }
    public Rectangle2D.Double getHitbox(){
        return new Rectangle2D.Double(x,y,width,height);
    }
}

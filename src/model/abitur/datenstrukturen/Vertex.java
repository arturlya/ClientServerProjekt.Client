package model.abitur.datenstrukturen;

import model.framework.GraphicalVertex;

import java.awt.geom.Point2D;

/**
 * <p>
 * Materialien zu den zentralen NRW-Abiturpruefungen im Fach Informatik ab 2018
 * </p>
 * <p>
 * Klasse Vertex
 * </p>
 * <p>
 * Die Klasse Vertex stellt einen einzelnen Knoten eines Graphen dar. Jedes Objekt 
 * dieser Klasse verfuegt ueber eine im Graphen eindeutige ID als String und kann diese 
 * ID zurueckliefern. Darueber hinaus kann eine Markierung gesetzt und abgefragt werden.
 * </p>
 * 
 * @author Qualitaets- und UnterstuetzungsAgentur - Landesinstitut fuer Schule
 * @version Oktober 2015
 */
public class Vertex <ContentType extends GraphicalVertex>{
  //Einmalige ID des Knotens und Markierung
  private boolean mark;
  private ContentType contentType;
  
  /**
  * Ein neues Objekt vom Typ Vertex wird erstellt. Seine Markierung hat den Wert false.
  */
  public Vertex(ContentType contentType,String pID){
    contentType.setName(pID);
    mark = false;
    this.contentType = contentType;

  }


  public Point2D getLocation(){
    Point2D point = new Point2D.Double(getContent().getX(),getContent().getY());
    return point;
  }

  public ContentType getContent(){return contentType;}

  public void setContentType(ContentType contentType) {
    this.contentType = contentType;
  }

  /**
  * Die Anfrage liefert die ID des Knotens als String.
  */
  public String getID(){
    return new String(contentType.getName());
  }
  
  /**
  * Der Auftrag setzt die Markierung des Knotens auf den Wert pMark.
  */
  public void setMark(boolean pMark){
    mark = pMark;
  }
  
  /**
  * Die Anfrage liefert true, wenn die Markierung des Knotens den Wert true hat, ansonsten false.
  */
  public boolean isMarked(){
    return mark;
  }
  
}

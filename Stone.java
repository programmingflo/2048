import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Stone on the canvas
 *
 * @author Florian Mansfeld & Georg Roemmling
 *
 * @version 0.250; 2018.11.21 - 00:30
 *
 */
public class Stone extends Actor
{
    /**
     *  Mit "alreadyCombined" soll verhindert werden, dass sich ein Stein mit einem anderen Stein verbindet, obwohl sich einer der beiden schon verbunden hat.
     *  Heisst,
     *  2-2-4-8
     *  soll beim Bewegen nach rechts hierzu werden:
     *  0-4-4-8
     *  und nicht zu:
     *  0-0-0-16
     */

    private int value;
    private boolean alreadyCombined;

    public Stone(int value){
        this.value = value;
        this.alreadyCombined = false;
        this.changeImageBasedOnValue();
    }

    public int getValue(){
        return value;
    }

    void doubleValue(){
        // this method doesn't change "alreadyCombined" to true
        this.value *= 2;
        changeImageBasedOnValue();
        this.alreadyCombined = true;
    }

    public void setAlreadyCombined(boolean alreadyCombined)
    {
        this.alreadyCombined = alreadyCombined;
    }
    public boolean getAlreadyCombined()
    {
        return alreadyCombined;
    }

    public void changeImageBasedOnValue()
    {
        // changes image based on the value of the stone, path to image is "value of the stone + .png"
        // if this result is an invalid picture (no power of two), the image is set to "Fehler.png"
        int valueStone = this.getValue();
        try{
            this.setImage(valueStone + ".png");
        }catch (Exception e) {
            this.setImage("Fehler.png");
        }
    }

    public boolean canAct(int directionX, int directionY)
    {
        // Die Tatsache, dass nur eine lineare Bewegung in eine der Richtungen durchgefuehrt wird, setz ich jetzt einfach mal voraus
        // erste Pruefung darauf ob wir mit der gewuenschten Bewegung ueberhaupt auf dem Spielfeld landen wuerden:
        if (!isAtBorder(directionX, directionY))
        {
            // Falls in der gewuenschten Richtung kein Stein liegt, gehts:
            Stone stoneOther = (Stone) getOneObjectAtOffset(directionX, directionY, Stone.class);
            if (stoneOther == null)
            {
                return true;    // Kein anderer Stein in Bewegungsrichtung => Bewegung moeglich
            } else {
                // Andernfalls wird geprueft, ob die beiden den selben Wert haben:
                if (this.getValue() == stoneOther.getValue())
                {
                    // Haben die beiden Steine denselben Wert, wird geprueft ob sie sich schon verbunden haben:
                    if (this.getAlreadyCombined() != true && stoneOther.getAlreadyCombined() != true)
                    {
                        return true;        // Anderer Stein hat denselben Wert, beide haben sich noch nicht verbunden == Verbindung moeglich
                    } else {
                        return false;       // Anderer Stein hat denselben Wert, mind. einer der beidne hat sich schon verbunden == Verbindung nicht moeglich
                    }
                } else {
                    return false;       // Anderer Stein hat anderen Wert === nichts moeglich
                }
            }
        } else {
            return false;       // Bewegung wuerde ausserhalb des Spielfelds landen == Geht nicht
        }
    }

    public void moveOrCombine(int directionX, int directionY)
    {
        // Falls kein Stein in Bewegungsrichtung liegt, wird sich einfach in die Richtung bewegt.
        Stone stoneOther = (Stone) getOneObjectAtOffset(directionX, directionY, Stone.class);
        if (stoneOther == null)
        {
            setLocation(getX() + directionX, getY() + directionY);
        } else {
            // andernfalls wird geprueft, ob der Stein vor uns denselben Wert hat:
            if (this.getValue() == stoneOther.getValue())
            {
                // falls selber Wert, wird geprueft ob sich beide noch nicht verbunden haben --> falls dem so ist, wird sich verbunden
                if (this.getAlreadyCombined() != true && stoneOther.getAlreadyCombined() != true)
                {
                    // combination of two stones
                    getWorld().removeObject(stoneOther);
                    this.doubleValue();
                    setLocation(getX() + directionX, getY() + directionY);
                }
            }
        }
    }

    boolean checkIntersection(List<Stone> stones, int directionX, int directionY){
        boolean intersection = false;
        for (Stone stone : stones) {
            if(this.getX() + directionX == stone.getX() &&
                this.getY() + directionY == stone.getY()){
                intersection = true;
            }
        }
        return intersection;
    }

    boolean isAtBorder(int directionX, int directionY){
        if(directionX == 1 && getX() == 3){
            return true;
        }else if(directionX == -1 && getX() == 0){
            return true;
        }else if(directionY == 1 && getY() == 3){
            return true;
        }else if(directionY == -1 && getY() == 0){
            return true;
        }else{
            return false;
        }
    }
}

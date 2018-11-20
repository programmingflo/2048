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
    public Stone(){
        this.value = 2;
        this.alreadyCombined = false;
        this.changeImageBasedOnValue();
    }
    public Stone(int value){
        this.value = value;
    }
    public void act()
    {
        // "act"-method is defined in "Canvas"-class
    }

    public int getValue(){
        return value;
    }
    public void setValueAsTest(int value)
    {
        this.value = value;
        changeImageBasedOnValue();
    }


    void doubleValue(){
        // this method doesn't change "alradyCombined" to true
        value *= 2;
        changeImageBasedOnValue();
    }

    public void setalreadyCombined(boolean alreadyCombined)
    {
        this.alreadyCombined = alreadyCombined;
    }
    public boolean getalreadyCombined()
    {
        return alreadyCombined;
    }

    public void changeImageBasedOnValue()
    {
        /**
         * Momentan noch fuer jeden zulaessigen Wert ein "richtiges" Bild, spaeter vielleicht mit ner Funktion oder so.
         * Bild des Stein auf zulaessiges Bild wechseln, falls:
         * 1.) Wert groesser gleich 2
         * 2.) Wert kleiner gleich 2048
         * 3.) Wert Potenz von 2
         */
        int valueStone = this.getValue();
        if (isPowerOfTwo(valueStone))
        {
            this.setImage(valueStone + ".png");
        } else {
            this.setImage("Fehler.png");
        }
    }

    public static boolean isPowerOfTwo(int number){
        /**
         * Binaerschreibweise einer Potenz von 2 ist eine 1 gefolgt von x-mal 0.
         * Potenz von zwei - 1 = 0 voran und Rest 1en
         * => z.B.:
         * 100000000 = number
         * 011111111 = number - 1
         * => number & (number - 1) = 000000000 == 0
         * Quelle:
         * https://codereview.stackexchange.com/questions/172849/checking-if-a-number-is-power-of-2-or-not
         */
        return number >= 2 && number <= 2048 && ((number & (number - 1)) == 0);
    }

    public boolean canAct(int directionX, int directionY)
    {
        // Die Tatsache, dass nur eine lineare Bewegung in eine der Richtungen durchgefuehrt wird, setz ich jetzt einfach mal voraus
        // erste Pruefung darauf ob wir mit der gewuenschten Bewegung ueberhaupt auf dem Spielfeld landen wuerden:
        if (getX() + directionX >= 0 && getX() + directionX <= 3 && getY() + directionY >= 0 && getY() + directionY <= 3)
        {
            // Falls in der gewuenschten Richtung kein Stein liegt, gehts:
            Stone stoneOther = (Stone)getOneObjectAtOffset(directionX, directionY, Stone.class);
            if (stoneOther == null)
            {
                return true;    // Kein anderer Stein in Bewegungsrichtung => Bewegung moeglich
            } else {
                // Andernfalls wird geprueft, ob die beiden den selben Wert haben:
                if (this.getValue() == stoneOther.getValue())
                {
                    // Haben die beiden Steine denselben Wert, wird geprueft ob sie sich schon verbunden haben:
                    if (this.getalreadyCombined() != true && stoneOther.getalreadyCombined() != true)
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
        Stone stoneOther = (Stone)getOneObjectAtOffset(directionX, directionY, Stone.class);
        if (stoneOther == null)
        {
            setLocation(getX() + directionX, getY() + directionY);
        } else {
            // andernfalls wird geprueft, ob der Stein vor uns denselben Wert hat:
            if (this.getValue() == stoneOther.getValue())
            {
                // falls selber Wert, wird geprueft ob sich beide noch nicht verbunden haben
                // falls dem so ist, wird sich verbunden
                if (this.getalreadyCombined() != true && stoneOther.getalreadyCombined() != true)
                {
                    // irgendwie removen
                    //greenfoot.removeObject(stoneOther);
                    getWorld().removeObject(stoneOther);
                    this.doubleValue();
                    this.setalreadyCombined(true);
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

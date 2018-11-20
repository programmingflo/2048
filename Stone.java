import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Stone on the canvas
 *
 * @author Florian Mansfeld & Georg Roemmling
 *
 * // ich hab hier jetzt einfach mal die Version 0.100 ausgewaehlt, als Zeitpunkt einfach die Zeit, zu der ich meinen Senf dazu hochlade
 * @version 0.100; 2018.11.18 - 22:00
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
        // Add your action code here.
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

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Stone on the canvas
 *
 * @author Florian Mansfeld
 * @version (a version number or a date)
 */
public class Stone extends Actor
{
    private int value;

    public Stone(){
        this.value = 2;
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

    void doubleValue(){
        value *= 2;
    }

    boolean checkIntersection(Actor actor){
        return this.intersects(actor);
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

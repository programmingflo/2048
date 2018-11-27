import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;

/**
 * Stone on the canvas
 *
 * @author Florian Mansfeld & Georg Roemmling
 *
 * @version 1.0
 *
 */
public class Stone extends Actor
{
    /**
     *  "alreadyCombined" is supposed to limit a Stone's ability to combine with another Stone to once per turn
     *  e.g. pressing "right" in this moment:
     *  2-2-4-8
     *  is supposed to turn into:
     *  0-4-4-8
     *  instead of:
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

    public void act() {
        //
    }

    void doubleValue(){
        // doubles the Stones' value, changes it's image and sets "alreadyCombined" to true
        // if the Stone's value reaches 2048, the player has won
        this.value *= 2;
        Canvas canvas = (Canvas) getWorld();
        canvas.score.addToScore(this.value);

        changeImageBasedOnValue();
        this.alreadyCombined = true;
        if (this.value == 2048)
        {
            canvas.gameOver("Win");
        }
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
            System.out.println("Es ist ein Fehler aufgetreten.");
            Greenfoot.stop();
        }
    }

    public boolean canAct(int directionX, int directionY)
    {
        // there is no check as to if the desired movement is NOT diagonal, since the directionX and directionY are passed on from "moveStones"
        // first check is if the moevement is within the boundaries of the canvas
        if (!isAtBorder(directionX, directionY))
        {
            // if there is no other stone in front, movement is possible
            Stone stoneOther = (Stone) getOneObjectAtOffset(directionX, directionY, Stone.class);
            if (stoneOther == null)
            {
                return true;    // no other stone in front => movement possible
            } else {
                // other stone in front, comparing values of both stones
                if (this.getValue() == stoneOther.getValue())
                {
                    // if both Stones have the same value, the next check is if any of them have already been combined this turn
                    if (this.getAlreadyCombined() != true && stoneOther.getAlreadyCombined() != true)
                    {
                        return true;        // both have same value, not combined yet => combination possible
                    } else {
                        return false;       // both have same value, at least one has already been combined this turn => combination not possible
                    }
                } else {
                    return false;       // other Stone has different value => combination not possible
                }
            }
        } else {
            return false;       // movement wouldn't end on Canvas => not possible
        }
    }

    public void moveOrCombine(int directionX, int directionY)
    {
        // if no other Stone is in front, movement is possible
        Stone stoneOther = (Stone) getOneObjectAtOffset(directionX, directionY, Stone.class);
        if (stoneOther == null)
        {
            setLocation(getX() + directionX, getY() + directionY);
        } else {
            // if other Stone is in front, comparison of values
            if (this.getValue() == stoneOther.getValue())
            {
                // if both Stones same value and none have been combined this turn, they will get combined
				// "combine" = remove other Stone in front, double value of .this, move into desired direction
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

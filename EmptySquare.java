/**
 * EmptySquares are supposed to be the x- and y- coordinates of an Empty Square, meaning there's no Stone at this position
 *
 * @author Florian Mansfeld & Georg Roemmling
 * 
 * @version 0.250; 2018.11.21 - 00:30
 *
 */
public class EmptySquare  
{
    // instance variables - replace the example below with your own
    private int x;
    private int y;

    /**
     * Constructor for objects of class Coordinates
     */
    public EmptySquare(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public void setX(int x)
    {
        this.x = x;
    }
    
    public int getY()
    {
        return y;
    }

    public void setY(int y)
    {
        this.y = y;
    }
}

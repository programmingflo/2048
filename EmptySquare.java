/**
 * EmptySquares are supposed to be the x- and y- coordinates of an Empty Square, meaning there's no Stone at this position
 *
 * @author Florian Mansfeld & Georg Roemmling
 * 
 * @version 1.0
 *
 */
public class EmptySquare  
{
    private int x;
    private int y;

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

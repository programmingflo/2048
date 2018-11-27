import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Messages such as "Gewonnen!", which are supposed to be displayed at the end of the game
 *
 * @author Florian Mansfeld & Georg Roemmling
 *
 * @version 1.0
 *
 */
public class EndingMessage extends Actor
{
    public EndingMessage(String message)
    {
        //setImage(new GreenfootImage("GAME OVER", 48, Color.WHITE, Color.BLACK));
        setImage(new GreenfootImage(message, 56, Color.DARK_GRAY, null));
    }
}

import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;
/**
 * creates a canvas for the game 2048
 *
 * @author Florian Mansfeld & Georg Roemmling
 *
 * @version 1.0
 *
 */
public class Canvas extends World
{
    /**
     * Constructor for objects of class Canvas.
     *
     */
    private boolean canMoveUp;
    private boolean canMoveRight;
    private boolean canMoveDown;
    private boolean canMoveLeft;
    private boolean didAnythingHappen;
    private List<Stone> stoneList;
    private List<EmptySquare> emptySquares;
    private final int chancefor4 = 14;
    private int dx;
    private int dy;
    private int dx1;
    private int dx2;
    private int dy1;
    private int dy2;
    private GreenfootImage imageStone;
    private EndingMessage gameEnded;
    String pressedKey;
    MouseInfo mouse;
    Score score = new Score();
    
    
    public Canvas()
    {
        super(4, 4, 100);
        setBackground("2048_Background.png");
        addObject(new Stone(2),1,1);
        addObject(new Stone(2),3,2);
        addObject(new Stone(2),2,2);
        addObject(new Stone(2),1,2);
    }

    public void gameOver(String message)
    {
        // when the game has ended, it's supposed to display "Game over" or "Gewonnen!" respectively and display the score the player has reached
        if (message.equals("Loss"))
        {
            turnStonesTransparent();
            gameEnded = new EndingMessage("Game over :-(");
        } else if (message.equals("Win")) {
            turnStonesTransparent();
            gameEnded = new EndingMessage("Gewonnen!");
        }
        this.addObject(gameEnded, 1, 0);
        displayScore();
        Greenfoot.stop();
    }
    
    public void turnStonesTransparent()
    {
        for (Stone s: stoneList)
        {
            imageStone = s.getImage();
            imageStone.setTransparency(60);
            s.setImage(imageStone);
        }
    }
    
    public void displayScore()
    {
        gameEnded = new EndingMessage("Score:");
        this.addObject(gameEnded, 1, 1);
        gameEnded = new EndingMessage(""+this.score.getScore());    // "" + ... is needed in order to turn the int returned by .getScore() into a String
        this.addObject(gameEnded, 1, 2);
    }
    
    public void resetAlreadyCombined()
    {
        stoneList = getObjects(Stone.class);
        // reset "alreadyCombined" for all Stones
        for (Stone s: stoneList)
        {
            s.setAlreadyCombined(false);
        }
    }

    public void checkPossibilityOfMovement()
    {
        canMoveUp = false;
        canMoveRight = false;
        canMoveDown = false;
        canMoveLeft = false;
        for (Stone s: stoneList)
        {
            if (s.canAct(0, -1)) { canMoveUp = true;}
            if (s.canAct(1, 0)) { canMoveRight = true;}
            if (s.canAct(0, 1)) { canMoveDown = true;}
            if (s.canAct(-1, 0)) { canMoveLeft = true;}
        }
    }

    public List<EmptySquare> checkForEmptySquares()
    {
        // checks squares if they are empty (== no Stone)
        // if square is empty, its added to an ArrayList<EmptySquare>
        // returns said List
        List<EmptySquare> list2 = new ArrayList<EmptySquare>();
        for (int x = 0; x <= 3; x ++)
        {
            for (int y = 0; y <= 3; y++)
            {
                List<Stone> sList3 = getObjectsAt(x, y, Stone.class);
                if (sList3.isEmpty())
                {
                    list2.add(new EmptySquare(x, y));
                }
            }
        }
        return list2;
    }

    public void createRandomNewStone()
    {
        emptySquares = checkForEmptySquares();
        // only if emptySquares actually contains at least one EmptySquare, the method creates a new Stone.
        if (emptySquares.size() > 0)
        {
            EmptySquare es = emptySquares.get(Greenfoot.getRandomNumber(emptySquares.size()));
            int random = Greenfoot.getRandomNumber(100);
            if(random < chancefor4){
                addObject(new Stone(4), es.getX(), es.getY());
            }else{
                addObject(new Stone(2), es.getX(), es.getY());
            }
        }
    }

    public void act(){
        // the creation of the stoneList and returning "alreadyCombined" to "false" for all Stones is supposed to be done, regardless of which button was pressed
        resetAlreadyCombined();
        // Keyboard input has first priority
        // Mouse movement has second priority
        pressedKey = Greenfoot.getKey();
        if (pressedKey != null)
        {
            if (pressedKey.equals("right") || pressedKey.equals("left") || pressedKey.equals("up") || pressedKey.equals("down"))
            {
                // before stones are attempted to move, there is a check in which direction movement is possible
                checkPossibilityOfMovement();
                // if no movement is possible at all, the game is supposed to end with a "Game over"
                if (canMoveUp == false && canMoveRight == false && canMoveDown == false && canMoveLeft == false)
                {
                    gameOver("Loss");
                }
                // if movement is possible, the pressedKey is passed on
                actBasedOnInput(pressedKey);
            }
        } else {
            // if no button has been pressed, the movement of the mouse is checked
            // because the mouseDrag-event isn't tied to any actors, the mouseDrag-start is replaced with mousePressed
            // because the mouseDrag-event isn't tied to any actors, it's parameter is (null)
            // before stones are attempted to move, there is a check in which direction movement is possible
            checkPossibilityOfMovement();
            if (Greenfoot.mousePressed(null))
            {
                mouse = Greenfoot.getMouseInfo();
                // If the mouse wasn't on the canvas, Greenfoot.getMouseInfo() will return null
                if (mouse!=null)
                {
                    dx1 = mouse.getX();
                    dy1 = mouse.getY();
                }
            }
            if (Greenfoot.mouseDragEnded(null))
            {
                mouse = Greenfoot.getMouseInfo();
                if (mouse!=null)
                {
                    dx2 = mouse.getX();
                    dy2 = mouse.getY();
                    dx = dx2 - dx1;
                    dy = dy2 - dy1;
                    // movement based on mouse-input is complicated because the canvas is a 4x4 grid
                    // for instance, mouse-input such as "1 up, 1 left" can't be resolved to either "up" or "left"
                    // if dx and dy are the same, their sum will be 2*dx
                    // if dx = -dy, their sum will be 0
                    // both cases plus (dy==0 && dx==0) won't work
                    if (dx+dy!=0 && dx+dy!=2*dx && (dx!=0 || dy!=0))
                    {
                        if(dx>=0 && dy>=0 && dx>dy) {
                            actBasedOnInput("right"); // dx and dy positive, dx larger than dy = right
                        } else if (dx>=0 && dy>=0 && dx<dy) {
                            actBasedOnInput("down"); // dx and dy positive, dx smaller than dy = down
                        } else if (dx>=0 && dy<0 && dx>Math.abs(dy)) {
                            actBasedOnInput("right"); // dx pos and dy neg, dx larger than abs(dy) = right
                        } else if (dx>=0 && dy<0 && dx<Math.abs(dy)) {
                            actBasedOnInput("up"); // dx pos and dy neg, dx smaller than abs(dy) = up
                        } else if (dx<0 && dy>=0 && Math.abs(dx)>dy) {
                            actBasedOnInput("left"); // dx neg and dy pos, abs(dx) larger than dy = left
                        } else if (dx<0 && dy>=0 && Math.abs(dx)<dy) {
                            actBasedOnInput("down"); // dx neg and dy pos, abs(dx) smaller than dy = down
                        } else if (dx<0 && dy<0 && Math.abs(dx)>Math.abs(dy)) {
                            actBasedOnInput("left"); // dx and dy neg, abs(dx) larger than abs(dy) = left
                        } else if (dx<0 && dy<0 && Math.abs(dx)<Math.abs(dy)) {
                            actBasedOnInput("up"); // dx and dy neg, abs(dx) smaller than abs(dy) = up
                        }
                    }
                }
            }
        }
    }

    public void actBasedOnInput(String input)
    {
        // if a certain button has been pressed and movement into this direction is possible, the Stone(s) get moved
        // since this will always end with at least one Stone being moved, another Stone will be randomly generated on an empty space
        if(input.equals("right") && canMoveRight == true){
            List<List<Stone>> sortedStones = sortToRight(stoneList);
            moveStones(sortedStones,1,0);
            createRandomNewStone();
        }else if(input.equals("left") && canMoveLeft == true){
            List<List<Stone>> sortedStones = sortToLeft(stoneList);
            moveStones(sortedStones,-1,0);
            createRandomNewStone();
        }else if(input.equals("up") && canMoveUp == true){
            List<List<Stone>> sortedStones = sortToUp(stoneList);
            moveStones(sortedStones,0,-1);
            createRandomNewStone();
        }else if(input.equals("down") && canMoveDown == true){
            List<List<Stone>> sortedStones = sortToDown(stoneList);
            moveStones(sortedStones,0,1);
            createRandomNewStone();
        }
    }

    List<List<Stone>> sortToRight(List<Stone> stones){
        List<List<Stone>> output = new ArrayList<List<Stone>>();
        //initialize two-dimensional list
        for (int i = 0; i < 4; i++) {
            output.add(new ArrayList<Stone>());
        }

        //sort by line
        for(Stone stone: stones){
            output.get(stone.getY()).add(stone);
        }

        //sort in line (inverted bubblesort)
        for (int i = 0; i < output.size(); i++) {
            for (int k = 1; k < output.get(i).size() && output.get(i).size() != 1; k++) {
                for (int j = 1; j < output.get(i).size() && output.get(i).size() != 1; j++) {
                    //if stone(j-1) is more right then stone(j) -> swap
                    if(output.get(i).get(j).getX() > output.get(i).get(j-1).getX()){
                        //swap temp=a; a=b; b=temp;
                        Stone temp = output.get(i).get(j-1);
                        output.get(i).set(j-1,output.get(i).get(j));
                        output.get(i).set(j,temp);
                    }
                }
            }
        }
        return output;
    }

    List<List<Stone>> sortToLeft(List<Stone> stones){
        List<List<Stone>> output = new ArrayList<List<Stone>>();
        //initialize two-dimensional list
        for (int i = 0; i < 4; i++) {
            output.add(new ArrayList<Stone>());
        }

        //sort by line
        for(Stone stone: stones){
            output.get(stone.getY()).add(stone);
        }

        //sort in line (inverted bubblesort)
        for (int i = 0; i < output.size(); i++) {
            for (int k = 1; k < output.get(i).size() && output.get(i).size() != 1; k++) {
                for (int j = 1; j < output.get(i).size() && output.get(i).size() != 1; j++) {
                    //if stone(j-1) is more right then stone(j) -> swap
                    if(output.get(i).get(j).getX() < output.get(i).get(j-1).getX()){
                        //swap
                        Stone temp = output.get(i).get(j-1);
                        output.get(i).set(j-1,output.get(i).get(j));
                        output.get(i).set(j,temp);
                    }
                }
            }
        }
        return output;
    }

    List<List<Stone>> sortToUp(List<Stone> stones){
        List<List<Stone>> output = new ArrayList<List<Stone>>();
        //initialize two-dimensional list
        for (int i = 0; i < 4; i++) {
            output.add(new ArrayList<Stone>());
        }

        //sort by coloumn
        for(Stone stone: stones){
            output.get(stone.getX()).add(stone);
        }

        //sort in line (inverted bubblesort)
        for (int i = 0; i < output.size(); i++) {
            for (int k = 1; k < output.get(i).size() && output.get(i).size() != 1; k++) {
                for (int j = 1; j < output.get(i).size() && output.get(i).size() != 1; j++) {
                    //if stone(j-1) is more right then stone(j) -> swap
                    if(output.get(i).get(j).getY() < output.get(i).get(j-1).getY()){
                        //swap
                        Stone temp = output.get(i).get(j-1);
                        output.get(i).set(j-1,output.get(i).get(j));
                        output.get(i).set(j,temp);
                    }
                }
            }
        }
        return output;
    }

    List<List<Stone>> sortToDown(List<Stone> stones){
        List<List<Stone>> output = new ArrayList<List<Stone>>();
        //initialize two-dimensional list
        for (int i = 0; i < 4; i++) {
            output.add(new ArrayList<Stone>());
        }

        //sort by coloumn
        for(Stone stone: stones){
            output.get(stone.getX()).add(stone);
        }

        //sort in line (inverted bubblesort)
        for (int i = 0; i < output.size(); i++) {
            for (int k = 1; k < output.get(i).size() && output.get(i).size() != 1; k++) {
                for (int j = 1; j < output.get(i).size() && output.get(i).size() != 1; j++) {
                    //if stone(j-1) is more right then stone(j) -> swap
                    if(output.get(i).get(j).getY() > output.get(i).get(j-1).getY()){
                        //swap
                        Stone temp = output.get(i).get(j-1);
                        output.get(i).set(j-1,output.get(i).get(j));
                        output.get(i).set(j,temp);
                    }
                }
            }
        }
        return output;
    }

    void moveStones(List<List<Stone>> stones, int directionX, int directionY){
        // stoneList-wise, every stone will perform "moveOrCombine" until it can no longer move / combine
        // this fact is represented by the boolean returned from the method "canAct"
        for (List<Stone> stoneList : stones) {
            for(int i = 0; i < stoneList.size();i++){

                while(stoneList.get(i).canAct(directionX, directionY)){
                    stoneList.get(i).moveOrCombine(directionX, directionY);
                }
            }
        }
    }

}

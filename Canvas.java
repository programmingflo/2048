import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;

/**
 * creates a canvas for the game 2048
 *
 * @author Florian Mansfeld & Georg Roemmling
 *
 * @version 0.300; 2018.11.22 - 11:00
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

    public Canvas()
    {
        super(4, 4, 100);
        setBackground("2048_Background.png");
        addObject(new Stone(2),1,1);
        addObject(new Stone(2),3,2);
        addObject(new Stone(2),2,2);
        addObject(new Stone(2),1,2);

    }

    public void resetAlreadyCombined()
    {
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
        // Checks squares if they are empty (== no Stone)
        // If square is empty, its added to an ArrayList<EmptySquare>
        // Returns said List
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
        // Only if emptySquares actually contains at least one EmptySquare, the method creates a new Stone.
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
        // Greenfoot is supposed to take no action unless one of the arrows is pressed:
        if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("down"))
        {
            stoneList = getObjects(Stone.class);
            // The creation of the stoneList and returning "alreadyCombined" to "false" for all Stones is supposed to be done, regardless of which button was pressed
            resetAlreadyCombined();

                System.out.println("alreadyCombined");
            // Before stones are attempted to move, there is a check whether or not the movement in those directions is even possible:
            checkPossibilityOfMovement();

                System.out.println("movement possible");
            // If no movement is possible anymore, the game is supposed to end with a "Game over"
            if (canMoveUp == false && canMoveRight == false && canMoveDown == false && canMoveLeft == false)
            {
                // file created with Audacity, 32 bit version didn't work, 16 boit version sounds like shit
                Greenfoot.playSound("game over.wav");
                Greenfoot.stop();
            }

            System.out.println(String.valueOf(canMoveUp));
            System.out.println(String.valueOf(canMoveRight));
            System.out.println(String.valueOf(canMoveDown));
            System.out.println(String.valueOf(canMoveLeft));
            // If a certain button has been pressed and movement into this direction is possible, the Stone(s) get moved.
            // Since this will always end with at least one Stone being moved, another Stone will be randomly generated on an empty space.
            if(Greenfoot.isKeyDown("right") && canMoveRight == true){
                List<List<Stone>> sortedStones = sortToRight(stoneList);
                moveStones(sortedStones,1,0);
                createRandomNewStone();
            }else if(Greenfoot.isKeyDown("left") && canMoveLeft == true){
                List<List<Stone>> sortedStones = sortToLeft(stoneList);
                moveStones(sortedStones,-1,0);
                createRandomNewStone();
            }else if(Greenfoot.isKeyDown("up") && canMoveUp == true){
                List<List<Stone>> sortedStones = sortToUp(stoneList);
                moveStones(sortedStones,0,-1);
                createRandomNewStone();
            }else if(Greenfoot.isKeyDown("down") && canMoveDown == true){
                List<List<Stone>> sortedStones = sortToDown(stoneList);
                moveStones(sortedStones,0,1);
                createRandomNewStone();
            }
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
        // This fact is represented with the boolean returned from the method "canAct"

            System.out.println("moveStones");
        for (List<Stone> stoneList : stones) {
            for(int i = 0; i < stoneList.size();i++){

                while(stoneList.get(i).canAct(directionX, directionY)){
                    stoneList.get(i).moveOrCombine(directionX, directionY);
                }
            }
        }
    }

}

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
    public Canvas()
    {
        super(4, 4, 100);
        setBackground("2048_Background.png");   // Background ist im Endeffekt nur ein weisses Feld mit einem 1px dicken, grauen Rand oben und links
        addObject(new Stone(),1,1);
        addObject(new Stone(),3,2);
        addObject(new Stone(),2,2);
        addObject(new Stone(),1,2);
        
    }

    public void createStoneListAndChangeAlreadyTurnedToFalse()
    {
        stoneList = getObjects(Stone.class);
        // Zuruecksetzen von "alradyCombined" fuer alle Steine
        for (Stone s: stoneList)
        {
            s.setalreadyCombined(false);
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
            addObject(new Stone(), es.getX(), es.getY());
        }
        /**
         * Test to see the contents of the emptySquares-List
        for (EmptySquare es: emptySquares)
        {
            System.out.println("es ist hier: (" + es.getX() + "/" + es.getY() + ").");
        }
        if (emptySquares.isEmpty())
        {
            System.out.println("Die Liste emptySquares gibts garnicht");
        }
        */
    }
    
    
    public void act(){
        // Greenfoot is supposed to take no action unless one of the arrows is pressed:
        if (Greenfoot.isKeyDown("right") || Greenfoot.isKeyDown("left") || Greenfoot.isKeyDown("up") || Greenfoot.isKeyDown("down"))
        {
            // The creation of the stoneList and returning "alreadyCombined" to "false" for all Stones is supposed to be done, regardless of which button was pressed
            createStoneListAndChangeAlreadyTurnedToFalse();
            // Before stones are attempted to move, there is a check whether or not the movement in those directions is even possible:
            checkPossibilityOfMovement();
            // If no movement is possible anymore, the game is supposed to end with a "Game over"
            if (canMoveUp == false && canMoveRight == false && canMoveDown == false && canMoveLeft == false)
            {
                // file created with Audacity, 32 bit version didn't work, 16 boit version sounds like shit
                Greenfoot.playSound("game over.wav");
                Greenfoot.stop();
            }
            
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
        
        
        //test output
        /*for (Stone stone : sortedStones.get(1)) {
            System.out.println("output of sorted stones");
            System.out.print(stone.getX());
            System.out.println(stone.getY());
        }*/

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

    List<List<Stone>> sortByPosition(){
        List<Stone> stones = getObjects(Stone.class);
        List<List<Stone>> output = new ArrayList<List<Stone>>();
        //initialize two-dimensional list
        for (int i = 0; i < 4; i++) {
            output.add(new ArrayList<Stone>());
        }


        if(Greenfoot.isKeyDown("right")){
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
                            //swap
                            Stone temp = output.get(i).get(j-1);
                            output.get(i).set(j-1,output.get(i).get(j));
                            output.get(i).set(j,temp);
                        }
                    }
                }
            }

            //test output
            for (Stone stone : output.get(2)) {
                System.out.println("test2");
                System.out.print(stone.getX());
                System.out.println(stone.getY());
            }
        }else if(Greenfoot.isKeyDown("left")){
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

          //test output
          for (Stone stone : output.get(2)) {
              System.out.println("test2");
              System.out.print(stone.getX());
              System.out.println(stone.getY());
          }
        }else if(Greenfoot.isKeyDown("up")){
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

          //test output
          for (Stone stone : output.get(1)) {
              System.out.println("test3");
              System.out.print(stone.getX());
              System.out.println(stone.getY());
          }
        }else if(Greenfoot.isKeyDown("down")){
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


        }

        return output;
    }

    void moveStones(List<List<Stone>> stones, int directionX, int directionY){
        // stoneList-wise, every stone will perform "moveOrCombine" until it can no longer move / combine
        // This fact is represented with the boolean returned from the method "canAct"
        for (List<Stone> stoneList : stones) {
            for(int i = 0; i < stoneList.size();i++){

                while(stoneList.get(i).canAct(directionX, directionY)){
                    stoneList.get(i).moveOrCombine(directionX, directionY);
                }
            }
        }
        /**
         * Synthax, den Flo davor genommen hatte:
         *
        for (List<Stone> stoneList : stones) {
            for(int i = 0; i < stoneList.size(); i++){
                //check if two adjecent stones has the same value
                if(i < stoneList.size()-1){
                    if(stoneList.get(i).getValue() == stoneList.get(i+1).getValue()){
                        //"connect the two stones" -> double the value of the first stone and remove the second stone
                        stoneList.get(i).doubleValue();
                        removeObject(stoneList.get(i+1));
                        stoneList.remove(i+1);
                    }
                }
                //move as far as possible
                while(!stoneList.get(i).isAtBorder(directionX,directionY)){
                    System.out.println("move");
                    if(i>0 && stoneList.get(i).checkIntersection(stoneList, directionX, directionY)){
                        System.out.println("intersects");
                        break;
                    }
                    stoneList.get(i).setLocation(stoneList.get(i).getX()+directionX,stoneList.get(i).getY()+directionY);
                }
            }
        }
        */
    }
    
}

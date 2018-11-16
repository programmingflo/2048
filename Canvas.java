import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.List;
import java.util.ArrayList;

/**
 * creates a canvas for the game 2048
 *
 * @author Florian Mansfeld
 * @version (a version number or a date)
 */
public class Canvas extends World
{
    /**
     * Constructor for objects of class Canvas.
     *
     */
    public Canvas()
    {
        super(4, 4, 50);
        addObject(new Stone(),1,1);
        addObject(new Stone(),3,2);
        addObject(new Stone(),2,2);
        addObject(new Stone(),1,2);
    }

    public void act(){
        List<Stone> stoneList = getObjects(Stone.class);

        if(Greenfoot.isKeyDown("right")){
            List<List<Stone>> sortedStones = sortToRight(stoneList);
            moveStones(sortedStones,1,0);
        }else if(Greenfoot.isKeyDown("left")){
            List<List<Stone>> sortedStones = sortToLeft(stoneList);
            moveStones(sortedStones,-1,0);
        }else if(Greenfoot.isKeyDown("up")){
            List<List<Stone>> sortedStones = sortToUp(stoneList);
            moveStones(sortedStones,0,-1);
        }else if(Greenfoot.isKeyDown("down")){
            List<List<Stone>> sortedStones = sortToDown(stoneList);
            moveStones(sortedStones,0,1);
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
        for (List<Stone> stoneList : stones) {
            for(int i = 0; i < stoneList.size()-1;i++){
                //check if two adjecent stones has the same value
                if(stoneList.get(i).getValue() == stoneList.get(i+1).getValue()){
                    //"connect the two stones" -> double the value of the first stone and remove the second stone
                    stoneList.get(i).doubleValue();
                    removeObject(stoneList.get(i+1));
                    stoneList.remove(i+1);
                }
                //move as far as possible
                while(!stoneList.get(i).isAtBorder(directionX,directionY)){
                    //TODO: at edge only in one direction
                    //TODO: better logic?
                    if(i>0){
                        if(stoneList.get(i).checkIntersection(stoneList.get(i-1))){
                            break;
                        }else{
                            stoneList.get(i).setLocation(stoneList.get(i).getX()+directionX,stoneList.get(i).getY()+directionY);
                        }
                    }else{
                        stoneList.get(i).setLocation(stoneList.get(i).getX()+directionX,stoneList.get(i).getY()+directionY);
                    }
                }
            }
        }
    }
}

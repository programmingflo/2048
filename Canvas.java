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
        }else if(Greenfoot.isKeyDown("left")){
            List<List<Stone>> sortedStones = sortToLeft(stoneList);
        }else if(Greenfoot.isKeyDown("up")){
            List<List<Stone>> sortedStones = sortToUp(stoneList);
        }else if(Greenfoot.isKeyDown("down")){
            List<List<Stone>> sortedStones = sortToDown(stoneList);
        }

        //test output
        for (Stone stone : sortedStones.get(1)) {
            System.out.println("output of sorted stones");
            System.out.print(stone.getX());
            System.out.println(stone.getY());
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
    }

    List<List<Stone>> sortTopDown(List<Stone> stones){
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
}

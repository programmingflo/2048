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
        addObject(new Stone(),1,2);
        addObject(new Stone(),2,1);
    }

    public void act(){
        sortByPosition();


    }

    public List<List<Stone>> sortByPosition(){
        List<Stone> stones = getObjects(Stone.class);
        List<List<Stone>> output = new ArrayList<List<Stone>>();
        for (int i = 0; i < 4; i++) {
            output.add(new ArrayList<Stone>());
        }


        if(Greenfoot.isKeyDown("right")){
          //sort by line
            for(Stone stone: stones){
                //System.out.println("stone");
                output.get(stone.getY()).add(stone);
            }
            //sort in line
            for (int i = 0; i < output.size(); i++) {
                for (int j = 1; j < output.get(i).size() && output.get(i).size() != 1; j++) {
                    //System.out.println("test");
                    if(output.get(i).get(j).getX() < output.get(i).get(j-1).getX()){
                        //System.out.println("test");
                        //swap
                        Stone temp = output.get(i).get(j-1);
                        output.get(i).set(j-1,output.get(i).get(j));
                        output.get(i).set(j,output.get(i).get(j-1));
                    }
                }
            }

            //test output
            for (Stone stone : output.get(2)) {
              System.out.println("test");
                System.out.print(stone.getX());
                System.out.println(stone.getY());
            }
        }else if(Greenfoot.isKeyDown("left")){
            for(Stone stone: stones){
                output.get(stone.getY()).add(stone);
            }
        }/*else if(Greenfoot.isKeyDown("up")){
            for(Stone stone: stones){
                output[stone.getX()].add(stone);
            }
        }else if(Greenfoot.isKeyDown("down")){
            for(Stone stone: stones){
                output[stone.getX()].add(stone);
            }
        }*/

        return output;
    }
}

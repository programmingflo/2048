/**
 * Write a description of class Score here.
 *
 * @author Florian Mansfeld and Georg Roemmling
 * @version 0.1
 */
class Score
{
    private int score;
    private String player;

    Score(){
        score = 0;
    }

    int getScore(){
        return this.score;
    }

    void addToScore(int summand){
        this.score += summand;
    }

    void gameOver(){
        System.out.println("Dein Score beträgt " + this.score);
    }
}

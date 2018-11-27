/**
 * Tracks the score the player has reached by combining Stones
 *
 * @author Florian Mansfeld & Georg Roemmling
 *
 * @version 1.0
 *
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
}

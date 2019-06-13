/*
 * This class represents a single player
 */

public class Person {
    private String name;
    private int round1_score;
    private int round2_score;
    private boolean free_turn;

    // initializer to use at the beginning of the game
    Person(String name) {
        this.name = name;
        this.round1_score = 0;
        this.round2_score = 0;
        this.free_turn = false;
    }

    /**
     * Adds a score value to the round one score
     *
     * @param score the score to add
     */
    public void inc_round1_score(int score) {
        this.round1_score += score;
    }

    /**
     * Adds a score value to the round two score
     *
     * @param score the score to add
     */
    public void inc_round2_score(int score) {
        this.round2_score += score;
    }

    /**
     * Tally up the player's current total score
     *
     * @return the total score
     */
    public int calcTotalScore() {
        return round1_score + round2_score;
    }

    /**
     * Gets the name of the player
     *
     * @return the name of the player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the first round score of the player
     *
     * @return the first round score
     */
    public int getRound1_score() {
        return this.round1_score;
    }

    /**
     * Gets the second round score of the player
     *
     * @return the second round score
     */
    public int getRound2_score() {
        return this.round2_score;
    }

    /**
     * Checks whether the player has a free turn
     *
     * @return whether the player has a free turn
     */
    public boolean getFree_turn() {
        return this.free_turn;
    }

    /**
     * set whether the player has a free turn card
     *
     * @param free whether the player has a free turn card
     */
    public void setFree_turn(boolean free) {
        this.free_turn = free;
    }
}

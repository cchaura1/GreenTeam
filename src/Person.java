/*
 * This class represents a single player
 */

public class Person {
    private String name;
    private int round1_score;
    private int round2_score;
    private int free_turns;

    // initializer to use at the beginning of the game
    Person(String name) {
        this.name = name;
        this.round1_score = 0;
        this.round2_score = 0;
        this.free_turns = 0;
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
     * Check how many free turns the player has
     *
     * @return the number of free turns
     */
    public int getFree_turns() {
        return this.free_turns;
    }

    /**
     * set the number of free turns
     *
     * @param free the number of free turns
     */
    public void setFree_turns(int free) {
        this.free_turns = free;
    }

    /**
     * add one free turn to this person
     */
    public void addOneFreeTurn() {
        this.free_turns += 1;
    }
}

/*
 * This class represents a single tile on the jepordy board
 */

public class BoardTile {
    private int value;
    private String question;
    private String answer;

    // initializer to create one tile on the board
    BoardTile(int value, String question, String answer) {
        this.value = value;
        this.question = question;
        this.answer = answer;
    }

    /**
     * Debug function for displaying the values for a board tile
     *
     * @returns a string representation of a board tile
     */
    public String toString() {
        return ("Question: " + this.question +
                "\nAnswer: " + this.answer +
                "\nValue: " + Integer.toString(this.value));
    }

    /**
     * gets the value of a board tile
     *
     * @return the monetary value of the tile
     */
    public int getValue() {
        return this.value;
    }

    /**
     * gets the question of the board tile
     *
     * @return the question you would like to ask
     */
    public String getQuestion() {
        return this.question;
    }

    /**
     * gets the answer of the board tile
     *
     * @return the answer to the corresponding BoardTile question
     */
    public String getAnswer() {
        return this.answer;
    }
}

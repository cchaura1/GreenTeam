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
     * sets the value of a board tile
     *
     * @param value the monetary value of the tile
     */
    public void setValue(int value) {
        this.value = value;
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
     * sets the question of the board tile
     *
     * @param question the question you want to include in the game
     */
    public void setQuestion(String question) {
        this.question = question;
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
     * sets the answer of the board tile
     *
     * @param answer the answer to the corresponding BoardTile question
     */
    public void setAnswer(String answer) {
        this.answer = answer;
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

/*
 * This class represents the jepordy-style board of questions
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONPointerException;
import org.json.JSONTokener;
import java.util.*;


public class QuestionBoard {
    private HashMap board;
    private HashMap currentQuestionDict;

    // Initialize the board
    QuestionBoard(String board_location, Integer round) {

        /**
         * The board is represented as a dictionary
         * The key of the board dictionary is the category of that column
         * The value of the key is a list of 5 tiles that are in the category
         * Note that there will be 2 boards, one in round 1, one in round 2
         */
        this.board = new HashMap<String, ArrayList<BoardTile>>();

        /**
         * We also have to keep track of the current question for each category
         * Every time someone answers a question on the board they will move to the next question
         * We can represent this as a dictionary, the categories are keys, the question index is the value
         * index is 0-4 for a standard jepordy board
         */
        this.currentQuestionDict = new HashMap<String, Integer>();

        // set up the board
        load_board(board_location, round);

        // the initializer can be updated to read from a file instead of empty initializer
    }

    /**
     * Get the current question for a specified category
     *
     * @param questionCategory the category you want to ask a question
     *
     * @return the question
     */
    public String currentQuestion(String questionCategory) {
        int currentIndex = (Integer) this.currentQuestionDict.get(questionCategory);
        ArrayList<BoardTile> row = (ArrayList<BoardTile>) this.board.get(questionCategory);
        BoardTile tile = row.get(currentIndex);
        return tile.getQuestion();
    }

    /**
     * Get the answer for the question for a specified category
     *
     * @param answerCategory the category you need the answer for
     *
     * @return the answer
     */
    public String currentAnswer(String answerCategory) {
        int currentIndex = (Integer) this.currentQuestionDict.get(answerCategory);
        ArrayList<BoardTile> row = (ArrayList<BoardTile>) this.board.get(answerCategory);
        BoardTile tile = row.get(currentIndex);
        return tile.getAnswer();
    }

    /**
     * Get the value for the question for a specified category
     *
     * @param valueCategory the category you need the value for
     *
     * @return the value
     */
    public Integer currentQuestionValue(String valueCategory) {
        int currentIndex = (Integer) this.currentQuestionDict.get(valueCategory);
        ArrayList<BoardTile> row = (ArrayList<BoardTile>) this.board.get(valueCategory);
        BoardTile tile = row.get(currentIndex);
        return tile.getValue();
    }

    /**
     * Get the BoardTile object for the question for a specified category
     *
     * @param category the category you need the value for
     *
     * @return the BoardTile
     */
    public BoardTile currentTile(String category) {
        int currentIndex = (Integer) this.currentQuestionDict.get(category);
        ArrayList<BoardTile> row = (ArrayList<BoardTile>) this.board.get(category);
        BoardTile tile = row.get(currentIndex);
        return tile;
    }

    /**
     * increment the current question dictionary to the next question for
     * the given category
     *
     * @param category the category to increment
     */
    public void incrementCategotry(String category) {
        Integer currentIndex = (Integer) this.currentQuestionDict.get(category);
        Integer nextIndex = currentIndex + 1;
        this.currentQuestionDict.replace(category, nextIndex);
    }

    /**
     * get the categories for the board
     *
     * @return the list of categories
     */
    public List<String> getAllCategories() {
        ArrayList<String> categories = new ArrayList<String>();

        // add all the categories for the board
        for(String key: this.getBoard().keySet()) {
            categories.add(key);
        }

        return categories;
    }

    /**
     * Load the board
     *
     * ******* This is just an example, we should load from a file **********
     * Feel very free to update the questions...
     * 
     */
    public void load_board(String fname, Integer roundNum) {

        try {
            JSONTokener tokener = new JSONTokener(new FileReader(fname));
            JSONObject result = new JSONObject(tokener);

            JSONObject round;
            if (roundNum == 1) {
                round = (JSONObject) result.get("round1");
            }
            else {
                round = (JSONObject) result.get("round2");
            }

            JSONArray categories = (JSONArray) round.get("categories");

            for (Object c : categories) {
                ArrayList<BoardTile> board_column = new ArrayList<BoardTile>();

                JSONObject category = (JSONObject) c;
                String categoryName = (String) category.get("name");

                JSONArray questions = (JSONArray) category.get("questions");

                for (Object q: questions){
                    JSONObject questionObj = (JSONObject) q;

                    BoardTile tile = new BoardTile((Integer) questionObj.get("value"),
                            (String) questionObj.get("question"),
                            (String) questionObj.get("answer"));
                    board_column.add(tile);
                }

                this.board.put(categoryName, board_column);
                this.currentQuestionDict.put(categoryName, 0);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONPointerException e) {
            e.printStackTrace();
        }
    }

    /**
     * get the board
     *
     * @return the board
     */
    public HashMap<String, List<BoardTile>> getBoard() {
        return this.board;
    }

    /**
     * get the current question dict
     *
     * @return the current question dict
     */
    public HashMap<String, Integer> getCurrentQuestionDict() {
        return this.currentQuestionDict;
    }

    /**
     * get the the contents of the board as a readable string
     *
     * @returns a string representation of the board
     */
    public String toString() {

        String boardString = "";

        // iterate over each category
        for (Object key : this.board.keySet()) {
            boardString += ("-----------------------------------\n");
            boardString += ("Category: \n" + key.toString() + "\n");

            boardString += ("Current index is: " +
                    Integer.valueOf(this.getCurrentQuestionDict().get(key))) + "\n";

            // get the question column for the given category
            List<BoardTile> column = new ArrayList<BoardTile>();
            column = this.getBoard().get(key);

            // for each question in the category display the board tile
            for (BoardTile tile : column) {
                boardString += (tile.toString() + "\n");
            }
        }
        boardString += ("-----------------------------------\n");
        return boardString;
    }
}

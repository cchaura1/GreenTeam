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
import java.util.*;


public class QuestionBoard {
    private HashMap board;
    private HashMap currentQuestionDict;

    // Initialize the board
    QuestionBoard(String board_location) {

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
        load_board(board_location);

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
    public void load_board(String fname) {

        // working code (not reading from a file though)

        // first create 5 board tiles (to make up a single category)
        BoardTile tile1 = new BoardTile(200,
                "What is the seventh planet in the solar system?",
                "Saturn");
        BoardTile tile2 = new BoardTile(400,
                "When will Halleys Comet return to Earth?",
                "2016");
        BoardTile tile3 = new BoardTile(600,
                "How much does a full NASA spacesuit cost (approximately)?",
                "$12,000,000");
        BoardTile tile4 = new BoardTile(800,
                "What was the number of the last Apollo mission?",
                "17");
        BoardTile tile5 = new BoardTile(1000,
                "How many known moons orbit Neptune?",
                "14");

        // next put all the tiles into a list
        ArrayList<BoardTile> board_column1 = new ArrayList<BoardTile>();
        board_column1.add(tile1);
        board_column1.add(tile2);
        board_column1.add(tile3);
        board_column1.add(tile4);
        board_column1.add(tile5);

        // then put the list in the dictionary
        this.board.put("Space", board_column1);

        // and initialize the currentQuestionDict for this category to 0
        this.currentQuestionDict.put("Space", 0);

        // and do everything again: (probably best to loop)
        BoardTile tile6 = new BoardTile(200,
                "Which NHL player has the most career points?",
                "Wayne Gretzky");
        BoardTile tile7 = new BoardTile(400,
                "Who is the winningest NHL goalie of all time",
                "Martain Brodeur");
        BoardTile tile8 = new BoardTile(600,
                "What is the term for a goal, an assist, and a fight in one game?",
                "Gordie Howe Hat Trick");
        BoardTile tile9 = new BoardTile(800,
                "Who shot the fastest puck in recorded history?",
                "Bobby Hull (rec. 118.3 mph)");
        BoardTile tile10 = new BoardTile(1000,
                "Which NHL player holds the record for the most points in a single game?",
                "Darryl Sittler");

        ArrayList<BoardTile> board_column2 = new ArrayList<BoardTile>();
        board_column2.add(tile6);
        board_column2.add(tile7);
        board_column2.add(tile8);
        board_column2.add(tile9);
        board_column2.add(tile10);

        this.board.put("Hockey", board_column2);

        this.currentQuestionDict.put("Hockey", 0);

        // do a few more times (6) to make a full board
        // can wrap into a loop if you have a properly formatted file



        // Below is Chetan's code

//        ArrayList<BoardTile> board_column1 = new ArrayList<BoardTile>();
//        int categoryIndex = 0;
//    	String line1;
//
//        	try {
//                BufferedReader reader1 = new BufferedReader(new FileReader(
//                        "src/board.txt"));
//				while ((line1 = reader1.readLine()) != null ) {
//					  if(line1 == null || line1.isEmpty()) {
//						  	line1 = reader1.readLine();
//							String title = line1;
//							line1 = reader1.readLine();
//							this.currentQuestionDict.put(title, categoryIndex);
//							// then put the list in the dictionary
//					        this.board.put(title, board_column1);
//					        categoryIndex ++;
//					  }
//
//					  String [] d = line1.split(":");
//					  board_column1.add(new BoardTile(Integer.parseInt(d[0]),d[1],d[2]));
//				}
//
//			} catch (IOException e) {
//				e.printStackTrace();
//			}

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

/*
 * This class represents the jepordy-style board of questions
 */

import java.util.*;

public class QuestionBoard {
    private HashMap board;

    // Initialize the board
    QuestionBoard() {

        /**
         * The board is represented as a dictionary
         * The key of the board dictionary is the category of that column
         * The value of the key is a list of 5 tiles that are in the category
         * Note that there will be 2 boards, one in round 1, one in round 2
         */
        this.board = new HashMap<String, ArrayList<BoardTile>>();

        // the initializer can be updated to read from a file instead of empty initializer
    }

    /**
     * Load the board
     *
     * ******* This is just an example, we should load from a file **********
     * Feel very free to update the questions...
     */
    public void load_board() {
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

        // and do everything again:
        BoardTile tile6 = new BoardTile(200,
                "Which NHL player has the most career points?",
                "Wayne Gretzky");
        BoardTile tile7 = new BoardTile(400,
                "Who is the winningest NHL goalie ov all time",
                "Martain Brodeur");
        BoardTile tile8 = new BoardTile(600,
                "What is the term for a goal, an assist, and a fight in one game?",
                "Gordie Howe Hat Trick");
        BoardTile tile9 = new BoardTile(800,
                "Who shot the fastest puck in recorded history?",
                "Bobby Hull");
        BoardTile tile10 = new BoardTile(1000,
                "Which NHL player holds the record for the most points in a single game?",
                "Darryl Sittler");

        ArrayList<BoardTile> board_column2 = new ArrayList<BoardTile>();
        board_column1.add(tile6);
        board_column1.add(tile7);
        board_column1.add(tile8);
        board_column1.add(tile9);
        board_column1.add(tile10);

        this.board.put("Hockey", board_column2);

        // do a few more times to make a full board
        // can wrap into a loop if you have a properly formatted file
    }

    /**
     * get the board
     *
     * @return the board
     */
    public HashMap<String, List<BoardTile>> getBoard() {
        return this.board;
    }
}

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
	private BufferedReader reader1;
	private BufferedReader reader2;

    // Initialize the board
    QuestionBoard() {

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
        load_board();

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
     * Load the board
     *
     * ******* This is just an example, we should load from a file **********
     * Feel very free to update the questions...
     * 
     */
    public void load_board() {

        // next put all the tiles into a list
        ArrayList<BoardTile> board_column1 = new ArrayList<BoardTile>();
        int categoryIndex = 0;
    	String line1;
    	
        	try {
            	URL path1 = Paths.get(System.getProperty("user.dir")+"/board.txt").toUri().toURL();		
                	File file1 = new File(path1.getFile());
        		reader1 = new BufferedReader(new FileReader(file1));
				while ((line1 = reader1.readLine()) != null ) {
					  if(line1 == null || line1.isEmpty()) {
						  	line1 = reader1.readLine();
							String title = line1;		
							line1 = reader1.readLine();
							this.currentQuestionDict.put(title, categoryIndex);
							// then put the list in the dictionary
					        this.board.put(title, board_column1);
					        categoryIndex ++;
					  }
					
		 				String [] d = line1.split(":");
		 				board_column1.add(new BoardTile(Integer.parseInt(d[0]),d[1],d[2]));			
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

        // do a few more times (6) to make a full board
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

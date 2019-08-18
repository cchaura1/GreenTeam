/*
 * This is the main class, runs the game
 */


import javafx.application.Application;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Pair;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WheelOfJepordyMain extends Application {

    // this is the view
    // when we want to switch to Chetan's GUI we can just hot swap the views
    // this will require Chetan to have all of the methods that my view has
    static TextView view = new TextView();

    // create the people that are playing
    static List<Person> people = view.createPeople();
    static List<Text> mycategories = new ArrayList<Text>();
    public static void main(String args[]) {


    	
        // initialize the game
        view.initialize();

        // initialize the game
        boolean continue_game = true;
        Person current_player = people.get(0);
        int round = 1;
        boolean use_free_turn = false;

        // create the board and wheel for round 1
        QuestionBoard board = new QuestionBoard("board1.json", round);
        Wheel wheel = new Wheel(board);
        
        //***********Initiating GUI**********************
        for(String key: wheel.getSectors()) {
        	Text t = new Text();
        	t.setText(key);
            mycategories.add(t);
        }
        WheelGui ob = new WheelGui(mycategories);
    	ob.launch(args);
    	//***********************************************
    	
    	
        // debug to make sure the board looks good
        //System.out.println("Board: \n" + board.toString());

        // game loop
        while (continue_game) {

            // update the view
            view.updatePeople(people);
            view.indicateRound(round);
            view.updateCurrentPlayer(current_player);
            view.updateWheelCounter(wheel);

            // the current player spins the wheel
            String spun_category = wheel.spinWheel(board);

            // the view shows the result of spinning the wheel
            view.spinWheel(spun_category);

            // take action based on the category you spin
            if (spun_category.equals("Lose Turn")) {
                use_free_turn = loseTurn(current_player);
            }

            else if (spun_category.equals("Free Turn")) {
                giveFreeTurn(current_player);
            }

            else if (spun_category.equals("Bankrupt")) {
                bankruptPlayer(current_player, round);
            }

            else if (spun_category.equals("Double your Score")) {
                doubleScore(current_player, round);
            }

            else if (spun_category.equals("Player's Choice")) {
                view.indicatePlayersChoice();

//                System.out.println("1. " + board.toString());

                String selected_category  = view.getSelectedCategory(board);
                BoardTile tile = board.currentTile(selected_category);

//                System.out.println("2. " + board.toString());

                // ask the player hte question and track score
                String was_correct = view.askQuestion(tile);

                // if the answer was correct add the score to the player
                if (was_correct.equals("y")) {
                    addScoreToPlayer(tile.getValue(), current_player, round);
                }
                // if the answer was wrong subtract the score from the player
                else if (was_correct.equals("n")) {
                    addScoreToPlayer(-1 * tile.getValue(), current_player, round);
                }
                // if the answer was p do not add any score

                // move to the next question in the category
                board.incrementCategotry(selected_category);
                view.updatePeople(people);
                view.updateBoard(board);

//                System.out.println("4. " + board.toString());
            }

            else if (spun_category.equals("Opponent's Choice")) {
                view.indicateOpponentsChoice();

                String selectedCategory  = view.getSelectedCategory(board);
                BoardTile tile = board.currentTile(selectedCategory);

                // ask the player hte question and track score
                String was_correct = view.askQuestion(tile);
                // if the answer was correct add the score to the player
                if (was_correct.equals("y")) {
                    addScoreToPlayer(tile.getValue(), current_player, round);
                }
                // if the answer was wrong subtract the score from the player
                else if (was_correct.equals("n")) {
                    addScoreToPlayer(-1 * tile.getValue(), current_player, round);
                }
                // if the answer was p do not add any score

                // move to the next question in the category
                board.incrementCategotry(selectedCategory);
                view.updatePeople(people);
                view.updateBoard(board);
            }

            // check if the spun category is a jeopardy question
            else if (wheel.getSectors().contains(spun_category)) {
                BoardTile tile = board.currentTile(spun_category);

                // ask the player hte question and track score
                String was_correct = view.askQuestion(tile);
                // if the answer was correct add the score to the player
                if (was_correct.equals("y")) {
                    addScoreToPlayer(tile.getValue(), current_player, round);
                }
                // if the answer was wrong subtract the score from the player
                else if (was_correct.equals("n")) {
                    addScoreToPlayer(-1 * tile.getValue(), current_player, round);
                }
                // if the answer was p do not add any score

                // move to the next question in the category
                board.incrementCategotry(spun_category);
                view.updatePeople(people);
                view.updateBoard(board);

            }

            else {
                System.out.println("ERROR: This is not a category");
            }

            // turn is over, move to next player if there is no token used
            if (!use_free_turn) {
                current_player = getNextPlayer(current_player);
            }

            // end of turn cleanup
            use_free_turn = false;

            // check if we should continue the round
            if (isRoundOver(board, wheel)) {
                round += 1;
                board = new QuestionBoard("src/board1.json", round);
                wheel = new Wheel(board);
            }

            // check if we should continue the game
            if (round > 2) {
                continue_game = false;
            }

            view.goToNextTurn();
        }

        // handle the end of the game
        Person winner = getWinner();
        view.endGame(winner);

    }

    /**
     * Check if the round is over
     *
     * @param board the board for the game
     * @param wheel the wheel for the game
     *
     * @return if the round is over
     */
    public static boolean isRoundOver(QuestionBoard board, Wheel wheel) {
        boolean is_over_spins = true;
        boolean is_over_categories = true;
        boolean is_over = false;

        // check if there are spins remaining in the wheel
        if (wheel.getSpinsRemaining() > 0) {
            is_over_spins = false;
        }

        // check if any of the categories have tiles remaining
        for (String category: board.getAllCategories()) {
            if (board.getCurrentQuestionDict().get(category) <= 4) {
                is_over_categories = false;
                // Print for error checking only
                //System.out.println("NOT OVER B/C " + category + " is not empty");
            }
        }

        // if either the wheel is out of spins or the board is empty: round over
        if (is_over_spins || is_over_categories) {
            is_over = true;
        }

        // indicate if the round is over
        return is_over;
    }

    /**
     * Gets the player that goes after the current player
     * If the current player is tha last player, wrap back to the first player
     *
     * @return the next player
     */
    public static Person getNextPlayer(Person current_player) {

        // initialize the index for the next player
        int next_index = 0;

        // get index of current player. The index of the next player is this + 1
        for (int index = 0; index < people.size(); index++) {
            if (people.get(index) == current_player) {
                next_index = index + 1;
            }
        }

        // make sure we dont index out of bounds
        if (next_index > people.size() - 1) {
            next_index = 0;
        }

        Person next_player = people.get(next_index);
        return next_player;
    }

    /**
     * helper function for when a player loses a turn
     *
     * @param current_player the player whose turn it is
     *
     * @return whether the player uses a free turn token
     */
    public static boolean loseTurn(Person current_player) {

        boolean use_token = false;

        // check if the player has a token
        if (current_player.getFree_turns() > 0) {
            use_token = view.checkUseFreeTurn();

            // track that the player used their free turn
            if (use_token) {
                current_player.useOneFreeTurn();
            }
        }

        return use_token;
    }

    /**
     * add a free turn to the current player
     *
     * @param current_player the current player
     *
     * @return
     */
    public static void giveFreeTurn(Person current_player) {

        // iterate over the people in the game and give free turn to current player
        for (int index = 0; index < people.size(); index++) {
            if (people.get(index) == current_player) {
                people.get(index).addOneFreeTurn();
            }
        }
    }

    /**
     * Return the round score of the player to 0
     *
     * @param current_player the player to bankrupt
     * @param round the current round
     */
    public static void bankruptPlayer(Person current_player, int round) {

        // iterate over the people in the game and bankrupt the player for this round
        for (int index = 0; index < people.size(); index++) {
            if (people.get(index) == current_player) {
                if (round == 1) {
                    people.get(index).bankrupt_round1_score();
                }
                else {
                    people.get(index).bankrupt_round2_score();
                }
            }
        }
    }


    /**
     * Double the score for the current player in the current round
     *
     * @param current_player the player whose score to double
     * @param round the round for which score to double
     */
    public static void doubleScore(Person current_player, int round) {

        // iterate over the people in the game and double the score for the player for this round
        for (int index = 0; index < people.size(); index++) {
            if (people.get(index) == current_player) {
                if (round == 1) {
                    people.get(index).double_round1_score();
                }
                else {
                    people.get(index).double_round2_score();
                }
            }
        }
    }

    /**
     * Add given score to given player for given round
     *
     * @param score the score to add
     * @param player the player to add the score to
     * @param round the round for which to add the score
     */
    public static void addScoreToPlayer(int score, Person player, int round) {

        // iterate over the people in the game and add the score to the round
        for (int index = 0; index < people.size(); index++) {
            if (people.get(index) == player) {
                if (round == 1) {
                    people.get(index).inc_round1_score(score);
                }
                else {
                    people.get(index).inc_round2_score(score);
                }
            }
        }
    }

    /**
     * function to determine the winner of the game
     *
     * @return the winner
     */
    public static Person getWinner() {
        // some variables to help track who is the winner
        int highest = 0;
        int highest_index = 0;
        int current_score = 0;

        // iterate over each person to find the winner
        for (int i = 0; i < people.size(); i++ ) {
            current_score = people.get(i).calcTotalScore();

            // check if the player has a higher score than the highest
            if (current_score > highest) {
                highest = current_score;
                highest_index = i;
            }
        }

        // declare the winner
        Person winner = people.get(highest_index);

        return winner;
    }

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		WheelGui obj = new WheelGui(mycategories);
		obj.start(primaryStage);
	}
}

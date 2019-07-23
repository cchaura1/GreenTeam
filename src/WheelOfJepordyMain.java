/*
 * This is the main class, runs the game
 */

import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class WheelOfJepordyMain {

    // this is the view
    // when we want to switch to Chetan's GUI we can just hot swap the views
    // this will require Chetan to have all of the methods that my view has
    static TextView view = new TextView();

    // create the people that are playing
    static List<Person> people = view.createPeople();

    public static void main(String args[]) {

        // initialize the game
        view.initialize();

        // initialize the game
        boolean continue_game = true;
        Person current_player = people.get(0);
        int round = 1;
        boolean use_free_turn = false;

        // create the board and wheel for round 1
        QuestionBoard board = new QuestionBoard();
        Wheel wheel = new Wheel(board);

        // debug to make sure the board looks good
        System.out.println("Board: \n" + board.toString());


        // game loop
        while (continue_game) {

            // update the view
            view.updatePeople(people);
            view.indicateRound(round);
            view.updateCurrentPlayer(current_player);
            view.updateWheelCounter(wheel);

            // the current player spins the wheel
            String spun_category = wheel.spinWheel();

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
                boolean was_correct = view.askQuestion(tile);
                if (was_correct) {
                    addScoreToPlayer(tile.getValue(), current_player, round);
                }

//                System.out.println("3. " + board.toString());

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
                boolean was_correct = view.askQuestion(tile);
                if (was_correct) {
                    addScoreToPlayer(tile.getValue(), current_player, round);
                }

                // move to the next question in the category
                board.incrementCategotry(selectedCategory);
                view.updatePeople(people);
                view.updateBoard(board);
            }

            // check if the spun category is a jeopardy question
            else if (wheel.getSectors().contains(spun_category)) {
                BoardTile tile = board.currentTile(spun_category);

                // ask the player hte question and track score
                boolean was_correct = view.askQuestion(tile);
                if (was_correct) {
                    addScoreToPlayer(tile.getValue(), current_player, round);
                }

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
            if (wheel.getSpinsRemaining() <= 0) {
                round += 1;
                board = new QuestionBoard();
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


//        // demo code...
//
//        // we can create some Person objects like so:
////        Person Chetan = new Person("Chetan");
////        Person Mike = new Person("Mike");
////        Person Paul = new Person("Paul");
////        Person Brian = new Person("Brian");
////
////        System.out.println("Person - Chetan: \n" + Chetan.toString());
////        System.out.println("Person - Mike: \n" + Mike.toString());
////        System.out.println("Person - Paul: \n" + Paul.toString());
////        System.out.println("Person - Brian: \n" + Brian.toString());
//
//
//        // this is how you create the board
////        QuestionBoard QuestionBoard = new QuestionBoard();
//
//        // show the board contents
//        System.out.println(QuestionBoard.toString());
//
//        // one way to access values from the board
////        String question1 = QuestionBoard.getBoard().get("Space").get(0).getQuestion();
////        System.out.println(question1);
////        String answer1 = QuestionBoard.getBoard().get("Space").get(0).getAnswer();
////        System.out.println(answer1);
////
////        String question2 = QuestionBoard.getBoard().get("Hockey").get(3).getQuestion();
////        System.out.println(question2);
////
////        String answer2 = QuestionBoard.getBoard().get("Hockey").get(3).getAnswer();
////        System.out.println(answer2);
//
//        // to access the current question for a category
//        // note that the current question should iterate as players answer questions
//        // to start the current question is the first question
//        System.out.println(QuestionBoard.currentQuestion("Hockey"));
//
//        // now get the answer for the category
//        System.out.println(QuestionBoard.currentAnswer("Hockey"));
//
//        // then increment to the next question
//        QuestionBoard.incrementCategotry("Hockey");
//
//        // And now we can access the next question:
//        System.out.println(QuestionBoard.currentQuestion("Hockey"));
//        System.out.println(QuestionBoard.currentAnswer("Hockey"));
//        System.out.println(QuestionBoard.currentQuestionValue("Hockey"));
//        System.out.println(QuestionBoard.currentTile("Hockey"));
//        QuestionBoard.incrementCategotry("Hockey");
//
//
//        // this is how you create the wheel
//        Wheel wheel = new Wheel(QuestionBoard);
//
//        // and this is what the wheel looks like
//        wheel.printWheel();
//
//        // and this is how you spin the wheel to get a random sector
//        System.out.println("The sector is: " + wheel.spinWheel());


        // you can use the output of the wheel to access the questionBoard,
        // or to access game logic for sectors like Lose Turn or Bankrupt
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
}

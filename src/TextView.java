// This class is just a text based view for our minimal increment

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class TextView {
	
	public static List<Person> people = new ArrayList<Person>();

    TextView() {
        // do nothing
    }

    public static void initialize() {
        System.out.println("Welcome to Wheel of Jeopardy, a combination game");
        System.out.println("of the televised games Wheel of Fortune and Jeopardy.");
        System.out.println("We do hope that you have fun playing!\n\n");
    }

    // some gui function to create the people playing
    public static List<Person> createPeople() {
        return people;
    }

    // specifying what player is playing would be good for the GUI
    // Even if we just make their name bold, something
    public static void updateCurrentPlayer(Person player) {
        System.out.println("\n\nThe current player is: " + player.getName());
    }

    // show the result of spinning the wheel
    public static void spinWheel(String result) {
        System.out.println("The category that was spun is: " + result);
    }

    // check with the current player if they want to use their free turn
    public static boolean checkUseFreeTurn() {
        boolean use_turn = true;

        Scanner input = new Scanner(System. in);
        System.out.print("Would you like to use a free turn? (y or n): ");
        String use_turn_string = input.nextLine();

        if (!use_turn_string.equals("y")) {
            use_turn = false;
        }

        return use_turn;
    }

    public static void updatePeople(List<Person> people) {
        for (Person person: people) {
            System.out.println(person.toString());
        }
    }

    public static String askQuestion(BoardTile tile) {
        Scanner input = new Scanner(System .in);
        String is_correct = "";

        System.out.println("For " + String.valueOf(tile.getValue()) + " points.. ");
        System.out.println(tile.getQuestion());
        System.out.print("\nPress ENTER when the question has been answered: ");

        String nothing = input.nextLine();

        while (is_correct == "") {
            System.out.println("The correct answer is: " + tile.getAnswer());
            System.out.print("\nWas the answer correct? (y=yes, n=no, p=pass): ");
            String is_correct_string = input.next();

            if (is_correct_string.equals("y") || is_correct_string.equals("n")
                || is_correct_string.equals("p")) {
                is_correct = is_correct_string;
            }
            else {
                is_correct = "";
            }
        }

        return is_correct;

    }

    public static void updateBoard(QuestionBoard board) {
        // do nothing for text based, would like to gray out when we do the GUI
    }

    public static void updateWheelCounter(Wheel wheel) {
        System.out.println("There are " + String.valueOf(wheel.getSpinsRemaining()) +
        " spins remaining in the round");
    }

    public static void indicateRound(int round) {
        System.out.println("Round " + String.valueOf(round));
    }

    public static void goToNextTurn() {
        Scanner input = new Scanner(System .in);
        System.out.println("\nPress ENTER to go to the next turn: " );
        String nothing = input.nextLine();
    }

    public static void indicatePlayersChoice() {
        System.out.println("\nThe current player will choose a category to answer");
    }

    public static void indicateOpponentsChoice() {
        System.out.println("\nThe opposing players will choose a category for the " +
                "\ncurrent player to answer");
    }

    public static String getSelectedCategory(QuestionBoard board) {
        Scanner input = new Scanner(System .in);
        List<String> categories = board.getAllCategories();
        String selected_category = "";

        // track if the user inputs an invalid category
        boolean invalid_category = true;

        // make sure the user inputs a valid category
        while (invalid_category) {
            System.out.println("Select which category to answer from the following list: ");
            for (String cateogry : categories) {
                System.out.println("    " + cateogry);
            }

            // get the user input for the selected category
            selected_category = input.nextLine();

            // check if the category is valid
            if (categories.contains(selected_category)) {
                invalid_category = false;
            } else {
                System.out.println("This category is invalid, please try again (CaSe SeNsItIvE)\n");
            }
        }

        // return the selected category
        return selected_category;
    }

    public static void endGame(Person winner) {
        System.out.println("The winner of Wheel of Jeopardy is: " + winner.getName());
        System.out.println("Thank you for playing!");
    }
}

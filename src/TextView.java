// This class is just a text based view for our minimal increment

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TextView {

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

        List<Person> people = new ArrayList<Person>();
//        boolean add_more_people = true;
//
//        Scanner input = new Scanner(System. in);
//        while (add_more_people) {
//            System.out.print("Enter the name of the next Person (or 'done' if done): ");
//            String name = input.nextLine();
//
//            if (!name.equals("done")) {
//                Person person = new Person(name);
//                people.add(person);
//            }
//            else {
//                add_more_people = false;
//            }
//        }

        // uncomment the previous lines for a more interactive experience
        Person Brian = new Person("Brian");
        Person Jerry = new Person("Jerry");
        people.add(Brian);
        people.add(Jerry);

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

    public static boolean askQuestion(BoardTile tile) {
        Scanner input = new Scanner(System .in);
        boolean is_correct = false;

        System.out.println("For " + String.valueOf(tile.getValue()) + " points.. ");
        System.out.println(tile.getQuestion());
        System.out.print("\nPress ENTER when the question has been answered: ");
        String nothing = input.nextLine();
        System.out.println("The correct answer is: " + tile.getAnswer());
        System.out.print("\nWas the answer correct? (y or n): ");
        String is_correct_string = input.next();

        if (is_correct_string.equals("y")) {
            is_correct = true;
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

    public static void endGame(Person winner) {
        System.out.println("The winner of Wheel of Jeopardy is: " + winner.getName());
        System.out.println("Thank you for playing!");
    }
}

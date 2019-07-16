/*
 * This is the main class, runs the game
 */

public class WheelOfJepordyMain {

    public static void main(String args[]) {

        // demo code...

        // we can create some Person objects like so:
        Person Chetan = new Person("Chetan");
        Person Mike = new Person("Mike");
        Person Paul = new Person("Paul");
        Person Brian = new Person("Brian");

        System.out.println("Person - Chetan: \n" + Chetan.toString());
        System.out.println("Person - Mike: \n" + Mike.toString());
        System.out.println("Person - Paul: \n" + Paul.toString());
        System.out.println("Person - Brian: \n" + Brian.toString());


        // this is how you create the board
        QuestionBoard QuestionBoard = new QuestionBoard();

        // show the board contents
        System.out.println(QuestionBoard.toString());

        // one way to access values from the board
//        String question1 = QuestionBoard.getBoard().get("Space").get(0).getQuestion();
//        System.out.println(question1);
//        String answer1 = QuestionBoard.getBoard().get("Space").get(0).getAnswer();
//        System.out.println(answer1);
//
//        String question2 = QuestionBoard.getBoard().get("Hockey").get(3).getQuestion();
//        System.out.println(question2);
//
//        String answer2 = QuestionBoard.getBoard().get("Hockey").get(3).getAnswer();
//        System.out.println(answer2);

        // to access the current question for a category
        // note that the current question should iterate as players answer questions
        // to start the current question is the first question
        System.out.println(QuestionBoard.currentQuestion("Hockey"));

        // now get the answer for the category
        System.out.println(QuestionBoard.currentAnswer("Hockey"));

        // then increment to the next question
        QuestionBoard.incrementCategotry("Hockey");

        // And now we can access the next question:
        System.out.println(QuestionBoard.currentQuestion("Hockey"));
        System.out.println(QuestionBoard.currentAnswer("Hockey"));
        System.out.println(QuestionBoard.currentQuestionValue("Hockey"));
        System.out.println(QuestionBoard.currentTile("Hockey"));
        QuestionBoard.incrementCategotry("Hockey");


        // this is how you create the wheel
        Wheel wheel = new Wheel(QuestionBoard);

        // and this is what the wheel looks like
        wheel.printWheel();

        // and this is how you spin the wheel to get a random sector
        System.out.println("The sector is: " + wheel.spinWheel());


        // you can use the output of the wheel to access the questionBoard,
        // or to access game logic for sectors like Lose Turn or Bankrupt
    }
}

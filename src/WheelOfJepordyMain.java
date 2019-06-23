/*
 * This is the main class, runs the game
 */

public class WheelOfJepordyMain {

    public static void main(String args[]) {

        // demo code...


        // this is how you create the board
        QuestionBoard QuestionBoard = new QuestionBoard();

        // how to access values from the board
        String question1 = QuestionBoard.getBoard().get("Space").get(0).getQuestion();
        System.out.println(question1);
        String answer1 = QuestionBoard.getBoard().get("Space").get(0).getAnswer();
        System.out.println(answer1);

        String question2 = QuestionBoard.getBoard().get("Hockey").get(3).getQuestion();
        System.out.println(question2);
        
        String answer2 = QuestionBoard.getBoard().get("Hockey").get(3).getAnswer();
        System.out.println(answer2);

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
        wheel.printWheel();

        // and this is how you spin the wheel to get a random sector
        System.out.println("The sector is: " + wheel.spinWheel());
    }
}

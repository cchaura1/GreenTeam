import java.net.Authenticator;

public class WheelOfJepordyMain {

    public static void main(String args[]) {
        // this is how you access the board
        QuestionBoard QuestionBoard = new QuestionBoard();
        QuestionBoard.load_board();

        // just how to access values from the board
        String question = QuestionBoard.getBoard().get("Space").get(0).getQuestion();
        System.out.println(question);

        String answer = QuestionBoard.getBoard().get("Space").get(3).getAnswer();
        System.out.println(answer);


    }
}

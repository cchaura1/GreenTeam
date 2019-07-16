 /*
  * This class is the wheel that you spin on your turn
  */

 import java.util.ArrayList;
 import java.util.List;
 import java.util.Random;

 public class Wheel {
     private List<String> sectors;
     private int spinsRemaining;

     // initializer for the wheel
     Wheel(QuestionBoard board) {
         this.sectors = new ArrayList<String>();

         // add each of the question categories to the wheel
         for(String category: board.getBoard().keySet()) {
             System.out.println("These are the categories: " + category);
             this.sectors.add(category);
         }

         // add each of the actions to the wheel
         this.sectors.add("Lose Turn");
         this.sectors.add("Free Turn");
         this.sectors.add("Bankrupt");
         this.sectors.add("Player's Choice");
         this.sectors.add("Opponent's Choice");
         this.sectors.add("Double your Score");

         // initialize the spins to 50
         this.spinsRemaining = 50;
     }

     /**
      * Spin the wheel
      *
      * @return the sector that the wheel landed on
      */
     public String spinWheel() {

         // get a random number between 0 and the number of sectors-1
         Random random = new Random();
         int index = random.nextInt(this.sectors.size());

         // map the index to a sector
         String sector = sectors.get(index);

         // manage the number of spins remaining
         this.spinsRemaining -= 1;

         // return the random sector
         return sector;
     }

     /**
      * get the sectors of the wheel
      *
      * @return the sectors
      */
     public List<String> getSectors() {
         return this.sectors;
     }

     /**
      * get the number of spins remaining this round
      *
      * @return the the number of spins remaining
      */
     public int getSpinsRemaining() {
         return this.spinsRemaining;
     }

//     /**
//      * Sets the number of spins remaining in this round
//      *
//      * @param spins the number of spins remaining
//      */
//     public void setSpinsRemaining(int spins) {
//         this.spinsRemaining = spins;
//     }

     /**
      * Debug function for seeing the contents of the wheel
      */
     public void printWheel() {
         System.out.println("The categories are: ");
         for(String category: sectors) {
             System.out.println(category);
         }
         System.out.println("There are " + Integer.toString(sectors.size())
                 + " sectors");
         System.out.println("There are " + Integer.toString(spinsRemaining)
             + " spins remaining");
     }
 }

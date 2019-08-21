import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.*;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TextInputDialog;

public class WheelGui extends Application {

	private static final int Screen_Height = 800;
	private static final int Screen_WIDHT = 1200;
	private static final int WOJ_CENTER_X = Screen_WIDHT / 2;
	private static final int WOJ_CENTER_Y = Screen_Height - (Screen_WIDHT / 4);
	private static final double ORBIT = 200;
	private static final int FONT_SIZE = 4;
	private static final List<Text> WOJCategories = new ArrayList<>();
	private List<Point> points;
	private List<Text> All_Categories;
	private List<Point> pointList = new ArrayList<>();
	private Label selectedCategoryLabel = new Label();
	private GridPane gameGrid = new GridPane();
	static List<Text> mycategories;
	static Label activePlayerLabel;
	static Label playersScore;
	static GridPane gridPane;
	static QuestionBoard board1;
	static QuestionBoard board2;
	static Wheel wheel1;
	static Wheel wheel2;
	int cyclesPerTimeline;
	static int round = 1;
	static int spins = 5; 
	static JeopardyGrid gridController;
	static Stage primaryStage;
	static Pane mainPane;
	WheelGui(Wheel wheel1,Wheel wheel2, QuestionBoard board1, QuestionBoard board2){
		mycategories = new ArrayList<Text>(); 
	    for(String key: wheel1.getSectors()) {
        	Text t = new Text();
        	t.setText(key);
            mycategories.add(t);
        }
		this.All_Categories = mycategories;
		WheelGui.board1 = board1;
		WheelGui.board2 = board2;
		WheelGui.wheel1 = wheel1;
		WheelGui.wheel2 = wheel2;
		
	}
	
	
	@Override
	public void start(Stage primaryStage) {
		 WheelGui.primaryStage = primaryStage;
		 primaryStage.setTitle("Player portal Wheel of Jeopardy");
	        GridPane gridPane = createPlayerFormPane();
	        addGUIAddPlayerControls(gridPane, primaryStage);
	        Scene scene = new Scene(gridPane, 800, 500);
	        primaryStage.setScene(scene);	        
	        primaryStage.show(); 
	}
	public GridPane createPlayerFormPane() {
	    gridPane = new GridPane();
	    gridPane.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
	    gridPane.setAlignment(Pos.CENTER);
	    gridPane.setPadding(new Insets(40, 40, 40, 40));
	    gridPane.setHgap(10);
	    gridPane.setVgap(10);
	    ColumnConstraints colConst = new ColumnConstraints(100, 100, Double.MAX_VALUE);
	    colConst.setHalignment(HPos.RIGHT);
	    ColumnConstraints col2Const = new ColumnConstraints(200,200, Double.MAX_VALUE);
	    col2Const.setHgrow(Priority.ALWAYS);
	    gridPane.getColumnConstraints().addAll(colConst, col2Const);
	    return gridPane;
	}
	
	public void addGUIAddPlayerControls(GridPane gridPane, Stage primaryStage) {
	    Label headerLabel = new Label("Add Players");
	    headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
	    headerLabel.setTextFill(Color.ANTIQUEWHITE);
	    gridPane.add(headerLabel, 0,0,2,1);
	    GridPane.setHalignment(headerLabel, HPos.CENTER);
	    GridPane.setMargin(headerLabel, new Insets(20, 0,20,0));

	    Label nameLabel = new Label("Player Name : ");
	    nameLabel.setTextFill(Color.ANTIQUEWHITE);
	    gridPane.add(nameLabel, 0,1);

	    TextField nameField = new TextField();
	    nameField.setPrefHeight(40);
	    gridPane.add(nameField, 1,1);
	    
//	    Label powerText = new Label("Powered by Green Team");
//	    powerText.setFont(Font.font("Arial", FontPosture.ITALIC, 8));
//	    powerText.setTextFill(Color.LIGHTGREEN);
//	 //   powerText.setPrefHeight(80);
//	    powerText.setLayoutX(500);
//	    powerText.setLayoutY(500);
//	    GridPane.getChildren().add(powerText);
	    
	   

	    Button addButton = new Button("Add Player");
	    addButton.setPrefHeight(40);
	    addButton.setDefaultButton(true);
	    addButton.setPrefWidth(100);
	    gridPane.add(addButton, 0, 4, 2, 1);
	    GridPane.setHalignment(addButton, HPos.CENTER);
	    GridPane.setMargin(addButton, new Insets(20, 0,20,0));
	    
	    Button doneButton = new Button("Done adding players");
	    doneButton.setPrefHeight(40);
	    doneButton.setDefaultButton(true);
	    doneButton.setPrefWidth(200);
	    gridPane.add(doneButton, 0, 4, 2, 12);
	    GridPane.setHalignment(doneButton, HPos.CENTER);
	    GridPane.setMargin(doneButton, new Insets(20, 0,20,0));
	    
	    addButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(nameField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a players name");
                    return;
                }
                showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Added Successfully!", "Player name: " + nameField.getText());
                TextView.people.add(new Person(nameField.getText()));
                nameField.setText("");
            }
        });
	    
	    doneButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
            	 TextView.createPeople();
            	initiateWOJBoard(primaryStage);
            }
        });
	}
	
	   public void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
	        Alert alert = new Alert(alertType);
	        alert.setTitle(title);
	        alert.setHeaderText(null);
	        alert.setContentText(message);
	        alert.initOwner(owner);
	        alert.show();
	    }
	private void initiateWOJBoard(Stage primaryStage) {

		int pos = 0;

		mainPane = new Pane();	
		mainPane.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
		WOJCategories.addAll(All_Categories);
		for (Text key : WOJCategories) {
			key.setUserData(new Integer(pos++));		
		}
	
		points = calculatePoints(WOJCategories.size(), WOJ_CENTER_X, WOJ_CENTER_Y, ORBIT);
		cyclesPerTimeline = 6;
		Timeline nextTimeline = null;
		
		for (int numberOfTimelines = 2; numberOfTimelines > 0; numberOfTimelines--) {
			final KeyFrame duration = new KeyFrame(Duration.millis(60 * numberOfTimelines));
			nextTimeline = createTimeline(mainPane, duration, nextTimeline);
			nextTimeline.setCycleCount(cyclesPerTimeline);
		}
	
		//initial label
		selectedCategoryLabel.setTextFill(Color.ANTIQUEWHITE);
		selectedCategoryLabel.setLayoutX(10);
		selectedCategoryLabel.setLayoutY(300);
	
		mainPane.getChildren().add(selectedCategoryLabel);
		
		Circle circle = new Circle(WOJ_CENTER_X+20, WOJ_CENTER_Y+10, ORBIT+20);
		circle.setFill(Color.DARKBLUE);
		mainPane.getChildren().add(circle);
		
		Rectangle rectangle = new Rectangle();
		rectangle.setFill(Color.BLACK);
		rectangle.setX(380);
		rectangle.setY((Screen_Height-Screen_WIDHT / 4 - 30));
		rectangle.setWidth(ORBIT+80);
		rectangle.setHeight(50);
		rectangle.setArcWidth(30.0); 
	    rectangle.setArcHeight(20.0); 
		mainPane.getChildren().add(rectangle);		
		mainPane.getChildren().addAll(WOJCategories);
		
		//Show players list
		List<String> playerList = new ArrayList<String>(); 
		for (Person player : TextView.people ){
			playerList.add(player.getName());
		}

		 TextView.updateCurrentPlayer(TextView.people.get(0));
		 displaycurrentPlayer(mainPane, TextView.people.get(0));
		 displayPlayersScore(mainPane, TextView.people.get(0), TextView.people.get(1));
		
		Scene scene = new Scene(mainPane, Screen_WIDHT, Screen_Height);	
		primaryStage.setTitle("Green Team Wheel Of Jeopardy");
		primaryStage.setScene(scene);	
		primaryStage.show();
		//nextTimeline.play();
		
    	JeopardyGrid gridController = new JeopardyGrid(All_Categories, board1, board2, gridPane);
    	gameGrid = gridController.makeGrid();
    	mainPane.getChildren().add(gameGrid);
    	
	}
	
	public static void displaycurrentPlayer(Pane mainPane, Person playerName) {
		  activePlayerLabel = new Label("Current Active Player :"+playerName.getName());
		  activePlayerLabel.setTextFill(Color.ANTIQUEWHITE);
		  activePlayerLabel.setLayoutX(10);
		  activePlayerLabel.setLayoutY(400);
		    mainPane.getChildren().add(activePlayerLabel);
		   // mainPane.add(activePlayerLabel, 0,1);
		    
		//  displaycurrentTokenStatus(mainPane, playerName); 
		
	}
	
//	public static void displaycurrentTokenStatus(Pane mainPane,  Person activePlayer) {
//		  activePlayerTokenLabel = new Label("Available Tokens: "+activePlayer.getFree_turns());
//		  activePlayerTokenLabel.setTextFill(Color.ANTIQUEWHITE);
//		  activePlayerTokenLabel.setLayoutX(10);
//		  activePlayerTokenLabel.setLayoutY(430);
//		    mainPane.getChildren().add(activePlayerTokenLabel);	
//	}
//	
	public static void displayPlayersScore(Pane mainPane,  Person player1, Person player2) {
		  playersScore = new Label(player1.toString() + "\n\n"+ player2.toString());
		  playersScore.setTextFill(Color.ANTIQUEWHITE);
		  playersScore.setLayoutX(10);
		  playersScore.setLayoutY(450);
		    mainPane.getChildren().add(playersScore);	
	}
	
	private Timeline createTimeline(final Pane root, final KeyFrame duration, final Timeline nextTimeline) {
		final Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
	
			@Override
			public void handle(ActionEvent event) {
				moveCategory(duration);
			}
		}), duration);
		
    	
		
		timeline.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				gridController = new JeopardyGrid(All_Categories, board1, board2, gridPane);
				if (nextTimeline != null) {
					nextTimeline.play();
				} 
				else {
					
				//Display end result here..
					for (Point key : pointList) {
						if((int)key.x == 400 && (int)key.y == 500) {					
								selectedCategoryLabel.setText("The category that was spun is: "+key.name + "\nSpins Remaining: "+spins + "\n\nCurrent Round: "+round);													
								gridController.processCategory(key.name, gameGrid);
											
						}
	
					}
				}
			};
		});
		Button btn = new Button("Spin");
		root.getChildren().add(btn);
		btn.setPrefWidth(120);
		btn.setPrefHeight(50);
		btn.setLayoutX(220);
		btn.setLayoutY((Screen_Height-Screen_WIDHT / 4 - 30));
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(round == 1 && isRoundOver(board1, wheel1)) {
					round = 2;
					spins = 5;
					//Update the allcategories
					 All_Categories.clear();
					  for(String key: wheel2.getSectors()) {
				        	Text t = new Text();
				        	t.setText(key);
				        	All_Categories.add(t);
				        }
					  
					  WOJCategories.clear();
					  pointList.clear();
					  mainPane.getChildren().removeAll();
					 
					  initiateWOJBoard(primaryStage) ;
				}
				if(round == 2 && isRoundOver(board2, wheel2)) {
					//Gameover shit..
				    showAlert(Alert.AlertType.INFORMATION, gridPane.getScene().getWindow(), "GAME OVER", "THE WINNER IS\n" + getWinner());
					
				}
								
				Random r = new Random();
				cyclesPerTimeline = r.nextInt((14 - 3) + 3)+1 ;
				nextTimeline.setCycleCount(cyclesPerTimeline);
				nextTimeline.play();
				spins--;
			}
		});
	
		return timeline;
	}
	 public static boolean isRoundOver(QuestionBoard board, Wheel wheel) {
	        boolean is_over_spins = true;
	        boolean is_over_categories = true;
	        boolean is_over = false;

	        // check if there are spins remaining in the wheel
	        if (spins > 0) {
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
	 public static Person getWinner() {
    	 List<Person> people = TextView.people;
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
	private void moveCategory(final KeyFrame duration) {
		for (Text name : WOJCategories) {
			Point nextPoint = getNextPoint((Integer) name.getUserData());
			TranslateTransition move = TranslateTransitionBuilder.create()
					.node(name)
					.fromX(name.translateXProperty().doubleValue())
					.fromY(name.translateYProperty().doubleValue())
					.toX(nextPoint.x)
					.toY(nextPoint.y)
					.duration(duration.getTime())
					.build();
			formatText(name, nextPoint);
			//System.out.println("Name is: "+name + " | Next Point "+ nextPoint);
			ParallelTransition parallelTransition = new ParallelTransition();
			parallelTransition.getChildren().add(move);
			parallelTransition.playFromStart();	
			name.setUserData(((Integer) name.getUserData()) + 1);
			if (((Integer) name.getUserData()) > points.size() - 1) {
				name.setUserData(0);
			}
			//nextPoint.name = name;
			nextPoint.name = name.getText();
		}
	}
	
	private void formatText(Text name, Point point) {
		Font font;
		font = Font.font("Verdana", FontWeight.MEDIUM, FONT_SIZE * 1.8);
		name.setFill(Color.ANTIQUEWHITE);
		name.setFont(font);
		return;
	}
	
	private Point getNextPoint(int positionRect) {
		return positionRect + 1 >= points.size() ? points.get(0) : points.get(positionRect + 1);
	}

	class Point {
		double x;
		double y;
		int position;
		double angle;
		String name;
	}
	
	private List<Point> calculatePoints(int points, double centerX, double centerY, double radius) {
		

		double rotateAngleDegree = 360d / (double) WOJCategories.size();

		double startAngleDegree = 270;
		for (int rotationStep = 0; rotationStep < points; rotationStep++) {
			double degreeStart = rotationStep * rotateAngleDegree;
			
			double angleAlpha = (degreeStart + startAngleDegree) * ((Math.PI) / 180);
			Point p = new Point();
			p.x = WOJ_CENTER_X + ORBIT * Math.sin(angleAlpha);
			p.y = WOJ_CENTER_Y - ORBIT * Math.cos(angleAlpha);
			p.position = rotationStep;
			p.angle = degreeStart;
			pointList.add(p);
		}
		return pointList;
	}
	

}

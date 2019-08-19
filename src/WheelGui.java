import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
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
	private GridPane playerGrid = new GridPane();
	WheelGui(List<Text> allCategories){
		this.All_Categories = allCategories;
	}
	
	@Override
	public void start(Stage primaryStage) {
		
		//setPlayer(primaryStage);
		Random r = new Random();
		int lowCycle = 7;
		int highCycle = 16;
		int lowTimeline = 3;
		int highTimeline = 8;
		int pos = 0;
		
		int randomCycle = (int) (Math.random() * (highCycle - lowCycle)) + lowCycle;
		int randomTimeline = (int) (Math.random() * (highTimeline - lowTimeline)) + lowTimeline;
		System.out.println("randomCycle |" + randomCycle);
		System.out.println("randomTimeline |" + randomTimeline);
		final Pane mainPane = new Pane();	
		mainPane.setBackground(new Background(new BackgroundFill(Color.rgb(40, 40, 40), CornerRadii.EMPTY, Insets.EMPTY)));
		WOJCategories.addAll(All_Categories);
		for (Text key : WOJCategories) {
			key.setUserData(new Integer(pos++));		
		}
	
		points = calculatePoints(WOJCategories.size(), WOJ_CENTER_X, WOJ_CENTER_Y, ORBIT);
		int cyclesPerTimeline = randomCycle;
		Timeline nextTimeline = null;
		
		for (int numberOfTimelines = randomTimeline; numberOfTimelines > 0; numberOfTimelines--) {
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
		Scene scene = new Scene(mainPane, Screen_WIDHT, Screen_Height);	
		primaryStage.setTitle("Green Team Wheel Of Jeopardy");
		primaryStage.setScene(scene);	
		primaryStage.show();
		nextTimeline.play();
		
    	JeopardyGrid gridController = new JeopardyGrid(All_Categories);
    	gameGrid = gridController.makeGrid();
    	mainPane.getChildren().add(gameGrid);
    	
    	PlayerManager playerManager = new PlayerManager();
    	
    	Button addPlayerButton = new Button("Add Player");
		addPlayerButton.setPrefHeight(40);
    	addPlayerButton.setLayoutX(950);
    	addPlayerButton.setLayoutY(300); 	
    	
    	Button doneAddingPlayers = new Button("Done Adding Players");
		doneAddingPlayers.setPrefHeight(40);
    	doneAddingPlayers.setLayoutX(950);
    	doneAddingPlayers.setLayoutY(350); 
    	
    	TextInputDialog td = new TextInputDialog();
    	td.setHeaderText("Enter Player Name");
    	Label playerName = new Label();
        EventHandler<ActionEvent> addPlayerEvent = new EventHandler<ActionEvent>() 
        { 
            public void handle(ActionEvent e) 
            { 
                td.showAndWait();
                playerName.setText(td.getEditor().getText());
                Person currentPlayer = new Person(playerName.getText());
                playerManager.addNewPlayer(currentPlayer);
                TextView.people.add(currentPlayer);
                td.getEditor().clear();
            } 
        };
        addPlayerButton.setOnAction(addPlayerEvent);
        GridPane playerGrid = playerManager.makePlayerGUI();
        playerGrid.setLayoutX(950);
        playerGrid.setLayoutY(400);
        mainPane.getChildren().add(playerGrid);
        
        EventHandler<ActionEvent> doneAddingEvent = new EventHandler<ActionEvent>() 
        { 
            public void handle(ActionEvent e) 
            { 
            	addPlayerButton.setDisable(true);
            } 
        };
        
        doneAddingPlayers.setOnAction(doneAddingEvent);
        
        
    	mainPane.getChildren().add(addPlayerButton);
    	mainPane.getChildren().add(doneAddingPlayers);
        
	}
	
	private void setPlayer(Stage primaryStage) {
		final Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.initOwner(primaryStage);
        VBox dialogVbox = new VBox(20);
        dialogVbox.getChildren().add(new Text("This is a Dialog"));
        Scene dialogScene = new Scene(dialogVbox, 300, 200);
        dialog.setScene(dialogScene);
        dialog.show();
	}
	
	private Timeline createTimeline(final Pane root, final KeyFrame duration, final Timeline nextTimeline) {
		final Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
	
			@Override
			public void handle(ActionEvent event) {
				moveCategory(duration);
			}
		}), duration);
		
    	JeopardyGrid gridController = new JeopardyGrid(All_Categories);
		
		timeline.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (nextTimeline != null) {
					nextTimeline.play();
				} 
				else {
				//Display end result here..
					for (Point key : pointList) {
						if((int)key.x == 400 && (int)key.y == 500) {
							selectedCategoryLabel.setText("Selected Category: "+key.name);
													
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
				  nextTimeline.play();
			}
		});
	
		return timeline;
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

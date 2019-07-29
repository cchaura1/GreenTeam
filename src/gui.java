import javafx.scene.control.Button;
import java.awt.Dimension;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.*;
import javafx.stage.Stage;
import javafx.util.Duration;

public class gui extends Application {

	private static final int Screen_Height = 800;
	private static final int Screen_WIDHT = 1200;
	private static final int WOJ_CENTER_X = Screen_WIDHT / 2;
	private static final int WOJ_CENTER_Y = Screen_Height / 2;
	private static final double ORBIT = 250;
	private static final int FONT_SIZE = 8;
	private static final List<Text> WOJCategories = new ArrayList<>();
	private List<Point> points;
	
	
	@Override
	public void start(Stage primaryStage) {
		int pos = 0;
		final Pane mainPane = new Pane();		
		// Load all name from the file
		WOJCategories.addAll(loadWOJCategories(new File("names.txt")));	
		for (Text name : WOJCategories) {
			name.setUserData(new Integer(pos++));
		}
	
		points = calculatePoints(WOJCategories.size(), WOJ_CENTER_X, WOJ_CENTER_Y, ORBIT);
	
		int cyclesPerTimeline = 10;
		Timeline nextTimeline = null;
		
		for (int numberOfTimelines = 5; numberOfTimelines > 0; numberOfTimelines--) {
			final KeyFrame duration = new KeyFrame(Duration.millis(60 * numberOfTimelines));
			nextTimeline = createTimeline(mainPane, duration, nextTimeline);
			nextTimeline.setCycleCount(cyclesPerTimeline);
		}
	
		Rectangle rectangle = new Rectangle();
		rectangle.setFill(Color.GREENYELLOW);
		rectangle.setX(340);
		rectangle.setY((Screen_Height-75)/2);
		rectangle.setWidth(150);
		rectangle.setHeight(60);
		rectangle.setArcWidth(30.0); 
	    rectangle.setArcHeight(20.0); 
		mainPane.getChildren().add(rectangle);		
		mainPane.getChildren().addAll(WOJCategories);
		Scene scene = new Scene(mainPane, Screen_WIDHT, Screen_Height);	
		primaryStage.setTitle("Wheel Of Jeopardy");
		primaryStage.setScene(scene);	
		primaryStage.show();
		nextTimeline.play();
	}
	
	private Timeline createTimeline(final Pane root, final KeyFrame duration, final Timeline nextTimeline) {
		final Timeline timeline = new Timeline(new KeyFrame(Duration.ZERO, new EventHandler<ActionEvent>() {
	
			@Override
			public void handle(ActionEvent event) {
				moveNames(duration);
			}
		}), duration);
	
		Button btn = new Button("Spin");
		timeline.setOnFinished(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if (nextTimeline != null) {
					nextTimeline.play();
				} 
			};
		});
		root.getChildren().add(btn);
		btn.setPrefWidth(120);
		btn.setPrefHeight(50);
		btn.setLayoutX(50);
		btn.setLayoutY((Screen_Height-75)/2);
		btn.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Set the wheel animation to play here
				  nextTimeline.play();
			}
		});
		return timeline;
	}

	private void moveNames(final KeyFrame duration) {
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
			ParallelTransition parallelTransition = new ParallelTransition();
			parallelTransition.getChildren().add(move);
			parallelTransition.playFromStart();	
			name.setUserData(((Integer) name.getUserData()) + 1);
			if (((Integer) name.getUserData()) > points.size() - 1) {
				name.setUserData(0);
			}
		}
	}
	
	private void formatText(Text name, Point point) {
		Font font;
		font = Font.font("Verdana", FontWeight.BOLD, FONT_SIZE * 1.8);
		name.setFill(Color.BLUE);
		name.setFont(font);
		return;
	}
	
	private Point getNextPoint(int positionRect) {
		return positionRect + 1 >= points.size() ? points.get(0) : points.get(positionRect + 1);
	}
	
	private List<Text> loadWOJCategories(File filePath) {
		TextBuilder<?> textBuilder = TextBuilder.create().x(0).y(0);
		List<Text> nameList = new ArrayList<Text>();
		try {
			@SuppressWarnings("resource")
			BufferedReader in = new BufferedReader(new FileReader(filePath));
			String line = "";
			while ((line = in.readLine()) != null) {
				nameList.add(textBuilder.text(line).build());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return nameList;
	}

	class Point {
		double x;
		double y;
		int position;
		double angle;
	}
	
	private List<Point> calculatePoints(int points, double centerX, double centerY, double radius) {
		List<Point> pointList = new ArrayList<>();

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

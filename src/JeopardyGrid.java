import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.animation.KeyFrame;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.geometry.Insets;

public class JeopardyGrid
{
	private List<Text> All_Categories;
	private QuestionBoard board;
	private GridPane gridPane;

	JeopardyGrid(List<Text> allCategories, QuestionBoard board, GridPane gridPane)
	{
		this.All_Categories = allCategories;
		this.board = board;
		this.gridPane = gridPane;
	}

	public GridPane makeGrid()
	{
		GridPane game_grid = new GridPane();
		game_grid.setPadding(new Insets(25, 25, 25, 25));
		game_grid.setHgap(80);
		game_grid.setVgap(20);
		game_grid.setGridLinesVisible(true);
		game_grid.setAlignment(Pos.CENTER); 

		String value1 = "200";
		String value2 = "400";
		String value3 = "600";
		String value4 = "800";
		String value5 = "1000";

		Button cat1 = new Button(All_Categories.get(0).getText());
		Button cat2 = new Button(All_Categories.get(1).getText());
		Button cat3 = new Button(All_Categories.get(2).getText());
		Button cat4 = new Button(All_Categories.get(3).getText());
		Button cat5 = new Button(All_Categories.get(4).getText());
		Button cat6 = new Button(All_Categories.get(5).getText());


		game_grid.add(cat1, 0, 0, 1, 1);
		game_grid.add(cat2, 1, 0, 1, 1);
		game_grid.add(cat3, 2, 0, 1, 1);
		game_grid.add(cat4, 3, 0, 1, 1);
		game_grid.add(cat5, 4, 0, 1, 1);
		game_grid.add(cat6, 5, 0, 1, 1);

		for (int i = 0; i < 6; i++)
		{
			game_grid.add(new Button(value1), i, 1, 1, 1);
			game_grid.add(new Button(value2), i, 2, 1, 1);
			game_grid.add(new Button(value3), i, 3, 1, 1);
			game_grid.add(new Button(value4), i, 4, 1, 1);
			game_grid.add(new Button(value5), i, 5, 1, 1);

		}

		return game_grid;
	}

	private Button getButton(GridPane game_grid, Integer row, Integer col)
	{
		ObservableList<Node> children = game_grid.getChildren();
		Node result = null;
		for (Node node : children) 
		{
			if(game_grid.getRowIndex(node) == row && game_grid.getColumnIndex(node) == col) 
			{
				result = node;
				break;
			}
		}

		return (Button) result;
	}

	public void disableButton(GridPane game_grid, Integer row, Integer col)
	{
		Button button = getButton(game_grid, row, col);
		button.setBackground(new Background(new BackgroundFill(Color.rgb(0, 153, 0), CornerRadii.EMPTY, Insets.EMPTY)));
		button.setTextFill(Color.WHITE);
		button.setDisable(true);
	}

	public Integer findNextButton(GridPane gameGrid, Integer col)
	{
		ObservableList<Node> children = gameGrid.getChildren();
		Node result = null;
		Integer row = 0;
		for (Node node : children) 
		{
			if(gameGrid.getColumnIndex(node) == col) 
			{
				if(node.isDisabled() || gameGrid.getRowIndex(node) == 0)
				{
					continue;
				}
				else
				{
					row = gameGrid.getRowIndex(node);
					break;
				}
			}
		}

		return row;

	}

	public void processCategory(String category, GridPane gameGrid) 
	{
		Integer gridRow = 0;
		
		if(category.equals(All_Categories.get(0).getText()))
		{			
			gridRow = findNextButton(gameGrid, 0);
			if(gridRow == 5)
			{
				disableButton(gameGrid, 0, 0);
			}
			disableButton(gameGrid, gridRow, 0);
			displayQuestion(category);
			SwitchPlayer();
			
		}
		else if(category.equals(All_Categories.get(1).getText()))
		{			
			gridRow = findNextButton(gameGrid, 1);
			if(gridRow == 5)
			{
				disableButton(gameGrid, 0, 1);
			}
			disableButton(gameGrid, gridRow, 1);
			displayQuestion(category);
			SwitchPlayer();
		}
		else if(category.equals(All_Categories.get(2).getText()))
		{			
			gridRow = findNextButton(gameGrid, 2);
			if(gridRow == 5)
			{
				disableButton(gameGrid, 0, 2);
			}
			disableButton(gameGrid, gridRow, 2);
			displayQuestion(category);
			SwitchPlayer();
		}
		else if(category.equals(All_Categories.get(3).getText()))
		{			
			gridRow = findNextButton(gameGrid, 3);
			if(gridRow == 5)
			{
				disableButton(gameGrid, 0, 3);
			}
			disableButton(gameGrid, gridRow, 3);
			displayQuestion(category);
			SwitchPlayer();
		}
		else if(category.equals(All_Categories.get(4).getText()))
		{			
			gridRow = findNextButton(gameGrid, 4);
			if(gridRow == 5)
			{
				disableButton(gameGrid, 0, 4);
			}
			disableButton(gameGrid, gridRow, 4);
			displayQuestion(category);
			SwitchPlayer();
		}
		else if(category.equals(All_Categories.get(5).getText()))
		{			
			gridRow = findNextButton(gameGrid, 5);
			if(gridRow == 5)
			{
				disableButton(gameGrid, 0, 5);
			}
			disableButton(gameGrid, gridRow, 5);
			displayQuestion(category);
			SwitchPlayer();
		}
		else if(category.equals("Opponent's Choice") || category.equals("Player's Choice"))
		{			
			chooseCategory(gameGrid);
			SwitchPlayer();
		}	
		else if(category.equals("Lose Turn"))
		{			
			if(TextView.current_player.getFree_turns() > 0) {			
				confirmationToUseToken(category);
			}
			else {
				SwitchPlayer();
			}				 
		}	
		else if(category.equals("Free Turn"))
		{							
			TextView.current_player.addOneFreeTurn();

			SwitchPlayer();
		}
		else if(category.equals("Bankrupt"))
		{			
			if(WheelGui.round == 1) {
				TextView.current_player.bankrupt_round1_score();
			}
			if(WheelGui.round == 2) {
				TextView.current_player.bankrupt_round2_score();
			}
			SwitchPlayer();
		}
		else if(category.equals("Double your Score"))
		{			
			if(WheelGui.round == 1) {
				TextView.current_player.double_round1_score();
			}
			if(WheelGui.round == 2) {
				TextView.current_player.double_round2_score();
			}
			SwitchPlayer();
		}

	}

	public void SwitchPlayer() {
		String previous = TextView.current_player.getName();
		Person previousPerson = TextView.current_player;
		if(previous.equals(TextView.people.get(0).getName())) {
			TextView.updateCurrentPlayer(TextView.people.get(1));
			WheelGui.activePlayerLabel.setText("Current Active Player :"+TextView.people.get(1).getName());
			WheelGui.activePlayerTokenLabel.setText("Available Tokens: "+TextView.people.get(1).getFree_turns());
			WheelGui.playersScore.setText(previousPerson.toString() + "\n\n"+ TextView.current_player.toString());
			System.out.print("Current "+TextView.current_player.getName()+ "| Switching to "+ TextView.people.get(1).getName());
		}
		if(previous.equals(TextView.people.get(1).getName())) {
			TextView.updateCurrentPlayer(TextView.people.get(0));
			WheelGui.activePlayerLabel.setText("Current Active Player :"+TextView.people.get(0).getName());
			WheelGui.activePlayerTokenLabel.setText("Available Tokens: "+TextView.people.get(0).getFree_turns());
			WheelGui.playersScore.setText(previousPerson.toString() + "\n\n"+ TextView.current_player.toString());
			System.out.print("Current "+TextView.current_player.getName()+ "| Switching to "+ TextView.people.get(0).getName());
		}
	}

	public void confirmationToUseToken(String category) {
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);

		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");
		VBox vbox = new VBox(new Text("Would you like to you use token?"), yesButton, noButton);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(15));
		dialogStage.setScene(new Scene(vbox));
		dialogStage.show();

		yesButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				//Deduct the token here and respond to yes
				dialogStage.close();
				// track that the player used their free turn

				TextView.current_player.useOneFreeTurn();

			}
		});
		noButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				SwitchPlayer();
				dialogStage.close();           	
			}
		});
	}


	public void displayQuestion(String category) {
		Stage dialogStage = new Stage();
		dialogStage.initModality(Modality.WINDOW_MODAL);

		Button yesButton = new Button("Yes");
		Button noButton = new Button("No");
		Button showButton = new Button("Show answer");
		VBox YNvbox = new VBox(new Text(board.currentTile(category).getAnswer()), yesButton, noButton);
		VBox vbox = new VBox(new Text(board.currentTile(category).getQuestion()), showButton);
		vbox.setAlignment(Pos.CENTER);
		vbox.setPadding(new Insets(15));
		dialogStage.setScene(new Scene(vbox));
		dialogStage.show();

		showButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				YNvbox.setAlignment(Pos.CENTER);
				YNvbox.setPadding(new Insets(15));
				dialogStage.setScene(new Scene(YNvbox));
				dialogStage.show();
				dialogStage.setTitle("Is the below answer is correct ?");
				dialogStage.setHeight(300);
				dialogStage.setWidth(300);
				//dialogStage.close();   
				yesButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						//Add the score for this player
						if(WheelGui.round == 1) {
							TextView.current_player.inc_round1_score(board.currentTile(category).getValue());
						}
						if(WheelGui.round == 2) {
							TextView.current_player.inc_round2_score(board.currentTile(category).getValue());
						}
											
						dialogStage.close();
					}
				});
				noButton.setOnAction(new EventHandler<ActionEvent>() {
					@Override
					public void handle(ActionEvent event) {
						//Subtract the score for this player
						if(WheelGui.round == 1) {
							TextView.current_player.inc_round1_score(-1*board.currentTile(category).getValue());
						}
						if(WheelGui.round == 2) {
							TextView.current_player.inc_round2_score(-1*board.currentTile(category).getValue());
						}
						dialogStage.close();           	
					}
				});
			}
		});
	}

	public void chooseCategory(GridPane gameGrid) 
	{
		String selectedCatName = "hello" ;
		
		Button cat1 = getButton(gameGrid, 0, 0);
		Button cat2 = getButton(gameGrid, 0, 1);
		Button cat3 = getButton(gameGrid, 0, 2);
		Button cat4 = getButton(gameGrid, 0, 3);
		Button cat5 = getButton(gameGrid, 0, 4);
		Button cat6 = getButton(gameGrid, 0, 5);

		cat1.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent event) 
			{
				int gridRow = findNextButton(gameGrid, 0);
				if(gridRow == 5)
				{
					disableButton(gameGrid, 0, 0);
				}
				disableButton(gameGrid, gridRow, 0);
				displayQuestion(cat1.getText());
			}
		});
		cat2.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent event) 
			{
				int gridRow = findNextButton(gameGrid, 1);
				if(gridRow == 5)
				{
					disableButton(gameGrid, 0, 1);
				}
				disableButton(gameGrid, gridRow, 1);
				displayQuestion(cat2.getText());
			}
		});
		cat3.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent event) 
			{
				int gridRow = findNextButton(gameGrid, 2);
				if(gridRow == 5)
				{
					disableButton(gameGrid, 0, 2);
				}
				disableButton(gameGrid, gridRow, 2);
				displayQuestion(cat3.getText());
			}
		});
		cat4.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent event) 
			{
				int gridRow = findNextButton(gameGrid, 3);
				if(gridRow == 5)
				{
					disableButton(gameGrid, 0, 3);
				}
				disableButton(gameGrid, gridRow, 3);
				displayQuestion(cat4.getText());
			}
		});
		cat5.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent event) 
			{
				int gridRow = findNextButton(gameGrid, 4);
				if(gridRow == 5)
				{
					disableButton(gameGrid, 0, 4);
				}
				disableButton(gameGrid, gridRow, 4);
				displayQuestion(cat5.getText());
			}
		});
		cat6.setOnAction(new EventHandler<ActionEvent>() 
		{
			public void handle(ActionEvent event) 
			{
				int gridRow = findNextButton(gameGrid, 5);
				if(gridRow == 5)
				{
					disableButton(gameGrid, 0, 5);
				}
				disableButton(gameGrid, gridRow, 5);
				displayQuestion(cat6.getText());
			}
		});
		
	}

}
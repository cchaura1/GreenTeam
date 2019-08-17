import javafx.scene.control.Button;
import java.util.ArrayList;
import java.util.List;
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
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class JeopardyGrid
{
	private List<Text> All_Categories;
	
	JeopardyGrid(List<Text> allCategories)
	{
		this.All_Categories = allCategories;
	}
	
	public GridPane makeGrid()
	{
		GridPane game_grid = new GridPane();
		
		game_grid.setHgap(50);
		game_grid.setVgap(50);
		game_grid.setGridLinesVisible(true);
		game_grid.setAlignment(Pos.CENTER); 
		
		String value1 = "200";
		String value2 = "400";
		String value3 = "600";
		String value4 = "800";
		String value5 = "1000";
		
		Label cat1 = new Label(All_Categories.get(0).getText());
		Label cat2 = new Label(All_Categories.get(1).getText());
		Label cat3 = new Label(All_Categories.get(2).getText());
		Label cat4 = new Label(All_Categories.get(3).getText());
		Label cat5 = new Label(All_Categories.get(4).getText());
		Label cat6 = new Label(All_Categories.get(5).getText());
		
		
		game_grid.add(cat1, 0, 0, 1, 1);
		game_grid.add(cat2, 1, 0, 1, 1);
		game_grid.add(cat3, 2, 0, 1, 1);
		game_grid.add(cat4, 3, 0, 1, 1);
		game_grid.add(cat5, 4, 0, 1, 1);
		game_grid.add(cat6, 5, 0, 1, 1);
			
		for (int i = 0; i < All_Categories.size(); i++)
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
		button.setDisable(true);
	}
	
	
}
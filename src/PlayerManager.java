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
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.control.Label;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.geometry.Insets;

public class PlayerManager
{
	private List<Person> Players;
	
	PlayerManager(List<Person> people)
	{
		this.Players = people;
	}
	
	public GridPane makePlayerGUI()
	{
		GridPane playerGrid = new GridPane();
		playerGrid.setPadding(new Insets(25, 25, 25, 25));
		playerGrid.setHgap(80);
		playerGrid.setVgap(20);
		playerGrid.setGridLinesVisible(true);
		playerGrid.setAlignment(Pos.CENTER); 
		
		for(int i = 0; i < Players.size(); i++)
		{
			Person player = Players.get(i);
			String name = player.getName();
			playerGrid.add(new Label(name), 0, i);
			playerGrid.add(new Label("0"), 1, i);
		}
		return playerGrid;
	}
}




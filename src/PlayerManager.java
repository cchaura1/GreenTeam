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
	private GridPane playersGrid = new GridPane();
	
	PlayerManager()
	{
		playersGrid.add(new Label("Player Name"), 0, 0);
		playersGrid.add(new Label("Player Score"), 1, 0);
	}
	
	public void addNewPlayer(Person player)
	{
		String name = player.getName();
		int rows = getRowCount(playersGrid);
		playersGrid.add(new Label(name), 0, rows);
		playersGrid.add(new Label("0"), 1, rows);	
	}
	
	public void updatePlayerScore(Person player, int newScore)
	{
		ObservableList<Node> children = playersGrid.getChildren();
		int row = 0;
		Label thisPerson = new Label();
	    for (Node node : children) 
	    {
	    	thisPerson = (Label) node;
	    	String thisName = thisPerson.getText();
	    	if(player.getName().equals(thisName))
	    	{
	    		row = playersGrid.getRowIndex(node);
	    		break;
	    	}
	    }
	    String scoreString = Integer.toString(newScore);
	    playersGrid.getChildren().remove(thisPerson);
	    playersGrid.add(new Label(scoreString), 1, row);
	}
	
	private int getRowCount(GridPane pane) 
	{
        int numRows = pane.getRowConstraints().size();
        for (int i = 0; i < pane.getChildren().size(); i++) 
        {
            Node child = pane.getChildren().get(i);
            if (child.isManaged()) 
            {
                Integer rowIndex = GridPane.getRowIndex(child);
                if(rowIndex != null)
                {
                    numRows = Math.max(numRows,rowIndex+1);
                }
            }
        }
        return numRows;
    }

}




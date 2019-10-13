
// Assignment #: Arizona State University CSE205
//         Name: Nandakishore Vudumula
//    StudentID: 1214723796
//      Lecture: MWF 10:45
//  Description: The DrawingPane class creates a canvas where we can use
//               mouse key to draw lines with different colors and widths.
//               We can also use the the two buttons to erase the last
//				     drawn line or clear them all.


//import any classes necessary here
//----
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Line;
import javafx.scene.paint.Color;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Orientation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent; 	

import java.util.ArrayList;

public class DrawingPane extends BorderPane
{
   private Button undoButton, eraseButton;
   private ComboBox<String> colorCombo, widthCombo;
   private ArrayList<Line> lineList;
   private Pane canvas;
   
   private Line line1;
   private Color cColor = Color.BLACK;
   private double wWidth = 1;
   private double x;
   private double y;
   private double x1;
   private double y1;
   
   //declared all necessary instance variables 

   public DrawingPane()
   {
      
      undoButton = new Button("Undo");
      eraseButton = new Button("Erase");
      undoButton.setMinWidth(80.0);
      eraseButton.setMinWidth(80.0);
      lineList = new ArrayList<Line>();
      //initializing buttons, setting their sizes, and initializing the arrayList
      colorCombo = new ComboBox<String>();
      widthCombo = new ComboBox<String>();
      //instantiating the two ComboBox objects for the color and width selection
      colorCombo.getItems().addAll("Black","Blue","Red", "Yellow", "Green" );
      colorCombo.getSelectionModel().select("Black");
      //adding all the color options to colorCombo, and setting the initial option to black
      widthCombo.getItems().addAll("1", "3", "5", "7");
      widthCombo.getSelectionModel().select("1");
      //adding all the width options to width combo, and setting the initial option to 1
     
     
      //topPane should contain two combo boxes and two buttons
      HBox topPane = new HBox();
      topPane.setSpacing(40);
      topPane.setPadding(new Insets(10, 10, 10, 10));
      topPane.setStyle("-fx-border-color: black");
      topPane.getChildren().addAll(colorCombo, widthCombo, undoButton, eraseButton);
       //Hbox that contains all the button options 
       
      canvas = new Pane();
      canvas.setStyle("-fx-background-color: white;");
       //white pane where the lines will be drawn

       
      
      this.setCenter(canvas);
      this.setTop(topPane);
      //adding the hbox and the pane to the borderpane
       
     
      canvas.setOnMousePressed(new MouseHandler());
      canvas.setOnMouseDragged(new MouseHandler());
      canvas.setOnMouseReleased(new MouseHandler());
      //setting three separate mouse handlers for three separate mouse actions
      ButtonHandler handle = new ButtonHandler();
      eraseButton.setOnAction(handle);
      undoButton.setOnAction(handle);
      ColorHandler color = new ColorHandler();
      colorCombo.setOnAction(color);
      WidthHandler width = new WidthHandler();
      widthCombo.setOnAction(width);
      //setting the button handler, the color handler, and the width handler 
      
      
   }



    
    private class MouseHandler implements EventHandler<MouseEvent>
    {
        public void handle(MouseEvent event)
        {
            
        	if(event.getEventType()==MouseEvent.MOUSE_PRESSED) {
        		x = event.getX();
        		y = event.getY();
        		line1 = new Line();
        		line1.setStartX(x);
        		line1.setStartY(y);
        		line1.setEndX(x);
        		line1.setEndY(y);
        		line1.setStroke(cColor);
        		line1.setStrokeWidth(wWidth);
        		
        		canvas.getChildren().add(line1);
        		
        		
        	}
        	else if(event.getEventType()==MouseEvent.MOUSE_DRAGGED) {
        		x1 = event.getX();
        		y1 = event.getY();
        		line1.setEndX(x1);
        		line1.setEndY(y1);
        		
        	}
        	else if(event.getEventType()==MouseEvent.MOUSE_RELEASED) {
        		line1.setStroke(cColor);
        		line1.setStrokeWidth(wWidth); 
        		line1 = new Line(x,y,x1, y1);
        		lineList.add(line1);
        	}
            //mouse handler class that checks the event type for which mouse action is being used
        	//when the mouse is pressed, it creates a new line at that coordinate and then sets both the start and end points to the same x,y values and the canvas adds the line 
        	//sets color and stroke to the variables that store the color and width values later in the color handler and width handler class
        	//if the mouse is dragged, it will receive the x y coordinates to where the line is dragged to, and then set the end point with those coordinates
        	//once the mouse is released the line sets the color and stroke again, and then adds the line to lineList 
        	
            
            
        }
    }

   
    private class ButtonHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {
            
        	Object s = event.getSource();
        	// object variable that stores which button is pressed based on the actionevent
        	if(s == undoButton && lineList.size()>0) {
        		int i = lineList.size();
        		lineList.remove(i-1);
        		canvas.getChildren().remove(i-1);
        		//if statement that checks if undoButton is pressed, and makes sure lineList is > 0 before executing its body where the last index of lineList is removed both from lineList and canvas
        		
        	}
        	if(s == eraseButton) {
        		canvas.getChildren().clear();
        		lineList.clear();
        	//if statement that checks if eraseButton is pressed, and then clears both lineList and canvas 	
        		
        	}
            
            
            
        }
    }



   
   private class ColorHandler implements EventHandler<ActionEvent>
   {
       public void handle(ActionEvent event)
       {
           //string variable that stores which option is selected in the colorCombo ComboBox
    	   String cSelect = colorCombo.getSelectionModel().getSelectedItem();
    	   if (cSelect.equals("Black")) {
    		   cColor = Color.BLACK;
    	   }
    	   else if (cSelect.equals("Blue")) {
    		   cColor = Color.BLUE;
    	   }
    	   else if (cSelect.equals("Red")) {
    		   cColor = Color.RED;
    	   }
    	   else if (cSelect.equals("Green")) {
    		   cColor = Color.GREEN;
    	   }
    	   else if (cSelect.equals("Yellow")) {
    		   cColor = Color.YELLOW;
    	   }
           //if and multiple else if statements that check which option is selected and then sets the color variables cColor to that particular color, which is then used in the MouseHandler class when creating the line
           
           
       }
   }
    
    
    private class WidthHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent event)
        {
            //String variable that stores which option is selected from the widthCombo ComboBox
        	String wSelect = widthCombo.getSelectionModel().getSelectedItem();
        	if(wSelect.equals("1")) {
        		wWidth = 1.0;
        	}
        	else if(wSelect.equals("3")) {
        		wWidth = 3.0;
        	}
        	else if(wSelect.equals("5")) {
        		wWidth = 5.0;
        	}
        	else if(wSelect.equals("7")) {
        		wWidth = 7.0;
        	}
            //if and multiple else if statements that check which option is selected and then sets the int variable wWdith to that particular width, which is then used in the MouseHandler class when creating the line
            
        }
    }//end WidthHandler
}//end class DrawingPane

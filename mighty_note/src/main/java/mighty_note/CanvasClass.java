package mighty_note;

import javafx.application.Application;

import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

import java.io.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;

import java.util.regex.Pattern;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Parent;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.paint.Color;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.collections.*;
import javafx.scene.effect.*;
import javafx.scene.control.Alert.AlertType;

import java.text.DateFormat;
import java.util.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javafx.geometry.Orientation;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.awt.Desktop;
import java.net.URI;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.HttpURLConnection;
import java.net.URL;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Alert.AlertType;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.event.ActionEvent;
 

public class CanvasClass{
	
	AnchorPane root;
	
	Label pageLabel = new Label("Page");
	
	static ColorPicker colorPicker = new ColorPicker();
	static ColorPicker backgroundColorPicker = new ColorPicker();
	
	static ComboBox penSize = new ComboBox();
	static ComboBox format = new ComboBox();
	
	Button handwritingRecognition = new Button("Handwriting Recognition");
	Button speakToNote = new Button("Speak to make note");
	Button read = new Button("Read");
	Button save = new Button("Save");
	
	
	@FXML
	AnchorPane topBackground = new AnchorPane();
	
	@FXML
	AnchorPane middleBackground = new AnchorPane();
	
	static String backgroundColor = "";
	static String backgroundFormat = "";
	
	class ResizableCanvas extends Canvas {
		 
        private void draw() {
            double width = getWidth();
            double height = getHeight();
 
            GraphicsContext gc = getGraphicsContext2D();
            gc.clearRect(0, 0, width, height);
 
            gc.setStroke(Color.RED);
            gc.strokeLine(0, 0, width, height);
            gc.strokeLine(0, height, width, 0);
        }
 
        @Override
        public boolean isResizable() {
            return true;
        }
 
        @Override
        public double prefWidth(double height) {
            return getWidth();
        }
 
        @Override
        public double prefHeight(double width) {
            return getHeight();
        }
    }

	static StackPane background = new StackPane();
	
	static String stringFormat = "";
	
	public static void setPage() {
		background.setStyle("-fx-background-color: '" + backgroundColorPicker.getValue() + "' " + 
								stringFormat + ";");
		}
	
	
	public void start(){
		CanvasClass.penSize.getItems().clear();
		CanvasClass.format.getItems().clear();
		
		save.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                FileChooser fileChooser = new FileChooser();
                
                //Set extension filter
                FileChooser.ExtensionFilter extFilter = 
                        new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
                fileChooser.getExtensionFilters().add(extFilter);
              
                //Show save file dialog
                File file = fileChooser.showSaveDialog(s);
                
                if(file != null){
                    try {
                        WritableImage writableImage = new WritableImage((int)background.getWidth(), 
                        												(int)background.getHeight());
                        background.snapshot(null, writableImage);
                        RenderedImage renderedImage = SwingFXUtils.fromFXImage(writableImage, null);
                        ImageIO.write(renderedImage, "png", file);
                    } catch (IOException ex) {
                        Logger.getLogger(CanvasClass.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
        });
		
		penSize.getItems().addAll("0.5", "1", "2", "4", "5");
		penSize.setValue("1");
		format.getItems().addAll("Blank", "Lining", "Mathematics", "English");
		format.setValue("Blank");
		colorPicker.setValue(Color.BLACK);
		
		backgroundColorPicker.setOnAction(e -> setPage());
		format.setOnAction(e -> {
			switch(format.getValue().toString().toLowerCase().trim()) {
			case "mathematics":
				stringFormat = ", linear-gradient(from 0.5px 0.0px to 15.5px  0.0px, repeat, grey 5%, transparent 5%)," + 
						"linear-gradient(from 0.0px 0.5px to  0.0px 15.5px, repeat, grey 5%, transparent 5%)";
			break;
			case "lining":
				stringFormat = ", linear-gradient(from 0.0px 0.5px to  0.0px 15.5px, repeat, grey 5%, transparent 5%)";
			break;
			case "english":
				stringFormat = "";
			break;
			default:
				stringFormat = "";
			}
			setPage();
		});
		
		setPage();
		ResizableCanvas canvas = new ResizableCanvas();
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();
        initDraw(graphicsContext);
        
        colorPicker.setOnAction(e -> initDraw(graphicsContext));
        penSize.setOnAction(e -> initDraw(graphicsContext));
        
        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, 
                new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                graphicsContext.beginPath();
                graphicsContext.moveTo(event.getX(), event.getY());
                graphicsContext.stroke();
            }
        });
        
        canvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, 
                new EventHandler<MouseEvent>(){

            @Override
            public void handle(MouseEvent event) {
                graphicsContext.lineTo(event.getX(), event.getY());
                graphicsContext.stroke();
            }
        });
        
        
        background.getChildren().add(canvas);
        //background.setStyle("-fx-background-color: '" + backgroundColor + "';"
        //						+ "-fx-border-color: black;");
        
        // Bind canvas size to stack pane size.
        canvas.widthProperty().bind(background.widthProperty());
        canvas.heightProperty().bind(background.heightProperty());
        
        try {
			root = FXMLLoader.load(getClass().getResource("/fxml/Canvas.fxml"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        topBackground.setPrefHeight(64.0);
        topBackground.setStyle("-fx-background-color: '#000099';");
        middleBackground.setPrefHeight(35.0);
        middleBackground.setStyle("-fx-background-color: '#3385ff';");
        
        pageLabel.setStyle("-fx-font-size: 26px;" + "-fx-text-fill: 'white';");
        topBackground.getChildren().addAll(pageLabel);
        
        middleBackground.getChildren().addAll(colorPicker, penSize, format, handwritingRecognition,
        		speakToNote, read, save, backgroundColorPicker);
        
        //colorPicker
        AnchorPane.setTopAnchor(colorPicker, 5.0);
        AnchorPane.setLeftAnchor(colorPicker, 10.0);
        //backgroundColorPicker
        AnchorPane.setTopAnchor(backgroundColorPicker, 5.0);
        AnchorPane.setLeftAnchor(backgroundColorPicker, 150.0);
        //penSize
        AnchorPane.setTopAnchor(penSize, 5.0);
        AnchorPane.setLeftAnchor(penSize, 150.0 + 150.0);
        //format
        AnchorPane.setTopAnchor(format, 5.0);
        AnchorPane.setLeftAnchor(format, 220.0 + 150.0);
        //handwritingRecognition
        AnchorPane.setTopAnchor(handwritingRecognition, 5.0);
        AnchorPane.setLeftAnchor(handwritingRecognition, 300.0 + 200.0);
        //speakToNote
        AnchorPane.setTopAnchor(speakToNote, 5.0);
        AnchorPane.setLeftAnchor(speakToNote, 460.0 + 200.0);
        //read
        AnchorPane.setTopAnchor(read, 5.0);
        AnchorPane.setLeftAnchor(read, 600.0 + 200.0);
        //save
        AnchorPane.setTopAnchor(save, 5.0);
        AnchorPane.setLeftAnchor(save, 660.0 + 200.0);
        
        //pageLabel
        AnchorPane.setTopAnchor(pageLabel, 14.0);
        AnchorPane.setLeftAnchor(pageLabel, 25.0);
        //topBackground
        AnchorPane.setLeftAnchor(topBackground, 0.0);
        AnchorPane.setRightAnchor(topBackground, 0.0);
        //middleBackground
        AnchorPane.setTopAnchor(middleBackground, 64.0);
        AnchorPane.setLeftAnchor(middleBackground, 0.0);
        AnchorPane.setRightAnchor(middleBackground, 0.0);
        //background
        AnchorPane.setTopAnchor(background, 110.0);
        AnchorPane.setLeftAnchor(background, 10.0);
        AnchorPane.setRightAnchor(background, 10.0);
        AnchorPane.setBottomAnchor(background, 10.0);
        
        root.getChildren().addAll(topBackground, middleBackground, background);
        
        Scene scene = new Scene(root, 1000, 500);
        scene.getStylesheets().add("grid-with-borders.css");
        //background.getStyleClass().add("lining");
        
        s.setScene(scene);
        s.setTitle("Page");
        s.show();
	
		
	}
	
	Stage s = new Stage();
	
	private static void initDraw(GraphicsContext gc){
        double canvasWidth = gc.getCanvas().getWidth();
        double canvasHeight = gc.getCanvas().getHeight();
        
        gc.strokeRect(
                0,              //x of the upper left corner
                0,              //y of the upper left corner
                canvasWidth,    //width of the rectangle
                canvasHeight);  //height of the rectangle
        
        //gc.setFill();
        gc.setStroke(colorPicker.getValue());
        gc.setLineWidth(Double.parseDouble(penSize.getValue().toString()));
        
    }
	
	public void loadImage(ImageView image) {
		background.getChildren().add(image);
		start();
	}
	
}

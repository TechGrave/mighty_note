package mighty_note;

import javafx.application.Application;

import java.awt.image.RenderedImage;
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
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
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
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.commons.lang3.text.WordUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert.AlertType;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.event.ActionEvent;
import org.apache.commons.lang3.text.WordUtils;

public class HomeController {
	
	@FXML
	static
	Label name;
	
	@FXML
	HBox workbooks;
	
	@FXML
	HBox notebooks;
	
	@FXML
	Label bookId;
	
	@FXML
	Label subject;
	
	@FXML
	Label teacher;
	
	@FXML
	Label className;
	
	@FXML
	Label date;
	
	@FXML
	Button refresh;
	
	@FXML
	Button help;
	
	@FXML
	Button updateAccount;
	
	@FXML
	Button logout;
	
	@FXML
	Button visit;
	
	@FXML
	Button addToMyAccount;
	
	@FXML
	TextField subjectText;
	
	@FXML
	TextField teacherText;
	
	@FXML
	TextField classText;
	
	@FXML
	Button addPage;
	
	@FXML
	Button updateBookDetails;
	
	@FXML
	Button deleteBook;
	
	@FXML
	AnchorPane pages;
	
	static TilePane tile = new TilePane();
	
	static TableView workbookTable = new TableView();
	static TableColumn<String , NotebookClass> column2 = new TableColumn<>("Subject");
	
	static TableView notebookTable = new TableView();
	static TableColumn<String , NotebookClass> columnN2 = new TableColumn<>("Subject");
	
	public static void refresh() {
		name.setText("Ashutosh Paul");
		//workbookTable
		workbookTable.setStyle("-fx-border-color: '#808080';" + "-fx-border-width: 1px;");
		column2.setCellValueFactory(new PropertyValueFactory<>("subject"));
		column2.setMinWidth(275.0);
		column2.setMaxWidth(275.0);
		workbookTable.getColumns().addAll(column2);
		
		//notebookTable
		notebookTable.setStyle("-fx-border-color: '#808080';" + "-fx-border-width: 1px;");
		columnN2.setCellValueFactory(new PropertyValueFactory<>("subject"));
		columnN2.setMinWidth(275.0);
		columnN2.setMaxWidth(275.0);
		notebookTable.getColumns().addAll(columnN2);
		
		//load workbooks
		notebookTable.getItems().clear();
		try {
			File directory = new File("D:\\workbooks\\");
			
		    FileFilter directoryFileFilter = new FileFilter() {
		        public boolean accept(File file) {
		            return file.isDirectory();
		        }
		    };
				
		    File[] directoryListAsFile = directory.listFiles(directoryFileFilter);
		    List<String> foldersInDirectory = new ArrayList<String>(directoryListAsFile.length);
		    for (File directoryAsFile : directoryListAsFile) {
		        foldersInDirectory.add(directoryAsFile.getName());
		        workbookTable.getItems().add(new NotebookClass(directoryAsFile.getName()));
		    }
		}catch(Exception e) {}
		
		//load notebooks
		notebookTable.getItems().clear();
		try {
			File directory = new File("D:\\notebooks\\");
			
		    FileFilter directoryFileFilter = new FileFilter() {
		        public boolean accept(File file) {
		            return file.isDirectory();
		        }
		    };
				
		    File[] directoryListAsFile = directory.listFiles(directoryFileFilter);
		    List<String> foldersInDirectory = new ArrayList<String>(directoryListAsFile.length);
		    for (File directoryAsFile : directoryListAsFile) {
		        foldersInDirectory.add(directoryAsFile.getName());
		        notebookTable.getItems().add(new NotebookClass(directoryAsFile.getName()));
		    }
		}catch(Exception e) {}
		
	}
	
	final char[] delimiters = { ' ', '_' };
	
	@FXML
	@SuppressWarnings("deprecation")
	public void initialize() {
		//showPages();
		
		visit.setOnAction(e -> {
			try {
				if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
				    Desktop.getDesktop().browse(new URI("D:/dummy2/index.html"));
				    System.out.println("visit");
				}
			}catch(Exception ex) {
				System.out.println("visit: " + ex);
			}
		});
		
		workbooks.getChildren().add(workbookTable);
		notebooks.getChildren().add(notebookTable);
		refresh();
		
		notebookTable.setOnMouseClicked(e -> {
			tile.getChildren().clear();
			NotebookClass person = (NotebookClass) notebookTable.getSelectionModel().getSelectedItem();
			System.out.println(person.getSubject());
			
			//display details
			try {
				//connecting
				URL url = new URL("http://127.0.0.1:5000/notebook/details/" + person.getSubject().replace(" ", "%20"));
				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
				conn.setRequestMethod("GET");
				conn.setRequestProperty("Accept", "JSON");
			
				//storing JSON object in a string
				BufferedReader br = new BufferedReader(new InputStreamReader(
						(conn.getInputStream())));
				String output,result = "";
				while ((output = br.readLine()) != null) {
					result += output.trim();
				}
				System.out.println("Server Output: " + result);
			
				JSONParser parse = new JSONParser(); 
				JSONObject jsonObject = (JSONObject)parse.parse(result);
				
				//loading user details
				bookId.setText(WordUtils.capitalizeFully(jsonObject.get("book_id").toString(), delimiters));
				subject.setText(WordUtils.capitalizeFully(jsonObject.get("subject").toString(), delimiters));
				teacher.setText(WordUtils.capitalizeFully(jsonObject.get("teacher").toString(), delimiters));
				className.setText(jsonObject.get("class").toString());
				date.setText(jsonObject.get("date").toString());
				
			}catch(Exception err) {
				System.out.println("NotebookTable: " + err);
			}
			
			showPages(person.getSubject());
			
		});
		
		workbookTable.setOnMouseClicked(e -> {
			tile.getChildren().clear();
			NotebookClass person = (NotebookClass) workbookTable.getSelectionModel().getSelectedItem();
			System.out.println(person.getSubject());
			
			//display details
			try {
				bookId.setText(WordUtils.capitalizeFully("23", delimiters));
				subject.setText(WordUtils.capitalizeFully("English", delimiters));
				teacher.setText(WordUtils.capitalizeFully("Ms. Marry", delimiters));
				className.setText("KG");
				date.setText("12-04-2019");
				
			}catch(Exception err) {
				System.out.println("WorkbookTable: " + err);
			}
			
			ScrollPane root = new ScrollPane();
	        root.setStyle("-fx-background-color: DAE6F3;");
	        tile.setPadding(new Insets(15, 15, 15, 15));
	        tile.setHgap(15);

	        tile.setMaxWidth(557.0);
	        tile.setMinHeight(271.0);
	        String path = "D:/workbooks/" + "ABCD" + "/";

	        File folder = new File(path);
	        File[] listOfFiles = folder.listFiles();

	        for (File file : listOfFiles) {
	                ImageView imageView;
	                imageView = createImageView(file);
	                tile.getChildren().addAll(imageView);
	                System.out.println("I was here");
	                
	                //opening image
	                imageView.setOnMouseClicked(ex -> {
	                	System.out.println("I was clicked");
	                	
	                	Image image;
						try {
							image = new Image(new FileInputStream(file));
							ImageView imageViewX = new ImageView(image);
							
							CanvasClass canvasClass = new CanvasClass();
		                	canvasClass.loadImage(imageViewX);
						} catch (FileNotFoundException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
	                    
	                });
	        }


	        root.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Horizontal
	        root.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical scroll bar
	        root.setFitToWidth(true);
	        root.setContent(tile);
			
		});
		
		addToMyAccount.setOnAction(e -> {
			
			subjectText.setText("");
			teacherText.setText("");
			className.setText("");
			
			//check for empty fields
        	if(subjectText.getText().trim().equalsIgnoreCase("") 
        			|| teacherText.getText().equalsIgnoreCase("")
        			|| className.getText().equalsIgnoreCase("")) {
        		
        		Alert emptyFields = new Alert(AlertType.WARNING);
        		emptyFields.setTitle("Message");
        		emptyFields.setHeaderText("Field(s) found empty");
        		emptyFields.setContentText("Please fill in the required details and try again.\n\n\n");
        		emptyFields.show();
        	}else {
        		try {
        			// connecting
    				URL url = new URL("http://127.0.0.1:5000/add/notebook/" + subjectText.getText().trim().replace(" ", "%20") + 
    						"/" + teacherText.getText().replace(" ", "%20") + "/" + className.getText().replace(" ", "%20"));
    				
    				HttpURLConnection conn = (HttpURLConnection) url.openConnection();
    				conn.setRequestMethod("GET");
    				conn.setRequestProperty("Accept", "JSON");
    				
    				//storing JSON object in a string
    				BufferedReader br = new BufferedReader(new InputStreamReader(
    						(conn.getInputStream())));
    				String output,result = "";
    				while ((output = br.readLine()) != null) {
    					result += output.trim();
    				}
    				
    				System.out.println("Server Output: " + result);
    				
    				JSONParser parse = new JSONParser(); 
    				JSONObject jsonObject = (JSONObject)parse.parse(result);
    				
    				if(jsonObject.get("message").toString().equalsIgnoreCase("True")) {
    					
    					System.out.println("Book added.");
    					Alert added = new Alert(AlertType.INFORMATION);
    					added.setTitle("Message");
    					added.setHeaderText("Added");
    					added.setContentText("New Notebook added successfully."
								+ "\n\n\n");
    					added.show();
    					
    					//make new folder
    					System.out.println("Enter the path to create a directory: ");
    				    String path = "D:\\notebooks\\";
    				    System.out.println("Enter the name of the desired a directory: ");
    				    path = path+ subjectText.getText().toLowerCase().trim();
    				    //Creating a File object
    				    File file = new File(path);
    				    //Creating the directory
    				    boolean bool = file.mkdir();
    				    if(bool){
    				       System.out.println("Directory created successfully");
    				    }else{
    				       System.out.println("Sorry couldn’t create specified directory");
    				    }
    					
    					refresh();
    				}else {
    					Alert wrongCredentials = new Alert(AlertType.ERROR);
    					wrongCredentials.setTitle("Message");
    					wrongCredentials.setHeaderText("Failed");
    					wrongCredentials.setContentText(jsonObject.get("message").toString()
								+ "\n\n\n");
    					wrongCredentials.show();
    				}
        		}catch(Exception exp) {
        			System.out.println("From addToMyAccount: " + exp);
        		}
        	}
		});
		
		pages.getChildren().add(tile);
		pages.setMaxHeight(271.0);
		
		
		addPage.setOnAction(e -> {
			CanvasClass page = new CanvasClass();
			CanvasClass.background.getChildren().clear();
			//CanvasClass.penSize.getItems().clear();
			//CanvasClass.format.getItems().clear();
			page.start();
		});
		
	}
	
	public static void showPages(String subject) {
		ScrollPane root = new ScrollPane();
        root.setStyle("-fx-background-color: DAE6F3;");
        tile.setPadding(new Insets(15, 15, 15, 15));
        tile.setHgap(15);

        tile.setMaxWidth(557.0);
        tile.setMinHeight(271.0);
        String path = "D:/notebooks/" + subject + "/";

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();

        for (File file : listOfFiles) {
                ImageView imageView;
                imageView = createImageView(file);
                tile.getChildren().addAll(imageView);
                System.out.println("I was here");
                
                //opening image
                imageView.setOnMouseClicked(e -> {
                	System.out.println("I was clicked");
                	
                	Image image;
					try {
						image = new Image(new FileInputStream(file));
						ImageView imageViewX = new ImageView(image);
						
						CanvasClass canvasClass = new CanvasClass();
	                	canvasClass.loadImage(imageViewX);
					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
                    
                });
        }


        root.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // Horizontal
        root.setVbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED); // Vertical scroll bar
        root.setFitToWidth(true);
        root.setContent(tile);
	}
	
	public static ImageView createImageView(final File imageFile) {
        
		ImageView imageView = null;
        try {
            final Image image = new Image(new FileInputStream(imageFile), 150, 0, true,
                    true);
            imageView = new ImageView(image);
            imageView.setFitWidth(150);
            imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    if(mouseEvent.getButton().equals(MouseButton.PRIMARY)){

                        if(mouseEvent.getClickCount() == 2){
                            try {
                                BorderPane borderPane = new BorderPane();
                                ImageView imageView = new ImageView();
                                Image image = new Image(new FileInputStream(imageFile));
                                imageView.setImage(image);
                                imageView.setStyle("-fx-background-color: BLACK");
                                imageView.setPreserveRatio(true);
                                imageView.setSmooth(true);
                                imageView.setCache(true);
                                borderPane.setCenter(imageView);
                                borderPane.setStyle("-fx-background-color: BLACK");
                                Scene scene = new Scene(borderPane,Color.BLACK);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                }
            });
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
    }
	
}

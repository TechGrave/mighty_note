package mighty_note;

import java.io.BufferedReader;
import java.io.InputStreamReader;
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


public class LoginPageController {
	
	@FXML
	TextField loginEmail;
	
	@FXML
	PasswordField loginPassword;
	
	@FXML
	Button login;
	
	@FXML
	TextField username;
	
	@FXML
	TextField email;
	
	@FXML
	PasswordField password;
	
	@FXML
	PasswordField confirmPassword;
	
	@FXML
	Button signup;
	
	@FXML
	public void initialize() {
		
		
		login.setOnAction(e -> {
			// check for empty fields
        	if(loginEmail.getText().trim().equalsIgnoreCase("") 
        			|| loginPassword.getText().equalsIgnoreCase("")) {
        		
        		Alert emptyFields = new Alert(AlertType.WARNING);
        		emptyFields.setTitle("Message");
        		emptyFields.setHeaderText("Field(s) found empty");
        		emptyFields.setContentText("Email ID or password is not entered. Please fill in the"
						+ "required details and try again.\n\n\n");
        		emptyFields.show();
        	}else {
        		try {
        			// connecting
    				URL url = new URL("http://127.0.0.1:5000/login/" + loginEmail.getText().trim() + 
    						"/" + loginPassword.getText());
    				
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
    					
    					System.out.println("Logged In");
    					Driver.connectedEmail = email.getText().toLowerCase().trim();
    					
    					try {
    						
    						Parent loader = FXMLLoader.load(getClass().getResource("/fxml/home_page.fxml"));
    						
    						Scene scene = login.getScene();
    						Window window = scene.getWindow();
    						Stage stage = (Stage) window;
    						
    						login.getScene().setRoot(loader);
    						
    					}catch(Exception exp) {
    						System.out.println("Going to HomePage: " + exp);
    					}
    					
    				}else {
    					Alert wrongCredentials = new Alert(AlertType.ERROR);
    					wrongCredentials.setTitle("Message");
    					wrongCredentials.setHeaderText("Login Failed");
    					wrongCredentials.setContentText(jsonObject.get("message").toString()
								+ "\n\n\n");
    					wrongCredentials.show();
    				}
        		}catch(Exception exp) {
        			System.out.println("From LoginPageController: " + exp);
        		}
        	}
		});
		
		signup.setOnAction(e -> {
			// check for empty fields
        	if(username.getText().trim().equalsIgnoreCase("") || email.getText().equalsIgnoreCase("")
        			|| password.getText().equalsIgnoreCase("") 
        			|| confirmPassword.getText().equalsIgnoreCase("")) {
        		
        		Alert emptyFields = new Alert(AlertType.WARNING);
        		emptyFields.setTitle("Message");
        		emptyFields.setHeaderText("Field(s) found empty");
        		emptyFields.setContentText("Please fill in the required details and try again.\n\n\n");
        		emptyFields.show();
        	}else {
        		try {
        			//connecting
    				URL url = new URL("http://127.0.0.1:5000/signup/" + username.getText().replace(" ", "%20") + "/" 
    							+ email.getText().trim() + "/" + password.getText() +
    						"/" + confirmPassword.getText());
    				
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
    					
    					System.out.println("Signed Up");
    					Alert failed = new Alert(AlertType.INFORMATION);
    					failed.setTitle("Message");
    					failed.setHeaderText("Signup Successful");
    					failed.setContentText("Enter your email ID and password to login.\n\n\n");
    					failed.show();
    				} else {
    					Alert failed = new Alert(AlertType.ERROR);
    					failed.setTitle("Message");
    					failed.setHeaderText("Signup Failed");
    					failed.setContentText(jsonObject.get("message").toString() + "\n\n\n");
    					failed.show();
    				}
        		}catch(Exception exp) {
        			System.out.println("Signup: " + exp);
        		}
        	}
		});
	}
}

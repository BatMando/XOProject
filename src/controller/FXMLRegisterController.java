/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Helper.AskDialog;
import Helper.NavigationController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author peter
 */
public class FXMLRegisterController implements Initializable {

    @FXML
    private TextField txtEmail;
    
    @FXML
    private TextField txtUsername;
    @FXML
    private ImageView backBtn;
    @FXML
    private Button btnBack;
    @FXML
    private Button registerBtn;
    
    private Thread thread;
    StringTokenizer token;
    @FXML
    private Label errorLabel;
    @FXML
    private PasswordField txtPassword;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void backToLoginPage(ActionEvent event) {
       NavigationController onlineModeBtn = new NavigationController("/view/FXMLLogin.fxml");
        onlineModeBtn.navigateTo(event);
    }

   

    @FXML
    private void registerBtnPressed(ActionEvent event) {
        
        String userName = txtUsername.getText().trim();
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();
        
        //check if there's empty data
        if(userName.isEmpty() || email.isEmpty() || password.isEmpty()){
                System.out.println("there is missing data!");
                errorLabel.setText("Enter all required fields");
        }
        else if(userName.length()>10||email.length()>25||password.length()>10||password.length()<4){
            System.out.println("some data is too large!");
            errorLabel.setText("Enter a valid data");
        }
        else{
            //check for email validation
            String regex = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(txtEmail.getText());
            if(!matcher.matches()){
                System.out.println("Enter a valid email!");
                errorLabel.setText("Enter a valid email!");
            }
            else{
                System.out.println(userName+" - "+email+" - "+password);
                
                FXMLHomeScreenController.ps.println("SignUp###"+txtUsername.getText()+"###"+txtEmail.getText()+"###"+txtPassword.getText());
                
                thread = new Thread(){
                    @Override
                    public void run() {
                        try {
                            String respond = FXMLHomeScreenController.dis.readLine();
                            token = new StringTokenizer(respond,"###");
                            String msg = token.nextToken();
                            
                            System.out.println(msg);
                            
                            switch(msg){
                                case "already signed-up":
                                    System.out.println(msg);
                                    Platform.runLater(()->{
                                       errorLabel.setText("This Email is "+msg);
                                    });
                                    break;
                                case "Registered Successfully":
                                    String playerData = FXMLHomeScreenController.dis.readLine();
                                    System.out.println(playerData);
                                    StringTokenizer dataToken = new StringTokenizer(playerData,"###");
                                    FXMLHomeScreenController.hash.put("username", dataToken.nextToken());
                                    FXMLHomeScreenController.hash.put("email", dataToken.nextToken());
                                    FXMLHomeScreenController.hash.put("score","0");
                                    Platform.runLater(new Runnable() {
                                        @Override
                                        public void run() {
                                            backToLoginPage(event);
                                        }
                                    });
                                    
                                    break;
                            }
                        } catch (IOException ex) {
                            Platform.runLater(() -> {
                            try {
                                close();                            
                                FXMLHomeScreenController.socket.close();
                                FXMLHomeScreenController.dis.close();
                                FXMLHomeScreenController.ps.close();
                            } catch (IOException ex1) {
                                Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex1);
                            }

                            });
                            
                        }
                    }
                    
                };
                thread.start();

            }
            
        }
        
    }
    private void close(){
        System.out.println("Server Colsed");
                            
        AskDialog  serverIssueAlert  = new AskDialog();
        serverIssueAlert.serverIssueAlert("There is issue in connection game page will be closed");
        try {
            gotoHell();
        } catch (IOException ex) {
            Logger.getLogger(FXMLFindPlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     
      public void gotoHell() throws IOException {
        
        System.out.println("back to Hell");
        Parent menu_parent = FXMLLoader.load(getClass().getResource("/view/FXMLHome.fxml"));
        Scene SceneMenu = new Scene(menu_parent);
        Stage stage = (Stage)registerBtn.getParent().getScene().getWindow();
        stage.setScene(SceneMenu);
        stage.show();
    }
    
}

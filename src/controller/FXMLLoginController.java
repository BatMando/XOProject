/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Helper.AskDialog;
import Helper.NavigationController;
import static controller.FXMLHomeScreenController.socket;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author mando
 */
public class FXMLLoginController implements Initializable {

    @FXML
    private ImageView backBtn;
    @FXML
    private Button btnBack;
    @FXML
    private TextField txtEmail;
    
    @FXML
    private Button loginBtn;
    @FXML
    private Button registerBtn;

    private Thread thread;
    StringTokenizer token;
    @FXML
    private Label txtAlret;
    @FXML
    private PasswordField txtPassword;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void backToMainPage(ActionEvent event) {
         try {
                System.out.println("Home screen");
                FXMLHomeScreenController.socket.close();
                FXMLHomeScreenController.dis.close();
                FXMLHomeScreenController.ps.close();
            } catch (IOException ex) {
                Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex);
            }
        NavigationController btnback = new NavigationController("/view/FXMLHome.fxml");
        btnback.navigateTo(event);
    }

    private void goToOnlineMode(ActionEvent event) {
        NavigationController onlineModeBtn = new NavigationController("/view/FXMLOnlineMode.fxml");
        onlineModeBtn.navigateTo(event);
    }

    @FXML
    private void loginBtnPressed(ActionEvent event) {
        
        String regex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(regex);     
        Matcher matcher = pattern.matcher(txtEmail.getText());
        String email = txtEmail.getText().trim();
        String password = txtPassword.getText().trim();
        if(email.isEmpty() || password.isEmpty()){
            Platform.runLater(()->{
              txtAlret.setText("Empty Fields is Required");
             });
        }else if(!matcher.matches()){
            Platform.runLater(()->{
              txtAlret.setText("Please enter a valid email");
             }); 
        }else{
            txtAlret.setText("");
            FXMLHomeScreenController.ps.println("SignIn###"+txtEmail.getText()+"###"+txtPassword.getText());
            thread =  new Thread(){ 
            String state,playerData;
            @Override
            public void run(){
                try {
                    state = FXMLHomeScreenController.dis.readLine();
                    token = new StringTokenizer(state,"###");
                    String receivedState = token.nextToken();
                    System.out.println("sign in page "+receivedState);
                    switch(receivedState){
                        case "Logged in successfully":
                            playerData = FXMLHomeScreenController.dis.readLine();
                            System.out.println("player data "+playerData);

                            StringTokenizer token2 = new StringTokenizer(playerData,"###");
                            FXMLHomeScreenController.hash.put("email", token2.nextToken());
                            FXMLHomeScreenController.hash.put("password",token2.nextToken());
                            FXMLHomeScreenController.hash.put("score", token2.nextToken());
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                  goToOnlineMode(event);
                                }
                            });   
                            break;
                        case "This Email is alreay sign-in":
                            System.out.println("already sign in before run later");
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    txtAlret.setText(receivedState);
                                }
                            });                                                              
                            break;
                        case "Email is incorrect":
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    txtAlret.setText(receivedState);
                                }
                            });                              
                            break;
                        case "Password is incorrect":
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    txtAlret.setText(receivedState);
                                }
                            });                               
                            break;
                        case "Connection issue, please try again later":
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    txtAlret.setText(receivedState);
                                }
                            });
                            break;
                        default :
                            Platform.runLater(new Runnable() {
                                @Override
                                public void run() {
                                    txtAlret.setText("Please Enter valid Credentials");
                                }
                            });
                    }
                }catch (IOException ex) {
                    Platform.runLater(() -> {
                        try {
                            AskDialog  serverIssueAlert  = new AskDialog();
                            serverIssueAlert.serverIssueAlert("There is issue in connection game page will be closed");
                
                            thread.stop();
                            FXMLHomeScreenController.socket.close();
                            FXMLHomeScreenController.dis.close();
                            FXMLHomeScreenController.ps.close();
                        } catch (IOException ex1) {
                            Logger.getLogger(FXMLLoginController.class.getName()).log(Level.SEVERE, null, ex1);
                        }

                        });
                    System.out.println("111111111");
                }
            }};   
            thread.start();
        }
    }

    @FXML
    private void goToRegisterScreen(ActionEvent event) {
        NavigationController onlineModeBtn = new NavigationController("/view/FXMLRegister.fxml");
        onlineModeBtn.navigateTo(event);
    }
    
}

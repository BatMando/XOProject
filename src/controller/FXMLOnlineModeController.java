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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author peter
 */
public class FXMLOnlineModeController implements Initializable {

    @FXML
    private Button findPlayerBtn;
    @FXML
    private Button leaderboardBtn;
    @FXML
    private Button gameRecordsBtn;
    @FXML
    private ImageView backBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Text scoreLabel;
    String score;
    Thread thread;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        score = FXMLHomeScreenController.hash.get("score");
        scoreLabel.setText(score);
        // TODO
    }    

    @FXML
    private void navigateToFindPlayerScreen(ActionEvent event) {
         NavigationController navigateToFindPlayers = new NavigationController("/view/FXMLFindPlayersScreen.fxml");
        navigateToFindPlayers.navigateTo(event);
    }

    @FXML
    private void navigateToLeaderboardScreen(ActionEvent event) {
    }

    @FXML
    private void navigateToRecordedGamesScreen(ActionEvent event) {
        NavigationController navigateToRecordedGames = new NavigationController("/view/FXMLRecordsScreen.fxml");
        navigateToRecordedGames.navigateToRecordList(event,"online-mode");
    }

 
    @FXML
    private void Logout(ActionEvent event) {
         System.out.println("backToMainPage: called");
        System.out.println("Emial " + FXMLHomeScreenController.hash.get("email"));
        if(FXMLHomeScreenController.hash.get("email")!= null){
           AskDialog  logoutAlert  = new AskDialog();
           Boolean logedOut  = logoutAlert.alert("Are you sure you want to logout","Alert Issue");
           if(logedOut){
               System.out.println("Send to server to logout");
               FXMLHomeScreenController.ps.println("logout###"+FXMLHomeScreenController.hash.get("email"));
               
               try {
                   FXMLHomeScreenController.socket.close();
                   FXMLHomeScreenController.dis.close();
                   FXMLHomeScreenController.ps.close();
               } catch (IOException ex) {
                   Logger.getLogger(FXMLOnlineModeController.class.getName()).log(Level.SEVERE, null, ex);
               }
               NavigationController btnback = new NavigationController("/view/FXMLHome.fxml");
               btnback.navigateTo(event);
           }
        }
        
    }
    
}

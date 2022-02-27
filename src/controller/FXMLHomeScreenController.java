/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;
import helper.NavigationController;

import helper.CustomDialog;

import java.util.prefs.Preferences;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 *
 * @author Thoraya Hamdy
 */
public class FXMLHomeScreenController implements Initializable {

    @FXML
    private Button singleModeBtn;
    @FXML
    private Button oneVsOneBtn;
    @FXML
    private Button onlineModeBtn;
    @FXML
    private Button gameRecordsBtn;
    
   Preferences prefs ;
    int checkname;
    
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(FXMLHomeScreenController.class);

    }    
    
    @FXML
    public void navigateToOneVSOneScreen(ActionEvent event) {
        
        System.out.println("changeSceneToTwoPlayers: called");
           CustomDialog fristPlayerNameDialog = new CustomDialog();
           Boolean isCancled = fristPlayerNameDialog.displayDialog("Enter First Player Name");
           prefs.put("fristPlayer", fristPlayerNameDialog.getName());
           prefs.putInt("firstPlayerScore",0);
           System.out.println("fristPlayer" +fristPlayerNameDialog.getName() );
           if(!isCancled){
              CustomDialog secondtPlayerNameDialog = new CustomDialog();
             Boolean isSecondCancled = secondtPlayerNameDialog.displayDialog("Enter Second Player Name"); 
             System.out.println("secondPlayer" +secondtPlayerNameDialog.getName() );
             prefs.put("secondPlayer", secondtPlayerNameDialog.getName());
             prefs.putInt("secondPlayerScore",0);
             NavigationController nav = new NavigationController("/view/FXMLOneVSOneMode.fxml");
             nav.navigateTo(event);
           }
    }
    
    @FXML
    public void navigateToChooseLevelScreen(ActionEvent event) {
        NavigationController navigateToSinglePlay = new NavigationController("/view/FXMLChooseLevel.fxml");
        navigateToSinglePlay.navigateTo(event);
          
    }
    
}

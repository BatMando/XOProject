/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;

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
    private Button btnBack;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void navigateToFindPlayerScreen(ActionEvent event) {
    }

    @FXML
    private void navigateToLeaderboardScreen(ActionEvent event) {
    }

    @FXML
    private void navigateToRecordedGamesScreen(ActionEvent event) {
    }

    @FXML
    private void backToMainPage(ActionEvent event) {
    }
    
}

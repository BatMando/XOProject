/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Helper.NavigationController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author peter
 */
public class FXMLRegisterController implements Initializable {

    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPassword;
    @FXML
    private TextField txtUsername;
    @FXML
    private ImageView backBtn;
    @FXML
    private Button btnBack;
    @FXML
    private Button registerBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    @FXML
    private void backToMainPage(ActionEvent event) {
        NavigationController btnback = new NavigationController("/view/FXMLHome.fxml");
        btnback.navigateTo(event);
    }

    @FXML
    private void goToOnlineMode(ActionEvent event) {
    }
    
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Helper.NavigationController;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.ImageView;

/**
 * FXML Controller class
 *
 * @author mando
 */
public class FXMLRecordsScreenController implements Initializable {

    @FXML
    private ListView<String> recordsListView;
    @FXML
    private ImageView backBtn;
    @FXML
    private Button back;
    String [] list = {"one","two"};

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recordsListView.getItems().addAll(list);
        recordsListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>(){
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                System.out.println(recordsListView.getSelectionModel().getSelectedItem()); //To change body of generated methods, choose Tools | Templates.
            }
        
        });
       
        // TODO
    }    

    @FXML
    private void backToMainPage(ActionEvent event) {
        System.out.println("backToMainPage: called");
        NavigationController btnback = new NavigationController("/view/FXMLHome.fxml");
        btnback.navigateTo(event);
    }
    
}

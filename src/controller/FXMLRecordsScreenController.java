/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Helper.NavigationController;
import java.io.File;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

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

   
    @FXML
    private ScrollPane recordScroll;
    
    public static String recordedGameFileName;
    String listType;
    Socket socket;
    ArrayList <String> fileNames = new ArrayList<>();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        recordsListView.applyCss();
        System.out.println(listType);  
        
       
        // TODO
    }    
    
    private void getFilesName (String listType){
        String dir="";
        if(listType.equals("local-mode")){
               dir = "savedLocalGame";
        }else if (listType.equals("online-mode")){
              dir = "savedOnlineGame";
        }
        
        File folder = new File("record/"+dir);
        System.out.println("record/"+dir);

        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
         if (listOfFiles[i].isFile()) {
             fileNames.add(listOfFiles[i].getName());
             System.out.println(listOfFiles[i].getName());
            } 
         }
        
        recordsListView.getItems().addAll(fileNames);
        recordScroll.setContent(recordsListView);
    }

    
    @FXML
    private void backToMainPage(ActionEvent event) {
        System.out.println("backToMainPage: called");
        NavigationController btnback = new NavigationController("/view/FXMLHome.fxml");
        btnback.navigateTo(event);
    }
    
    public void  setType(String  stringListType){ 
         listType = stringListType;
         
         System.out.println(listType);
         getFilesName(listType); 
               
    }
    
    
    
    public void changeSceneToWatchGame(MouseEvent event) {
        
        System.out.println("changeSceneToWatchGame: called");
        System.out.println(listType);
        if(listType.equals("local-mode")){
        NavigationController navigateToListPage = new NavigationController("/view/FXMLWatchGameScreen.fxml");
        navigateToListPage.navigateToWatchGame(event,"local-mode"); 
            
        }else if (listType.equals("online-mode")){
        NavigationController navigateToListPage = new NavigationController("/view/FXMLWatchGameScreen.fxml");
        navigateToListPage.navigateToWatchGame(event,"online-mode"); 
        }
    
    }
    
    
    @FXML
     public void selectedItem()
    {     
            recordsListView.setOnMouseClicked((MouseEvent event) -> {
            //System.out.println(recordsListView.getSelectionModel().getSelectedItems().toString());
            // recordsListView.getSelectionModel().clearSelection();

             String selectedItem = recordsListView.getSelectionModel().getSelectedItems().toString();
            
             if(selectedItem !="[]") {
                selectedItem=selectedItem.substring(1);
                int  index=selectedItem.indexOf("]");
                if(index!=-1)
                {
                 selectedItem=selectedItem.substring(0,index);
                 recordedGameFileName=selectedItem;
                 System.out.println(recordedGameFileName);
                 changeSceneToWatchGame(event);
                }
                        
              
                //System.out.println(recordedGameFileName);
                
             
             }    
        });
          
    }
}
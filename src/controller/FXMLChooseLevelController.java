/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Helper.ReadWriteHelper;
import Helper.AskDialog;
import Helper.CustomDialog;
import Helper.NavigationController;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

/**
 * FXML Controller class
 *
 * @author mando
 */
public class FXMLChooseLevelController implements Initializable {

    @FXML
    private Button backBtn;
    
    static boolean isrecord=false;
    Preferences prefs;
    
    @FXML
    private Button hardBtn;
    @FXML
    private Button EasyBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        prefs = Preferences.userNodeForPackage(FXMLHomeScreenController.class);

    }

    @FXML
    public void backToMainPage(ActionEvent event) {
        System.out.println("backToMainPage: called");
        NavigationController btnback = new NavigationController("/view/FXMLHome.fxml");
        btnback.navigateTo(event);
    }
    @FXML
    public void navigateToEasyMode(ActionEvent event) {
        try {
            System.out.println("navigateToEasyMode: called");
            if (prefs.nodeExists("/controller")) {
                String s = prefs.get("username", "");
                System.out.println(s.length());
                CustomDialog cd = new CustomDialog();
                Boolean isCancelled = cd.displayDialog("Enter Your Name");
                if(!isCancelled){
                prefs.put("username", cd.getName());
                 AskDialog isrecoredGame = new AskDialog();
                 Boolean check=isrecoredGame.alert("Do you want to record game ?");
                     if(check)
                       {
                        ReadWriteHelper.createFile("local-mode");
                        ReadWriteHelper.writeFile(prefs.get("username","")+".");
                        ReadWriteHelper.writeFile("user"+".");
                        isrecord=true;
                        }
                NavigationController navToEasy = new NavigationController("/view/FXMLOneVSComputerMode.fxml");
                navToEasy.navigateTo(event);
                
                }
            }else
            {
                  AskDialog isrecoredGame = new AskDialog();

                  Boolean check=isrecoredGame.alert("Do you want to record game ?");
                  if(check)
                  {
                   ReadWriteHelper.createFile("local-mode");
                   ReadWriteHelper.writeFile(prefs.get("username", "")+".");
                   ReadWriteHelper.writeFile("user"+".");
                     isrecord=true;
                  }
                   
              NavigationController navToEasy = new NavigationController("/view/FXMLOneVSComputerMode.fxml");
                navToEasy.navigateTo(event);
                
            }
            

        } catch (BackingStoreException ex) {
            Logger.getLogger(FXMLChooseLevelController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    @FXML
    public void navigateToHardMode(ActionEvent event) {
        try {
            System.out.println("navigateToHardMode: called");
            if (prefs.nodeExists("/controller")) {
                String s = prefs.get("username", "");
                System.out.println(s.length());
                CustomDialog cd = new CustomDialog();
                Boolean isCancelled = cd.displayDialog("Enter Your Name");
                if(!isCancelled){
                prefs.put("username", cd.getName());
                AskDialog isrecoredGame = new AskDialog();
                 Boolean check=isrecoredGame.alert("Do you want to record game ?");
                     if(check)
                       {
                        ReadWriteHelper.createFile("local-mode");
                        ReadWriteHelper.writeFile(prefs.get("username","")+".");
                        ReadWriteHelper.writeFile("user"+".");
                        isrecord=true;
                        }
                NavigationController navToEasy = new NavigationController("/view/FXMLOneVSComputerModeHard.fxml");
                navToEasy.navigateTo(event);
                
                }
            }else
            {
                  AskDialog isrecoredGame = new AskDialog();

                  Boolean check=isrecoredGame.alert("Do you want to record game ?");
                  if(check)
                  {
                   ReadWriteHelper.createFile("local-mode");
                   ReadWriteHelper.writeFile(prefs.get("username", "")+".");
                   ReadWriteHelper.writeFile("user"+".");
                     isrecord=true;
                  }
                   
              NavigationController navToEasy = new NavigationController("/view/FXMLOneVSComputerModeHard.fxml");
                navToEasy.navigateTo(event);
                
            }

        } catch (BackingStoreException ex) {
            Logger.getLogger(FXMLChooseLevelController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
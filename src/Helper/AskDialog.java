/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Helper;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.stage.StageStyle;
/**
 *
 * @author Toba
 */


public class AskDialog {
    public AskDialog() {};
    boolean check = false;

    public Boolean alert(String s) {
        ButtonType Yes = new ButtonType("Yes");
        ButtonType No = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert a = new Alert(Alert.AlertType.NONE);
        //a.initStyle(StageStyle.TRANSPARENT);
        a.setTitle("Alert ASk");
        a.getDialogPane().getButtonTypes().addAll(Yes, No);
        a.setHeaderText(s);
        
        DialogPane dialogPane = a.getDialogPane();
        dialogPane.getStylesheets().add(
        getClass().getResource("/CSS/cssStyling.css").toExternalForm());
        dialogPane.getStyleClass().add("infoDialog");
        
        a.showAndWait();

        if (a.getResult() == Yes) {
            check = true;
            System.out.println("alertyes");
        } else if (a.getResult() == No) {
            check = false;
            System.out.println("alertNo");
        }
        return check;
    }


    public boolean alert(String message, String title) {

        ButtonType yes = new ButtonType("Yes");
        ButtonType no = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setTitle(title);
        a.getDialogPane().getButtonTypes().addAll(yes, no);
        a.setHeaderText(message);
        
        DialogPane dialogPane = a.getDialogPane();
        dialogPane.getStylesheets().add(
        getClass().getResource("/CSS/cssStyling.css").toExternalForm());
        dialogPane.getStyleClass().add("infoDialog");
        
        a.showAndWait();

        if (a.getResult() == yes) {
            return true;
        }
        return false;
    }

    public void inValidIp(String s){
        ButtonType Ok = new ButtonType("Ok"); 
         Alert alert = new Alert(Alert.AlertType.NONE); 
        alert.setTitle("Alert ASk");
        alert.getDialogPane().getButtonTypes().addAll(Ok);
        alert.setHeaderText(s);
        
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(
        getClass().getResource("/CSS/cssStyling.css").toExternalForm());
        dialogPane.getStyleClass().add("infoDialog");
        alert.showAndWait();
        
    }
    
    public void serverIssueAlert(String message){  
        ButtonType yes = new ButtonType("Yes"); 
        Alert a = new Alert(Alert.AlertType.NONE); 
        a.setTitle("Alert Issue");
        a.getDialogPane().getButtonTypes().add(yes);
        a.setHeaderText(message);
//
//         //a.setContentText(s);
        DialogPane dialogPane = a.getDialogPane();
        dialogPane.getStylesheets().add(
        getClass().getResource("/CSS/cssStyling.css").toExternalForm());
        dialogPane.getStyleClass().add("infoDialog");

        a.showAndWait();    
    }

}
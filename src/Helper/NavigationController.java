package Helper;

import controller.FXMLRecordsScreenController;
import controller.FXMLVideoController;
import controller.FXMLWatchGameScreenController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Toba
 */

public class NavigationController {
      private String source;

     public NavigationController(){

    }

    public NavigationController(String source){
        this.source = source;
    }

    public void navigateTo(ActionEvent event){
         //get scene
        Parent buttonParent;
        try {
         buttonParent = FXMLLoader.load(getClass().getResource(source));
             //generate new scene
        Scene buttonScene = new Scene(buttonParent);

        //get stage information
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

        window.setTitle("Home");
        window.setScene(buttonScene);
        window.show();
        } catch (IOException ex) {
            Logger.getLogger(NavigationController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }





    public void navigateToRecordList(ActionEvent event,String listType){
         //get scene
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(source));
            Parent root = (Parent)fxmlLoader.load();   
           FXMLRecordsScreenController controller = fxmlLoader.<FXMLRecordsScreenController>getController();
           controller.setType(listType);

            Scene buttonScene = new Scene(root);
           fxmlLoader.setController(controller);
           //get stage information
           Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

           window.setTitle("List Player");
           window.setScene(buttonScene);
           window.show();
        } catch (IOException ex) {
            System.out.println("handle button back catch");
            Logger.getLogger(NavigationController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }



    public void navigateToWatchGame(MouseEvent event,String listType){
         //get scene
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(source));
            Parent root = (Parent)fxmlLoader.load();   

           FXMLWatchGameScreenController controller = fxmlLoader.<FXMLWatchGameScreenController>getController();
           controller.setType(listType);
           Scene buttonScene = new Scene(root);
           fxmlLoader.setController(controller);
            
           //get stage information

           Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
           window.setTitle("List Player");
           window.setScene(buttonScene);
           window.show();
        } catch (IOException ex) {
            System.out.println("handle button back catch");
            Logger.getLogger(NavigationController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
    public void displayVideo(String playerWinnerOrNot, String title){
        try {
            //get scene
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(source));
            Parent root = (Parent)fxmlLoader.load();        
            FXMLVideoController controller = fxmlLoader.<FXMLVideoController>getController();
            controller.setType(playerWinnerOrNot);
            //generate new scene
            Scene RegisterScene = new Scene(root);
            fxmlLoader.setController(controller);
           
            //get stage information
            Stage window = new Stage();
            window.initModality(Modality.APPLICATION_MODAL);
            window.setTitle(title);
            window.setScene(RegisterScene);
            window.setMinHeight(280);
            window.setMinWidth(500);
            
            window.setResizable(false);
            window.show();
            
            
                PauseTransition wait = new PauseTransition(Duration.seconds(10));
                            wait.setOnFinished((e) -> {
                                /*YOUR METHOD*/
                                window.close();
                                //btn.setDisable(false);
                                wait.playFromStart();
                            });
                            wait.play();
                            
                window.setOnCloseRequest((event) -> {
                    System.out.println("closing vid");
                     FXMLVideoController.mp.stop();
                });
        } catch (IOException ex) {
            Logger.getLogger(NavigationController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    


}
package Helper;


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





    public void handleButtonBack(ActionEvent event,String listType){
         //get scene
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(source));
            Parent root = (Parent)fxmlLoader.load();   
          //  ListRecordedGamesController controller = fxmlLoader.<ListRecordedGamesController>getController();
           // controller.setType(listType);

            Scene buttonScene = new Scene(root);
          //  fxmlLoader.setController(controller);
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



    public void handleButtonBack(MouseEvent event,String listType){

         //get scene
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(source));
            Parent root = (Parent)fxmlLoader.load();   

          // WatchGameController controller = fxmlLoader.<WatchGameController>getController();
           // controller.setType(listType);


           // Scene buttonScene = new Scene(root);
            //fxmlLoader.setController(controller);
           //get stage information

           Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();

           window.setTitle("List Player");



         //  window.setScene(buttonScene);
           window.show();
        } catch (IOException ex) {
            System.out.println("handle button back catch");
            Logger.getLogger(NavigationController.class.getName()).log(Level.SEVERE, null, ex);
        }


    }


}
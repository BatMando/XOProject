package Helper;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
/**
 *
 * @author Toba
 */
public class CustomDialog {

   private String name = "";
   private ButtonType buttonTypeOk,buttonTypeCancel;
    public CustomDialog(){}

    public CustomDialog(ButtonType buttonTypeOk,ButtonType buttonTypeCancel){
        this.buttonTypeOk = buttonTypeOk;
        this.buttonTypeCancel = buttonTypeCancel;
    }
     public String getName()
     {
      return name;

     }

    public boolean displayDialog(String message){
        boolean isCancled = false;

       Alert alert = new Alert(Alert.AlertType.NONE);
       TextField content = new TextField();
       alert.setTitle("Alert");
       alert.setHeaderText(message);
       alert.getDialogPane().setContent(content);

        ButtonType buttonTypeOk = new ButtonType("Ok");
        ButtonType buttonTypeCancel = new ButtonType("Cancel", 
        ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOk,
                buttonTypeCancel);


        Optional<ButtonType> result = alert.showAndWait();
       if(result.get() == buttonTypeOk){
         name = content.getText().trim();
          if(name.isEmpty()){
            displayDialog("Name is required");
            System.out.println("Please Enter your name");
            isCancled = true;
          }else{
              isCancled = false;
           System.out.println(name);
          }
       }else if(result.get() == buttonTypeCancel){
           isCancled = true;
       }

       if(!name.isEmpty() &&isCancled ){
               isCancled = false;
           }
       return isCancled;

    }   

    public Optional<ButtonType> displayReplayDialog(String message ){

       Alert alert = new Alert(Alert.AlertType.NONE);
       TextField content = new TextField();
       alert.setTitle("Alert");
       alert.setHeaderText(message);
       alert.getDialogPane().setContent(content);

         buttonTypeOk = new ButtonType("Ok");
         buttonTypeCancel = new ButtonType("Cancel", 
        ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(buttonTypeOk,
                buttonTypeCancel);


        Optional<ButtonType> result = alert.showAndWait();
       return result;

    }

}
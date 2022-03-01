package controller;

import Helper.AskDialog;
import Helper.NavigationController;
import Helper.CustomDialog;
import Helper.ReadWriteHelper;
import static controller.FXMLChooseLevelController.isrecord;
import java.util.prefs.Preferences;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;


public class FXMLHomeScreenController implements Initializable {


    @FXML
    private Button singleModeBtn;

   Preferences prefs ;
    int checkname;
    @FXML
    private Button oneVSOneBtn;
    @FXML
    private Button onlineModeBtn;
    @FXML
    private Button gameRecordsBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
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
            AskDialog isrecoredGame = new AskDialog();
            Boolean check=isrecoredGame.alert("Do you want to record game ?");
            if(check)
            {
                ReadWriteHelper.createFile("local-mode");
                ReadWriteHelper.writeFile(prefs.get("fristPlayer","")+".");
                ReadWriteHelper.writeFile(prefs.get("secondPlayer", "") + ".");
                ReadWriteHelper.writeFile("fristPlayer"+".");
                ReadWriteHelper.writeFile("secondPlayer"+".");
                isrecord=true;
            }
             NavigationController nav = new NavigationController("/view/FXMLOneVSOneMode.fxml");
             nav.navigateTo(event);
           }
    }

    @FXML
    public void navigateToChooseLevelScreen(ActionEvent event) {
        NavigationController navigateToSinglePlay = new NavigationController("/view/FXMLChooseLevel.fxml");
        navigateToSinglePlay.navigateTo(event);

    }
    @FXML
    public void navigateToRecordedGamesScreen(ActionEvent event) {
        NavigationController navigateToRecordedGames = new NavigationController("/view/FXMLRecordsScreen.fxml");
        navigateToRecordedGames.navigateToRecordList(event,"local-mode");

    }
    @FXML
    public void navigateToOnlineMode(ActionEvent event) {
        NavigationController navigateToRecordedGames = new NavigationController("/view/FXMLRecordsScreen.fxml");
        navigateToRecordedGames.navigateTo(event);

    }

}
package controller;

import Helper.AskDialog;
import Helper.NavigationController;
import Helper.CustomDialog;
import Helper.IpValidation;
import Helper.ReadWriteHelper;
import static controller.FXMLChooseLevelController.isrecord;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.prefs.Preferences;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;


public class FXMLHomeScreenController implements Initializable {


    @FXML
    private Button gameRecordsBtn;

    static boolean checkip=false;
    static Socket socket;
    static DataInputStream dis;
    static PrintStream ps;
    Preferences prefs;
    int checkname;
    static HashMap<String, String>hash = new HashMap<>();
    @FXML
    private Button singleModeBtn;
    @FXML
    private Button oneVSOneBtn;
    @FXML
    private Button onlineModeBtn;
    
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
      public void changeSceneToOnlineGame(ActionEvent event) {
        System.out.println("changeSceneToOnlineGame: called");
        NavigationController navigateToLoginOrRegister = new NavigationController("/view/FXMLLogin.fxml");
        navigateToLoginOrRegister.navigateTo(event);
        //CustomDialog cd = new CustomDialog();
       // Boolean isCancled = cd.displayDialog("Enter Server IP");
        // System.out.println("you entered ip ="+cd.getName());
         

       // if(!isCancled){   
       // boolean conn= connection(cd.getName());
        
       // if(conn){ 
       //  checkip=true;
        // NavigationController navigateToLoginOrRegister = new NavigationController("/view/FXMLLogin.fxml");
       //  System.out.println("socket is "+socket.isConnected()+" from main controller");
       //  navigateToLoginOrRegister.navigateTo(event);
       // }else 
       // {AskDialog dialog=new AskDialog();
       //  dialog.inValidIp("you entered an InValid IP, Please try Again");
       // }

       // }
                                
    }

      
       public boolean connection(String s){
           
        if(IpValidation.isValidIPAddress(s)) { 
        try {
            System.out.println("enter try valip ip");
            if(socket == null || socket.isClosed()){
                socket = new Socket(s,9081);
                System.out.println("conncet valid ip ");
                System.out.println(IpValidation.getIp());
                dis = new DataInputStream(socket.getInputStream());
                ps = new PrintStream(socket.getOutputStream());
            }

            return true;
        } catch (IOException ex) {
            try {
                System.out.println("closing socket in home controller");
                if(socket != null){
                    socket.close();
                    dis.close();
                    ps.close();
                }
                        
            } catch (IOException ex1) {
                Logger.getLogger(FXMLHomeScreenController.class.getName()).log(Level.SEVERE, null, ex1);
            }
            return false;
        }
                
        }else {      
            checkip=false;
            return false;
             }
        }

}
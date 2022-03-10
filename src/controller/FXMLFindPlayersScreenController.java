/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Helper.AskDialog;
import Helper.CustomDialog;
import Helper.NavigationController;
import Helper.Player;
import Helper.ReadWriteHelper;
import static controller.FXMLChooseLevelController.isrecord;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author mando
 */
public class FXMLFindPlayersScreenController implements Initializable {

    private ArrayList<Player> onlinePlayers;
    @FXML
    private ScrollPane scrollpane;
    @FXML
    private Button back;
    private VBox vbox = new VBox();
    private Alert alert;
    Thread thread;
    private Boolean loaded = false;
    private Player player;
    private StringTokenizer token;
    public static int opponentScore;
    public static String opponentUsername ;
    public static boolean state;
    Preferences prefs;
    public static boolean isrecordOnline = false;
    /**
     * Initializes the controller class.
     */
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        loaded = true;
        FXMLHomeScreenController.ps.println("playerlist");
        onlinePlayers = new ArrayList();  
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(loaded){
                        onlinePlayers.clear();
                    do{

                        try{
                            String data = FXMLHomeScreenController.dis.readLine();
                            if(data.equals("null")){
                                break;
                            }
                            switch(data){
                                case "requestPlaying":
                                    System.out.println("request received "+data);
                                    recievedRequest(); // alert
                                    break;
                                case "decline":
                                    popUpRefuse();
                                    break;
                                case "gameOn":
                                   
                                    Platform.runLater(()->{
                                    try {
                                      startGame(true);
                                    } catch (IOException ex) {
                                       Logger.getLogger(FXMLFindPlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
                                    } 
                                     }); 
                                     thread.stop();
                                   
                                    break;
                                default :
                                    System.out.println("default"+ data);
                                    readOnlineList(data);
                            }
                        } catch (IOException ex) {                                                     
                            close();                        
                        }
                    }while(true);
                    listOnlinePlayers();
                    try{
                            Thread.sleep(300);
                        }catch(InterruptedException ex){
                            thread.stop();
                        }
                    }
                }                   
            }
        });
        thread.start();
    }    
    public synchronized void startGame(boolean startPlayer) throws IOException {
        FXMLGamingOnlineController.myTurn = startPlayer;
        FXMLGamingOnlineController.opponentTurn = !startPlayer;
        System.out.println("game starteddddd");
        Parent menu_parent = FXMLLoader.load(getClass().getResource("/view/FXMLGamingOnline.fxml"));
        Scene SceneMenu = new Scene(menu_parent);
        System.out.println("started game" + state);
        Stage stage = (Stage)back.getParent().getScene().getWindow();
        stage.setScene(SceneMenu);
        stage.show();
    }
    
    
    public void gotoHell() throws IOException {
        
        System.out.println("back to Hell");
        Parent menu_parent = FXMLLoader.load(getClass().getResource("/view/FXMLHome.fxml"));
        Scene SceneMenu = new Scene(menu_parent);
        Stage stage = (Stage)back.getParent().getScene().getWindow();
        stage.setScene(SceneMenu);
        stage.show();
    }

    @FXML
    private void backToOnlineScreen(ActionEvent event) {
         NavigationController navigateToListPage = new NavigationController("/view/FXMLOnlineMode.fxml");
            navigateToListPage.navigateTo(event); 
    }
     private void listOnlinePlayers(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
//                try {
                    //btn1.getScene().getStylesheets().add(getClass().getResource("/css/fullpackstyling.css").toString());
                    scrollpane.setContent(null);
                    vbox.getChildren().clear();
                    scrollpane.getStylesheets().add(getClass().getResource("/CSS/cssStyling.css").toExternalForm());
                    for(Player x : onlinePlayers){
                        System.out.println("inside for loop");
                        ImageView view,view2;
                        // avatar view
                         view = new ImageView(new Image(this.getClass().getResourceAsStream("/resources/man@2x.png")));
                         view.setFitHeight(30);
                         view.setPreserveRatio(true);
                        
                    
                        Button button = new Button(x.getUserName(),view);
                        button.setAlignment(Pos.BOTTOM_LEFT);
                        
                        button.setId(""+x.getEmail());
                        //button.getId().getScene().getStylesheets().add(getClass().getResource("/CSS/cssStyling.css"));
                        if(x.isIsPlaying()){
                            button.setDisable(true);
                        }
                        
                        button.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                opponentUsername = x.getUserName();
                                opponentScore = x.getScore();
                                String userName = FXMLHomeScreenController.hash.get("username");
                                String score = FXMLHomeScreenController.hash.get("score");
                                
                                AskDialog isrecoredGame = new AskDialog();
                                Boolean check=isrecoredGame.alert("Do you want to record game ?");
                                if(check)
                                {
                                    ReadWriteHelper.createFile("online-mode");
                                    ReadWriteHelper.writeFile(userName + ".");
                                    ReadWriteHelper.writeFile(opponentUsername + ".");
                                    ReadWriteHelper.writeFile("fristPlayer"+".");
                                    ReadWriteHelper.writeFile("secondPlayer"+".");
                                    isrecordOnline = true;
                                }
                                System.out.println("btn clicked "+opponentUsername+" score is : "+opponentScore);
                                FXMLHomeScreenController.ps.println("request###"+button.getId()+"###"+FXMLHomeScreenController.hash.get("email")+"###"+ userName +"###"+ score);
                                // pop up waiting for response from server
                                
                                ButtonType Yes = new ButtonType("Ok"); // can use an Alert, Dialog, or PopupWindow as needed...
                                alert = new Alert(Alert.AlertType.NONE);
                                alert.setTitle("Information Dialog");
                                alert.setHeaderText("Please Wait The Opponent to respond..");
                                alert.getDialogPane().getButtonTypes().addAll(Yes);
                               
                                DialogPane dialogPane = alert.getDialogPane();
                                dialogPane.getStylesheets().add(
                                getClass().getResource("/CSS/cssStyling.css").toExternalForm());
                                     dialogPane.getStyleClass().add("myDialog");
                                // hide popup after 3 seconds:
                                PauseTransition delay = new PauseTransition(Duration.seconds(10));
                                delay.setOnFinished(e -> alert.hide());

                                alert.show();
                                delay.play();
                                //if()
                                //if (alert.getResult() == ButtonType.YES) {
                                    
                                //}
                                
                            }
                        });
                        vbox.getChildren().add(button);
                        scrollpane.setContent(vbox);
                        scrollpane.getStyleClass().add("findPlayerScroll");
                        vbox.getStyleClass().add("vBoxStyle");
                        button.getStyleClass().add("playerBtn");
                    }
                    onlinePlayers.clear();
                
            }
        });
    }
     private void recievedRequest() throws IOException{
        String opponentData = FXMLHomeScreenController.dis.readLine();
        System.out.println("recieved request");
         System.out.println(opponentData);
        token = new StringTokenizer(opponentData,"###");
        String opponentMail = token.nextToken();
        opponentUsername = token.nextToken();
        String sOpponentScore = token.nextToken();
        opponentScore = Integer.parseInt(sOpponentScore);
        Platform.runLater(new Runnable(){
            @Override
            public void run() {
                System.out.println("recieved request run");
                
                ButtonType Yes = new ButtonType("Yes"); 
                ButtonType No = new ButtonType("NO", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle("Confirmation");
                alert.setHeaderText(opponentUsername+" wants to Challenge you, Are you Okay with that ?");
                alert.getDialogPane().getButtonTypes().addAll(Yes,No);
                
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                getClass().getResource("/CSS/cssStyling.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialog");
                
                PauseTransition delay = new PauseTransition(Duration.seconds(10));
                delay.setOnFinished(e -> alert.hide());
                                        
                Optional<ButtonType> result = alert.showAndWait();
                String userName = FXMLHomeScreenController.hash.get("username");
                if (result.get() == Yes){ // accept to play
                    AskDialog isrecoredGame = new AskDialog();
                    Boolean check=isrecoredGame.alert("Do you want to record game ?");
                    if(check)
                    {
                        ReadWriteHelper.createFile("online-mode");
                        ReadWriteHelper.writeFile(userName + ".");
                        ReadWriteHelper.writeFile(opponentUsername + ".");
                        ReadWriteHelper.writeFile("fristPlayer"+".");
                        ReadWriteHelper.writeFile("secondPlayer"+".");
                        isrecordOnline = true;
                    }
                    System.out.println("game on");
                    FXMLHomeScreenController.ps.println("accept###"+FXMLHomeScreenController.hash.get("email")+"###"+userName+"###"+opponentMail);
                    // initialize game
                    System.out.println("navigate to game screen");
                     Platform.runLater(()->{
                    try {
                        thread.stop();
                        startGame(false);
                        

                    } catch (IOException ex) {
                        Logger.getLogger(FXMLFindPlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
                    } 
                     }); 
                     
                    
                }else {
                    System.out.println("no first request");
                    FXMLHomeScreenController.ps.println("decline###"+opponentMail);
                }
                delay.play();
            }

        });
    }
    private void readOnlineList(String data){
        System.out.println("data in read online list :"+data+"\n");
        token = new StringTokenizer(data, "###");
        player = new Player();
        player.setUserName(token.nextToken());
        player.setEmail(token.nextToken());
        player.setIsActive(Boolean.parseBoolean(token.nextToken()));
        player.setIsPlaying(Boolean.parseBoolean(token.nextToken()));
        player.setScore(Integer.parseInt(token.nextToken()));
        
        System.out.println(FXMLHomeScreenController.hash.get("email"));
        System.out.println(player.getEmail());
        if(!FXMLHomeScreenController.hash.get("email").equals(player.getEmail())){
            System.out.println("Add list");
            onlinePlayers.add(player);
        }
    }
    
     private void popUpRefuse(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(alert.isShowing())
                    alert.close();
                ButtonType Yes = new ButtonType("Ok"); 
                alert = new Alert(Alert.AlertType.NONE);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Your Opponent Refused to Challenge you!");
                alert.getDialogPane().getButtonTypes().addAll(Yes);
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.getStylesheets().add(
                getClass().getResource("/CSS/cssStyling.css").toExternalForm());
                dialogPane.getStyleClass().add("myDialog");
                
                alert.showAndWait();
            }
        });
    }
     
     private void close(){
        System.out.println("Server Colsed");
                            
        Platform.runLater(() -> {
        AskDialog  serverIssueAlert  = new AskDialog();
        serverIssueAlert.serverIssueAlert("There is issue in connection game page will be closed");
            try {
                gotoHell();
            } catch (IOException ex) {
                Logger.getLogger(FXMLFindPlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread.stop();
    }
}

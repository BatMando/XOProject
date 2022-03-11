/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Helper.AskDialog;
import Helper.NavigationController;
import Helper.Player;
import Helper.ReadWriteHelper;
import static controller.FXMLFindPlayersScreenController.state;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mando
 */
public class FXMLGamingOnlineController implements Initializable {

    @FXML
    private Button btn1;
    @FXML
    private Button btn9;
    @FXML
    private Button btn8;
    @FXML
    private Button btn7;
    @FXML
    private Button btn6;
    @FXML
    private Button btn5;
    @FXML
    private Button btn4;
    @FXML
    private Button btn3;
    @FXML
    private Button btn2;
    @FXML
    private Text player2Move;
    @FXML
    private Text player2Label;
    @FXML
    private Text player1Label;
    @FXML
    private Text player1Move;
    @FXML
    private Button backBtn;
    @FXML
    private Text playStateLabel;
    @FXML
    private Label player2scoreLbl;
    @FXML
    private Label player1scoreLbl;

    
    private Stage thisStage;
    private Thread thread;
    private Player player;
    private StringTokenizer token;
    private Alert alert;
    private Boolean loaded = false;
    private VBox vbox = new VBox();
    private HashMap<String, Button> btn;
    public static boolean myTurn,opponentTurn,gameState=false;
    private String myTic,oppTic;
    private String opponentUsername ;
    private Preferences pref ;
    private Boolean isrecord = false;

    private int currentScore;
    private int opponentScore;
    private ImageView view;
    
    private Boolean display = false;
    @FXML
    private Text turnLabel;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
        showGame();
        btn = new HashMap();
        btn.put("btn1", btn1);
        btn.put("btn2", btn2);
        btn.put("btn3", btn3);
        btn.put("btn4", btn4);
        btn.put("btn5", btn5);
        btn.put("btn6", btn6);
        btn.put("btn7", btn7);
        btn.put("btn8", btn8);
        btn.put("btn9", btn9);
        //FXMLHomeScreenController.ps.println("gameTic");
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    do {                        
                         try{
                            String data = FXMLHomeScreenController.dis.readLine();
                            if(data==null){
                                System.err.println("there is null");
                                close();
                                break;
                            }
                            switch(data){
                                case "gameTic":
                                    //System.out.println("recieve game tic "+FXMLHomeScreenController.dis.readLine());
                                    opponentTurn();
                                    break;
                                case "finalgameTic":
                                    opponentTurn();
                                    endGame();
                                    break;
                                case "endGame":
                                    endGame();
                                    break;
                                case "withdraw":
                                    withdraw();
                                    break;
                                case "close":
                                    close();
                                default :
                                    System.out.println("default in gaming");
                                    System.out.println("**********"+data+"/////////////");
                            }
                        } catch (IOException | NullPointerException ex) {
                            close();
                        }
                    } while(true);
                    try{
                           Thread.sleep(300);
                        }catch(InterruptedException ex){
                            thread.stop();
                        }
                    }
                }                   
        });
        thread.start();
    }

    private void makeGridEmpty(){
        btn1.setText("");
        btn2.setText("");
        btn3.setText("");
        btn4.setText("");
        btn5.setText("");
        btn6.setText("");
        btn7.setText("");
        btn8.setText("");
        btn9.setText("");
                    
    }
    
    @FXML
    private void buttonPressed(ActionEvent event) {
        Button buttonPressed = null;
        if(gameState && myTurn){
            buttonPressed = (Button) event.getSource();
            if(buttonPressed.getText().equals("")){
                buttonPressed.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: #AFE0AF;"); 
                buttonPressed.setText(myTic);
                System.out.println("My Turn " +myTic);
                if(FXMLFindPlayersScreenController.isrecordOnline)
                    ReadWriteHelper.writeFile(buttonPressed.getId()+myTic+".");
                myTurn = false;
                opponentTurn = true;
                if(myTurn && myTic.equals("X")){
                    playStateLabel.setText("X");
                    turnLabel.setStyle("-fx-fill: #AFE0AF;");
                    playStateLabel.setStyle("-fx-fill: #AFE0AF;");
                }else if(myTurn && myTic.equals("O")){
                    playStateLabel.setText(myTic);
                    turnLabel.setStyle("-fx-fill: #ffe591;");
                    playStateLabel.setStyle("-fx-fill: #ffe591;");
                }
                else{
                    playStateLabel.setText(oppTic);
                    turnLabel.setStyle("-fx-fill: #ffe591;");
                    playStateLabel.setStyle("-fx-fill: #ffe591;");
                }
//                if(FXMLFindPlayersScreenController.isrecordOnline)
//                    ReadWriteHelper.writeFile(buttonPressed.getId()+buttonPressed.getText()+".");
                System.out.println("I pressed "+buttonPressed.getId());
                if(checkState()){
                    FXMLHomeScreenController.ps.println("finishgameTic###"+FXMLHomeScreenController.hash.get("email")+"###"+buttonPressed.getId());
                }else{
                    FXMLHomeScreenController.ps.println("gameTic###"+FXMLHomeScreenController.hash.get("email")+"###"+buttonPressed.getId());
                     System.out.println("sending game tic");
                }
            }
            
        }
    }


    private void withdraw() {
        System.out.println("withdraw");
        updateScore();
        FXMLHomeScreenController.ps.println("available###"+FXMLHomeScreenController.hash.get("email"));
        Platform.runLater(() -> {
            AskDialog  serverIssueAlert  = new AskDialog();
            serverIssueAlert.serverIssueAlert("You opponent has withdrawed, you are the winner!!!");
            thread.stop();
            try {
                gotoHell();
            } catch (IOException ex) {
                Logger.getLogger(FXMLGamingOnlineController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    
    private void updateScore(){
        
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try{
                    currentScore += 10;
                    FXMLHomeScreenController.hash.put("score", ""+currentScore);
                } catch(NumberFormatException ex){ 

                }
                player1scoreLbl.setText(""+currentScore);
                FXMLHomeScreenController.ps.println("updateScore###"+FXMLHomeScreenController.hash.get("email")+"###"+currentScore);
            }
        });
    }
    
    
    private void showGame(){
        makeGridEmpty();
        System.out.println("my state: "+myTurn);

        gameState = true;
        if(myTurn){
            myTic = "X";
            oppTic = "O";
            player1Label.setText("Me");
            player1scoreLbl.setText(FXMLHomeScreenController.hash.get("score")); 
            player2Label.setText(FXMLFindPlayersScreenController.opponentUsername);
            player2scoreLbl.setText(FXMLFindPlayersScreenController.opponentScore+"");
        }else{
            myTic = "O";
            oppTic = "X";
            player2Label.setText("Me");
            player2scoreLbl.setText(FXMLHomeScreenController.hash.get("score")); 
            player1Label.setText(FXMLFindPlayersScreenController.opponentUsername);
            player1scoreLbl.setText(FXMLFindPlayersScreenController.opponentScore+"");
        }
        System.out.println("my tic" +myTic);
    }
    
  

    private void opponentTurn(){
        try {
            String oppPressed = FXMLHomeScreenController.dis.readLine();
            System.out.println(oppPressed + " button pressed");
            Button btnOpp = btn.get(oppPressed);
            btnOpp.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Button button = (Button) event.getSource();
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            button.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: #ffe591;"); 
                            button.setText(oppTic);
                           
                            System.out.println("myTic "+ oppTic);
                           
                            if(FXMLFindPlayersScreenController.isrecordOnline)
                                ReadWriteHelper.writeFile(btnOpp.getId()+oppTic+".");
                            checkState();
                        }
                    });
                }
            });
            btnOpp.fire();
            myTurn= true;
            opponentTurn = false;
            playStateLabel.setText(myTic);
            turnLabel.setStyle("-fx-fill: #AFE0AF;");
            playStateLabel.setStyle("-fx-fill: #AFE0AF;");
          
        } catch (IOException ex) {
            Logger.getLogger(FXMLGamingOnlineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

     
    private void checkRows(){
        if(btn1.getText().equals(btn2.getText()) && btn2.getText().equals(btn3.getText()) && !btn1.getText().equals("")){
            gameState = false;
            if(btn1.getText().equals(myTic)){
                btn1.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn2.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn3.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                display = true;
                updateScore();
            }else{
                btn1.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn2.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn3.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                System.out.println("opp win");
            }
        }
        else if(btn4.getText().equals(btn5.getText()) && btn5.getText().equals(btn6.getText()) && !btn4.getText().equals("")){
            gameState = false;
            if(btn4.getText().equals(myTic)){
               display = true;
                btn4.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn5.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn6.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                updateScore();
            }else{
                 btn4.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn5.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn6.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                System.out.println("opp won!");
            }
        }
        else if(btn7.getText().equals(btn8.getText()) && btn8.getText().equals(btn9.getText()) && !btn7.getText().equals("")){
            gameState = false;
            if(btn7.getText().equals(myTic)){
               display = true;
                btn7.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn8.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn9.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                updateScore();
            }else{
             btn7.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn8.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn9.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
            }
        }
    }
    
    private void checkColumns(){
        if(btn1.getText().equals(btn4.getText()) && btn4.getText().equals(btn7.getText()) && !btn1.getText().equals("")){
            if(btn1.getText().equals(myTic)){
               display = true;
               btn1.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn4.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn7.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                updateScore();
            }
            else{
            btn1.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn4.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn7.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); }
            gameState = false;
        }
        else if(btn2.getText().equals(btn5.getText()) && btn5.getText().equals(btn8.getText()) && !btn2.getText().equals("")){
            if(btn2.getText().equals(myTic)){
              display = true;
                btn2.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn5.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn8.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                updateScore();
            }else{
                 btn2.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn5.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn8.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
            }
            gameState = false;
        }
        else if(btn3.getText().equals(btn6.getText()) && btn6.getText().equals(btn9.getText()) && !btn3.getText().equals("")){
            if(btn3.getText().equals(myTic)){
             display = true;
               updateScore();
                btn3.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn6.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn9.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
            }
            else{
                btn3.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn6.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn9.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
            }
            gameState = false;
        }
    }
    
    private void checkDiagonal(){
        if(btn1.getText().equals(btn5.getText()) && btn5.getText().equals(btn9.getText()) && !btn1.getText().equals("")){
            if(btn1.getText().equals(myTic)){
                 btn1.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn5.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn9.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
              display = true;
                updateScore();
            }else{
                btn1.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn5.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn9.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
            }
            gameState = false;
        }
        else if(btn3.getText().equals(btn5.getText()) && btn5.getText().equals(btn7.getText()) && !btn3.getText().equals("")){
            if(btn3.getText().equals(myTic)){
               display = true;
                btn3.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn5.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn7.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                updateScore();
            }else{
                 btn3.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn5.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn7.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
            }
            gameState = false;
        }
    }
    
    private boolean isFullGrid(){
        if(!btn1.getText().equals("") && !btn2.getText().equals("") && !btn3.getText().equals("") && !btn4.getText().equals("")
                && !btn5.getText().equals("") && !btn6.getText().equals("")&& !btn7.getText().equals("")
                && !btn8.getText().equals("") && !btn9.getText().equals("")){
                return true;
        }else{
            return false;
        }
    }
    
    private boolean checkState(){
        System.out.println("checking state");
        
        checkColumns();
        checkRows();
        checkDiagonal();
        
        if(!gameState){
            FXMLHomeScreenController.ps.println("updateGameState###"+FXMLHomeScreenController.hash.get("email"));
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    if(display){
                        //System.out.println("display winner");
                        displayVideo("winner");
                        
                    }else{
                        displayVideo("loser");
                        
                    }
                }
            });
            endGame();
            return true; // ended game
            
        }else if(isFullGrid()){
            displayVideo("draw");

            endGame();
            return true;
        }
        System.out.println("checking ended");
        return false;
    }

    private void endGame(){

        FXMLHomeScreenController.ps.println("available###"+FXMLHomeScreenController.hash.get("email"));
        thread.stop();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                try {
                    gotoHell();
                } catch (IOException ex) {
                    Logger.getLogger(FXMLGamingOnlineController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void gotoHell() throws IOException {
        
        System.out.println("back to Hell");
        Parent menu_parent = FXMLLoader.load(getClass().getResource("/view/FXMLOnlineMode.fxml"));
        Scene SceneMenu = new Scene(menu_parent);
        Stage stage = (Stage)backBtn.getParent().getScene().getWindow();
        stage.setScene(SceneMenu);
        stage.show();
    }
    
    public void gotoHomeHell() throws IOException {
        
        System.out.println("back to Hell");
        Parent menu_parent = FXMLLoader.load(getClass().getResource("/view/FXMLHomeScreen.fxml"));
        Scene SceneMenu = new Scene(menu_parent);
        Stage stage = (Stage)backBtn.getParent().getScene().getWindow();
        stage.setScene(SceneMenu);
        stage.show();
    }
    
    private void close(){
        System.out.println("Server Colsed");
                            
        Platform.runLater(() -> {
        AskDialog  serverIssueAlert  = new AskDialog();
        serverIssueAlert.serverIssueAlert("There is issue in connection game page will be closed");
            try {
                gotoHomeHell();
            } catch (IOException ex) {
                Logger.getLogger(FXMLFindPlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread.stop();
    }
    private void displayVideo(String type){
        if(type.equals("winner")){
           NavigationController displayVideo = new NavigationController("/view/FXMLVideo.fxml");
           displayVideo.displayVideo("winner","Congratulation"); 
        }else if(type.equals("loser")){
           NavigationController displayVideo = new NavigationController("/view/FXMLVideo.fxml");
           displayVideo.displayVideo("loser","opps!!");  
        }else{
             NavigationController displayVideo = new NavigationController("/view/FXMLVideo.fxml");
           displayVideo.displayVideo("draw","opps!!");  
        }
    }
    
    
    @FXML
     public void backToOnlinePage(ActionEvent event){
        System.out.println("backToOnlinePage: called");
        System.out.println("Emial " + FXMLHomeScreenController.hash.get("email"));
        if(FXMLHomeScreenController.hash.get("email")!= null){
           AskDialog  logoutAlert  = new AskDialog();
           Boolean logedOut  = logoutAlert.alert("Are you sure you want to logout","Alert Issue");
           if(logedOut){
               System.out.println("Send to server to logout");
               FXMLHomeScreenController.ps.println("withdraw");
               FXMLHomeScreenController.ps.println("available###"+FXMLHomeScreenController.hash.get("email"));
               thread.stop();
               NavigationController btnback = new NavigationController("/view/FXMLOnlineMode.fxml");
               btnback.navigateTo(event); 
           }
        }
    }
}

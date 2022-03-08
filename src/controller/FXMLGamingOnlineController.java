/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Helper.AskDialog;
import Helper.Player;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    private Boolean rematch1 = false;
    private Boolean rematch2 = false;
    private VBox vbox = new VBox();
    private HashMap<String, Button> btn;
    boolean myTurn,opponentTurn,gameState=false;
    private String myTic,oppTic;
    private String opponentUsername ;
    private Preferences pref ;
    private Boolean isrecord = false;

    private int currentScore;
    private int opponentScore;
    private ImageView view;
    public static boolean state;
    private Boolean display = false;

    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        player1Label.setText(FXMLHomeScreenController.hash.get("username"));
        player1scoreLbl.setText(FXMLHomeScreenController.hash.get("score")); 
        player2Label.setText(FXMLFindPlayersScreenController.opponentUsername);
        player2scoreLbl.setText(FXMLFindPlayersScreenController.opponentScore+"");
        //loaded = true;
        //FXMLHomeScreenController.ps.println("playerlist");
        
        //pref =Preferences.userNodeForPackage(OnlinePlayerController.class);
        
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
        showGame();
        //onlinePlayers = new ArrayList();           
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                        try{
                            String data = FXMLHomeScreenController.dis.readLine();
                            if(data.equals("null")){
                                break;
                            }
                            switch(data){
                                case "gameTic":
                                    opponentTurn();
                                    break;
                                case "finalgameTic":
                                    opponentTurn();
                                    reset();
                                    break;
                                case "endGame":
                                    endGame();
                                    break;
                                case "withdraw":
                                    
                                    break;
                                case "close":
                                    close();
                                default :
                                    System.out.println("default");
                            }
                        } catch (IOException ex) {
                            close();
                        }
                    
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
        Button buttonPressed ;
        if(gameState && myTurn){
            buttonPressed = (Button) event.getSource();
            if(buttonPressed.getText().equals("")){
                buttonPressed.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: #AFE0AF;"); 
                buttonPressed.setText(myTic);
                System.out.println("My Turn " +myTic);
                //if(MainController.isrecord)
                /*if(isrecord){
                  AccessFile.writeFile(buttonPressed.getId()+buttonPressed.getText()+".");  
                }*/
                myTurn = false;
                opponentTurn = true;
                if(myTurn && myTic.equals("X")){
                    playStateLabel.setText("X");
                    playStateLabel.setStyle("-fx-text-fill: #AFE0AF;");
                }else{
                    playStateLabel.setText("O");
                    playStateLabel.setStyle("-fx-text-fill: #ffe591;");
                }
                System.out.println("I pressed "+buttonPressed.getId());
                if(checkState()){
                    
                    FXMLHomeScreenController.ps.println("finishgameTic###"+FXMLHomeScreenController.hash.get("email")+"###"+buttonPressed.getId());
                }else{
                    FXMLHomeScreenController.ps.println("gameTic###"+FXMLHomeScreenController.hash.get("email")+"###"+buttonPressed.getId());
                }
            }
        }
    }


    @FXML
    private void withdraw(ActionEvent event) {
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
        System.out.println("my state: "+state);
        myTurn = state;
        opponentTurn = !state;
        gameState = true;
        if(state){
            myTic = "X";
            oppTic = "O";
        }else{
            myTic = "O";
            oppTic = "X";
        }
        System.out.println("my tic" +myTic);
    }
    
    private void endGame(){
        /*Platform.runLater(() -> {
            if(alert.isShowing()){
                alert.close();
            }
            gameState = false;
            ps.println("gameEnded###"+hash.get("email")+"###");//add score
        });*/
        System.out.println("end game called");
    }
    

    private void opponentTurn(){
        try {
            String oppPressed = FXMLHomeScreenController.dis.readLine();
            System.out.println(oppPressed);
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
                            /*if(isrecord){
                              AccessFile.writeFile(btnOpp.getId()+btnOpp.getText()+".");  
                            }*/
                            checkState();
                        }
                    });
                }
            });
            btnOpp.fire();
            myTurn= true;
            opponentTurn = false;

        } catch (IOException ex) {
            Logger.getLogger(FXMLGamingOnlineController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    

     
    private void checkRows(){
        if(btn1.getText().equals(btn2.getText()) && btn2.getText().equals(btn3.getText()) && !btn1.getText().equals("")){
            gameState = false;
            if(btn1.getText().equals(myTic)){
                display = true;
                updateScore();
            }else{
                System.out.println("opp win");
            }
        }
        else if(btn4.getText().equals(btn5.getText()) && btn5.getText().equals(btn6.getText()) && !btn4.getText().equals("")){
            gameState = false;
            if(btn4.getText().equals(myTic)){
               display = true;
                updateScore();
            }else{
                System.out.println("opp won!");
            }
        }
        else if(btn7.getText().equals(btn8.getText()) && btn8.getText().equals(btn9.getText()) && !btn7.getText().equals("")){
            gameState = false;
            if(btn7.getText().equals(myTic)){
               display = true;
                updateScore();
            }
        }
    }
    
    private void checkColumns(){
        if(btn1.getText().equals(btn4.getText()) && btn4.getText().equals(btn7.getText()) && !btn1.getText().equals("")){
            if(btn1.getText().equals(myTic)){
               display = true;
                updateScore();
            }
            gameState = false;
        }
        else if(btn2.getText().equals(btn5.getText()) && btn5.getText().equals(btn8.getText()) && !btn2.getText().equals("")){
            if(btn2.getText().equals(myTic)){
              display = true;
                updateScore();
            }
            gameState = false;
        }
        else if(btn3.getText().equals(btn6.getText()) && btn6.getText().equals(btn9.getText()) && !btn3.getText().equals("")){
            if(btn3.getText().equals(myTic)){
             display = true;
               updateScore();
            }
            gameState = false;
        }
    }
    
    private void checkDiagonal(){
        if(btn1.getText().equals(btn5.getText()) && btn5.getText().equals(btn9.getText()) && !btn1.getText().equals("")){
            if(btn1.getText().equals(myTic)){
              display = true;
                updateScore();
            }
            gameState = false;
        }
        else if(btn3.getText().equals(btn5.getText()) && btn5.getText().equals(btn7.getText()) && !btn3.getText().equals("")){
            if(btn3.getText().equals(myTic)){
               display = true;
                updateScore();
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
                        System.out.println("display winner");
                        //displayVideo("winner");
                        //AskDialog  serverIssueAlert  = new AskDialog();
                        //serverIssueAlert.serverIssueAlert("Congrats !! , your score right now is :"+ MainController.hash.get("score"));
                        
                    }else{
                        System.out.println("display loser");
                        //AskDialog  serverIssueAlert  = new AskDialog();
                        //serverIssueAlert.serverIssueAlert("Oh, Hardluck next time..");
                    }
                }
            });
            reset();
            return true; // ended game
            
        }else if(isFullGrid()){
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    AskDialog  serverIssueAlert  = new AskDialog();
                    serverIssueAlert.serverIssueAlert("It's adraw !!");
                }                
            });
            reset();
            return true;
        }
        return false;
    }

    private void reset(){

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
                gotoHell();
            } catch (IOException ex) {
                Logger.getLogger(FXMLFindPlayersScreenController.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        thread.stop();
    }
    
}

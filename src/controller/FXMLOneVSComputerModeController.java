/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Helper.NavigationController;
import java.awt.Color;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author mando
 */
 
public class FXMLOneVSComputerModeController implements Initializable {

    private String player = "X";
    private Button buttonPressed;
    private boolean winner = false;
    private boolean display = false;
    private Preferences prefs ;
    private int score = 0;
    private Boolean computerWin = false ;
    // green #beffbe 
    // buttonbg  #ffffff57
    // yellow #ffe591
//    Color myGreen = Color.decode("#beffbe");
//    Color myButtonBg = Color.decode("#ffffff57");
//    Color myYellow = Color.decode("#ffe591");

  
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
    private Button playAgainBtn;
    @FXML
    private ImageView backBtn;
    @FXML
    private Text userNameLabel;
    @FXML
    private Button back;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         makeGridEmpty();
          prefs = Preferences.userNodeForPackage(FXMLOneVSComputerModeController.class); 
        try {
            if(prefs.nodeExists("/controller"))
            {
             System.out.println("init");
              String userName=prefs.get("username","");
              System.out.println(userName);
              
              if(userName.length() != 0){
                 userNameLabel.setText(userName); 
              }
             
              
            }
        } catch (BackingStoreException ex) {
            Logger.getLogger(FXMLOneVSComputerModeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    @FXML
     public void buttonPressed(ActionEvent e){
        if(!winner){
            System.err.println("x");
            buttonPressed = (Button) e.getSource();
            if(buttonPressed.getText().equals("")){
                    buttonPressed.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: #AFE0AF;"); 
                //buttonPressed.setStyle("-fx-background-color:#ffffff57;"); 
                buttonPressed.setText(player);
                
                
                if(player=="X"){
                    player="O";
                }
                else{
                    player="X";
                }  
                
                checkState();
                if(!winner){
                    computerTurn();
                    checkState();
                }
            }else{
                if(isFullGrid()){
                    //txtWinner.setText("It's a Draw");
                    playAgainBtn.setVisible(true);
                }
                
            }
        }else{
            // show video
        }

    }

    private void computerTurn(){
        Random r;
        Button[] btns = {btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9};
        Button myBtn;
        do{
            r = new Random();
            int i =r.nextInt(9);
            myBtn = btns[i];
            if(isFullGrid()){
                break;
            }
        }while(!myBtn.getText().equals(""));
        myBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
            System.err.println("button pressed");
            buttonPressed = (Button) e.getSource();

                if(buttonPressed.getText().equals("")){
                    buttonPressed.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: #ffe591;"); 
                    
                    buttonPressed.setText(""+player);
                    

                    if(player=="X"){
                        player="O";
                    }
                    else{
                        player="X";
                    }        
                }else{
                    if(isFullGrid() && !winner){
                        displayVideo("draw");
                        playAgainBtn.setVisible(true);
                    }
                }
            }
        });
        myBtn.fire();

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
    
    private boolean checkRows(){
        if(btn1.getText().equals(btn2.getText()) && btn2.getText().equals(btn3.getText()) && !btn1.getText().equals("")){
            //drawLine(btn1,btn3);
            if(btn1.getText().equals("X")){
                btn1.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn2.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn3.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 

                System.out.println("x won");
                //displayVideo();
                display = true;
       
            }else{
                System.out.println("computer won!");
                btn1.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn2.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn3.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                computerWin = true;
            }
            winner = true;
        }
        else if(btn4.getText().equals(btn5.getText()) && btn5.getText().equals(btn6.getText()) && !btn4.getText().equals("")){
         //   drawLine(btn4,btn6);
            if(btn4.getText().equals("X")){
                System.out.println("x won");
                btn4.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn5.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn6.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                //displayVideo();
                display = true;
            }else{
                System.out.println("computer won!");
                btn4.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn5.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn6.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                computerWin = true;
            }
            winner = true;
        }
        else if(btn7.getText().equals(btn8.getText()) && btn8.getText().equals(btn9.getText()) && !btn7.getText().equals("")){
            //drawLine(btn7,btn9);
            if(btn7.getText().equals("X")){
                System.out.println("x won");
                btn7.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn8.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn9.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                //displayVideo();
                display = true;
            }else{
               // System.out.println("computer won!");
                btn7.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn8.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn9.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                computerWin = true;
            }
            winner = true;
        }else{
            return false;
        }
        
        return winner;
    }
    
    private boolean checkColumns(){
        if(btn1.getText().equals(btn4.getText()) && btn4.getText().equals(btn7.getText()) && !btn1.getText().equals("")){
//            drawLine(btn1,btn7);
            if(btn1.getText().equals("X")){
                System.out.println("x won");
                //displayVideo();
                btn1.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn4.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn7.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                display = true;
            }else{
                System.out.println("computer won!");
                btn1.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn4.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn7.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                computerWin = true;
            }
            winner = true;
        }
        else if(btn2.getText().equals(btn5.getText()) && btn5.getText().equals(btn8.getText()) && !btn2.getText().equals("")){
           // drawLine(btn2,btn8);
            if(btn2.getText().equals("X")){
                System.out.println("x won");
                btn2.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn5.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn8.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                //displayVideo();
                display = true;
            }else{
                System.out.println("computer won!");
                btn2.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn5.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn8.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                computerWin = true;
            }
            winner = true;
        }
        else if(btn3.getText().equals(btn6.getText()) && btn6.getText().equals(btn9.getText()) && !btn3.getText().equals("")){
           // drawLine(btn3,btn9);
            if(btn3.getText().equals("X")){
                System.out.println("x won");
               // displayVideo();
               btn3.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn6.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn9.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
               display = true;
            }else{
                btn3.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn6.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn9.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                System.out.println("x won");
                computerWin = true;
            }
            winner = true;
        }else{
            return false;
        }
        return winner;
    }
    
    private boolean checkDiagonal(){
        if(btn1.getText().equals(btn5.getText()) && btn5.getText().equals(btn9.getText()) && !btn1.getText().equals("")){
           // drawLine(btn1,btn9);
            if(btn1.getText().equals("X")){
                System.out.println("x won");
                btn1.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn5.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn9.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                //displayVideo();
                display = true;
            }else{
                System.out.println("computer won!");
                btn1.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn5.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn9.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                computerWin = true;
            }
            winner = true;
        }
        else if(btn3.getText().equals(btn5.getText()) && btn5.getText().equals(btn7.getText()) && !btn3.getText().equals("")){
            //drawLine(btn3,btn7);
            if(btn3.getText().equals("X")){
                System.out.println("x won");
                //displayVideo();
                btn3.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn5.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                btn7.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;"); 
                display = true;
            }else{
                btn3.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn5.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                btn7.setStyle("-fx-background-color:#DE7D7D;"+"-fx-text-fill:#ffffff;"); 
                System.out.println("computer won!");
                computerWin = true;
            }
            winner = true;
        }else{
            return false;
        }
        return winner;
    }
    private void checkState (){

        checkColumns();
        checkRows();
        checkDiagonal();
        if(display){
            displayVideo("winner");             
            System.out.println("Synch");
            prefs.putInt("score",score);
            playAgainBtn.setVisible(true);
        }else if(computerWin){
            System.out.println("computer wins");
            displayVideo("loser");
           playAgainBtn.setVisible(true); 
           
        }

    }
    
   
    @FXML
    public void backToMainPage(ActionEvent event){
        System.out.println("backToMainPage: called");
        NavigationController btnback = new NavigationController("/view/FXMLChooseLevel.fxml");
        btnback.navigateTo(event);
        
    } 
  
   
    @FXML
      public void replayAgain(ActionEvent event){

        playAgainBtn.setVisible(false);

        NavigationController rematch = new NavigationController("/view/FXMLOneVSComputerMode.fxml");
        rematch.navigateTo(event);

         
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
}

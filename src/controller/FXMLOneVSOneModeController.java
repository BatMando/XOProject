/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Helper.NavigationController;
import Helper.ReadWriteHelper;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.BackingStoreException;
import java.util.prefs.Preferences;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Thoraya Hamdy
 */
public class FXMLOneVSOneModeController implements Initializable {

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
    
    private Button pressedButton;
    private boolean isXorO = false;
    private int counter = 0;
    private boolean isFullGame = false;
    private String winnerType = "";
    private String greenColor = "#AFE0AF";
    private String yellowColor = "#ffe591";
    private Preferences prefs ;
    @FXML
    private Button backBtn;
    @FXML
    private Button playAgainBtn;
    @FXML
    private Text player2Name;
    @FXML
    private Text player1Name;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        updateGame();
         prefs = Preferences.userNodeForPackage(FXMLOneVSComputerModeController.class); 
        try {
            if(prefs.nodeExists("/controller"))
            {
             System.out.println("init");
              String userName1=prefs.get("fristPlayer","");
              String userName2=prefs.get("secondPlayer","");
              System.out.println(userName1);
              System.out.println(userName2);
              
              if(userName1.length() != 0){
                 player1Name.setText(userName1); 
              }
              if(userName2.length() != 0){
                 player2Name.setText(userName2); 
              }
         
            }
        } catch (BackingStoreException ex) {
            Logger.getLogger(FXMLOneVSComputerModeController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    public void onAnyButtonClicked(ActionEvent e){
        pressedButton = (Button) e.getSource();
        if(pressedButton.getText().equals("") && !isFullGame){
            if (isXorO == false){
                pressedButton.setText("X");
                pressedButton.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + greenColor + ";"); 
                isXorO = true;
            }
            else if(isXorO == true){
                pressedButton.setText("O");
                pressedButton.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + yellowColor + ";"); 
                isXorO = false;
            }
            if(FXMLChooseLevelController.isrecord)
                 ReadWriteHelper.writeFile(pressedButton.getId()+pressedButton.getText()+".");
            counter++;
            checkIfGameIsOver();
        }
    }
    @FXML
    public void backToMainPage(ActionEvent event) {
        System.out.println("backToMainPage: called");
        NavigationController btnback = new NavigationController("/view/FXMLHome.fxml");
        btnback.navigateTo(event);
    }
    public void updatePlayAgainButton(){
        if(playAgainBtn.isDisabled()){
            playAgainBtn.setDisable(false);
        }
        else{
            playAgainBtn.setDisable(true);
        }
    }
    public boolean checkWinner(String line){
        //X winner
        if ((line != null) && line.equals("XXX")) {
            winnerType = "X";
            isFullGame = true;
        }
        //O winner
        else if ((line != null) && line.equals("OOO")) {
            winnerType = "O";
            isFullGame = true;
        }
        return isFullGame;
    }
    
    public void checkIfGameIsOver(){
        boolean isWinner = false;
        for (int a = 0; a < 8; a++) {
            String line = ""; 
            String btnSeq = "";
            switch (a) {
                case 0: 
                    line = btn1.getText() + btn2.getText() + btn3.getText();
                    btnSeq = "btn1 btn2 btn3";
                    break;
                case 1: 
                    line = btn4.getText() + btn5.getText() + btn6.getText();
                    btnSeq = "btn4 btn5 btn6";
                    break;
                case 2: 
                    line = btn7.getText() + btn8.getText() + btn9.getText();
                    btnSeq = "btn7 btn8 btn9";
                    break;
                case 3: 
                    line = btn1.getText() + btn5.getText() + btn9.getText();
                    btnSeq = "btn1 btn5 btn9";
                    break;

                case 4: 
                    line = btn3.getText() + btn5.getText() + btn7.getText();
                    btnSeq = "btn3 btn5 btn7";
                    break;
                case 5: 
                    line = btn1.getText() + btn4.getText() + btn7.getText();
                    btnSeq = "btn1 btn4 btn7";
                    break;
                case 6: 
                    line = btn2.getText() + btn5.getText() + btn8.getText();
                    btnSeq = "btn2 btn5 btn8";
                    break;
                case 7: 
                    line = btn3.getText() + btn6.getText() + btn9.getText();
                    btnSeq = "btn3 btn6 btn9";
                    break;
                default: line = null;
            };
            if(checkWinner(line)){
                isWinner = true;
                if(!winnerType.equals("D"))
                    makeColoredButtons(btnSeq);
                displayVideo();
                updateButtonsAccess();
                playAgainBtn.setVisible(true);
                playAgainBtn.setDisable(false);
                break;
            }
        }
        if(isWinner == false && counter == 9){
            winnerType = "D";
            isFullGame = true;
            displayVideo();
            updateButtonsAccess();
            playAgainBtn.setVisible(true);
            playAgainBtn.setDisable(false);
        }
    }
    private void makeColoredButtons(String str){
        String[]arr = str.split(" ");
        System.out.println("plaaaaaaaaaaaaaaaaaaaaaaaaaa");
        System.out.println(str);
        for(int i = 0; i < 3; ++i){
            switch (arr[i]){
                case "btn1": setColor(btn1); break;
                case "btn2": setColor(btn2); break;
                case "btn3": setColor(btn3); break;
                case "btn4": setColor(btn4); break;
                case "btn5": setColor(btn5); break;
                case "btn6": setColor(btn6); break;
                case "btn7": setColor(btn7); break;
                case "btn8": setColor(btn8); break;
                case "btn9": setColor(btn9); break;
                default: break;
            }
        }
    }
    private void setColor(Button b){
        b.setStyle("-fx-background-color:#AFE0AF;"+"-fx-text-fill:#ffffff;");
    }
    public void updateGame(){
        isFullGame = false;
        counter = 0;
        isXorO = false;
        winnerType = "";
        updateButtonsAccess();
        updatePlayAgainButton();
        makeGridEmpty();
        playAgainBtn.setVisible(false);
        playAgainBtn.setDisable(true);
        
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
    public void updateButtonsAccess(){
        if(btn1.isDisable())
            makeButtonsEnabled();
        else
            makeButtonsDisabled();
    }
    public void makeButtonsEnabled(){
        btn1.setDisable(true);
        btn2.setDisable(true);
        btn3.setDisable(true);
        btn4.setDisable(true);
        btn5.setDisable(true);
        btn6.setDisable(true);
        btn7.setDisable(true);
        btn8.setDisable(true);
        btn9.setDisable(true);
    }
    public void makeButtonsDisabled(){
        btn1.setDisable(false);
        btn2.setDisable(false);
        btn3.setDisable(false);
        btn4.setDisable(false);
        btn5.setDisable(false);
        btn6.setDisable(false);
        btn7.setDisable(false);
        btn8.setDisable(false);
        btn9.setDisable(false);
    }

    @FXML
    private void playAgain(ActionEvent event) {
        playAgainBtn.setVisible(false);
        playAgainBtn.setDisable(true);
        NavigationController rematch = new NavigationController("/view/FXMLOneVSOneMode.fxml");
        rematch.navigateTo(event);
    }
    
    private void displayVideo(){
        if(winnerType.equals("X") || winnerType.equals("O")){
           NavigationController displayVideo = new NavigationController("/view/FXMLVideo.fxml");
           String winnerName = winnerType.equals("X") ? player1Name.getText() : player2Name.getText();
           displayVideo.displayVideo("winner","Congratulation " + winnerName); 
        }else{
             NavigationController displayVideo = new NavigationController("/view/FXMLVideo.fxml");
           displayVideo.displayVideo("draw","opps!!");  
        }
    }
}

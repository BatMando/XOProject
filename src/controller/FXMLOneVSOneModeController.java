/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
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
    //private boolean isWinner = false;
    private boolean isFullGame = false;
    private Text winnerText;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
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
    public boolean isFull(){
        return counter == 9;
    }
    public void checkIfGameIsOver(){
        for (int a = 0; a < 8; a++) {
            String line = ""; 
            switch (a) {
                case 0: 
                    line = btn1.getText() + btn2.getText() + btn3.getText();
                    break;
                case 1: 
                    line = btn4.getText() + btn5.getText() + btn6.getText();
                    break;
                case 2: 
                    line = btn7.getText() + btn8.getText() + btn9.getText();
                    break;
                case 3: 
                    line = btn1.getText() + btn5.getText() + btn9.getText();
                    break;

                case 4: 
                    line = btn3.getText() + btn5.getText() + btn7.getText();
                    break;
                case 5: 
                    line = btn1.getText() + btn4.getText() + btn7.getText();
                    break;
                case 6: 
                    line = btn2.getText() + btn5.getText() + btn8.getText();
                    break;
                case 7: 
                    line = btn3.getText() + btn6.getText() + btn9.getText();
                    break;
                default: line = null;
            };
            
            //X winner
            if ((line != null) && line.equals("XXX")) {
                System.out.println("X won!");
                //winnerText.setText("X won!");
                makeButtonsDisabled();
                isFullGame = true;
            }

            //O winner
            else if ((line != null) && line.equals("OOO")) {
                System.out.println("O won!");
                //winnerText.setText("O won!");
                makeButtonsDisabled();
                isFullGame = true;
            }
        }
    }
    public void onAnyButtonClicked(ActionEvent e){
        
        pressedButton = (Button) e.getSource();
        
        if(pressedButton.getText().equals("") && isFullGame == false){
            if(counter == 9){
                System.out.println("Draw");
                winnerText.setText("It's Draw!");
            }
            else if (isXorO == false){
                pressedButton.setText("X");
                isXorO = true;
                counter++;
                checkIfGameIsOver();
                
            }
            else if(isXorO == true){
                pressedButton.setText("O");
                isXorO = false;
                counter++;
                checkIfGameIsOver();
            }
        }
    }
    
    
}

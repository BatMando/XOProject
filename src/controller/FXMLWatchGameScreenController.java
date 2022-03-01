/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Helper.NavigationController;
import Helper.ReadWriteHelper;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * FXML Controller class
 *
 * @author Toba
 */
public class FXMLWatchGameScreenController implements Initializable {

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
    private Text userName2Label;
    @FXML
    private Text userName1Label;
    @FXML
    private Button back;
    
    
    protected File file;
    protected String characterToBePlayed;
    protected String position;
    String datareaded;
    public String [] arrOfData;
    Thread thread;
    String listType;
    private String greenColor = "#AFE0AF";
    private String yellowColor = "#ffe591";
   static String filePath = new File("").getAbsolutePath();
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    


    
    @FXML
    private void backToMainPage(ActionEvent event) {
         System.out.println("backToRecordedGames: called");
        if(listType.equals("local-mode")){
          NavigationController btnback = new NavigationController("/view/FXMLRecordsScreen.fxml");
           btnback.navigateToRecordList(event,"local-mode");   
        }else if (listType.equals("online-mode")) {
            NavigationController btnback = new NavigationController("/view/FXMLRecordsScreen.fxml");
            btnback.navigateToRecordList(event,"online-mode"); 
        }    
    }
    
    public  String[] getRecordGame(String dataStored)
    { System.out.println("getRecordGame  ");
		int index;
                String []data=new String[9];
                index=dataStored.indexOf(".");
                String player1 = dataStored.substring(0,index );
                System.out.println("player1 " +player1);
                dataStored=dataStored.substring(index+1,dataStored.length());
                System.out.println("dataStored " +dataStored);
                index=dataStored.indexOf(".");
                String player2 = dataStored.substring(0, index);
                System.out.println("player2 " +player2);
                dataStored=dataStored.substring(index+1,dataStored.length());
                System.out.println("dataStored2 " +dataStored);
                    
                     if(player2.equals("user") || player2.equals("null")){
                        Platform.runLater(()->{
                             userName1Label.setText(player1);
                         });
                     }else{
                         Platform.runLater(()->{
                             userName1Label.setText(player1);
                           userName2Label.setText(player2);
                         });
                          
                     }
		while(dataStored.length()!=0)
		{ 
                    index=dataStored.indexOf(".");
                    if(index != -1){
                        
                    }
                    
                
                    for(int i=0;i<9;i++)
                    {
	                index=dataStored.indexOf(".");
			if(index!=-1)
                        {
                          data[i]=dataStored.substring(0,index);
                          System.out.println(data[i]);
                            
			  dataStored=dataStored.substring(index+1,dataStored.length());
			}
			else 
			{
			    dataStored="";
			}    
                    }

                }
               return data;        
  }
    
     public void  setType(String  stringListType){ 
         listType = stringListType;
         System.out.println(listType);
         
         //flag = true ;
         displayGame();
               
    }
     
     private void displayGame(){
        
        if(listType.equals("local-mode")){
            File dir = new File("record/savedLocalGame");
            dir.mkdirs();
            datareaded=ReadWriteHelper.readFileAsString(dir+"/"+FXMLRecordsScreenController.recordedGameFileName);
        }else if (listType.equals("online-mode")){
            File dir = new File("record/savedOnlineGame");
            dir.mkdirs();
            datareaded=ReadWriteHelper.readFileAsString(dir+"/"+FXMLRecordsScreenController.recordedGameFileName);
        }
            
       System.out.println("done");
      thread= new Thread(new Runnable() {
           @Override
           public void run() {
               {  String[] arrOfData=getRecordGame(datareaded);
                     
                 while(arrOfData!=null){
                   try {
                       for(int a=0;a<9 && arrOfData[a]!=null;a++)
                           {
                           
                             position=arrOfData[a].substring(3,4);
                             characterToBePlayed=arrOfData[a].substring(4,5);
                             Platform.runLater(new Runnable() {
                             @Override
                             public void run() {
                               switch(position)
                                    {
                                      case "1":
                                          if(characterToBePlayed.equalsIgnoreCase("x"))
                                             btn1.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + greenColor + ";");
                                          else
                                              btn1.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + yellowColor + ";");
                                         btn1.setText(characterToBePlayed);
                                         break;
                                     case "2":
                                         if(characterToBePlayed.equalsIgnoreCase("x"))
                                             btn2.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + greenColor + ";");
                                          else
                                              btn2.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + yellowColor + ";");
                                        btn2.setText(characterToBePlayed);
                                         break;
                                     case "3":
                                         if(characterToBePlayed.equalsIgnoreCase("x"))
                                             btn3.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + greenColor + ";");
                                          else
                                              btn3.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + yellowColor + ";");
                                         btn3.setText(characterToBePlayed);
                                         break;
                                     case "4":
                                         if(characterToBePlayed.equalsIgnoreCase("x"))
                                             btn4.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + greenColor + ";");
                                          else
                                              btn4.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + yellowColor + ";");
                                         btn4.setText(characterToBePlayed);
                                         break;
                                     case "5":
                                         if(characterToBePlayed.equalsIgnoreCase("x"))
                                             btn5.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + greenColor + ";");
                                          else
                                              btn5.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + yellowColor + ";");
                                         btn5.setText(characterToBePlayed);
                                         break;
                                     case "6":
                                         if(characterToBePlayed.equalsIgnoreCase("x"))
                                             btn6.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + greenColor + ";");
                                          else
                                              btn6.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + yellowColor + ";");
                                         btn6.setText(characterToBePlayed);
                                         break;
                                     case "7":
                                         if(characterToBePlayed.equalsIgnoreCase("x"))
                                             btn7.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + greenColor + ";");
                                          else
                                              btn7.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + yellowColor + ";");
                                         btn7.setText(characterToBePlayed);
                                         break;
                                     case "8":
                                         if(characterToBePlayed.equalsIgnoreCase("x"))
                                             btn8.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + greenColor + ";");
                                          else
                                              btn8.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + yellowColor + ";");
                                         btn8.setText(characterToBePlayed);
                                         break;
                                     case "9":
                                         if(characterToBePlayed.equalsIgnoreCase("x"))
                                             btn9.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + greenColor + ";");
                                          else
                                              btn9.setStyle("-fx-background-color:#ffffff57;"+"-fx-text-fill: " + yellowColor + ";");
                                         btn9.setText(characterToBePlayed);
                                         break;
                                     default:
                                         break;
                                    }
                                        }
                                    });
                                    Thread.sleep(1000L);
                    }}catch (InterruptedException ex) {
                       Logger.getLogger(FXMLWatchGameScreenController.class.getName()).log(Level.SEVERE, null, ex);
                   }thread.stop();
                   }
               }
            
               }
                     
       });
              thread.start();
    }
     
}

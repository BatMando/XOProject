/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author mando
 */
public class FXMLVideoController implements Initializable {

    @FXML
    private MediaView videoPlayer;
    Stage thisStage;
    private String typePlayer;
    public static MediaPlayer mp;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    private void displayVideo() {

        if (typePlayer.equals("winner")) {
            //get video file and set it to media
            setMedia("/resources/winner.mp4");
        } else if (typePlayer.equals("loser")) {
            setMedia("/resources/loser.mp4");
        }else if (typePlayer.equals("draw")) {
            setMedia("/resources/draw.mp4");
        }

    }

    private void setMedia(String videoPath) {
        Media media = new Media(getClass().getResource(videoPath).toExternalForm());

        mp = new MediaPlayer(media);
        videoPlayer.setMediaPlayer(mp);
        mp.play();

    }

    //setType
    // when called get string type from watching video page ,check it to handle with video will be show 

    public void setType(String stringTypePlayer) {
        typePlayer = stringTypePlayer;
        System.out.println(typePlayer);
        displayVideo();
    }    
}

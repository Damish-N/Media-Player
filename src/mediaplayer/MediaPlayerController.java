/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mediaplayer;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.Callable;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaView;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.util.Duration;

/**
 *
 * @author HP
 */
public class MediaPlayerController implements Initializable {

    @FXML
    private MediaView mv;
    private MediaPlayer mp;
    private Media me;

    @FXML
    private ProgressBar pb;

    @FXML
    private Slider slider;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        String path = new File("D:\\Projects\\Java-Works\\JavaFx\\MediaPlayer\\src\\media\\vid.mp4").getAbsolutePath();
        me = new Media(new File(path).toURI().toString());
        mp = new MediaPlayer(me);
        mv.setMediaPlayer(mp);
        mp.setAutoPlay(true);
        DoubleProperty width = mv.fitWidthProperty();
        DoubleProperty height = mv.fitHeightProperty();
        width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
        height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));

//        
        slider.maxProperty().bind(Bindings.createDoubleBinding(() -> mp.getTotalDuration().toSeconds(),
                mp.totalDurationProperty()));
        mp.currentTimeProperty().addListener(new ChangeListener<Duration>() {
            @Override
            public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                slider.setValue(newValue.toSeconds());
//            System.out.println(newValue.toSeconds());

                double c = newValue.toSeconds() / mp.getTotalDuration().toSeconds();
                pb.setProgress(c);
            }
        });

    }

    public void play(ActionEvent event) {
        mp.play();
    }

    public void pauase(ActionEvent event) {
        mp.pause();
    }

    public void fast(ActionEvent event) {
        mp.setRate(2);
    }

    public void slow(ActionEvent event) {
        mp.setRate(0.5);
    }

    public void reload(ActionEvent event) {
        mp.seek(mp.getStartTime());
        mp.play();
    }

    public void start(ActionEvent event) {
        mp.seek(mp.getStartTime());
        mp.stop();
    }

    public void last(ActionEvent event) {
        mp.seek(mp.getTotalDuration());
        mp.stop();
    }

    public void stop(ActionEvent event) {
        mp.stop();
    }

    public void handle(MouseEvent mouseEvent) {
//        Duration.seconds(0.5);
        double sceneX = mouseEvent.getSceneX();
        System.out.println(sceneX);

        mp.seek(Duration.seconds(slider.getValue()));

//        mp.seek(Duration.seconds(slider.getValue()));
        
    }

    public void open(ActionEvent event) {
        mp.stop();
        System.out.println("hello");
        FileChooser fc = new FileChooser();
        File sf = fc.showOpenDialog(null);

        if (sf != null) {
            String path = sf.getAbsolutePath().toString();

            me = new Media(new File(path).toURI().toString());
            mp = new MediaPlayer(me);
            mv.setMediaPlayer(mp);
            mp.setAutoPlay(true);
            DoubleProperty width = mv.fitWidthProperty();
            DoubleProperty height = mv.fitHeightProperty();
            width.bind(Bindings.selectDouble(mv.sceneProperty(), "width"));
            height.bind(Bindings.selectDouble(mv.sceneProperty(), "height"));

            slider.maxProperty().bind(Bindings.createDoubleBinding(
                    () -> mp.getTotalDuration().toSeconds(),
                    mp.totalDurationProperty()));
            mp.currentTimeProperty().addListener(new ChangeListener<Duration>() {
                @Override
                public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue) {
                    slider.setValue(newValue.toSeconds());
                    //System.out.println(newValue.toSeconds());
                    double c = newValue.toSeconds() / mp.getTotalDuration().toSeconds();
                    pb.setProgress(c);
                }
            });

        }
    }

}

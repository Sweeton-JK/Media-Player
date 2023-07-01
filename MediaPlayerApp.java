import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class MediaPlayerApp extends Application {

    private MediaPlayer mediaPlayer;
    private MediaView mediaView;
    private Button openButton;
    private Button playButton;
    private Button pauseButton;
    private Button stopButton;

    @Override
    public void start(Stage primaryStage) {
        openButton = new Button("Open");
        playButton = new Button("Play");
        pauseButton = new Button("Pause");
        stopButton = new Button("Stop");

        openButton.setOnAction(e -> openFile());
        playButton.setOnAction(e -> mediaPlayer.play());
        pauseButton.setOnAction(e -> mediaPlayer.pause());
        stopButton.setOnAction(e -> mediaPlayer.stop());

        playButton.setDisable(true);
        pauseButton.setDisable(true);
        stopButton.setDisable(true);

        VBox vbox = new VBox();
        vbox.getChildren().addAll(openButton, playButton, pauseButton, stopButton);

        Scene scene = new Scene(vbox, 400, 200);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Media Player");
        primaryStage.show();
    }

    private void openFile() {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Media Files", "*.mp4", "*.mp3", "*.wav");
        fileChooser.getExtensionFilters().add(filter);

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            Media media = new Media(file.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView = new MediaView(mediaPlayer);

            mediaPlayer.setOnReady(() -> {
                playButton.setDisable(false);
                pauseButton.setDisable(false);
                stopButton.setDisable(false);
            });

            VBox vbox = new VBox();
            vbox.getChildren().addAll(mediaView, openButton, playButton, pauseButton, stopButton);

            Scene scene = new Scene(vbox, 600, 400);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Media Player");
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

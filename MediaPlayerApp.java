import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
        openButton = createButton("Open", "button-open");
        playButton = createButton("Play", "button-play");
        pauseButton = createButton("Pause", "button-pause");
        stopButton = createButton("Stop", "button-stop");

        openButton.setOnAction(e -> openFile());
        playButton.setOnAction(e -> mediaPlayer.play());
        pauseButton.setOnAction(e -> mediaPlayer.pause());
        stopButton.setOnAction(e -> mediaPlayer.stop());

        playButton.setDisable(true);
        pauseButton.setDisable(true);
        stopButton.setDisable(true);

        VBox buttonBox = new VBox(10, openButton, playButton, pauseButton, stopButton);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(20));

        Label titleLabel = new Label("Fantastic Media Player");
        titleLabel.getStyleClass().add("title-label");

        HBox titleBox = new HBox(titleLabel);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(20));

        BorderPane mainPane = new BorderPane();
        mainPane.setTop(titleBox);
        mainPane.setCenter(buttonBox);

        Scene scene = new Scene(mainPane, 600, 400);
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        primaryStage.setScene(scene);
        primaryStage.setTitle("Fantastic Media Player");
        primaryStage.show();
    }

    private Button createButton(String text, String styleClass) {
        Button button = new Button(text);
        button.getStyleClass().add(styleClass);
        return button;
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

            BorderPane videoPane = new BorderPane();
            videoPane.setCenter(mediaView);

            VBox buttonBox = new VBox(10, openButton, playButton, pauseButton, stopButton);
            buttonBox.setAlignment(Pos.CENTER);
            buttonBox.setPadding(new Insets(20));

            BorderPane mainPane = new BorderPane();
            mainPane.setTop(buttonBox);
            mainPane.setCenter(videoPane);

            Scene scene = new Scene(mainPane, 800, 600);
            scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Fantastic Media Player");
            stage.show();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

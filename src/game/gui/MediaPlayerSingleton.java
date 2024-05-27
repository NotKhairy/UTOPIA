package game.gui;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MediaPlayerSingleton {
    private static MediaPlayer mediaPlayer;

    public static MediaPlayer getInstance(String mediaPath) {
        if (mediaPlayer == null) {
            Media media = new Media(MediaPlayerSingleton.class.getResource(mediaPath).toExternalForm());
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        }
        return mediaPlayer;
    }
}
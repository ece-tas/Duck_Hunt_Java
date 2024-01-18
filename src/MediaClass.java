import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/**
 * The MediaClass provides methods for playing audio media and adjusting the volume using JavaFX MediaPlayer.
 */
public class MediaClass {
    private File file;
    private MediaPlayer mediaPlayer;
    private Media media;

    /**
     * Sets the volume of the provided MediaPlayer.
     *
     * @param mediaPlayer The MediaPlayer to set the volume for.
     * @param volume      The volume value to set (between 0.0 and 1.0).
     */
    public static void setMediaPlayerVolume(MediaPlayer mediaPlayer, double volume) {
        mediaPlayer.setVolume(volume);
    }

    /**
     * Plays the media file at the specified path repeatedly.
     *
     * @param mediaPath The path of the media file to play.

     */
    public MediaPlayer playMedia(String mediaPath) {
        file = new File(mediaPath);
        String absolutePath = file.toURI().toString();

        media = new Media(absolutePath);
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        mediaPlayer.play();
        setMediaPlayerVolume(mediaPlayer, DuckHunt.VOLUME);

        return mediaPlayer;
    }

    /**
     * Plays the media file at the specified path once and returns the MediaPlayer object.
     *
     * @param mediaPath The path of the media file to play.
     * @return The MediaPlayer object used to play the media.
     */
    public MediaPlayer playMusicForOnce(String mediaPath) {
        file = new File(mediaPath);
        String absolutePath = file.toURI().toString();

        media = new Media(absolutePath);
        mediaPlayer = new MediaPlayer(media);
        setMediaPlayerVolume(mediaPlayer, DuckHunt.VOLUME);
        return mediaPlayer;
    }

    /**
     Returns the MediaPlayer object associated with this instance.
     @return The MediaPlayer object.
     */
    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }
}


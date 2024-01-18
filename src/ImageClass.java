
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 * The ImageClass provides methods for creating and manipulating JavaFX Image and ImageView objects.
 */
public class ImageClass {
    private Image image;
    private ImageView imageView;

    /**
     * Creates an ImageView with the specified image path that fits the width and height of the primaryStage.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     * @param imagePath    The path to the image file.
     * @return The created ImageView.
     */
    public ImageView createImageView(Stage primaryStage, String imagePath) {
        image = new Image(imagePath);
        imageView = new ImageView(image);
        imageView.fitWidthProperty().bind(primaryStage.widthProperty());
        imageView.fitHeightProperty().bind(primaryStage.heightProperty());
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Sets the ImageView with the specified image, fitting it to the width and height of the primaryStage.
     *
     * @param primaryStage The primary stage of the JavaFX application.
     * @param image        The image to be set in the ImageView.
     * @return The updated ImageView.
     */
    public ImageView setImageView(Stage primaryStage, Image image) {
        imageView = new ImageView(image);
        imageView.fitWidthProperty().bind(primaryStage.widthProperty());
        imageView.fitHeightProperty().bind(primaryStage.heightProperty());
        imageView.setPreserveRatio(true);
        return imageView;
    }

    /**
     * Returns the current ImageView.
     *
     * @return The current ImageView.
     */
    public ImageView getImageView() {
        return imageView;
    }

    /**
     * Sets the ImageView to the specified value.
     *
     * @param imageView The ImageView to be set.
     */
    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }
}

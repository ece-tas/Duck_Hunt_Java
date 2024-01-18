import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

/**
 * The LabelClass provides methods for creating and manipulating JavaFX Label objects.
 */
public class LabelClass {
    private Label label;

    /**
     * Creates a label with the specified text, color code, font, and size.
     *
     * @param text      The text to be displayed in the label.
     * @param colorCode The color code of the label text.
     * @param font      The font of the label text.
     * @param size      The size of the label text.
     */
    public void createLabel(String text, String colorCode, String font, int size) {
        label = new Label(text);
        label.setTextFill(Color.web(colorCode));
        label.setFont(Font.font(font, FontWeight.BOLD, size));
    }

    /**
     * Creates a flashing label with the specified duration for visibility and invisibility.
     *
     * @param label The label to be animated.
     * @param sec1  The duration in seconds for the label to be visible.
     * @param sec2  The duration in seconds for the label to be invisible.
     */
    public void createFlashingLabel(Label label, double sec1, double sec2) {
        label.setVisible(false);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(sec1), event -> label.setVisible(true)),
                new KeyFrame(Duration.seconds(sec2), event -> label.setVisible(false))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Creates a flashing label with the specified text, color code, font, size, and duration for visibility and invisibility.
     *
     * @param text      The text to be displayed in the label.
     * @param colorCode The color code of the label text.
     * @param font      The font of the label text.
     * @param size      The size of the label text.
     * @param sec1      The duration in seconds for the label to be visible.
     * @param sec2      The duration in seconds for the label to be invisible.
     */
    public void createFlashingLabel(String text, String colorCode, String font, int size, double sec1, double sec2) {
        label = new Label(text);
        label.setTextFill(Color.web(colorCode));
        label.setFont(Font.font(font, FontWeight.BOLD, size));
        label.setVisible(false);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(sec1), event -> label.setVisible(true)),
                new KeyFrame(Duration.seconds(sec2), event -> label.setVisible(false))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    /**
     * Sets the location of the label within the provided StackPane with the specified top and left margins.
     *
     * @param stackPane  The StackPane container.
     * @param labelClass The LabelClass instance containing the label.
     * @param top        The top margin of the label within the StackPane.
     * @param left       The left margin of the label within the StackPane.
     */
    public void setLocationOfLabel(StackPane stackPane, LabelClass labelClass, double top, double left) {

        stackPane.setAlignment(labelClass.getLabel(), Pos.TOP_CENTER);
        stackPane.setMargin(labelClass.getLabel(), new Insets(top, 0, 0, left));
    }

    /**
     * Returns the current label.
     *
     * @return The current label.
     */
    public Label getLabel() {
        return label;
    }

    /**
     * Sets the text of the label to the specified value.
     *
     * @param text The text to be set in the label.
     */
    public void setLabel(String text) {
        label.setText(text);
    }
}

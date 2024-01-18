
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;

/**
 * This class represents the Duck Hunt game and extends the Application class.
 */
public class DuckHunt extends Application {
    private StackPane newScreen, initialScreen;
    private Image image;
    private ImageView imageView, imageViewCrosshair;
    private Image finalImage, finalCursor, finalForeground;
    private MediaPlayer titleMedia, levelCompletedMedia, gunshotMedia, gameOverMedia, gameCompletedMedia;
    public static final int scale = 3;
    public static double VOLUME = 0.05;
    private final double screenHeight = 240 * scale;
    private final double screenWidth = 256 * scale;
    private static final String favicon_path = "assets\\favicon\\1.png";
    private final int ammoLeftLevel1 = 30;
    private final int ammoLeftLevel2 = 25;
    private final int ammoLeftLevel3 = 20;
    private final int ammoLeftLevel4 = 15;
    private final int ammoLeftLevel5 = 10;
    private final int ammoLeftLevel6 = 9;
    private int ammoLeft;
    private int duckHit;
    private boolean reflectRight;
    private boolean reflectRight2;
    private boolean reflectRight3;
    private int titleMediaCounter = 0;
    private MediaClass titleScreenMedia = new MediaClass();

    /**
     * The main method of the program.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }


    /**
     * The start method of the JavaFX application.
     * @param primaryStage The primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("HUBBM Duck Hunt");
        primaryStage.getIcons().add(new Image(new File(favicon_path).toURI().toString()));

        primaryStage.show();
        titleMedia = titleScreenMedia.playMedia
                ("assets\\effects\\Title.mp3");
        titleMedia.play();
        createInitialScreen(primaryStage);
    }

    /**
     * Creates the initial screen of the game.
     * @param primaryStage The primary stage for this application.
     */
    private void createInitialScreen(Stage primaryStage) {
        if (titleMediaCounter > 0) {
            titleMedia = titleScreenMedia.playMedia
                    ("assets\\effects\\Title.mp3");
            titleMedia.play();
        }
        initialScreen = new StackPane();
        Scene scene = new Scene(initialScreen, 256 * scale, 240 * scale);

        LabelClass flashingLabelEnter = new LabelClass();
        flashingLabelEnter.createFlashingLabel("PRESS ENTER TO START",
                "#F9A81D", "Arial", 18 * scale, 1, 2);
        flashingLabelEnter.getLabel().setTranslateY(50 * scale);

        LabelClass flashingLabelEscape = new LabelClass();
        flashingLabelEscape.createFlashingLabel("PRESS ESC TO EXIT",
                "#F9A81D", "Arial", 18 * scale, 1, 2);
        flashingLabelEscape.getLabel().setTranslateY(67 * scale);

        ImageClass imageWelcome = new ImageClass();
        imageWelcome.createImageView(primaryStage, "assets/welcome/1.png");

        initialScreen.getChildren().addAll(imageWelcome.getImageView(), flashingLabelEnter.getLabel(),
                flashingLabelEscape.getLabel());

        primaryStage.setScene(scene);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                createSelectionScreen(primaryStage);
            } else if (event.getCode() == KeyCode.ESCAPE) {
                primaryStage.close();
            }
        });
    }

    /**
     * Creates the selection screen of background and crosshair of the game.
     * @param primaryStage The primary stage for this application.
     */
    private void createSelectionScreen(Stage primaryStage) {
        newScreen = new StackPane();
        Scene scene = new Scene(newScreen, 256 * scale, 240 * scale);
        MediaClass selectionScreenMedia = new MediaClass();

        LabelClass labelText1 = new LabelClass();
        labelText1.createLabel("USE ARROW KEYS TO NAVIGATE", "#FFC400", "Arial", 11 * scale);

        LabelClass labelText2 = new LabelClass();
        labelText2.createLabel("PRESS ENTER TO START", "#FFC400", "Arial", 11 * scale);

        labelText1.setLocationOfLabel(newScreen, labelText1, 11* scale, 0);
        labelText2.setLocationOfLabel(newScreen, labelText2, 21 * scale, 0);

        final int[] currentImageIndex = {0};
        updateBackgroundImage(ArrayOf.arrayOfImage[currentImageIndex[0]]);

        final int[] currentCrosshairIndex = {0};
        updateCrosshair(ArrayOf.arrayOfCrosshair[currentCrosshairIndex[0]]);

        image = new Image("assets/background/1.png");
        imageView = new ImageView(image);
        imageView.fitWidthProperty().bind(primaryStage.widthProperty());
        imageView.fitHeightProperty().bind(primaryStage.heightProperty());
        imageView.setPreserveRatio(true);

        Image imageCrosshair = new Image("assets/crosshair/1.png");  //pass in the image path
        imageViewCrosshair= new ImageView(imageCrosshair);
        imageViewCrosshair.setFitWidth(11 * scale);
        imageViewCrosshair.setFitHeight(11 * scale);

        newScreen.getChildren().addAll(imageView, labelText1.getLabel(), labelText2.getLabel(), imageViewCrosshair);
        newScreen.requestFocus();

        primaryStage.setScene(scene);


        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.RIGHT) {
                currentImageIndex[0] = (currentImageIndex[0] + 1) % 6;
                updateBackgroundImage(ArrayOf.arrayOfImage[currentImageIndex[0]]);
                finalImage = new Image(ArrayOf.arrayOfImage[currentImageIndex[0]]);
                finalForeground = new Image(ArrayOf.arrayOfForeground[currentImageIndex[0]]);
            } else if (event.getCode() == KeyCode.LEFT) {
                currentImageIndex[0] = (currentImageIndex[0] - 1 + 6) % 6;
                updateBackgroundImage(ArrayOf.arrayOfImage[currentImageIndex[0]]);
                finalImage = new Image(ArrayOf.arrayOfImage[currentImageIndex[0]]);
                finalForeground = new Image(ArrayOf.arrayOfForeground[currentImageIndex[0]]);

            } else if (event.getCode() == KeyCode.UP) {
                currentCrosshairIndex[0] = (currentCrosshairIndex[0] + 1) % 7;
                updateCrosshair(ArrayOf.arrayOfCrosshair[currentCrosshairIndex[0]]);
                finalCursor = new Image(ArrayOf.arrayOfCrosshair[currentCrosshairIndex[0]]);
            } else if (event.getCode() == KeyCode.DOWN) {
                currentCrosshairIndex[0] = (currentCrosshairIndex[0] - 1 + 7) % 7;
                updateCrosshair(ArrayOf.arrayOfCrosshair[currentCrosshairIndex[0]]);
                finalCursor = new Image(ArrayOf.arrayOfCrosshair[currentCrosshairIndex[0]]);

            } else if (event.getCode() == KeyCode.ENTER) {
                titleMedia.stop();
                titleMediaCounter++;

                PauseTransition pause = new PauseTransition(Duration.seconds(6));
                pause.setOnFinished(eventWait -> {
                    createLevel1(primaryStage);
                });
                pause.play();

                selectionScreenMedia.playMusicForOnce
                        ("assets/effects/Intro.mp3").play();
            } else if (event.getCode() == KeyCode.ESCAPE) {
                createInitialScreen(primaryStage);
            }

        });
    }

    /**
     * Creates level 1 of the game.
     * @param primaryStage The primary stage for this application.
     * @return The StackPane containing the level 1 elements.
     */
    private StackPane createLevel1(Stage primaryStage) {
        ammoLeft = ammoLeftLevel1;
        duckHit = 0;
        reflectRight = false;
        newScreen = new StackPane();
        Scene scene = new Scene(newScreen, 256*scale, 240*scale);
        MediaClass mediaClass = new MediaClass();
        MediaPlayer duckFallsMedia = mediaClass.playMusicForOnce
                ("assets\\effects\\DuckFalls.mp3");

        if (finalImage == null) {
            finalImage = new Image("assets/background/1.png");
        }
        if (finalCursor == null) {
            finalCursor = new Image("assets/crosshair/1.png");
        }
        if (finalForeground == null) {
            finalForeground = new Image("assets/foreground/1.png");
        }

        ImageClass finalImageView = new ImageClass();
        ImageClass finalFGImageView = new ImageClass();
        LabelClass labelLevel = new LabelClass();
        LabelClass labelAmmo = new LabelClass();

        gameArea(primaryStage, scene, finalImageView, finalFGImageView, labelLevel, labelAmmo,
                "LEVEL 1/6", 207, ammoLeftLevel1);


        Image duckImage1 = new Image("assets/duck_black/5.png");
        ImageView duck = new ImageView(duckImage1);
        duck.setFitHeight(20 * scale);
        duck.setFitWidth(34 * scale);
        duck.setScaleX(1);

        double startPosition = -117 * scale;
        double endPosition = screenWidth - 142 * scale ;

        TranslateTransition translate = new TranslateTransition(Duration.seconds(3), duck);
        translate.setByX(endPosition - startPosition);
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setAutoReverse(true);
        duck.setTranslateX(startPosition);
        duck.setTranslateY(-83 * scale);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    duck.setScaleX(-1);
                }),
                new KeyFrame(Duration.seconds(6), event -> {
                    duck.setScaleX(1);
                })
        );
        Timeline timelineReflection = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    reflectRight = true;
                }),
                new KeyFrame(Duration.seconds(6), event -> {
                    reflectRight = false;
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timelineReflection.setCycleCount(Timeline.INDEFINITE);

        final int[] currentDuckIndex = {0};
        Timeline timelineFlying = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            if (duckHit == 0) {
                currentDuckIndex[0] = (currentDuckIndex[0] + 1) % 3;

                if (currentDuckIndex[0] == 1) {
                    duck.setImage(new Image("assets/duck_black/5.png"));
                    duck.setFitWidth(34 * scale);
                    duck.setFitHeight(20 * scale);
                    duck.setTranslateY(-86 * scale);
                } else if (currentDuckIndex[0] == 2) {
                    duck.setImage(new Image("assets/duck_black/6.png"));
                    duck.setFitWidth(34 * scale);
                    duck.setFitHeight(24 * scale);
                    duck.setTranslateY(-82 * scale);
                } else if (currentDuckIndex[0] == 0) {
                    duck.setImage(new Image("assets/duck_black/4.png"));
                    duck.setFitWidth(34 * scale);
                    duck.setFitHeight(29 * scale);
                    duck.setTranslateY(-80 * scale);
                }
            }
        }));
        timelineFlying.setCycleCount(Timeline.INDEFINITE);
        translate.play();
        timeline.play();
        timelineFlying.play();
        timelineReflection.play();

        newScreen.getChildren().addAll(finalImageView.getImageView(), labelLevel.getLabel(), labelAmmo.getLabel(),
                duck, finalFGImageView.getImageView());
        newScreen.requestFocus();

        duck.setOnMouseClicked(event -> {
            if (ammoLeft > 0) {
                duckHit++;
                duck.setImage(new Image("assets/duck_black/7.png"));
                duck.setFitHeight(29 * scale);
                duck.setFitWidth(31 * scale);
                translate.stop();
                timeline.stop();
                timelineReflection.stop();

                PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                pause.setOnFinished(eventWait -> {
                    duck.setImage(new Image("assets/duck_black/8.png"));
                    duck.setFitHeight(31 * scale);
                    duck.setFitWidth(18 * scale);
                    if (reflectRight == true) {
                        duck.setScaleX(-1);
                    } else if(reflectRight == false) {
                        duck.setScaleX(1);
                    }
                    duckFallsMedia.play();

                    double startPositionY = -83 * scale;
                    double endPositionY = screenHeight - 20 * scale ;

                    TranslateTransition translateWait = new TranslateTransition(Duration.seconds(3), duck);
                    translateWait.setByY(endPositionY - startPositionY);
                    translateWait.play();
                });
                pause.play();

                if (duckHit == 1) {
                    gunshotMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\Gunshot.mp3");
                    gunshotMedia.play();

                    if (ammoLeft > 0) {
                        ammoLeft--;
                        labelAmmo.setLabel("Ammo Left: " + ammoLeft);
                    }
                    levelCompletedMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\LevelCompleted.mp3");
                    levelCompletedMedia.play();

                    win(scene, () -> createLevel2(primaryStage), levelCompletedMedia, duckFallsMedia);
                }
            }
        });

        scene.setOnMouseClicked(event -> {
            if (ammoLeft > 0) {
                if (duckHit == 0) {
                    gunshotMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\Gunshot.mp3");
                    gunshotMedia.play();

                    if (ammoLeft > 0) {
                        ammoLeft--;
                        labelAmmo.setLabel("Ammo Left: " + ammoLeft);

                    } if (ammoLeft == 0) {
                        timelineReflection.stop();
                        gameOverMedia = mediaClass.playMusicForOnce
                                ("assets\\effects\\GameOver.mp3");
                        gameOverMedia.play();

                        lose(primaryStage, scene, () -> createLevel1(primaryStage),
                                gameOverMedia, duckFallsMedia, ammoLeftLevel1);
                    }
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
        return newScreen;
    }

    /**
     * Creates level 2 of the game.
     * @param primaryStage The primary stage for this application.
     * @return The StackPane containing the level 2 elements.
     */
    private StackPane createLevel2(Stage primaryStage) {
        ammoLeft = ammoLeftLevel2;
        duckHit = 0;
        reflectRight = false;

        newScreen = new StackPane();
        Scene scene = new Scene(newScreen, 256*scale, 240*scale);
        MediaClass mediaClass = new MediaClass();
        MediaPlayer duckFallsMedia = mediaClass.playMusicForOnce
                ("assets\\effects\\DuckFalls.mp3");

        ImageClass finalImageView = new ImageClass();
        ImageClass finalFGImageView = new ImageClass();
        LabelClass labelLevel = new LabelClass();
        LabelClass labelAmmo = new LabelClass();

        gameArea(primaryStage, scene, finalImageView, finalFGImageView, labelLevel, labelAmmo,
                "LEVEL 2/6", 207, ammoLeftLevel2);


        Image duckImage1 = new Image("assets/duck_red/5.png");
        ImageView duck = new ImageView(duckImage1);
        duck.setFitHeight(20 * scale);
        duck.setFitWidth(34 * scale);
        duck.setScaleX(1);

        double startPosition = -117 * scale;
        double endPosition = screenWidth - 142 * scale ;
        double startInitialPositionY = 0 * scale;
        double endInitialPositionY = -90 * scale;

        TranslateTransition translate = new TranslateTransition(Duration.seconds(2.4), duck);
        translate.setByX(endPosition - startPosition);
        translate.setByY(endInitialPositionY - startInitialPositionY);
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setAutoReverse(true);
        duck.setTranslateX(startPosition);
        duck.setTranslateY(startInitialPositionY);


        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2.4), event -> {
                    duck.setScaleX(-1);
                }),
                new KeyFrame(Duration.seconds(4.8), event -> {
                    duck.setScaleX(1);
                })
        );
        Timeline timelineReflection = new Timeline(
                new KeyFrame(Duration.seconds(2.4), event -> {
                    reflectRight = true;
                }),
                new KeyFrame(Duration.seconds(4.8), event -> {
                    reflectRight = false;
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timelineReflection.setCycleCount(Timeline.INDEFINITE);

        final int[] currentDuckIndex = {0};
        Timeline timelineFlying = new Timeline(new KeyFrame(Duration.millis(440), event -> {
            if (duckHit == 0) {
                currentDuckIndex[0] = (currentDuckIndex[0] + 1) % 3;

                if (currentDuckIndex[0] == 1) {
                    duck.setImage(new Image("assets/duck_red/5.png"));
                    duck.setFitWidth(34 * scale);
                    duck.setFitHeight(20 * scale);

                } else if (currentDuckIndex[0] == 2) {
                    duck.setImage(new Image("assets/duck_red/6.png"));
                    duck.setFitWidth(34 * scale);
                    duck.setFitHeight(24 * scale);

                } else if (currentDuckIndex[0] == 0) {
                    duck.setImage(new Image("assets/duck_red/4.png"));
                    duck.setFitWidth(34 * scale);
                    duck.setFitHeight(29 * scale);
                }
            }
        }));
        timelineFlying.setCycleCount(Timeline.INDEFINITE);
        translate.play();
        timeline.play();
        timelineFlying.play();
        timelineReflection.play();

        newScreen.getChildren().addAll(finalImageView.getImageView(), labelLevel.getLabel(), labelAmmo.getLabel(),
                duck, finalFGImageView.getImageView());
        newScreen.requestFocus();


        duck.setOnMouseClicked(event -> {
            if (ammoLeft > 0) {
                duckHit++;
                duck.setImage(new Image("assets/duck_red/7.png"));
                duck.setFitHeight(29 * scale);
                duck.setFitWidth(31 * scale);
                translate.stop();
                timeline.stop();
                timelineReflection.stop();

                PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                pause.setOnFinished(eventWait -> {
                    duck.setImage(new Image("assets/duck_red/8.png"));
                    duck.setFitHeight(31 * scale);
                    duck.setFitWidth(18 * scale);
                    if (reflectRight == true) {
                        duck.setScaleX(-1);
                    } else if(reflectRight == false) {
                        duck.setScaleX(1);
                    }
                    duckFallsMedia.play();

                    double startPositionY = -83 * scale;
                    double endPositionY = screenHeight - 20 * scale ;

                    TranslateTransition translateWait = new TranslateTransition(Duration.seconds(3.3), duck);
                    translateWait.setByY(endPositionY - startPositionY);
                    translateWait.play();
                });
                pause.play();

                if (duckHit == 1) {
                    gunshotMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\Gunshot.mp3");
                    gunshotMedia.play();

                    if (ammoLeft > 0) {
                        ammoLeft--;
                        labelAmmo.setLabel("Ammo Left: " + ammoLeft);
                    }

                    levelCompletedMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\LevelCompleted.mp3");
                    levelCompletedMedia.play();

                    win(scene, () -> createLevel3(primaryStage), levelCompletedMedia, duckFallsMedia);
                }

            }
        });

        scene.setOnMouseClicked(event -> {
            if (ammoLeft > 0) {

                if (duckHit == 0) {
                    gunshotMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\Gunshot.mp3");
                    gunshotMedia.play();

                    if (ammoLeft > 0) {
                        ammoLeft--;
                        labelAmmo.setLabel("Ammo Left: " + ammoLeft);

                    } if (ammoLeft == 0) {
                        timelineReflection.stop();
                        gameOverMedia = mediaClass.playMusicForOnce
                                ("assets\\effects\\GameOver.mp3");
                        gameOverMedia.play();

                        lose(primaryStage, scene, () -> createLevel1(primaryStage),
                                gameOverMedia, duckFallsMedia, ammoLeftLevel2);
                    }
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
        return newScreen;
    }

    /**
     * Creates level 3 of the game.
     * @param primaryStage The primary stage for this application.
     * @return The StackPane containing the level 3 elements.
     */
    private StackPane createLevel3(Stage primaryStage) {
        ammoLeft = ammoLeftLevel3;
        duckHit = 0;
        reflectRight = false;
        newScreen = new StackPane();
        Scene scene = new Scene(newScreen, 256*scale, 240*scale);
        MediaClass mediaClass = new MediaClass();
        MediaPlayer duckFallsMedia = mediaClass.playMusicForOnce
                ("assets\\effects\\DuckFalls.mp3");

        ImageClass finalImageView = new ImageClass();
        ImageClass finalFGImageView = new ImageClass();
        LabelClass labelLevel = new LabelClass();
        LabelClass labelAmmo = new LabelClass();

        gameArea(primaryStage, scene, finalImageView, finalFGImageView, labelLevel, labelAmmo,
                "LEVEL 3/6", 207, ammoLeftLevel3);


        Image duckImage1 = new Image("assets/duck_blue/2.png");
        ImageView duck = new ImageView(duckImage1);
        duck.setFitHeight(29 * scale);
        duck.setFitWidth(32 * scale);
        duck.setScaleX(1);

        double startPosition = -117 * scale;
        double endPosition = screenWidth - 142 * scale ;
        double startInitialPositionY = 0 * scale;
        double endInitialPositionY = -103 * scale;

        TranslateTransition translate = new TranslateTransition(Duration.seconds(3), duck);
        translate.setByX(endPosition - startPosition);
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setAutoReverse(true);
        duck.setTranslateX(startPosition);

        TranslateTransition translateY = new TranslateTransition(Duration.seconds(1.5), duck);
        translateY.setByY(endInitialPositionY - startInitialPositionY);
        translateY.setCycleCount(TranslateTransition.INDEFINITE);
        translateY.setAutoReverse(true);
        duck.setTranslateY(startInitialPositionY);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1.5), event -> {
                    duck.setScaleY(-1);
                    duck.setScaleX(1);
                }),
                new KeyFrame(Duration.seconds(3), event -> {
                    duck.setScaleY(1);
                    duck.setScaleX(-1);
                }),
                new KeyFrame(Duration.seconds(4.5), event -> {
                    duck.setScaleX(-1);
                    duck.setScaleY(-1);
                }),
                new KeyFrame(Duration.seconds(6), event -> {
                    duck.setScaleY(1);
                    duck.setScaleX(1);
                })
        );
        Timeline timelineReflection = new Timeline(
                new KeyFrame(Duration.seconds(1.5), event -> {
                    reflectRight = false;
                }),
                new KeyFrame(Duration.seconds(3), event -> {
                    reflectRight = true;
                }),
                new KeyFrame(Duration.seconds(4.5), event -> {
                    reflectRight = true;
                }),
                new KeyFrame(Duration.seconds(6), event -> {
                    reflectRight = true;
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timelineReflection.setCycleCount(Timeline.INDEFINITE);

        final int[] currentDuckIndex = {0};
        Timeline timelineFlying = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            if (duckHit == 0) {
                currentDuckIndex[0] = (currentDuckIndex[0] + 1) % 3;

                if (currentDuckIndex[0] == 1) {
                    duck.setImage(new Image("assets/duck_blue/2.png"));
                    duck.setFitWidth(32 * scale);
                    duck.setFitHeight(29 * scale);
                } else if (currentDuckIndex[0] == 2) {
                    duck.setImage(new Image("assets/duck_blue/3.png"));
                    duck.setFitWidth(25 * scale);
                    duck.setFitHeight(31 * scale);
                } else if (currentDuckIndex[0] == 0) {
                    duck.setImage(new Image("assets/duck_blue/1.png"));
                    duck.setFitWidth(27 * scale);
                    duck.setFitHeight(31 * scale);
                }
            }
        }));
        timelineFlying.setCycleCount(Timeline.INDEFINITE);
        translate.play();
        timeline.play();
        timelineFlying.play();
        translateY.play();
        timelineReflection.play();

        newScreen.getChildren().addAll(finalImageView.getImageView(), labelLevel.getLabel(), labelAmmo.getLabel(),
                duck, finalFGImageView.getImageView());
        newScreen.requestFocus();

        duck.setOnMouseClicked(event -> {
            if (ammoLeft > 0) {
                duckHit++;
                duck.setImage(new Image("assets/duck_blue/7.png"));
                duck.setFitHeight(29 * scale);
                duck.setFitWidth(31 * scale);
                translate.stop();
                translateY.stop();
                timeline.stop();
                timelineReflection.stop();

                PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                pause.setOnFinished(eventWait -> {
                    duck.setImage(new Image("assets/duck_blue/8.png"));
                    duck.setFitHeight(31 * scale);
                    duck.setFitWidth(18 * scale);
                    if (reflectRight == true) {
                        duck.setScaleX(-1);
                    } else if (reflectRight == false) {
                        duck.setScaleX(1);
                    }
                    duckFallsMedia.play();

                    double startPositionY = -83 * scale;
                    double endPositionY = screenHeight - 20 * scale;

                    TranslateTransition translateWait = new TranslateTransition(Duration.seconds(3.3), duck);
                    translateWait.setByY(endPositionY - startPositionY);
                    translateWait.play();
                });
                pause.play();

                if (duckHit == 1) {
                    gunshotMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\Gunshot.mp3");
                    gunshotMedia.play();

                    if (ammoLeft > 0) {
                        ammoLeft--;
                        labelAmmo.setLabel("Ammo Left: " + ammoLeft);
                    }
                    levelCompletedMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\LevelCompleted.mp3");
                    levelCompletedMedia.play();

                    win(scene, () -> createLevel4(primaryStage), levelCompletedMedia, duckFallsMedia);
                }
            }
        });

        scene.setOnMouseClicked(event -> {
            if (ammoLeft > 0) {

                if (duckHit == 0) {
                    gunshotMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\Gunshot.mp3");
                    gunshotMedia.play();

                    if (ammoLeft > 0) {
                        ammoLeft--;
                        labelAmmo.setLabel("Ammo Left: " + ammoLeft);

                    } if (ammoLeft == 0) {
                        timelineReflection.stop();
                        gameOverMedia = mediaClass.playMusicForOnce
                                ("assets\\effects\\GameOver.mp3");
                        gameOverMedia.play();

                        lose(primaryStage, scene, () -> createLevel1(primaryStage),
                                gameOverMedia, duckFallsMedia, ammoLeftLevel3);
                    }
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
        return newScreen;
    }

    /**
     * Creates level 4 of the game.
     * @param primaryStage The primary stage for this application.
     * @return The StackPane containing the level 4 elements.
     */
    private StackPane createLevel4(Stage primaryStage) {
        ammoLeft = ammoLeftLevel4;
        duckHit = 0;
        reflectRight = false;
        reflectRight2 = false;
        newScreen = new StackPane();
        Scene scene = new Scene(newScreen, 256*scale, 240*scale);
        MediaClass mediaClass = new MediaClass();
        MediaPlayer duckFallsMedia = mediaClass.playMusicForOnce
                ("assets\\effects\\DuckFalls.mp3");
        MediaPlayer duckFallsMedia2 = mediaClass.playMusicForOnce
                ("assets\\effects\\DuckFalls.mp3");

        ImageClass finalImageView = new ImageClass();
        ImageClass finalFGImageView = new ImageClass();
        LabelClass labelLevel = new LabelClass();
        LabelClass labelAmmo = new LabelClass();

        gameArea(primaryStage, scene, finalImageView, finalFGImageView, labelLevel, labelAmmo,
                "LEVEL 4/6", 207, ammoLeftLevel4);


        Image duckImage1 = new Image("assets/duck_blue/2.png");
        ImageView duck = new ImageView(duckImage1);
        duck.setFitHeight(29 * scale);
        duck.setFitWidth(32 * scale);
        duck.setScaleX(1);
        duck.setScaleY(1);

        Image duckImage2 = new Image("assets/duck_black/5.png");
        ImageView duck2 = new ImageView(duckImage2);
        duck2.setFitHeight(20 * scale);
        duck2.setFitWidth(34 * scale);
        duck2.setScaleX(1);

        double startPosition = -117 * scale;
        double endPosition = screenWidth - 142 * scale ;
        double startInitialPositionY = 20 * scale;
        double endInitialPositionY = -100 * scale;

        TranslateTransition translate = new TranslateTransition(Duration.seconds(3), duck);
        translate.setByX(endPosition - startPosition);
        translate.setByY(endInitialPositionY - startInitialPositionY);
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setAutoReverse(true);
        duck.setTranslateX(startPosition);
        duck.setTranslateY(startInitialPositionY);

        TranslateTransition translate2 = new TranslateTransition(Duration.seconds(2.4), duck2);
        translate2.setByX(endPosition - startPosition);
        translate2.setCycleCount(TranslateTransition.INDEFINITE);
        translate2.setAutoReverse(true);
        duck2.setTranslateX(startPosition);
        duck2.setTranslateY(-40 * scale);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    duck.setScaleX(-1);
                    duck.setScaleY(-1);
                }),
                new KeyFrame(Duration.seconds(6), event -> {
                    duck.setScaleX(1);
                    duck.setScaleY(1);
                })
        );
        Timeline timeline2 = new Timeline(
                new KeyFrame(Duration.seconds(2.4), event -> {
                    duck2.setScaleX(-1);
                }),
                new KeyFrame(Duration.seconds(4.8), event -> {
                    duck2.setScaleX(1);
                })
        );
        Timeline timelineReflection = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    reflectRight = true;
                }),
                new KeyFrame(Duration.seconds(6), event -> {
                    reflectRight = false;
                })
        );
        Timeline timeline2Reflection = new Timeline(
                new KeyFrame(Duration.seconds(2.4), event -> {
                    reflectRight = true;
                }),
                new KeyFrame(Duration.seconds(4.8), event -> {
                    reflectRight2 = false;
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline2.setCycleCount(Timeline.INDEFINITE);
        timelineReflection.setCycleCount(Timeline.INDEFINITE);
        timeline2Reflection.setCycleCount(Timeline.INDEFINITE);

        final int[] currentDuckIndex = {0};
        Timeline timelineFlying = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            if (duckHit == 0 || duckHit == 1) {
                currentDuckIndex[0] = (currentDuckIndex[0] + 1) % 3;

                if (currentDuckIndex[0] == 1) {
                    duck.setImage(new Image("assets/duck_blue/2.png"));
                    duck.setFitWidth(32 * scale);
                    duck.setFitHeight(29 * scale);

                } else if (currentDuckIndex[0] == 2) {
                    duck.setImage(new Image("assets/duck_blue/3.png"));
                    duck.setFitWidth(25 * scale);
                    duck.setFitHeight(31 * scale);

                } else if (currentDuckIndex[0] == 0) {
                    duck.setImage(new Image("assets/duck_blue/1.png"));
                    duck.setFitWidth(27 * scale);
                    duck.setFitHeight(31 * scale);
                }
            }
        }));
        final int[] currentDuck2Index = {0};
        Timeline timelineFlying2 = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            if (duckHit == 0 || duckHit == 1) {
                currentDuck2Index[0] = (currentDuck2Index[0] + 1) % 3;

                if (currentDuck2Index[0] == 1) {
                    duck2.setImage(new Image("assets/duck_black/5.png"));
                    duck2.setFitWidth(34 * scale);
                    duck2.setFitHeight(20 * scale);
                    duck2.setTranslateY(-41 * scale);

                } else if (currentDuck2Index[0] == 2) {
                    duck2.setImage(new Image("assets/duck_black/6.png"));
                    duck2.setFitWidth(34 * scale);
                    duck2.setFitHeight(24 * scale);
                    duck2.setTranslateY(-39 * scale);

                } else if (currentDuck2Index[0] == 0) {
                    duck2.setImage(new Image("assets/duck_black/4.png"));
                    duck2.setFitWidth(34 * scale);
                    duck2.setFitHeight(29 * scale);
                    duck2.setTranslateY(-37 * scale);
                }
            }
        }));
        timelineFlying.setCycleCount(Timeline.INDEFINITE);
        timelineFlying2.setCycleCount(Timeline.INDEFINITE);
        translate.play();
        translate2.play();
        timeline.play();
        timeline2.play();
        timelineFlying.play();
        timelineFlying2.play();
        timelineReflection.stop();
        timeline2Reflection.play();

        newScreen.getChildren().addAll(finalImageView.getImageView(), labelLevel.getLabel(), labelAmmo.getLabel(),
                duck, duck2, finalFGImageView.getImageView());
        newScreen.requestFocus();

        duck.setOnMouseClicked(event -> {
            if (ammoLeft > 0) {
                duckHit++;
                duck.setImage(new Image("assets/duck_blue/7.png"));
                duck.setFitHeight(29 * scale);
                duck.setFitWidth(31 * scale);
                translate.stop();
                timeline.stop();
                timelineFlying.stop();
                timelineReflection.stop();

                PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                pause.setOnFinished(eventWait -> {
                    duck.setImage(new Image("assets/duck_blue/8.png"));
                    duck.setFitHeight(31 * scale);
                    duck.setFitWidth(18 * scale);
                    if (reflectRight == true) {
                        duck.setScaleX(-1);
                    } else if (reflectRight == false) {
                        duck.setScaleX(1);
                    }
                    duckFallsMedia.play();

                    double startPositionY = -83 * scale;
                    double endPositionY = screenHeight - 20 * scale;

                    TranslateTransition translateWait = new TranslateTransition(Duration.seconds(3.3), duck);
                    translateWait.setByY(endPositionY - startPositionY);
                    translateWait.play();
                });
                pause.play();

                if (duckHit == 2) {
                    gunshotMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\Gunshot.mp3");
                    gunshotMedia.play();

                    if (ammoLeft > 0) {
                        ammoLeft--;
                        labelAmmo.setLabel("Ammo Left: " + ammoLeft);
                    }
                    levelCompletedMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\LevelCompleted.mp3");
                    levelCompletedMedia.play();
                    win(scene, () -> createLevel5(primaryStage), levelCompletedMedia, duckFallsMedia);
                }
            }
        });

        duck2.setOnMouseClicked(event -> {
            if (ammoLeft > 0) {
                duckHit++;
                duck2.setImage(new Image("assets/duck_black/7.png"));
                duck2.setFitHeight(29 * scale);
                duck2.setFitWidth(31 * scale);
                translate2.stop();
                timeline2.stop();
                timelineFlying2.stop();
                timeline2Reflection.stop();

                PauseTransition pause2 = new PauseTransition(Duration.seconds(0.5));
                pause2.setOnFinished(eventWait2 -> {
                    duck2.setImage(new Image("assets/duck_black/8.png"));
                    duck2.setFitHeight(31 * scale);
                    duck2.setFitWidth(18 * scale);
                    if (reflectRight2 == true) {
                        duck2.setScaleX(-1);
                    } else if (reflectRight2 == false) {
                        duck2.setScaleX(1);
                    }
                    duckFallsMedia2.play();

                    double startPositionY = -60 * scale;
                    double endPositionY = screenHeight - 20 * scale;

                    TranslateTransition translateWait = new TranslateTransition(Duration.seconds(3.3), duck2);
                    translateWait.setByY(endPositionY - startPositionY);
                    translateWait.play();
                });
                pause2.play();

                if (duckHit == 2) {
                    gunshotMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\Gunshot.mp3");
                    gunshotMedia.play();

                    if (ammoLeft > 0) {
                        ammoLeft--;
                        labelAmmo.setLabel("Ammo Left: " + ammoLeft);
                    }
                    levelCompletedMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\LevelCompleted.mp3");
                    levelCompletedMedia.play();
                    win(scene, () -> createLevel5(primaryStage), levelCompletedMedia, duckFallsMedia2);
                }
            }
        });


        scene.setOnMouseClicked(event -> {
            if (ammoLeft > 0) {
                if (duckHit < 2) {
                    gunshotMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\Gunshot.mp3");
                    gunshotMedia.play();

                    if (ammoLeft > 0) {
                        ammoLeft--;
                        labelAmmo.setLabel("Ammo Left: " + ammoLeft);
                    }
                }
            }  if (duckHit < 2 && ammoLeft == 0) {
                timelineReflection.stop();
                timeline2Reflection.stop();
                gameOverMedia = mediaClass.playMusicForOnce
                        ("assets\\effects\\GameOver.mp3");
                gameOverMedia.play();

                lose(primaryStage, scene, () -> createLevel1(primaryStage),
                        gameOverMedia, duckFallsMedia, ammoLeftLevel4);
                if (duckFallsMedia2 != null) {
                    duckFallsMedia2.stop();
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
        return newScreen;
    }

    /**
     * Creates level 5 of the game.
     * @param primaryStage The primary stage for this application.
     * @return The StackPane containing the level 5 elements.
     */
    private StackPane createLevel5(Stage primaryStage) {
        ammoLeft = ammoLeftLevel5;
        duckHit = 0;
        reflectRight = false;
        reflectRight2 = true;
        newScreen = new StackPane();
        Scene scene = new Scene(newScreen, 256*scale, 240*scale);
        MediaClass mediaClass = new MediaClass();
        MediaPlayer duckFallsMedia = mediaClass.playMusicForOnce
                ("assets\\effects\\DuckFalls.mp3");
        MediaPlayer duckFallsMedia2 = mediaClass.playMusicForOnce
                ("assets\\effects\\DuckFalls.mp3");

        ImageClass finalImageView = new ImageClass();
        ImageClass finalFGImageView = new ImageClass();
        LabelClass labelLevel = new LabelClass();
        LabelClass labelAmmo = new LabelClass();

        gameArea(primaryStage, scene, finalImageView, finalFGImageView, labelLevel, labelAmmo,
                "LEVEL 5/6", 207, ammoLeftLevel5);


        Image duckImage1 = new Image("assets/duck_red/2.png");
        ImageView duck = new ImageView(duckImage1);
        duck.setFitHeight(29 * scale);
        duck.setFitWidth(32 * scale);
        duck.setScaleX(1);

        Image duckImage2 = new Image("assets/duck_black/2.png");
        ImageView duck2 = new ImageView(duckImage2);
        duck2.setFitHeight(29 * scale);
        duck2.setFitWidth(32 * scale);
        duck2.setScaleX(-1);

        double startPosition = -117 * scale;
        double endPosition = screenWidth - 142 * scale ;
        double startInitialPositionY = 0 * scale;
        double endInitialPositionY = -90 * scale;

        TranslateTransition translate = new TranslateTransition(Duration.seconds(2.6), duck);
        translate.setByX(endPosition - startPosition);
        translate.setByY(endInitialPositionY - startInitialPositionY);
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setAutoReverse(true);
        duck.setTranslateX(startPosition);
        duck.setTranslateY(startInitialPositionY);

        TranslateTransition translate2 = new TranslateTransition(Duration.seconds(2.6), duck2);
        translate2.setByX(startPosition - endPosition);
        translate2.setByY(endInitialPositionY - startInitialPositionY);
        translate2.setCycleCount(TranslateTransition.INDEFINITE);
        translate2.setAutoReverse(true);
        duck2.setTranslateX(endPosition);
        duck2.setTranslateY(startInitialPositionY);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(2.6), event -> {
                    duck.setScaleX(-1);
                    duck.setScaleY(-1);
                }),
                new KeyFrame(Duration.seconds(5.2), event -> {
                    duck.setScaleX(1);
                    duck.setScaleY(1);
                })
        );
        Timeline timeline2 = new Timeline(
                new KeyFrame(Duration.seconds(2.6), event -> {
                    duck2.setScaleX(1);
                    duck2.setScaleY(-1);
                }),
                new KeyFrame(Duration.seconds(5.2), event -> {
                    duck2.setScaleX(-1);
                    duck2.setScaleY(1);
                })
        );
        Timeline timelineReflection = new Timeline(
                new KeyFrame(Duration.seconds(2.6), event -> {
                    reflectRight = true;
                }),
                new KeyFrame(Duration.seconds(5.2), event -> {
                    reflectRight = false;
                })
        );
        Timeline timelineReflection2 = new Timeline(
                new KeyFrame(Duration.seconds(2.6), event -> {
                    reflectRight2 = false;
                }),
                new KeyFrame(Duration.seconds(5.2), event -> {
                    reflectRight2 = true;
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline2.setCycleCount(Timeline.INDEFINITE);
        timelineReflection.setCycleCount(Timeline.INDEFINITE);
        timelineReflection2.setCycleCount(Timeline.INDEFINITE);

        final int[] currentDuckIndex = {0};
        Timeline timelineFlying = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            if (duckHit == 0 || duckHit == 1) {
                currentDuckIndex[0] = (currentDuckIndex[0] + 1) % 3;

                if (currentDuckIndex[0] == 1) {
                    duck.setImage(new Image("assets/duck_red/2.png"));
                    duck.setFitWidth(32 * scale);
                    duck.setFitHeight(29 * scale);

                } else if (currentDuckIndex[0] == 2) {
                    duck.setImage(new Image("assets/duck_red/3.png"));
                    duck.setFitWidth(25 * scale);
                    duck.setFitHeight(31 * scale);

                } else if (currentDuckIndex[0] == 0) {
                    duck.setImage(new Image("assets/duck_red/1.png"));
                    duck.setFitWidth(27 * scale);
                    duck.setFitHeight(31 * scale);
                }
            }
        }));
        final int[] currentDuck2Index = {0};
        Timeline timelineFlying2 = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            if (duckHit == 0 || duckHit == 1) {
                currentDuck2Index[0] = (currentDuck2Index[0] + 1) % 3;

                if (currentDuck2Index[0] == 1) {
                    duck2.setImage(new Image("assets/duck_black/2.png"));
                    duck2.setFitWidth(32 * scale);
                    duck2.setFitHeight(29 * scale);

                } else if (currentDuck2Index[0] == 2) {
                    duck2.setImage(new Image("assets/duck_black/3.png"));
                    duck2.setFitWidth(25 * scale);
                    duck2.setFitHeight(31 * scale);

                } else if (currentDuck2Index[0] == 0) {
                    duck2.setImage(new Image("assets/duck_black/1.png"));
                    duck2.setFitWidth(27 * scale);
                    duck2.setFitHeight(31 * scale);
                }
            }
        }));
        timelineFlying.setCycleCount(Timeline.INDEFINITE);
        timelineFlying2.setCycleCount(Timeline.INDEFINITE);
        translate.play();
        translate2.play();
        timeline.play();
        timeline2.play();
        timelineFlying.play();
        timelineFlying2.play();
        timelineReflection.play();
        timelineReflection2.play();


        newScreen.getChildren().addAll(finalImageView.getImageView(), labelLevel.getLabel(), labelAmmo.getLabel(),
                duck, duck2, finalFGImageView.getImageView());
        newScreen.requestFocus();


        duck.setOnMouseClicked(event -> {
            if (ammoLeft > 0) {
                duckHit++;
                duck.setImage(new Image("assets/duck_red/7.png"));
                duck.setFitHeight(29 * scale);
                duck.setFitWidth(31 * scale);
                translate.stop();
                timeline.stop();
                timelineFlying.stop();
                timelineReflection.stop();

                PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                pause.setOnFinished(eventWait -> {
                    duck.setImage(new Image("assets/duck_red/8.png"));
                    duck.setFitHeight(31 * scale);
                    duck.setFitWidth(18 * scale);
                    if (reflectRight == true) {
                        duck.setScaleX(-1);
                    } else if (reflectRight == false) {
                        duck.setScaleX(1);
                    }
                    duckFallsMedia.play();

                    double startPositionY = -83 * scale;
                    double endPositionY = screenHeight - 20 * scale;

                    TranslateTransition translateWait = new TranslateTransition(Duration.seconds(3.3), duck);
                    translateWait.setByY(endPositionY - startPositionY);
                    translateWait.play();
                });
                pause.play();

                if (duckHit == 2) {
                    gunshotMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\Gunshot.mp3");
                    gunshotMedia.play();

                    if (ammoLeft > 0) {
                        ammoLeft--;
                        labelAmmo.setLabel("Ammo Left: " + ammoLeft);
                    }
                    levelCompletedMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\LevelCompleted.mp3");
                    levelCompletedMedia.play();
                    win(scene, () -> createLevel6(primaryStage), levelCompletedMedia, duckFallsMedia);
                }
            }
        });

        duck2.setOnMouseClicked(event -> {
            if (ammoLeft > 0) {
                duckHit++;
                duck2.setImage(new Image("assets/duck_black/7.png"));
                duck2.setFitHeight(29 * scale);
                duck2.setFitWidth(31 * scale);
                translate2.stop();
                timeline2.stop();
                timelineFlying2.stop();
                timelineReflection2.stop();

                PauseTransition pause2 = new PauseTransition(Duration.seconds(0.5));
                pause2.setOnFinished(eventWait2 -> {
                    duck2.setImage(new Image("assets/duck_black/8.png"));
                    duck2.setFitHeight(31 * scale);
                    duck2.setFitWidth(18 * scale);
                    if (reflectRight2 == true) {
                        duck2.setScaleX(1);
                    } else if (reflectRight2 == false) {
                        duck2.setScaleX(-1);
                    }
                    duckFallsMedia2.play();

                    double startPositionY = -83 * scale;
                    double endPositionY = screenHeight - 20 * scale;

                    TranslateTransition translateWait = new TranslateTransition(Duration.seconds(3.3), duck2);
                    translateWait.setByY(endPositionY - startPositionY);
                    translateWait.play();
                });
                pause2.play();

                if (duckHit == 2) {
                    gunshotMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\Gunshot.mp3");
                    gunshotMedia.play();

                    if (ammoLeft > 0) {
                        ammoLeft--;
                        labelAmmo.setLabel("Ammo Left: " + ammoLeft);
                    }
                    levelCompletedMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\LevelCompleted.mp3");
                    levelCompletedMedia.play();
                    win(scene, () -> createLevel6(primaryStage), levelCompletedMedia, duckFallsMedia2);
                }
            }
        });


        scene.setOnMouseClicked(event -> {
            if (ammoLeft > 0) {
                if (duckHit < 2) {
                    gunshotMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\Gunshot.mp3");
                    gunshotMedia.play();

                    if (ammoLeft > 0) {
                        ammoLeft--;
                        labelAmmo.setLabel("Ammo Left: " + ammoLeft);

                    }
                }
            }  if (duckHit < 2 && ammoLeft == 0) {
               timelineReflection.stop();
               timelineReflection2.stop();
                gameOverMedia = mediaClass.playMusicForOnce
                        ("assets\\effects\\GameOver.mp3");
                gameOverMedia.play();

                lose(primaryStage, scene, () -> createLevel1(primaryStage),
                        gameOverMedia, duckFallsMedia, ammoLeftLevel5);
                if (duckFallsMedia2 != null) {
                    duckFallsMedia2.stop();
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
        return newScreen;
    }

    /**
     * Creates level 6 of the game.
     * @param primaryStage The primary stage for this application.
     * @return The StackPane containing the level 6 elements.
     */
    private StackPane createLevel6(Stage primaryStage) {
        ammoLeft = ammoLeftLevel6;
        duckHit = 0;
        reflectRight = false;
        reflectRight2 = true;
        reflectRight3 = false;
        newScreen = new StackPane();
        Scene scene = new Scene(newScreen, 256*scale, 240*scale);
        MediaClass mediaClass = new MediaClass();
        MediaPlayer duckFallsMedia = mediaClass.playMusicForOnce
                ("assets\\effects\\DuckFalls.mp3");
        MediaPlayer duckFallsMedia2 = mediaClass.playMusicForOnce
                ("assets\\effects\\DuckFalls.mp3");
        MediaPlayer duckFallsMedia3 = mediaClass.playMusicForOnce
                ("assets\\effects\\DuckFalls.mp3");

        ImageClass finalImageView = new ImageClass();
        ImageClass finalFGImageView = new ImageClass();
        LabelClass labelLevel = new LabelClass();
        LabelClass labelAmmo = new LabelClass();

        gameArea(primaryStage, scene, finalImageView, finalFGImageView, labelLevel, labelAmmo,
                "LEVEL 6/6", 203, ammoLeftLevel6);


        Image duckImage1 = new Image("assets/duck_red/5.png");
        ImageView duck = new ImageView(duckImage1);
        duck.setFitHeight(20 * scale);
        duck.setFitWidth(34 * scale);
        duck.setScaleX(1);

        Image duckImage2 = new Image("assets/duck_black/5.png");
        ImageView duck2 = new ImageView(duckImage2);
        duck2.setFitHeight(20 * scale);
        duck2.setFitWidth(34 * scale);
        duck2.setScaleX(-1);

        Image duckImage3 = new Image("assets/duck_blue/5.png");
        ImageView duck3 = new ImageView(duckImage3);
        duck3.setFitHeight(20 * scale);
        duck3.setFitWidth(34 * scale);
        duck3.setScaleX(1);

        double startPosition = -117 * scale;
        double endPosition = screenWidth - 142 * scale ;
        double startInitialPositionY = 0 * scale;
        double endInitialPositionY = -90 * scale;

        TranslateTransition translate = new TranslateTransition(Duration.seconds(3), duck);
        translate.setByX(endPosition - startPosition);
        translate.setByY(endInitialPositionY - startInitialPositionY);
        translate.setCycleCount(TranslateTransition.INDEFINITE);
        translate.setAutoReverse(true);
        duck.setTranslateX(startPosition);
        duck.setTranslateY(startInitialPositionY);

        TranslateTransition translate2 = new TranslateTransition(Duration.seconds(3), duck2);
        translate2.setByX(startPosition - endPosition);
        translate2.setByY(endInitialPositionY - startInitialPositionY);
        translate2.setCycleCount(TranslateTransition.INDEFINITE);
        translate2.setAutoReverse(true);
        duck2.setTranslateX(endPosition);
        duck2.setTranslateY(startInitialPositionY);

        TranslateTransition translate3 = new TranslateTransition(Duration.seconds(3), duck3);
        translate3.setByX(endPosition - startPosition);
        translate3.setCycleCount(TranslateTransition.INDEFINITE);
        translate3.setAutoReverse(true);
        duck3.setTranslateX(startPosition);

        TranslateTransition translateY3 = new TranslateTransition(Duration.seconds(1.5), duck3);
        translateY3.setByY(endInitialPositionY - startInitialPositionY);
        translateY3.setCycleCount(TranslateTransition.INDEFINITE);
        translateY3.setAutoReverse(true);
        duck3.setTranslateY(startInitialPositionY);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    duck.setScaleX(-1);
                }),
                new KeyFrame(Duration.seconds(6), event -> {
                    duck.setScaleX(1);
                })
        );
        Timeline timeline2 = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    duck2.setScaleX(1);
                }),
                new KeyFrame(Duration.seconds(6), event -> {
                    duck2.setScaleX(-1);
                })
        );
        Timeline timeline3 = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    duck3.setScaleX(-1);
                }),
                new KeyFrame(Duration.seconds(6), event -> {
                    duck3.setScaleX(1);
                })
        );
        Timeline timelineReflection = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    reflectRight = true;
                }),
                new KeyFrame(Duration.seconds(6), event -> {
                    reflectRight = false;
                })
        );
        Timeline timelineReflection2 = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    reflectRight2 = false;
                }),
                new KeyFrame(Duration.seconds(6), event -> {
                    reflectRight3 = true;
                })
        );
        Timeline timelineReflection3 = new Timeline(
                new KeyFrame(Duration.seconds(3), event -> {
                    reflectRight3 = true;
                }),
                new KeyFrame(Duration.seconds(6), event -> {
                    reflectRight3 = false;
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline2.setCycleCount(Timeline.INDEFINITE);
        timeline3.setCycleCount(Timeline.INDEFINITE);
        timelineReflection.setCycleCount(Timeline.INDEFINITE);
        timelineReflection2.setCycleCount(Timeline.INDEFINITE);
        timelineReflection3.setCycleCount(Timeline.INDEFINITE);

        final int[] currentDuckIndex = {0};
        Timeline timelineFlying = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            if (duckHit == 0 || duckHit == 1 || duckHit == 2) {
                currentDuckIndex[0] = (currentDuckIndex[0] + 1) % 3;

                if (currentDuckIndex[0] == 1) {
                    duck.setImage(new Image("assets/duck_red/5.png"));
                    duck.setFitWidth(34 * scale);
                    duck.setFitHeight(20 * scale);

                } else if (currentDuckIndex[0] == 2) {
                    duck.setImage(new Image("assets/duck_red/6.png"));
                    duck.setFitWidth(34 * scale);
                    duck.setFitHeight(24 * scale);

                } else if (currentDuckIndex[0] == 0) {
                    duck.setImage(new Image("assets/duck_red/4.png"));
                    duck.setFitWidth(34 * scale);
                    duck.setFitHeight(29 * scale);
                }
            }
        }));
        final int[] currentDuck2Index = {0};
        Timeline timelineFlying2 = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            if (duckHit == 0 || duckHit == 1 || duckHit == 2) {
                currentDuck2Index[0] = (currentDuck2Index[0] + 1) % 3;

                if (currentDuck2Index[0] == 1) {
                    duck2.setImage(new Image("assets/duck_black/5.png"));
                    duck2.setFitWidth(34 * scale);
                    duck2.setFitHeight(20 * scale);

                } else if (currentDuck2Index[0] == 2) {
                    duck2.setImage(new Image("assets/duck_black/6.png"));
                    duck2.setFitWidth(34 * scale);
                    duck2.setFitHeight(24 * scale);

                } else if (currentDuck2Index[0] == 0) {
                    duck2.setImage(new Image("assets/duck_black/4.png"));
                    duck2.setFitWidth(34 * scale);
                    duck2.setFitHeight(29 * scale);
                }
            }
        }));
        final int[] currentDuckIndex3 = {0};
        Timeline timelineFlying3 = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            if (duckHit == 0 || duckHit == 1 || duckHit == 2) {
                currentDuckIndex3[0] = (currentDuckIndex3[0] + 1) % 3;

                if (currentDuckIndex3[0] == 1) {
                    duck3.setImage(new Image("assets/duck_blue/5.png"));
                    duck3.setFitWidth(34 * scale);
                    duck3.setFitHeight(20 * scale);
                } else if (currentDuckIndex3[0] == 2) {
                    duck3.setImage(new Image("assets/duck_blue/6.png"));
                    duck3.setFitWidth(34 * scale);
                    duck3.setFitHeight(24 * scale);
                } else if (currentDuckIndex3[0] == 0) {
                    duck3.setImage(new Image("assets/duck_blue/4.png"));
                    duck3.setFitWidth(34 * scale);
                    duck3.setFitHeight(29 * scale);
                }
            }
        }));
        timelineFlying.setCycleCount(Timeline.INDEFINITE);
        timelineFlying2.setCycleCount(Timeline.INDEFINITE);
        timelineFlying3.setCycleCount(Timeline.INDEFINITE);
        translate.play();
        translate2.play();
        translate3.play();
        translateY3.play();
        timeline.play();
        timeline2.play();
        timeline3.play();
        timelineFlying.play();
        timelineFlying2.play();
        timelineFlying3.play();
        timelineReflection.play();
        timelineReflection2.play();
        timelineReflection3.play();

        newScreen.getChildren().addAll(finalImageView.getImageView(), labelLevel.getLabel(), labelAmmo.getLabel(),
                duck, duck2, duck3, finalFGImageView.getImageView());
        newScreen.requestFocus();


        duck.setOnMouseClicked(event -> {
            if (ammoLeft > 0) {
                duckHit++;
                duck.setImage(new Image("assets/duck_red/7.png"));
                duck.setFitHeight(29 * scale);
                duck.setFitWidth(31 * scale);
                translate.stop();
                timeline.stop();
                timelineFlying.stop();
                timelineReflection.stop();

                PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
                pause.setOnFinished(eventWait -> {
                    duck.setImage(new Image("assets/duck_red/8.png"));
                    duck.setFitHeight(31 * scale);
                    duck.setFitWidth(18 * scale);
                    if (reflectRight2 == true) {
                        duck.setScaleX(-1);
                    } else if (reflectRight == false) {
                        duck.setScaleX(1);
                    }
                    duckFallsMedia.play();

                    double startPositionY = -83 * scale;
                    double endPositionY = screenHeight - 20 * scale;

                    TranslateTransition translateWait = new TranslateTransition(Duration.seconds(3.3), duck);
                    translateWait.setByY(endPositionY - startPositionY);
                    translateWait.play();
                });
                pause.play();

                if (duckHit == 3) {
                    gunshotMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\Gunshot.mp3");
                    gunshotMedia.play();

                    if (ammoLeft > 0) {
                        ammoLeft--;
                        labelAmmo.setLabel("Ammo Left: " + ammoLeft);
                    }
                    gameCompletedMedia = mediaClass.playMusicForOnce
                            ("assets/effects/GameCompleted.mp3");
                    gameCompletedMedia.play();

                    titleMediaCounter = 0;
                    winGame(scene, () -> createLevel1(primaryStage),
                            () -> createInitialScreen(primaryStage), gameCompletedMedia, duckFallsMedia);
                }
            }
        });

        duck2.setOnMouseClicked(event -> {
            if (ammoLeft > 0) {
                duckHit++;
                duck2.setImage(new Image("assets/duck_black/7.png"));
                duck2.setFitHeight(29 * scale);
                duck2.setFitWidth(31 * scale);
                translate2.stop();
                timeline2.stop();
                timelineFlying2.stop();
                timelineReflection2.stop();

                PauseTransition pause2 = new PauseTransition(Duration.seconds(0.5));
                pause2.setOnFinished(eventWait2 -> {
                    duck2.setImage(new Image("assets/duck_black/8.png"));
                    duck2.setFitHeight(31 * scale);
                    duck2.setFitWidth(18 * scale);
                    if (reflectRight2 == true) {
                        duck2.setScaleX(1);
                    } else if (reflectRight2 == false) {
                        duck2.setScaleX(-1);
                    }
                    duckFallsMedia2.play();

                    double startPositionY = -83 * scale;
                    double endPositionY = screenHeight - 20 * scale;

                    TranslateTransition translateWait = new TranslateTransition(Duration.seconds(3.3), duck2);
                    translateWait.setByY(endPositionY - startPositionY);
                    translateWait.play();
                });
                pause2.play();

                if (duckHit == 3) {
                    gunshotMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\Gunshot.mp3");
                    gunshotMedia.play();

                    if (ammoLeft > 0) {
                        ammoLeft--;
                        labelAmmo.setLabel("Ammo Left: " + ammoLeft);
                    }
                    gameCompletedMedia = mediaClass.playMusicForOnce
                            ("assets/effects/GameCompleted.mp3");
                    gameCompletedMedia.play();

                    titleMediaCounter = 0;
                    winGame(scene, () -> createLevel1(primaryStage),
                            () -> createInitialScreen(primaryStage), gameCompletedMedia, duckFallsMedia2);
                }
            }
        });

        duck3.setOnMouseClicked(event -> {
            if (ammoLeft > 0) {
                duckHit++;
                duck3.setImage(new Image("assets/duck_blue/7.png"));
                duck3.setFitHeight(29 * scale);
                duck3.setFitWidth(31 * scale);
                translate3.stop();
                translateY3.stop();
                timeline3.stop();
                timelineFlying3.stop();
                timelineReflection3.stop();

                PauseTransition pause3 = new PauseTransition(Duration.seconds(0.5));
                pause3.setOnFinished(eventWait -> {
                    duck3.setImage(new Image("assets/duck_blue/8.png"));
                    duck3.setFitHeight(31 * scale);
                    duck3.setFitWidth(18 * scale);
                    if (reflectRight3 == true) {
                        duck3.setScaleX(-1);
                    } else if (reflectRight3 == false) {
                        duck3.setScaleX(1);
                    }
                    duckFallsMedia3.play();

                    double startPositionY3 = -83 * scale;
                    double endPositionY3 = screenHeight - 20 * scale;

                    TranslateTransition translateWait3 = new TranslateTransition(Duration.seconds(3.3), duck3);
                    translateWait3.setByY(endPositionY3 - startPositionY3);
                    translateWait3.play();
                });
                pause3.play();

                if (duckHit == 3) {
                    gunshotMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\Gunshot.mp3");
                    gunshotMedia.play();

                    if (ammoLeft > 0) {
                        ammoLeft--;
                        labelAmmo.setLabel("Ammo Left: " + ammoLeft);
                    }
                    gameCompletedMedia = mediaClass.playMusicForOnce
                            ("assets/effects/GameCompleted.mp3");
                    gameCompletedMedia.play();

                    titleMediaCounter = 0;
                    winGame(scene, () -> createLevel1(primaryStage),
                            () -> createInitialScreen(primaryStage), gameCompletedMedia, duckFallsMedia3);
                }
            }
        });

        scene.setOnMouseClicked(event -> {
            if (ammoLeft > 0) {
                if (duckHit < 3) {
                    gunshotMedia = mediaClass.playMusicForOnce
                            ("assets\\effects\\Gunshot.mp3");
                    gunshotMedia.play();

                    if (ammoLeft > 0) {
                        ammoLeft--;
                        labelAmmo.setLabel("Ammo Left: " + ammoLeft);
                    }
                }
            }   if (duckHit < 3 && ammoLeft == 0) {
                timelineReflection.stop();
                timelineReflection2.stop();
                timelineReflection3.stop();
                gameOverMedia = mediaClass.playMusicForOnce
                        ("assets\\effects\\GameOver.mp3");
                gameOverMedia.play();

                lose(primaryStage, scene, () -> createLevel1(primaryStage),
                        gameOverMedia, duckFallsMedia, ammoLeftLevel6);
                if (duckFallsMedia2 != null & duckFallsMedia3 != null) {
                    duckFallsMedia2.stop();
                    duckFallsMedia3.stop();
                }
            }
        });

        primaryStage.setScene(scene);
        primaryStage.show();
        return newScreen;
    }

    /**
     * Updates the background image of the application.
     *
     * @param imagePath The file path of the new background image.
     */
    private void updateBackgroundImage(String imagePath) {
        image = new Image(imagePath);
        if (imageView != null) {
            imageView.setImage(image);
        }
    }

    /**
     * Updates the crosshair image of the application.
     *
     * @param imagePath The file path of the new crosshair image.
     */
    private void updateCrosshair(String imagePath) {
        Image cursorImage = new Image(imagePath);
        if (imageViewCrosshair != null) {
            imageViewCrosshair.setImage(cursorImage);
        }
    }

    /**
     * Displays the win screen and handles key events when the player wins a level.
     *
     * @param scene              The scene in which the win screen is displayed.
     * @param createLevelMethod  A Runnable object that creates the next level.
     * @param media              The MediaPlayer for the current level's background music to stop them.
     *                           It can be null if there is no music.
     * @param media2             The MediaPlayer for additional audio effects to stop them.
     *                           It can be null if there are no additional audio effects.
     */
    private void win(Scene scene, Runnable createLevelMethod, MediaPlayer media, MediaPlayer media2) {
        LabelClass labelWin = new LabelClass();
        labelWin.createLabel("YOU WIN!", "#FFC400", "Arial", 17 * scale);
        labelWin.getLabel().setTranslateY(-17 * scale);

        LabelClass labelEnter = new LabelClass();
        labelEnter.createFlashingLabel("Press ENTER to play next level",
                "#FFC400", "Arial", 17 * scale, 1, 2);

        newScreen.getChildren().addAll(labelWin.getLabel(), labelEnter.getLabel());

        scene.setOnKeyPressed(eventKey -> {
            if (eventKey.getCode() == KeyCode.ENTER) {
                createLevelMethod.run();
                if (media != null) {
                    media.stop();
                } if (media2 != null) {
                    media2.stop();
                }
            }
        });
    }

    /**
     * Displays the win screen for the entire game and handles key events when the player completes the game.
     *
     * @param scene                 The scene in which the win screen is displayed.
     * @param createLevelMethod     A Runnable object that creates the first level for a new game.
     * @param createLevelMethod2    A Runnable object that creates the main menu or exit screen.
     *                              It depends on whether the player wants to play again or exit the game.
     * @param media                 The MediaPlayer for the current level's background music to stop them.
     *                              It can be null if there is no music.
     * @param media2                The MediaPlayer for additional audio effects to stop them.
     *                              It can be null if there are no additional audio effects.
     */
    private void winGame(Scene scene, Runnable createLevelMethod,Runnable createLevelMethod2,
                         MediaPlayer media, MediaPlayer media2) {
        LabelClass labelWin = new LabelClass();
        labelWin.createLabel("You have completed the game!", "#FFC400", "Arial", 17 * scale);

        LabelClass flashingLabelEnter = new LabelClass();
        flashingLabelEnter.createFlashingLabel("Press ENTER to play again",
                "#FFC400", "Arial", 17 * scale, 1, 2);

        LabelClass flashingLabelExit = new LabelClass();
        flashingLabelExit.createFlashingLabel("Press ESC to exit",
                "#FFC400", "Arial", 17 * scale, 1, 2);
        newScreen.setMargin(labelWin.getLabel(), new Insets(-40 * scale, 0, 0, 0));
        newScreen.setMargin(flashingLabelEnter.getLabel(), new Insets(-7 * scale, 0, 0, 0));
        newScreen.setMargin(flashingLabelExit.getLabel(), new Insets(27 * scale, 0, 0, 0));

        newScreen.getChildren().addAll(labelWin.getLabel(), flashingLabelEnter.getLabel(), flashingLabelExit.getLabel());

        scene.setOnKeyPressed(eventKey -> {
            if (eventKey.getCode() == KeyCode.ENTER) {
                createLevelMethod.run();
                if (media != null) {
                    media.stop();
                } if (media2 != null) {
                    media2.stop();
                }
            } else if (eventKey.getCode() == KeyCode.ESCAPE) {
                createLevelMethod2.run();
                if (media != null) {
                    media.stop();
                } if (media2 != null) {
                    media2.stop();
                }
            }
        });
    }

    /**
     * Displays the game over screen and handles key events when the player loses a level.
     *
     * @param primaryStage       The primary stage of the application.
     * @param scene              The scene in which the game over screen is displayed.
     * @param createLevelMethod  A Runnable object that creates the level when the player chooses to play again.
     * @param media              The MediaPlayer for the current level's background music to stop them.
     *                           It can be null if there is no music.
     * @param media2             The MediaPlayer for additional audio effects to stop them.
     *                           It can be null if there are no additional audio effects.
     * @param ammoCount          The number of ammo left when the player lost the level.
     */
    private void lose(Stage primaryStage, Scene scene, Runnable createLevelMethod,
                      MediaPlayer media, MediaPlayer media2, int ammoCount) {
        LabelClass labelGameOver = new LabelClass();
        labelGameOver.createLabel("GAME OVER!", "#FFC400", "Arial", 17 * scale);

        LabelClass flashingLabelEnter = new LabelClass();
        flashingLabelEnter.createFlashingLabel("Press ENTER to play again",
                "#FFC400", "Arial", 17 * scale, 1, 2);
        LabelClass flashingLabelExit = new LabelClass();
        flashingLabelExit.createFlashingLabel("Press ESC to exit",
                "#FFC400", "Arial", 17 * scale, 1, 2);

        newScreen.setMargin(labelGameOver.getLabel(), new Insets(-40 * scale, 0, 0, 0));
        newScreen.setMargin(flashingLabelEnter.getLabel(), new Insets(-7 * scale, 0, 0, 0));
        newScreen.setMargin(flashingLabelExit.getLabel(), new Insets(27 * scale, 0, 0, 0));

        newScreen.getChildren().addAll(labelGameOver.getLabel(), flashingLabelEnter.getLabel(),
                flashingLabelExit.getLabel());

        scene.setOnKeyPressed(eventKey -> {
            if (eventKey.getCode() == KeyCode.ENTER) {
                ammoLeft = ammoCount;
                createLevelMethod.run();
                if (media != null ) {
                    media.stop();
                } if (media2 != null ) {
                    media2.stop();
                }
            } else if (eventKey.getCode() == KeyCode.ESCAPE) {
                if (media != null) {
                    media.stop();
                } if (media2 != null ) {
                    media2.stop();
                }
                ammoLeft = ammoCount;
                finalForeground = new Image("assets/foreground/1.png");
                finalImage = new Image("assets/background/1.png");
                finalCursor = new Image("assets/crosshair/1.png");
                createInitialScreen(primaryStage);
            }
        });
    }

    /**
     * Sets up the game area by creating image views, labels, and setting the cursor image.
     *
     * @param primaryStage      The primary stage of the application.
     * @param scene             The scene in which the game area is displayed.
     * @param finalImageView    The ImageClass object representing the background image view.
     * @param finalFGImageView  The ImageClass object representing the foreground image view.
     * @param labelLevel        The LabelClass object representing the level label.
     * @param labelAmmo         The LabelClass object representing the ammo count label.
     * @param levelName         The name of the current level.
     * @param labelYPos         The vertical position of the label.
     * @param ammoCount         The initial ammo count for the level.
     */
    private void gameArea(Stage primaryStage, Scene scene, ImageClass finalImageView, ImageClass finalFGImageView,
                          LabelClass labelLevel, LabelClass labelAmmo,String levelName, double labelYPos, int ammoCount) {
        finalImageView.setImageView(primaryStage, finalImage);
        finalFGImageView.setImageView(primaryStage, finalForeground);

        labelLevel.createLabel(levelName, "#FFC400", "Arial", 7 * scale);

        labelAmmo.createLabel("Ammo Left: " + ammoCount, "#FFC400", "Arial", 7 * scale);

        labelLevel.setLocationOfLabel(newScreen, labelLevel, 0, 0);
        labelAmmo.setLocationOfLabel(newScreen, labelAmmo, 0, labelYPos * scale);

        ImageCursor imageCursor = new ImageCursor(finalCursor);
        scene.setCursor(imageCursor);
    }
}

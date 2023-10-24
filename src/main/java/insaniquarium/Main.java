package insaniquarium;

import insaniquarium.game.*;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;

import javafx.scene.paint.Color;
import javafx.stage.Stage;
import insaniquarium.game.eventsystem.EventSystem;
import insaniquarium.game.eventsystem.GameEvent;
import insaniquarium.game.gamemenu.GameMenu;


public class Main extends Application {
    public static final int WIDTH = 660;
    public static final int HEIGHT = 520;
    private final double TIME_STEP = 1.0 / 60.0;
    private double accumulatedTime = 0.0;
    private long lastTime = 0;

    private double inGameTime = 0;

    private Canvas canvas;

    ContinuumBold12Outline font;

    boolean showText = true;

    private double updateInterval = 2.0;
    private double timeSinceLastUpdate = 0.0;

    private int frameCount = 0;
    private double elapsedTime = 0.0;
    double fps;

    private Game game;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setResizable(false);
        primaryStage.setWidth(WIDTH);
        primaryStage.setHeight(HEIGHT);

        primaryStage.setOnCloseRequest(event ->{
            SoundManager.getInstance().shutDown();
        });

        canvas = new Canvas(WIDTH, HEIGHT);


        GraphicsContext gc = canvas.getGraphicsContext2D();

        StackPane root = new StackPane ();

        root.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Get the X and Y coordinates of the mouse click event
                double x = event.getSceneX() + 8;
                double y = event.getSceneY() + 20;
                System.out.println("Mouse clicked at X: " + x + " Y: " + y);
                EventSystem.getInstance().fireEvent(new GameEvent("MOUSE_CLICKED", new double[]{x, y}), 0);
            }
        });


/*
        root.setOnMouseClicked(event -> {

            double canvasX = canvas.getLayoutX() + canvas.getBoundsInParent().getMinX();
            double canvasY = canvas.getLayoutY() + canvas.getBoundsInParent().getMinY();

            double x = event.getSceneX() - canvasX;
            double y = event.getSceneY() - canvasY;
            EventSystem.getInstance().fireEvent(new GameEvent("MOUSE_CLICKED", new double[]{x, y}), 0);
        });
*/

        //root.setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));


        Scene scene = new Scene(root);
        Image image = new Image("images/aquarium1.gif");

        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );

        // set the background image to the scene
        Background background = new Background(backgroundImage);
        root.setBackground(background);


        //MENU BUTTONS
        VBox gameMenuBox = new VBox();
        gameMenuBox.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        GameMenu gameMenu = new GameMenu();
        gameMenuBox.getChildren().add(gameMenu);




        root.getChildren().add(gameMenuBox);
        root.getChildren().add(canvas);
        canvas.setMouseTransparent(true);

        primaryStage.setScene(scene);
        primaryStage.show();


        font = new ContinuumBold12Outline();

        PlayerData playerData = new PlayerData();
        game = new Game(gc, playerData, gameMenu);
        game.initGame();

        new AnimationTimer() {
            @Override
            public void handle(long currentTime) {
                if (lastTime == 0) {
                    lastTime = currentTime;
                }

                double deltaTime = (currentTime - lastTime) / 1e9;

                accumulatedTime += deltaTime;

                while (accumulatedTime >= TIME_STEP) {
                    //update in-game time
                    inGameTime += TIME_STEP;

                    // code to update the game state
                    update(inGameTime, TIME_STEP);

                    accumulatedTime -= TIME_STEP;
                }


                // code to render the game
                render(gc);


                lastTime = currentTime;

                //display fps
                frameCount++;
                elapsedTime += deltaTime;
                if (elapsedTime >= 1.0) {
                    fps = frameCount / elapsedTime;
                    frameCount = 0;
                    elapsedTime = 0.0;
                }
            }
        }.start();





    }
    private void update(double inGameTime, double deltaTime){

        timeSinceLastUpdate += TIME_STEP;
        if(timeSinceLastUpdate >= updateInterval){
            showText = !showText;
            timeSinceLastUpdate -= updateInterval;
        }
        game.update(inGameTime, deltaTime);
    }
    private void render(GraphicsContext gc){
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        if(showText){
            font.drawString(gc, "Welcome to Insaniquarium Deluxe !!!!", 200, 200, 1);
        }
        font.drawString(gc, "FPS: " + String.format("%.2f", fps), 15, 470, 1);

        game.draw();

    }


}
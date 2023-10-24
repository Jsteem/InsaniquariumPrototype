package insaniquarium.game.gamemenu;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import insaniquarium.game.GameImage;
import insaniquarium.game.eventsystem.EventSystem;
import insaniquarium.game.eventsystem.GameEvent;
import insaniquarium.game.eventsystem.GameListener;



public class GameMenu extends HBox implements GameListener {
    private GameButton slot1Button;
    private GameButton foodUpgradeButton;
    private GameButton maxFoodUpgradeButton;
    private GameButton slot4Button;
    private GameButton slot5Button;
    private GameButton laserUpgradeButton;
    private GameButton eggUpgradeButton;
    private BackgroundImage backgroundMenuPressed;

    private BackgroundImage backgroundMenuHovered;

    private static GameButton[] buttons;

    public GameMenu(){
        super();
        EventSystem.getInstance().addListener(this);
        this.setPrefHeight(65);
        GameImage image = new GameImage("menubar.gif");
        BackgroundImage backgroundImage = new BackgroundImage(
                image.getImage(),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, true)
        );
        Background background = new Background(backgroundImage);
        this.setBackground(background);
        //this.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, new BorderWidths(3))));

        //////////////////////////// BUTTON UNPRESSED ////////////////////////////
        GameImage imageUnpressed = new GameImage("mbuttonu.gif");

        BackgroundImage backgroundButtonImageUnpressed = new BackgroundImage(
                imageUnpressed.getImage(),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        GameButton.setUnpressed(backgroundButtonImageUnpressed);

        //////////////////////////// BUTTON HOVERED ////////////////////////////
        GameImage imageHovered = new GameImage("mbuttono.gif");

        BackgroundImage backgroundButtonImageHovered = new BackgroundImage(
                imageHovered.getImage(),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        GameButton.setHovered(backgroundButtonImageHovered);

        //////////////////////////// BUTTON PRESSED ////////////////////////////
        GameImage imagePressed = new GameImage("mbuttond.gif");

        BackgroundImage backgroundButtonImagePressed = new BackgroundImage(
                imagePressed.getImage(),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );
        GameButton.setPressed(backgroundButtonImagePressed);

        //////////////////////////// MENU BUTTON HOVERED ////////////////////////////
        GameImage imageMenuHovered = new GameImage("OPTIONSBUTTON.gif");

        backgroundMenuHovered = new BackgroundImage(
                imageMenuHovered.getImage(),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );

        //////////////////////////// MENU BUTTON PRESSED ////////////////////////////
        GameImage imageMenuPressed = new GameImage("OPTIONSBUTTONd.gif");

        backgroundMenuPressed = new BackgroundImage(
                imageMenuPressed.getImage(),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );


        createMenu();

    }
    public void createMenu(){
        slot1Button = new GameButton();
        HBox.setMargin(slot1Button, new Insets(0,0,0,19));

        foodUpgradeButton = new GameButton();
        HBox.setMargin(foodUpgradeButton, new Insets(0,0,0,15));

        maxFoodUpgradeButton = new GameButton();
        HBox.setMargin(maxFoodUpgradeButton, new Insets(0,0,0,2));

        slot4Button = new GameButton();
        HBox.setMargin(slot4Button, new Insets(0,0,0,18));

        slot5Button = new GameButton();
        HBox.setMargin(slot5Button, new Insets(0,0,0,18));

        laserUpgradeButton = new GameButton();
        HBox.setMargin(laserUpgradeButton, new Insets(0,0,0,18));

        eggUpgradeButton = new GameButton();
        HBox.setMargin(eggUpgradeButton, new Insets(0,0,0,18));

        buttons = new GameButton[]{slot1Button,foodUpgradeButton,maxFoodUpgradeButton,slot4Button,slot5Button,laserUpgradeButton,eggUpgradeButton};


        VBox controls = new VBox();
        HBox.setMargin(controls, new Insets(3,0,0,34));
        controls.setPrefSize(101,60);
        //controls.setBorder(new Border(new BorderStroke(Color.ROYALBLUE, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));
        Button menuButton = new Button();
        menuButton.setOpacity(0);
        menuButton.setPrefSize(101,28);
        menuButton.setOnMouseEntered(e -> {menuButton.setBackground(new Background(backgroundMenuHovered)); menuButton.setOpacity(1);});
        menuButton.setOnMouseExited(e -> { menuButton.setOpacity(0);});
        menuButton.setOnMousePressed(e -> {menuButton.setBackground(new Background(backgroundMenuPressed));});
        menuButton.setOnMouseReleased(e -> {menuButton.setBackground(new Background(backgroundMenuHovered));});
        //menuButton.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

        this.getChildren().clear();
        controls.getChildren().addAll(menuButton);

        this.getChildren().addAll(buttons);
        this.getChildren().add(controls);
    }

    public void processEvent(GameEvent event) {
        String message = event.getMessage();
        switch (message){
            case "UNLOCK_SLOT":
                int[] payload = (int[]) event.getPayload();
                int slotNumber = payload[0];
                this.buttons[slotNumber].setDisable(false);
                break;
            case "LEVEL_COMPLETE":
                GameButton.clear();
                createMenu();
                break;
        }
    }
    public void registerGameMenu(){
        EventSystem.getInstance().addListener(this);
    }

}

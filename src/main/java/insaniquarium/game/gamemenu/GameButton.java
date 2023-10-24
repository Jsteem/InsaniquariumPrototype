package insaniquarium.game.gamemenu;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import insaniquarium.game.eventsystem.EventSystem;
import insaniquarium.game.eventsystem.GameEvent;
import insaniquarium.game.eventsystem.GameListener;

import java.awt.image.BufferedImage;

public class GameButton extends VBox {
    private int slotNumber;
    private Button button;
    private static int totalSlotNumbers = 0;

    private static BackgroundImage unpressed;
    private static BackgroundImage hovered;
    private static BackgroundImage pressed;

    public GameButton(){
        super();
        this.slotNumber = ++totalSlotNumbers;

        this.setPrefSize(55,60);
        //setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));


        this.button = new Button();
        this.setDisable(true);
        this.button.setOpacity(0);
        this.button.setPrefSize(60,45);
        this.setBackground(new Background(unpressed));
        this.button.setOnMouseEntered(e -> {this.setBackground(new Background(hovered));});
        this.button.setOnMouseExited(e -> {this.setBackground(new Background(unpressed));});
        this.button.setOnMousePressed(e -> {this.setBackground(new Background(pressed)); onClick();});
        this.button.setOnMouseReleased(e -> {
            if(this.button.contains(e.getX(), e.getY())){
                this.setBackground(new Background(hovered));
            }
            else{
                this.setBackground(new Background(unpressed));
            }
        });

        //button.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, null, new BorderWidths(1))));

        this.getChildren().addAll(button);


    }

    public void onClick(){
        //send message to the message pump
        EventSystem.getInstance().fireEvent(new GameEvent("SLOT_BUTTON_PRESSED", this.slotNumber - 1), 0);

    }
    public int getSlotNumber(){
        return this.slotNumber;
    }

    public static void setUnpressed(BackgroundImage unpressed){
        GameButton.unpressed = unpressed;
    }
    public static void setHovered(BackgroundImage hovered){
        GameButton.hovered = hovered;
    }
    public static void setPressed(BackgroundImage pressed){
        GameButton.pressed = pressed;
    }

    public static void clear(){
        GameButton.totalSlotNumbers = 0;
    }

}

package insaniquarium.game.gamemenu;

import javafx.scene.canvas.GraphicsContext;
import insaniquarium.game.ContinuumBold12Outline;
import insaniquarium.game.GameImage;
import insaniquarium.game.eventsystem.EventSystem;
import insaniquarium.game.eventsystem.GameEvent;
import insaniquarium.game.eventsystem.GameListener;
import insaniquarium.game.gamesystem.ObjectType;

public class Overlay implements GameListener {
    private GraphicsContext gc;
    private GameImage imageSlot1;
    private GameImage imageFood;

    private GameImage imageSlot4;

    private GameImage imageSlot5;

    private GameImage imageLaser;
    private GameImage imageEggPiece;
    private ContinuumBold12Outline font;

    //OVERLAY DRAWING VARS
    private double[] midPoints = {57, 126, 185, 255, 330, 405, 475};
    private static int PRICE_TAG_Y = 66;
    private static int TOTAL_MONEY_TAG_Y = 60;
    private static int TOTAL_MONEY_TAG_X = 580;
    private static int[] eggOffsets = {-3, 0, 3};

    private static int TANK_TAG_X = 610;
    private static int TANK_TAG_Y = 475;

    private static int[] drawInfoSlot1; //slot1 options

    private static int[] drawInfoSlot4; //slot4 options

    private static int[] drawInfoSlot5; //slot5 options

    //OVERLAY DATA VARS
    private int tierFood = 0;
    private int maxFood = 1;
    private int laserUpgrade = 0;
    private int eggPiece = 0;
    private int totalAmountOfMoney = 0;
    private int[] prices = {0, 0, 0, 0, 0, 0, 0};

    private boolean[] openSlots = {false, false, false, false, false, false, false};

    private int tankNumber = 0;
    private int level = 0;

    public Overlay(GraphicsContext gc, int tankNumber, int level) {
        this.gc = gc;
        this.tankNumber = tankNumber;
        this.level = level;
        imageFood = new GameImage("food.gif");
        imageLaser = new GameImage("LazerGunz.gif");
        imageEggPiece = new GameImage("EggPieces.gif");
        font = new ContinuumBold12Outline();
        EventSystem.getInstance().addListener(this);


    }

    public void draw() {
        //slot1
        if (openSlots[0]) {
            this.gc.drawImage(imageSlot1.getImage(), drawInfoSlot1[0], drawInfoSlot1[1], drawInfoSlot1[2],
                    drawInfoSlot1[3], drawInfoSlot1[4], drawInfoSlot1[5], drawInfoSlot1[6], drawInfoSlot1[7]);
        }

        //slot2
        if (openSlots[1]) {
            if(this.tierFood < 2){
                this.gc.drawImage(imageFood.getImage(), 0, (tierFood + 1) * 40, 40, 40, 105, 25, 40, 40);
            }
            else{
                font.drawStringCenteredX(gc, "MAX", 122, 33, 1f);
            }
        }

        //slot3
        if (openSlots[2]) {
            if(this.maxFood < 10){
                font.drawStringCenteredX(gc, Integer.toString(maxFood + 1), 180, 33, 1.2f);
            }
            else{
                font.drawStringCenteredX(gc, "MAX", 179, 33, 1f);
            }

        }

        //slot4
        if (openSlots[3]) {
            this.gc.drawImage(imageSlot4.getImage(), drawInfoSlot4[0], drawInfoSlot4[1], drawInfoSlot4[2],
                    drawInfoSlot4[3], drawInfoSlot4[4], drawInfoSlot4[5], drawInfoSlot4[6], drawInfoSlot4[7]);
        }

        //slot5
        if (openSlots[4]) {
            this.gc.drawImage(imageSlot5.getImage(), drawInfoSlot5[0], drawInfoSlot5[1], drawInfoSlot5[2],
                    drawInfoSlot5[3], drawInfoSlot5[4], drawInfoSlot5[5], drawInfoSlot5[6], drawInfoSlot5[7]);
        }

        //slot6
        if (openSlots[5]) {
            this.gc.drawImage(imageLaser.getImage(), laserUpgrade * 46, 0, 46, 39, 382, 26, 40, 40);
        }

        //slot7
        if (openSlots[6]) {
            this.gc.drawImage(imageEggPiece.getImage(), eggPiece * 49, 0, 46, 39, 455 + eggOffsets[eggPiece], 26, 46, 38);
        }

        //draw the prices
        for (int i = 0; i < 7; i++) {
            if(openSlots[i]){
                font.drawStringCenteredX(gc, Integer.toString(prices[i]), midPoints[i], PRICE_TAG_Y, 0.8f);
            }

        }

        //draw the total amount of money
        font.drawStringCenteredX(gc, Integer.toString(totalAmountOfMoney), TOTAL_MONEY_TAG_X, TOTAL_MONEY_TAG_Y, 1f);

        //draw tank info
        font.drawStringCenteredX(gc, "Tank " + Integer.toString(this.tankNumber) + " - " + Integer.toString(this.level), TANK_TAG_X, TANK_TAG_Y, 1f);
    }

    public void createDrawingInformationForObjectType(ObjectType objectType) {
        if(objectType != null){
            if ((objectType.getValue() & ObjectType.GUPPY_SMALL.getValue()) > 0) {
                imageSlot1 = new GameImage("smallswim.gif");
                drawInfoSlot1 = new int[]{0, 0, 80, 80, 16, 5, 80, 80};
            } else if ((objectType.getValue() & ObjectType.CARNIVORE.getValue()) > 0) {
                imageSlot4 = new GameImage("smallswim.gif");
                drawInfoSlot4 = new int[]{0, 320, 80, 80, 236, 26, 40, 40};
            }
        }
    }

    @Override
    public void processEvent(GameEvent event) {
        String message = event.getMessage();
        switch (message){
            case "UNLOCK_SLOT":
                int[] payload = (int[]) event.getPayload();
                int slotNumber = payload[0];
                int price = payload[1];
                openSlots[slotNumber] = true;
                prices[slotNumber] = price;

                break;
            case "CONFIGURE_SLOTS_OVERLAY":
                ObjectType[] types = (ObjectType[]) event.getPayload();
                createDrawingInformationForObjectType(types[0]);
                createDrawingInformationForObjectType(types[1]);
                createDrawingInformationForObjectType(types[2]);
                break;

            case "INCREASE_MAX_FOOD":
                this.maxFood = (int) event.getPayload();
                break;

            case "UPGRADE_FOOD":
                this.tierFood = (int) event.getPayload();
                break;

            case "UPDATE_TOTAL_MONEY":
                this.totalAmountOfMoney = (int) event.getPayload();
                break;

            case "UPGRADE_WEAPON":
                this.laserUpgrade = (int) event.getPayload();
                break;

            case "UPGRADE_EGG":
                this.eggPiece = (int) event.getPayload();
                break;


        }
    }
}

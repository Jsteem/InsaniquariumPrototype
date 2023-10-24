package insaniquarium.game.gamesystem;

import insaniquarium.game.LevelData;
import insaniquarium.game.eventsystem.EventSystem;
import insaniquarium.game.eventsystem.GameEvent;
import insaniquarium.game.eventsystem.GameListener;

public class GameData implements GameListener {
    private int tierFood = 0;

    // private int maxFood = 1;
    private int maxFood = 100;

    private int numFood = 0;

    private int foodPrice = 5;

    private int laserUpgrade = 0;
    private int eggPiece = 0;
    private int totalAmountOfMoney = 20000;
    private int currentButtonUnlocked = 0;
    private LevelData levelData;

    private GameEntity gameEntities;



    public GameData(LevelData levelData){

        EventSystem.getInstance().addListener(this);
        this.levelData = levelData;


        //configure the images of the overlay menu
        GameEvent e1 = new GameEvent("CONFIGURE_SLOTS_OVERLAY", levelData.getVariableSlots());
        EventSystem.getInstance().fireEvent(e1, 0);

        //unlock the first slot
        GameEvent e3 = new GameEvent("UNLOCK_SLOT", new int[]{0, levelData.getPrices()[0]});
        EventSystem.getInstance().fireEvent(e3, 0);

        //update money
        GameEvent e4 = new GameEvent("UPDATE_TOTAL_MONEY", this.totalAmountOfMoney);
        EventSystem.getInstance().fireEvent(e4, 0);

        gameEntities = new GameEntity(this);
    }
    public String[] getAlienData(){
        return this.levelData.getAlienData();
    }

    public void increaseMoney(int money){
        this.totalAmountOfMoney += money;
        EventSystem.getInstance().fireEvent(new GameEvent("UPDATE_TOTAL_MONEY", this.totalAmountOfMoney), 0);
    }

    @Override
    public void processEvent(GameEvent event) {
        String message = event.getMessage();
        switch (message){
            case "SLOT_BUTTON_PRESSED" -> {
                int p = (int) event.getPayload();
                handleButtonPress(p);
            }

            case "BUY_FOOD" -> {
                //check if food can be dropped (does not exceed the max value)
                if (this.numFood < this.maxFood) {
                    if (this.totalAmountOfMoney >= foodPrice) {
                        double[] p = (double[]) event.getPayload();
                        EventSystem.getInstance().fireEvent(new GameEvent("DROP_FOOD", new double[]{p[0], p[1], this.tierFood}), 0);
                        this.numFood++;
                        buy(foodPrice);
                    }

                }
            }
            case "DECREASE_NUMBER_OF_FOOD" -> {
                this.numFood--;
            }
        }
    }

    private void handleButtonPress(int slotNumber) {
        int buyPrice = this.levelData.getPrices()[slotNumber];
        if(this.totalAmountOfMoney >= buyPrice){

            switch (slotNumber){

                case 0:
                    //spawn fish with ObjectType of slot1
                    GameEvent e1 = new GameEvent("SPAWN_ENTITY", this.levelData.getVariableSlots()[0]);
                    EventSystem.getInstance().fireEvent(e1, 0);
                    buy(buyPrice);
                    break;
                case 1:
                    //upgrade food quality, possible to do 2 upgrades
                    if(this.tierFood < 2){
                        this.tierFood++;
                        GameEvent e2 = new GameEvent("UPGRADE_FOOD", this.tierFood);
                        EventSystem.getInstance().fireEvent(e2, 0);
                        buy(buyPrice);
                    }
                    break;
                case 2:
                    //upgrade max food, this should not exceed the maximum of 10
                    if(this.maxFood < 10){
                        this.maxFood++;
                        GameEvent e3 = new GameEvent("INCREASE_MAX_FOOD", this.maxFood);
                        EventSystem.getInstance().fireEvent(e3, 0);
                        buy(buyPrice);
                    }
                    break;
                case 3:
                    //spawn fish with ObjectType of slot4
                    GameEvent e4 = new GameEvent("SPAWN_ENTITY", this.levelData.getVariableSlots()[1]);
                    EventSystem.getInstance().fireEvent(e4, 0);
                    buy(buyPrice);
                    if(currentButtonUnlocked < 4){
                        unlockNextButton();
                    }

                    break;
                case 4:
                    //spawn fish with ObjectType of slot5
                    GameEvent e5 = new GameEvent("SPAWN_ENTITY", this.levelData.getVariableSlots()[2]);
                    EventSystem.getInstance().fireEvent(e5, 0);
                    buy(buyPrice);
                    if(currentButtonUnlocked < 5){
                        unlockNextButton();
                    }
                    break;

                case 5:
                    //upgrade the weapon
                    this.laserUpgrade++;
                    GameEvent e6 = new GameEvent("UPGRADE_WEAPON", this.laserUpgrade);
                    EventSystem.getInstance().fireEvent(e6, 0);
                    buy(buyPrice);
                    break;

                case 6:
                    //upgrade the egg
                    if(this.eggPiece < 2){
                        this.eggPiece++;
                        GameEvent e7 = new GameEvent("UPGRADE_EGG", this.eggPiece);
                        EventSystem.getInstance().fireEvent(e7, 0);
                        buy(buyPrice);
                    }
                    else{
                        //egg fully upgraded, go to the next level
                        System.out.println("Level Completed!");
                        GameEvent e8 = new GameEvent("LEVEL_COMPLETE", null);
                        EventSystem.getInstance().fireEvent(e8, 0);

                    }
                    break;
            }

        }
        else{
            //not enough money: flash warning and play sound
        }
    }
    private void buy(int price){
        this.totalAmountOfMoney -= price;
        GameEvent e = new GameEvent("UPDATE_TOTAL_MONEY", this.totalAmountOfMoney);
        EventSystem.getInstance().fireEvent(e, 0);
    }
    public void unlockNextButton(){
        int[] prices = this.levelData.getPrices();
        if(currentButtonUnlocked < 6){
            //last button not unlocked, check the next slot that should be unlocked.
            //note that the buttons that should be unlocked are the buttons with prices != 0
            int nextButton = currentButtonUnlocked + 1;
            while(nextButton < 7 && prices[nextButton] == 0){
                nextButton++;
            }

            currentButtonUnlocked = nextButton;
            GameEvent e = new GameEvent("UNLOCK_SLOT", new int[]{currentButtonUnlocked, levelData.getPrices()[currentButtonUnlocked]});
            EventSystem.getInstance().fireEvent(e, 0);

            //slot2 unlocks slot3 and 4, same for slot6 and 7
            if(currentButtonUnlocked == 1 || currentButtonUnlocked == 2
                    || currentButtonUnlocked == 5){
                unlockNextButton();
            }
        }
    }

    public GameEntity getGameEntities() {
        return this.gameEntities;
    }
}

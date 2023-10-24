package insaniquarium.game;

public class PlayerData {
    private String name;
    private int unlockedTankNumber = 1;
    private int unlockedLevelNumber = 3;

    public PlayerData(){

    }
    public void increaseLevel(){
        unlockedLevelNumber++;
        if(unlockedLevelNumber>5){
            unlockedLevelNumber = 1;
            unlockedTankNumber++;
        }
    }
    public int getUnlockedTankNumber(){
        return this.unlockedTankNumber;
    }
    public int getUnlockedLevelNumber(){
        return this.unlockedLevelNumber;
    }

}

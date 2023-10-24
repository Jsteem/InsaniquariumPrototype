package insaniquarium.game;

import javafx.scene.canvas.GraphicsContext;
import insaniquarium.game.eventsystem.EventSystem;
import insaniquarium.game.eventsystem.GameEvent;
import insaniquarium.game.eventsystem.GameListener;
import insaniquarium.game.gamemenu.GameMenu;
import insaniquarium.game.gamemenu.Overlay;
import insaniquarium.game.gamesystem.GameData;
import insaniquarium.game.gamesystem.GameEntity;

public class Game implements GameListener {
    final private PlayerData playerData;
    private GameData gameData;
    private Overlay overlay;
    final private GraphicsContext gc;
    final private GameMenu gameMenu;
    public Game(GraphicsContext gc, PlayerData playerData, GameMenu menu){
        this.playerData = playerData;
        this.gc = gc;
        this.gameMenu = menu;
    }

    public void initGame(){
        EventSystem.getInstance().addListener(this);
        EventSystem.getInstance().addListener(this.gameMenu);

        //Level Data can be recreated every time the game is initialised
        LevelData levelData = new LevelData(playerData.getUnlockedTankNumber(), playerData.getUnlockedLevelNumber());

        //create overlay menu
        overlay = new Overlay(gc, levelData.getTankNumber(), levelData.getLevelNumber());

        //game data should be saved and read in again for this player
        gameData = new GameData(levelData);

    }
    public void completeGame(){
        //reset the game
        EventSystem.getInstance().reset();
        playerData.increaseLevel();
        initGame();
    }
    @Override
    public void processEvent(GameEvent event) {
        String message = event.getMessage();
        switch (message){
            case "LEVEL_COMPLETE":
                completeGame();
                break;
            case "GAME_OVER":
                System.out.println("GAME OVER");
                break;
        }
    }
    public void draw(){

        this.overlay.draw();

        GameEntity gameEntities = gameData.getGameEntities();

        if(gameEntities != null){
            //note: this should never be null!
            gameEntities.draw(this.gc);
        }






    }

    public void update(double inGameTime, double deltaTime) {

        EventSystem.getInstance().update(inGameTime);

        GameEntity gameEntities = gameData.getGameEntities();

        if(gameEntities != null){
            //note: this should never be null!
            gameEntities.update(deltaTime);
        }
    }
}

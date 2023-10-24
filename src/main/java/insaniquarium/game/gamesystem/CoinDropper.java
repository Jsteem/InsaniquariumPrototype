package insaniquarium.game.gamesystem;

import insaniquarium.game.Game;

public class CoinDropper {

    double timeSinceLastCoin = 0;
    double COIN_DROP_SPEED = 10;

    GameEntity entity;

    int parentId;

    public CoinDropper(GameEntity entity, int parentId) {
        this.entity = entity;
        this.parentId = parentId;
    }

    public void update(double delta) {
        timeSinceLastCoin += delta;

        if (timeSinceLastCoin >= COIN_DROP_SPEED) {
            this.entity.factory.dropCoin(entity.x[parentId], entity.y[parentId], getCoinLevel(entity.level[parentId]));
            timeSinceLastCoin -= COIN_DROP_SPEED;
        }
    }

    public int getCoinLevel(int parentLevel){
        if(parentLevel == 3){
            return 3;
        }
        else{
            return parentLevel - 1;
        }
    }
}

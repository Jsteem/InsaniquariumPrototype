package insaniquarium.game.gamesystem;

import insaniquarium.Main;

public class Factory {

    GameEntity entity;
    public Factory(GameEntity entity){
        this.entity = entity;
    }

    public void addSmallGuppyCarnivoreRandom(boolean carnivore) {

        //find first alive entity, else increase numEnties
        int[] ids = entity.lookForAvailableId();
        int id = ids[0];
        int uniqueId = ids[1];

        int level = 0;
        int objectType = ObjectType.GUPPY_SMALL.getValue();
        int groupObjectType = entity.OBJECT_TYPE_FISH;
        int targetObjectType = entity.OBJECT_TYPE_FOOD;

        if (carnivore) {
            level = 4;
            objectType = ObjectType.CARNIVORE.getValue();
            targetObjectType = ObjectType.GUPPY_SMALL.getValue();
            entity.coinDropper[id] = new CoinDropper(entity, id);
        }


        int boundingCircleRadius = entity.boundingCircleRadiusGuppyCarnivore[level];
        int minWidth = entity.SIDE_OFFSET + boundingCircleRadius;
        int maxWidth = Main.WIDTH - entity.SIDE_OFFSET - boundingCircleRadius;
        int maxHeight = Main.HEIGHT - boundingCircleRadius - entity.GROUND_OFFSET_HEIGHT;
        int minHeight = entity.MENU_OFFSET_HEIGHT + boundingCircleRadius;

        int x = (int) (Math.random() * (maxWidth - minWidth)) + minWidth;
        int y = (int) (Math.random() * (maxHeight - minHeight)) + minHeight;

        entity.targetX[id] = (int) (Math.random() * (maxWidth - minWidth)) + minWidth;
        entity.targetY[id] = (int) (Math.random() * (maxHeight - minHeight)) + minHeight;


        entity.setEntity(id, uniqueId, level, objectType, groupObjectType, targetObjectType,
                x, y, 0, 0, 0, 0, true,
                entity.boundingCircleRadiusGuppyCarnivore[level], entity.eatCircleRadiusGuppyCarnivore[level], entity.eatCircleOffsetGuppyCarnivore[level],
                80,
                new EntityFSM(GameEntity.ENTITY_STATE.IDLE, id, uniqueId, groupObjectType),
                new AnimationFSM(id, uniqueId, groupObjectType, entity.ANIMATION_SPEED_GUPPY_CARNIVORE_SLOW), 0);


    }

    public void dropFood(float x, float y, int level) {

        int[] ids = entity.lookForAvailableId();
        int id = ids[0];
        int uniqueId = ids[1];

        int radius = 10;
        y -= radius;
        x -= radius / 2;
        int minWidth = entity.SIDE_OFFSET + radius;
        int maxWidth = Main.WIDTH - entity.SIDE_OFFSET - radius;
        int maxHeight = Main.HEIGHT - radius - entity.GROUND_OFFSET_HEIGHT;
        int minHeight = entity.MENU_OFFSET_HEIGHT + radius;

        if (x < minWidth) {
            x = minWidth;
        }
        if (x > maxWidth) {
            x = maxWidth;
        }
        if (y < minHeight) {
            y = minHeight;
        }
        if (y > maxHeight) {
            y = maxHeight;
        }

        int objectType = -1;
        int targetObjectType = -1;
        int groupObjectType = entity.OBJECT_TYPE_FOOD;
        switch (level) {
            case 0:
                objectType = ObjectType.PELLET_FOOD.getValue();
                break;
            case 1:
                objectType = ObjectType.CANNED_FOOD.getValue();
                break;
            case 2:
                objectType = ObjectType.PILL_FOOD.getValue();
                break;
            case 3:
                objectType = ObjectType.POTION_FOOD.getValue();
                break;
        }

        entity.setEntity(id, uniqueId, level, objectType, groupObjectType, targetObjectType,
                x, y, 0, 0, 0, 0, true,
                radius, 0, 0,
                40,
                new EntityFSM(GameEntity.ENTITY_STATE.FALL_DOWN, id, uniqueId, groupObjectType),
                new AnimationFSM(id, uniqueId, groupObjectType, entity.ANIMATION_SPEED_FOOD), 0);


    }

    public void dropCoin(float x, float y, int level) {

        int[] ids = entity.lookForAvailableId();
        int id = ids[0];
        int uniqueId = ids[1];

        int radius = 20;
        float vx = 0;
        float vy = entity.COIN_FALL_SPEED;
        float ax = 0;
        float ay = 0;

        int objectType = -1;
        int targetObjectType = -1;
        int groupObjectType = entity.OBJECT_TYPE_MONEY;
        switch (level) {
            case 0:
                objectType = ObjectType.COIN_SILVER.getValue();
                break;
            case 1:
                objectType = ObjectType.COIN_GOLD.getValue();
                break;
            case 2:
                objectType = ObjectType.STAR.getValue();
                break;
            case 3:
                objectType = ObjectType.DIAMOND.getValue();
                break;
            case 4:
                objectType = ObjectType.CHEST.getValue();
                break;
            case 5:
                objectType = ObjectType.BEETLE.getValue();
                break;

        }

        entity.setEntity(id, uniqueId, level, objectType, groupObjectType, targetObjectType,
                x, y, vx, vy, ax, ay, true,
                radius, 0, 0,
                72,
                new EntityFSM(GameEntity.ENTITY_STATE.FALL_DOWN, id, uniqueId, groupObjectType),
                new AnimationFSM(id, uniqueId, groupObjectType, entity.ANIMATION_SPEED_FOOD), 0);
    }

    public void addAlien(String alien) {
        switch (alien){
            case "SYLVESTER" -> {
                int[] ids = entity.lookForAvailableId();
                int id = ids[0];
                int uniqueId = ids[1];

                int level = 0;
                int objectType = ObjectType.ALIEN.getValue();
                int groupObjectType = entity.OBJECT_TYPE_ALIEN;
                int targetObjectType = entity.OBJECT_TYPE_FISH;


                int boundingCircleRadius = 80;
                int minWidth = entity.SIDE_OFFSET + boundingCircleRadius;
                int maxWidth = Main.WIDTH - entity.SIDE_OFFSET - boundingCircleRadius;
                int maxHeight = Main.HEIGHT - boundingCircleRadius - entity.GROUND_OFFSET_HEIGHT;
                int minHeight = entity.MENU_OFFSET_HEIGHT + boundingCircleRadius;

                int x = (int) (Math.random() * (maxWidth - minWidth)) + minWidth;
                int y = (int) (Math.random() * (maxHeight - minHeight)) + minHeight;

                entity.targetX[id] = -1;
                entity.targetY[id] = -1;


                entity.setEntity(id, uniqueId, level, objectType, groupObjectType, targetObjectType,
                        x, y, 0, 0, 0, 0, true,
                        boundingCircleRadius, 15, 15,
                        160,
                        new EntityFSM(GameEntity.ENTITY_STATE.SEEK_HUNGRY, id, uniqueId, groupObjectType),
                        new AnimationFSM(id, uniqueId, groupObjectType, entity.ANIMATION_SPEED_GUPPY_CARNIVORE_SLOW), 0);

            }
        }
    }
}

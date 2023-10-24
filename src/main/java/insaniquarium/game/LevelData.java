package insaniquarium.game;

import insaniquarium.game.gamesystem.ObjectType;

public class LevelData {
    private int tankNumber;
    private int levelNumber;
    private ObjectType[] variableSlots;
    //variable
    private int[] prices;

    private String[] alienData;

    //variableSlotsData - what type should be in slot1, slot4 and slot5
    private static ObjectType[][] variableSlotsData = {
            //tank1
            {ObjectType.GUPPY_SMALL, null, null},
            {ObjectType.GUPPY_SMALL, null, null},
            {ObjectType.GUPPY_SMALL, ObjectType.CARNIVORE, null},
            {ObjectType.GUPPY_SMALL, ObjectType.CARNIVORE, null},
            {ObjectType.GUPPY_SMALL, ObjectType.CARNIVORE, null},
            //tank2
            {ObjectType.GUPPY_SMALL, ObjectType.POTION_FOOD, null},
            {ObjectType.GUPPY_SMALL, ObjectType.POTION_FOOD, ObjectType.STARCATCHER},
            {ObjectType.GUPPY_SMALL, ObjectType.POTION_FOOD, ObjectType.STARCATCHER},
            {ObjectType.GUPPY_SMALL, ObjectType.POTION_FOOD, ObjectType.STARCATCHER},
            {ObjectType.GUPPY_SMALL, ObjectType.POTION_FOOD, ObjectType.STARCATCHER},
            //tank3
            {ObjectType.GUPPY_SMALL, ObjectType.GUPPYCRUNSHER, null},
            {ObjectType.GUPPY_SMALL, ObjectType.GUPPYCRUNSHER, ObjectType.BEETLEMUNCHER},
            {ObjectType.GUPPY_SMALL, ObjectType.GUPPYCRUNSHER, ObjectType.BEETLEMUNCHER},
            {ObjectType.GUPPY_SMALL, ObjectType.GUPPYCRUNSHER, ObjectType.BEETLEMUNCHER},
            {ObjectType.GUPPY_SMALL, ObjectType.GUPPYCRUNSHER, ObjectType.BEETLEMUNCHER},
            //tank4
            {ObjectType.BREEDER, ObjectType.CARNIVORE, null},
            {ObjectType.BREEDER, ObjectType.CARNIVORE, ObjectType.ULTRAVORE},
            {ObjectType.BREEDER, ObjectType.CARNIVORE, ObjectType.ULTRAVORE},
            {ObjectType.BREEDER, ObjectType.CARNIVORE, ObjectType.ULTRAVORE},
            {ObjectType.BREEDER, ObjectType.CARNIVORE, ObjectType.ULTRAVORE},

    };
    //Note that we know what slots can be opened this level by looking at the non-zero prices
    private static int[][] pricesData = {
            //tank1
            {100, 0, 0, 0, 0, 0, 150}, //no aliens
            {100, 200, 300, 0, 0, 0, 500}, //sylvester
            {100, 200, 300, 1000, 0, 1000, 2000}, //sylvester
            {100, 200, 300, 1000, 0, 1000, 3000}, //balrog
            {100, 200, 300, 1000, 0, 1000, 5000}, //balrog + sylvester
            //tank2
            {100, 200, 300, 250, 0, 0, 750}, //sylvester
            {100, 200, 300, 250, 750, 1000, 3000}, //balrog
            {100, 200, 300, 250, 750, 1000, 5000}, //gus (type g)
            {100, 200, 300, 250, 750, 1000, 7500}, //destructor (type d)
            {100, 200, 300, 250, 750, 1000, 10000}, //destructor
            //tank3
            {100, 200, 300, 750, 0, 0, 1000}, //balrog
            {100, 200, 300, 750, 2000, 2000, 5000}, //destructor
            {100, 200, 300, 750, 2000, 2000, 7500}, //psychosquid
            {100, 200, 300, 750, 2000, 2000, 10000}, //cyclops
            {100, 200, 300, 750, 2000, 2000, 15000}, //cyclops + psychosquid
            //tank4
            {200, 200, 300, 1000, 0, 0, 3000}, //balrog
            {200, 200, 300, 1000, 10000, 5000, 25000}, //bilaterus (type II)
            {200, 200, 300, 1000, 10000, 5000, 50000}, //gus, psychoquid + balrog
            {200, 200, 300, 1000, 10000, 5000, 75000}, //bilaterus, cyclops + destructor
            {200, 200, 300, 1000, 10000, 5000, 99999}, //bilaterus, cyclops + destructor, psychoquid + balrog
            //boss
    };
    private static String[][] aliensData = {
            //tank1
            {"NONE"},
            {"SYLVESTER"},
            {"SYLVESTER"},
            {"BALROG"},
            {"BALROG","SYLVESTER"},
            //tank2
            {"SYLVESTER"},
            {"BALROG"},
            {"GUS"},
            {"DESTRUCTOR"},
            {"DESTRUCTOR"},
            //tank3
            {"BALROG"},
            {"DESTRUCTOR"},
            {"PSYCHOSQUID"},
            {"CYCLOPS"},
            {"CYCLOPS", "PSYCHOSQUID"},
            //tank4
            {"BALROG"},
            {"BILATERUS"},
            {"GUS", "CYCLOPS/DESTRUCTOR"},
            {"BILATERUS", "CYCLOPS/DESTRUCTOR"},
            {"BILATERUS", "CYCLOPS/DESTRUCTOR", "PSYCHOSQUID/BALROG"}
    };

    public LevelData(int tankNumber, int levelNumber){
        this.tankNumber = tankNumber;
        this.levelNumber = levelNumber;
        this.variableSlots = variableSlotsData[(tankNumber - 1) * 5 + levelNumber - 1];
        this.prices = pricesData[(tankNumber - 1) * 5 + levelNumber - 1];
        this.alienData = aliensData[(tankNumber - 1) * 5 + levelNumber - 1];
    }

    public ObjectType[] getVariableSlots(){
        return this.variableSlots;
    }
    public int[] getPrices(){
        return this.prices;
    }


    public int getTankNumber(){
        return this.tankNumber;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public String[] getAlienData(){return alienData;};
}

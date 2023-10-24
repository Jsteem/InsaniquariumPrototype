package insaniquarium.game.gamesystem;

import insaniquarium.game.Game;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import insaniquarium.Main;
import insaniquarium.game.GameImage;
import insaniquarium.game.SoundManager;
import insaniquarium.game.eventsystem.EventSystem;
import insaniquarium.game.eventsystem.GameEvent;
import insaniquarium.game.eventsystem.GameListener;

import java.util.HashMap;
import java.util.Random;


////////////////////////////////////////

//Idea is to store the data of all the guppies in this class, collision information (eat radius and bounding area)
//should be stored in a more efficient data structure
//so that the whole array of guppies gets compared efficiently against another entities for collisions

////////////////////////////////////////
public class GameEntity implements GameListener {
    int[] drawTypes = {OBJECT_TYPE_ALIEN, OBJECT_TYPE_FISH, OBJECT_TYPE_FOOD, OBJECT_TYPE_MONEY};
    public enum ENTITY_STATE {IDLE, SEEK_HUNGRY, SEEK_SICK, EAT_HUNGRY, EAT_SICK, FALL_DOWN}

    public enum ENTITY_ANIMATION_STATE {SWIM, EAT, TURN, DIE}

    public static final int MENU_OFFSET_HEIGHT = 80;
    static final int GROUND_OFFSET_HEIGHT = 40;
    static final int SIDE_OFFSET = 20;
    final int MAX_ENTITIES = 1000;
    int currentUniqueId = 0;
    int[] uniqueIds = new int[MAX_ENTITIES];
    int[] objectTypes = new int[MAX_ENTITIES];
    public int[] groupObjectTypes = new int[MAX_ENTITIES];
    int[] targetObjectTypes = new int[MAX_ENTITIES];
    float[] targetX = new float[MAX_ENTITIES];
    float[] targetY = new float[MAX_ENTITIES];
    float[] x = new float[MAX_ENTITIES];
    float[] y = new float[MAX_ENTITIES];
    float[] vx = new float[MAX_ENTITIES];
    float[] vy = new float[MAX_ENTITIES];
    float[] ax = new float[MAX_ENTITIES];
    float[] ay = new float[MAX_ENTITIES];
    boolean[] alive = new boolean[MAX_ENTITIES];
    int numEntities = 0;
    boolean firstLargeGuppy;
    
    int[] level = new int[MAX_ENTITIES];

    //int[] maxLevel; //always 10???
    int[] boundingCircleRadius = new int[MAX_ENTITIES];
    int[] eatCircleRadius = new int[MAX_ENTITIES];

    int[] eatCircleOffset = new int[MAX_ENTITIES];

    //spriteInfo
    int[] spriteWidth = new int[MAX_ENTITIES];

    GameImage smallSwim;
    GameImage smallEat;
    GameImage smallTurn;
    GameImage hungrySwim;
    GameImage hungryEat;
    GameImage hungryTurn;
    GameImage smallDie;
    GameImage imageMoney;
    GameImage imageFood;

    GameImage imageSylv;
    //collision info
    static int[] boundingCircleRadiusGuppyCarnivore = {20, 25, 30, 30, 40};
    static int[] eatCircleOffsetGuppyCarnivore = {10, 14, 20, 20, 20};
    static int[] eatCircleRadiusGuppyCarnivore = {8, 14, 14, 14, 14};

    GameData gameData;

    static double ANIMATION_SPEED_GUPPY_CARNIVORE_SLOW = 0.07;
    static double ANIMATION_SPEED_FOOD = 0.1;

    static long ENTITY_ON_GROUND = 3;

    static float ENTITY_FALL_SPEED = 60;
    static float COIN_FALL_SPEED = 100;
    static int OBJECT_TYPE_FISH = (ObjectType.GUPPY_SMALL.getValue() | ObjectType.GUPPY_MEDIUM.getValue()
            | ObjectType.GUPPY_LARGE.getValue() | ObjectType.GUPPY_KING.getValue() | ObjectType.CARNIVORE.getValue());
    static int OBJECT_TYPE_FOOD = (ObjectType.POTION_FOOD.getValue() | ObjectType.CANNED_FOOD.getValue()
            | ObjectType.PILL_FOOD.getValue() | ObjectType.PELLET_FOOD.getValue());
    static int OBJECT_TYPE_MONEY = (ObjectType.COIN_SILVER.getValue() | ObjectType.COIN_GOLD.getValue()
            | ObjectType.DIAMOND.getValue() | ObjectType.STAR.getValue() | ObjectType.CHEST.getValue() | ObjectType.BEETLE.getValue());

    static int OBJECT_TYPE_GUPPY_CARNIVORE = (ObjectType.GUPPY_SMALL.getValue() | ObjectType.GUPPY_MEDIUM.getValue()
            | ObjectType.GUPPY_LARGE.getValue() | ObjectType.GUPPY_KING.getValue() | ObjectType.CARNIVORE.getValue());

    static int OBJECT_TYPE_ALIEN = (ObjectType.ALIEN.getValue());

    static int[] GUPPY_CARNIVORE_LEVEL_TO_OBJECT_TYPE = new int[]{ObjectType.GUPPY_SMALL.getValue(), ObjectType.GUPPY_MEDIUM.getValue()
            , ObjectType.GUPPY_LARGE.getValue(), ObjectType.GUPPY_KING.getValue(), ObjectType.CARNIVORE.getValue()};
    private HashMap<Integer, KDTree> objectTypeToKDTree;

    private int[] targetsCollected = new int[MAX_ENTITIES];

    private EntityFSM[] entityFSM = new EntityFSM[MAX_ENTITIES];

    CoinDropper[] coinDropper = new CoinDropper[MAX_ENTITIES];

    private AnimationFSM[] entityAnimationFSM = new AnimationFSM[MAX_ENTITIES];

    Factory factory = new Factory(this);

    public GameEntity(GameData gameData) {
        this.gameData = gameData;
        EventSystem.getInstance().addListener(this);
        smallSwim = new GameImage("smallswim.gif");
        smallEat = new GameImage("smalleat.gif");
        smallTurn = new GameImage("smallturn.gif");

        hungrySwim = new GameImage("hungryswim.gif");
        hungryEat = new GameImage("hungryeat.gif");
        hungryTurn = new GameImage("hungryturn.gif");

        smallDie = new GameImage("smalldie.gif");


        imageFood = new GameImage("food.gif");
        imageMoney = new GameImage("money.gif");

        imageSylv = new GameImage("sylv.gif");

        factory.addSmallGuppyCarnivoreRandom(false);
        factory.addSmallGuppyCarnivoreRandom(false);

        SoundManager.getInstance().playSound("BUBBLES.ogg");


        String[] aliens = this.gameData.getAlienData();
        //factory.addAlien(aliens[0]);
    }

    public void setEntity(int id, int uniqueId, int level, int objectType, int groupObjectType, int targetObjectType,
                          float x, float y, float vx, float vy, float ax, float ay, boolean alive,
                          int boundingCircleRadius, int eatCircleRadius, int eatCircleOffset, int spriteWidth,
                          EntityFSM entityFSM,
                          AnimationFSM animationFSM, int targetsCollected) {

        this.level[id] = level;
        this.uniqueIds[id] = uniqueId;
        this.objectTypes[id] = objectType;
        this.groupObjectTypes[id] = groupObjectType;
        this.targetObjectTypes[id] = targetObjectType;
        this.x[id] = x;
        this.y[id] = y;
        this.vx[id] = vx;
        this.vy[id] = vy;
        this.ax[id] = ax;
        this.ay[id] = ay;
        this.alive[id] = alive;
        this.boundingCircleRadius[id] = boundingCircleRadius;
        this.eatCircleRadius[id] = eatCircleRadius;
        this.eatCircleOffset[id] = eatCircleOffset;
        this.spriteWidth[id] = spriteWidth;
        this.entityAnimationFSM[id] = animationFSM;
        this.entityFSM[id] = entityFSM;
        this.targetsCollected[id] = targetsCollected;
    }

   /* public void copyEntityFromIdToId(int fromId, int toId) {
        setEntity(toId,
                uniqueIds[fromId],
                level[fromId],
                objectTypes[fromId],
                groupObjectTypes[fromId],
                targetObjectTypes[fromId],
                x[fromId],
                y[fromId],
                vx[fromId],
                vy[fromId],
                ax[fromId],
                ay[fromId],
                alive[fromId],
                boundingCircleRadius[fromId],
                eatCircleRadius[fromId],
                eatCircleOffset[fromId],
                spriteWidth[fromId],
                entityFSM[fromId],
                entityAnimationFSM[fromId],
                targetsCollected[fromId]);

    }*/

    public void createKDTrees() {
        this.objectTypeToKDTree = new HashMap<>();
        int i = 0;
        while (i < numEntities) {
            if (alive[i]) {
                int objectType = this.objectTypes[i];
                int groupObjectType = this.groupObjectTypes[i];
                float x = this.x[i];
                float y = this.y[i];
                int radius = this.boundingCircleRadius[i];
                CollisionObject o = new CollisionObject(i, x, y, radius);
                KDTree treeObjectType = objectTypeToKDTree.get(objectType);
                KDTree treeGroupType = objectTypeToKDTree.get(groupObjectType);

                if (treeObjectType == null) {
                    treeObjectType = new KDTree();
                    objectTypeToKDTree.put(objectType, treeObjectType);
                }
                treeObjectType.add(o);

                if (treeGroupType == null) {
                    treeGroupType = new KDTree();
                    objectTypeToKDTree.put(groupObjectType, treeGroupType);
                }
                treeGroupType.add(o);
            }
            i++;
        }
    }

    public void lookForNearestTargetWhenHungry() {
        //If the entities are hungry -> look for nearest target by traversing the KDTree
        int i = 0;
        while (i < numEntities) {
            if (alive[i] && (entityFSM[i].currentState == ENTITY_STATE.SEEK_HUNGRY || entityFSM[i].currentState == ENTITY_STATE.SEEK_SICK)) {
                int targetObjectType = this.targetObjectTypes[i];
                if (targetObjectType != -1) {
                    float x = this.x[i];
                    float y = this.y[i];
                    KDTree tree = this.objectTypeToKDTree.get(targetObjectType);
                    if (tree != null) {
                        CollisionObject target = tree.searchNearest(x, y);
                        if (target != null) {
                            //if target is hungry
                            this.targetX[i] = target.x;
                            this.targetY[i] = target.y;
                            double offsetX = (vx[i] > 0 ? this.eatCircleOffset[i] : -this.eatCircleOffset[i]);

                            double dx = this.targetX[i] - (this.x[i] + offsetX);
                            double dy = this.targetY[i] - this.y[i];
                            double distance = Math.sqrt(dx * dx + dy * dy);
                            double sumRadius = target.radius + eatCircleRadius[i];
                            if (distance <= sumRadius) {
                                handleCollision(i, target.id);
                            }
                        } else {
                            this.targetX[i] = -1;
                            this.targetY[i] = -1;
                        }
                    }
                }
            }

            i++;
        }

    }

    public int[] lookForAvailableId() {
        int id = 0;
        while (id < numEntities && alive[id]) {
            id++;
        }
        if (id == numEntities) {
            if (numEntities < MAX_ENTITIES) {
                this.numEntities++;
            } else {
                //first entity gets overwritten
                id = 0;
            }
        }
        return new int[]{id, currentUniqueId++};
    }

    double AIUpdateSpeed = 0.2;
    double timeSinceLastUpdateAI = 0;

    public void update(double delta) {
        createKDTrees();
        lookForNearestTargetWhenHungry();

        if (timeSinceLastUpdateAI >= AIUpdateSpeed) {

            timeSinceLastUpdateAI -= AIUpdateSpeed;

            KDTree fishTree = objectTypeToKDTree.get(OBJECT_TYPE_FISH);

            if (fishTree == null || fishTree.isEmpty()) {
                EventSystem.getInstance().fireEvent(new GameEvent("GAME_OVER", null), 0);
            }

        }
        timeSinceLastUpdateAI += delta;


        int i = 0;
        while (i < numEntities) {
            if (alive[i]) {
                vx[i] += ax[i] * delta;
                vy[i] += ay[i] * delta;
                x[i] += vx[i] * delta + 0.5 * ax[i] * delta * delta;
                y[i] += vy[i] * delta + 0.5 * ay[i] * delta * delta;


                int radius = boundingCircleRadius[i];
                int minWidth = SIDE_OFFSET + radius;
                int maxWidth = Main.WIDTH - SIDE_OFFSET - radius;
                int maxHeight = Main.HEIGHT - radius - GROUND_OFFSET_HEIGHT;
                int minHeight = MENU_OFFSET_HEIGHT + radius;

                if (x[i] <= minWidth) {
                    x[i] = minWidth;
                } else if (x[i] >= maxWidth) {
                    x[i] = maxWidth;
                }
                if (y[i] <= minHeight) {
                    y[i] = minHeight;
                } else if (y[i] >= maxHeight) {
                    y[i] = maxHeight;
                }

                this.entityFSM[i].update(delta);
                this.entityAnimationFSM[i].update(delta);

                if (this.coinDropper[i] != null && entityFSM[i].currentState != null) {
                    if (entityFSM[i].currentState != ENTITY_STATE.FALL_DOWN) {
                        this.coinDropper[i].update(delta);
                    }

                }


                if (entityFSM[i].currentState != null) {
                    switch (entityFSM[i].currentState) {
                        case IDLE -> {
                            Random r = new Random();
                            if (r.nextDouble() < 0.001) {
                                targetX[i] = (int) (Math.random() * (maxWidth - minWidth)) + minWidth;
                                targetY[i] = (int) (Math.random() * (maxHeight - minHeight)) + minHeight;

                            }
                            if (targetX[i] > 0 && targetY[i] > 0) {

                                final double DESIRED_SPEED = 60;
                                final double CURVE_FACTOR = 0.10;
                                final double ARRIVAL_RADIUS = 5.0;


                                double dx = targetX[i] - x[i];
                                double dy = targetY[i] - y[i];

                                double directionLength = Math.sqrt(dx * dx + dy * dy);
                                if (directionLength > ARRIVAL_RADIUS) {
                                    double directionX = dx / directionLength;
                                    double directionY = dy / directionLength;

                                    // Calculate desired velocity
                                    double distanceFactor = Math.max(0.0, Math.min(1.0, directionLength / ARRIVAL_RADIUS));
                                    double desiredSpeed = distanceFactor * DESIRED_SPEED;
                                    double desiredVelocityX = directionX * desiredSpeed;
                                    double desiredVelocityY = directionY * desiredSpeed;

                                    // Calculate steering force
                                    double perpendicularX = -dy;
                                    double perpendicularY = dx;
                                    double steeringForceX = perpendicularX * CURVE_FACTOR;
                                    double steeringForceY = perpendicularY * CURVE_FACTOR;

                                    // Add steering force to desired velocity
                                    double finalVelocityX = desiredVelocityX + steeringForceX;
                                    double finalVelocityY = desiredVelocityY + steeringForceY;

                                    if (vx[i] * finalVelocityX < 0) {
                                        entityAnimationFSM[i].resetAnimationTo(ENTITY_ANIMATION_STATE.TURN);

                                    }
                                    // Calculate velocity components
                                    vx[i] = (float) ((finalVelocityX));
                                    vy[i] = (float) ((finalVelocityY));
                                } else {
                                    targetX[i] = -1;
                                    targetY[i] = -1;

                                }
                            } else {
                                vx[i] *= 0.99;
                                vy[i] *= 0.99;
                            }

                        }
                        case SEEK_HUNGRY, SEEK_SICK -> {

                            if (targetX[i] > 0 && targetY[i] > 0) {
                                final double DESIRED_SPEED = 80;
                                final double CURVE_FACTOR = 0.10;
                                final double ARRIVAL_RADIUS = 5.0;


                                double dx = targetX[i] - x[i];
                                double dy = targetY[i] - y[i];

                                double directionLength = Math.sqrt(dx * dx + dy * dy);
                                double directionX = dx / directionLength;
                                double directionY = dy / directionLength;

                                // Calculate desired velocity
                                double distanceFactor = Math.max(0.0, Math.min(1.0, directionLength / ARRIVAL_RADIUS));
                                double desiredSpeed = distanceFactor * DESIRED_SPEED;
                                double desiredVelocityX = directionX * desiredSpeed;
                                double desiredVelocityY = directionY * desiredSpeed;

                                // Calculate steering force
                                double perpendicularX = -dy;
                                double perpendicularY = dx;
                                double steeringForceX = perpendicularX * CURVE_FACTOR;
                                double steeringForceY = perpendicularY * CURVE_FACTOR;

                                // Add steering force to desired velocity
                                double finalVelocityX = desiredVelocityX + steeringForceX;
                                double finalVelocityY = desiredVelocityY + steeringForceY;

                                if (vx[i] * finalVelocityX < 0) {

                                    entityAnimationFSM[i].resetAnimationTo(ENTITY_ANIMATION_STATE.TURN);

                                }
                                // Calculate velocity components
                                vx[i] = (float) ((finalVelocityX));
                                vy[i] = (float) ((finalVelocityY));
                            } else {
                                vx[i] *= 0.99;
                                vy[i] *= 0.99;
                            }
                        }
                        case FALL_DOWN -> {
                            vx[i] = 0;
                            vy[i] = ENTITY_FALL_SPEED;

                            if (y[i] >= maxHeight) {
                                y[i] = maxHeight;
                                EventSystem.getInstance().fireEvent(new GameEvent("REMOVE_ENTITY", new int[]{i, uniqueIds[i]}), ENTITY_ON_GROUND);

                            }
                        }
                    }
                }
            }
            i++;
        }

    }

    float xclick = 0;
    float yclick = 0;


    public void draw(GraphicsContext gc) {
        drawCircleCenter(gc, this.xclick, this.yclick, 3, Color.BLACK, 1); //draw center

        for (int drawType : drawTypes) {
            for (int i = 0; i < numEntities; i++) {
                if (groupObjectTypes[i] == drawType && alive[i]) {
                    GameImage image = null;

                    if ((OBJECT_TYPE_GUPPY_CARNIVORE & this.objectTypes[i]) > 0) {
                        switch (entityAnimationFSM[i].currentState) {
                            case SWIM -> {
                                if (entityFSM[i].currentState == ENTITY_STATE.SEEK_SICK) {
                                    image = hungrySwim;
                                } else {
                                    image = smallSwim;
                                }

                            }
                            case EAT -> {
                                if (entityFSM[i].currentState == ENTITY_STATE.EAT_SICK) {
                                    image = hungryEat;
                                } else {
                                    image = smallEat;
                                }

                            }
                            case TURN -> {
                                if (entityFSM[i].currentState == ENTITY_STATE.SEEK_SICK) {
                                    image = hungryTurn;
                                } else {
                                    image = smallTurn;
                                }

                            }
                            case DIE -> {
                                image = smallDie;
                            }
                        }

                    } else if ((OBJECT_TYPE_FOOD & this.objectTypes[i]) > 0) {
                        image = imageFood;
                    } else if ((OBJECT_TYPE_MONEY & this.objectTypes[i]) > 0) {
                        image = imageMoney;
                    } else if ((ObjectType.ALIEN.getValue() & this.objectTypes[i]) > 0){
                        image = imageSylv;
                    }


                    int level = this.level[i];

                    int boundingCircleRadius = this.boundingCircleRadius[i];
                    int eatCircleOffset = this.eatCircleOffset[i];
                    int eatCircleRadius = this.eatCircleRadius[i];
                    int spriteWidth = this.spriteWidth[i];

                    if (image != null) {

                        if (vx[i] >= 0) {
                            if (entityAnimationFSM[i].currentState == ENTITY_ANIMATION_STATE.TURN) {
                                gc.drawImage(image.getImage(), entityAnimationFSM[i].frame * spriteWidth, level * spriteWidth, spriteWidth, spriteWidth,
                                        x[i] - spriteWidth / 2, y[i] - spriteWidth / 2, spriteWidth, spriteWidth);
                                drawCircleCenter(gc, x[i] - eatCircleOffset, y[i], eatCircleRadius, Color.GREEN, 2); //draw eat circle
                            } else {
                                //facing to the right
                                gc.scale(-1, 1);
                                gc.drawImage(image.getImage(), entityAnimationFSM[i].frame * spriteWidth, level * spriteWidth, spriteWidth, spriteWidth,
                                        -x[i] - spriteWidth / 2, y[i] - spriteWidth / 2, spriteWidth, spriteWidth);

                                gc.scale(-1, 1);
                                drawCircleCenter(gc, x[i] + eatCircleOffset, y[i], eatCircleRadius, Color.GREEN, 2); //draw eat circle
                            }


                        } else {
                            if (entityAnimationFSM[i].currentState == ENTITY_ANIMATION_STATE.TURN) {
                                //facing to the right
                                gc.scale(-1, 1);
                                gc.drawImage(image.getImage(), entityAnimationFSM[i].frame * spriteWidth, level * spriteWidth, spriteWidth, spriteWidth,
                                        -x[i] - spriteWidth / 2, y[i] - spriteWidth / 2, spriteWidth, spriteWidth);

                                gc.scale(-1, 1);
                                drawCircleCenter(gc, x[i] + eatCircleOffset, y[i], eatCircleRadius, Color.GREEN, 2); //draw eat circle
                            } else {
                                gc.drawImage(image.getImage(), entityAnimationFSM[i].frame * spriteWidth, level * spriteWidth, spriteWidth, spriteWidth,
                                        x[i] - spriteWidth / 2, y[i] - spriteWidth / 2, spriteWidth, spriteWidth);
                                drawCircleCenter(gc, x[i] - eatCircleOffset, y[i], eatCircleRadius, Color.GREEN, 2); //draw eat circle
                            }


                        }
                        //drawCircleCenter(gc, x[i], y[i], 3, Color.BLACK, 1); //draw center
                        drawCircleCenter(gc, x[i], y[i], boundingCircleRadius, Color.GREEN, 2); //draw bounding circle
                    }
                }
            }
        }
    }

    @Override
    public void processEvent(GameEvent event) {
        String message = event.getMessage();
        switch (message) {
            case "MOUSE_CLICKED" -> {
                double[] p = (double[]) event.getPayload();
                xclick = (float) p[0];
                yclick = (float) p[1];

                if (p[1] > GameEntity.MENU_OFFSET_HEIGHT) {
                    //check collisions for TYPE_MONEY
                    KDTree tree = this.objectTypeToKDTree.get(OBJECT_TYPE_MONEY);
                    if (tree != null) {
                        CollisionObject target = tree.searchNearest((float) p[0], (float) p[1]);
                        if (target != null) {
                            double dx = target.x - (float) p[0];
                            double dy = target.y - (float) p[1];
                            double distance = Math.sqrt(dx * dx + dy * dy);
                            if (distance <= target.radius) {
                                handleClickable(this.level[target.id]);
                                removeEntity(target.id);
                            } else {
                                //if no collisions, drop the food
                                EventSystem.getInstance().fireEvent(new GameEvent("BUY_FOOD", new double[]{p[0], p[1]}), 0);
                            }
                        } else {
                            EventSystem.getInstance().fireEvent(new GameEvent("BUY_FOOD", new double[]{p[0], p[1]}), 0);
                        }
                    } else {
                        EventSystem.getInstance().fireEvent(new GameEvent("BUY_FOOD", new double[]{p[0], p[1]}), 0);
                    }
                }
            }
            case "DROP_FOOD" -> {
                SoundManager.getInstance().playSound("DROPFOOD.ogg");
                double x = ((double[]) event.getPayload())[0];
                double y = ((double[]) event.getPayload())[1];
                double tierFood = ((double[]) event.getPayload())[2];
                factory.dropFood((float) x, (float) y, (int) tierFood);

            }

            case "SPAWN_ENTITY" -> {
                SoundManager.getInstance().playSound("SPLASH.ogg");
                if ((((ObjectType) event.getPayload()).getValue() & ObjectType.GUPPY_SMALL.getValue()) > 0) {
                    factory.addSmallGuppyCarnivoreRandom(false);
                } else if ((((ObjectType) event.getPayload()).getValue() & ObjectType.CARNIVORE.getValue()) > 0) {
                    factory.addSmallGuppyCarnivoreRandom(true);
                }
            }
            case "REMOVE_ENTITY" -> {
                int id = ((int[]) event.getPayload())[0];
                int idUnique = ((int[]) event.getPayload())[1];
                if (uniqueIds[id] == idUnique) {
                    removeEntity(id);
                }
            }
            case "PROCESS_EAT" -> {

                int id = ((int[]) event.getPayload())[0];
                int idUnique = ((int[]) event.getPayload())[1];
                if (uniqueIds[id] == idUnique) {
                    targetX[id] = -1;
                    targetY[id] = -1;
                    entityFSM[id].resetStateTo(ENTITY_STATE.IDLE);
                    int targetsCollected = ++this.targetsCollected[id];
                    if (targetsCollected > 1 && this.level[id] < 3) {
                        this.level[id]++;
                        this.targetsCollected[id] = 0;
                        this.objectTypes[id] = GUPPY_CARNIVORE_LEVEL_TO_OBJECT_TYPE[this.level[id]];
                        this.boundingCircleRadius[id] = boundingCircleRadiusGuppyCarnivore[this.level[id]];
                        this.eatCircleRadius[id] = eatCircleRadiusGuppyCarnivore[this.level[id]];
                        this.eatCircleOffset[id] = eatCircleOffsetGuppyCarnivore[this.level[id]];
                        if (this.level[id] == 1) {
                            coinDropper[id] = new CoinDropper(this, id);
                        }

                        if(this.level[id] == 2 && !firstLargeGuppy){
                            gameData.unlockNextButton();
                            firstLargeGuppy = true;
                        }
                        if(this.level[id] == 3){
                            SoundManager.getInstance().playSound("crowned1.ogg");
                        }
                        else{
                            SoundManager.getInstance().playSound("grow.ogg");
                        }
                    }
                }
            }
            case "ANIMATION_DIE" -> {
                int id = ((int[]) event.getPayload())[0];
                int idUnique = ((int[]) event.getPayload())[1];
                if (uniqueIds[id] == idUnique) {
                    entityAnimationFSM[id].resetAnimationTo(ENTITY_ANIMATION_STATE.DIE);
                    objectTypes[id] = objectTypes[id] | ObjectType.DEAD.getValue();
                    SoundManager.getInstance().playSound("DIE.ogg");
                }
            }
        }
    }

    private void handleClickable(int level) {
        int money = 0;
        switch (level) {
            case 0 -> {
                money = 10;
                SoundManager.getInstance().playSound("POINTS.ogg");
            }
            case 1 -> {
                money = 15;
                SoundManager.getInstance().playSound("POINTS.ogg");
            }
            case 2 -> {
                money = 20;
                SoundManager.getInstance().playSound("POINTS.ogg");
            }
            case 3 -> {
                money = 200;
                SoundManager.getInstance().playSound("diamond.ogg");
            }
            case 4 -> {
                money = 2000;
                SoundManager.getInstance().playSound("TREASURE.ogg");
            }
            case 5 -> {
                money = 30;
                SoundManager.getInstance().playSound("POINTS.ogg");
            }

        }
        gameData.increaseMoney(money);


    }

    void handleCollision(int idFrom, int idTo) {
        if (alive[idTo]) {
            if (entityAnimationFSM[idFrom].currentState != ENTITY_ANIMATION_STATE.TURN) {
                if (entityFSM[idFrom].currentState == ENTITY_STATE.SEEK_SICK) {
                    entityFSM[idFrom].resetStateTo(ENTITY_STATE.EAT_SICK);
                } else {
                    entityFSM[idFrom].resetStateTo(ENTITY_STATE.EAT_HUNGRY);
                }
                if(level[idFrom] < 4){
                    SoundManager.getInstance().playSound("SLURP.ogg");
                }
                else{
                    SoundManager.getInstance().playSound("chomp.ogg");
                }
                entityAnimationFSM[idFrom].resetAnimationTo(ENTITY_ANIMATION_STATE.EAT);
                removeEntity(idTo);
            }
        }
    }

    void removeEntity(int id) {
        if(this.alive[id]){
            this.alive[id] = false;
            this.coinDropper[id] = null;
            if (this.groupObjectTypes[id] == OBJECT_TYPE_FOOD) {
                EventSystem.getInstance().fireEvent(new GameEvent("DECREASE_NUMBER_OF_FOOD", null), 0);
            }

        }

    }


    void drawCircleCenter(GraphicsContext gc, float x, float y, int radius, Color color, int width) {
        gc.setStroke(color);
        gc.setLineWidth(width);
        gc.strokeOval(x - radius, y - radius, 2 * radius, 2 * radius);
    }

    void drawRectangle(GraphicsContext gc, float x, float y, int width, int height, Color color, int lineWidth) {
        gc.setStroke(color);
        gc.setLineWidth(lineWidth);
        gc.strokeRect(x, y, width, height);
    }
}

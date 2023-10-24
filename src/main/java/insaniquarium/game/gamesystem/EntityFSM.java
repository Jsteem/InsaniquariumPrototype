package insaniquarium.game.gamesystem;

import insaniquarium.game.eventsystem.EventSystem;
import insaniquarium.game.eventsystem.GameEvent;

public class EntityFSM {
    double currentTime = 0;
    double timeOfExecutionNextState = 0;
    GameEntity.ENTITY_STATE currentState;
    GameEntity.ENTITY_STATE nextState;

    int id;

    int uniqueId;

    int groupObjectType;

    boolean shouldUpdate = true;

    public EntityFSM(int id, int uniqueId){
        this.currentState = GameEntity.ENTITY_STATE.IDLE;
        setNextState();
    }

    public EntityFSM(GameEntity.ENTITY_STATE state, int id, int uniqueId, int groupObjectType){
        this.id = id;
        this.uniqueId = uniqueId;
        this.currentState = state;
        this.groupObjectType = groupObjectType;
        setNextState();
    }

    public void update(double delta){
        if(shouldUpdate){
            currentTime += delta;
            if(currentTime > timeOfExecutionNextState){
                if(nextState != null){
                    this.currentState = nextState;
                    currentTime = 0;
                    setNextState();

                }
            }
        }

    }
    public void resetStateTo(GameEntity.ENTITY_STATE state){
        currentTime = 0;
        this.currentState = state;
        this.setNextState();
    }
    public void setNextState(GameEntity.ENTITY_STATE nextState, double timeOfExecutionNextState) {
        this.nextState = nextState;
        this.timeOfExecutionNextState = timeOfExecutionNextState;
    }
    public void setNextState(){
        if(this.currentState != null){
            switch(this.currentState) {
                case IDLE -> {
                    setNextState(GameEntity.ENTITY_STATE.SEEK_HUNGRY, 5);
                }
                case SEEK_HUNGRY -> {
                    setNextState(GameEntity.ENTITY_STATE.SEEK_SICK, 5);
                }
                case SEEK_SICK -> {
                    setNextState(GameEntity.ENTITY_STATE.FALL_DOWN, 5);

                }
                case FALL_DOWN -> {
                    if(groupObjectType == GameEntity.OBJECT_TYPE_FISH){
                        EventSystem.getInstance().fireEvent(new GameEvent("ANIMATION_DIE", new int[]{id, uniqueId}), 0);
                        shouldUpdate = false;
                    }
                }
                 case EAT_SICK, EAT_HUNGRY -> {
                    setNextState(GameEntity.ENTITY_STATE.IDLE, 1);
                }

            }
        }


    }
}
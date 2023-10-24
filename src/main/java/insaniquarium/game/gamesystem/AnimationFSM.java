package insaniquarium.game.gamesystem;

import insaniquarium.game.eventsystem.EventSystem;
import insaniquarium.game.eventsystem.GameEvent;

public class AnimationFSM {

    int frame = 0;
    GameEntity.ENTITY_ANIMATION_STATE currentState;
    GameEntity.ENTITY_ANIMATION_STATE nextState;

    int id;

    int uniqueId;

    int groupObjectType;

    boolean shouldUpdate = true;

    double animationSpeed = 1;

    double timeSinceLastUpdateAnimation = 0;

    public AnimationFSM(int id, int uniqueId, int groupObjectType, double animationSpeed) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.groupObjectType = groupObjectType;
        this.currentState = GameEntity.ENTITY_ANIMATION_STATE.SWIM;
        this.animationSpeed = animationSpeed;
        setNextState();
    }

    public AnimationFSM(GameEntity.ENTITY_ANIMATION_STATE state, int id, int uniqueId, int groupObjectType) {
        this.id = id;
        this.uniqueId = uniqueId;
        this.currentState = state;
        this.groupObjectType = groupObjectType;
        setNextState();
    }

    public void update(double delta) {
        if (shouldUpdate) {
            if (timeSinceLastUpdateAnimation >= animationSpeed) {
                if (++frame >= 9) {
                    if (nextState != null) {
                        frame = 0;
                        onAnimationComplete();
                        this.currentState = nextState;
                        setNextState();
                    }
                }
                timeSinceLastUpdateAnimation -= animationSpeed;
            }
            timeSinceLastUpdateAnimation += delta;
        }


    }

    public void resetAnimationTo(GameEntity.ENTITY_ANIMATION_STATE state) {
        frame = 0;
        this.currentState = state;
        if(this.currentState != GameEntity.ENTITY_ANIMATION_STATE.EAT || this.nextState != GameEntity.ENTITY_ANIMATION_STATE.EAT){
            this.setNextState();
        }
        else{
            this.nextState = GameEntity.ENTITY_ANIMATION_STATE.EAT;
        }
    }

    public void setNextState(GameEntity.ENTITY_ANIMATION_STATE nextState) {
        this.nextState = nextState;

    }

    public void setNextState() {
        if (this.currentState != null) {
            switch (this.currentState) {
                case SWIM -> {
                    setNextState(GameEntity.ENTITY_ANIMATION_STATE.SWIM);
                }
                case TURN -> {
                    setNextState(GameEntity.ENTITY_ANIMATION_STATE.SWIM);
                }
                case EAT -> {
                    setNextState(GameEntity.ENTITY_ANIMATION_STATE.SWIM);
                }
                case DIE -> {
                    setNextState(GameEntity.ENTITY_ANIMATION_STATE.DIE);
                }
            }

        }
    }
    public void onAnimationComplete(){
        switch (this.currentState) {
            case SWIM -> {

            }
            case TURN -> {

            }
            case EAT -> {
                EventSystem.getInstance().fireEvent(new GameEvent("PROCESS_EAT", new int[]{id, uniqueId} ), 0);

            }
            case DIE -> {
                frame = 9;
                this.shouldUpdate = false;
            }
        }


    }

}

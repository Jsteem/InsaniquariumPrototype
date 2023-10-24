package insaniquarium.game.eventsystem;

import org.w3c.dom.events.Event;

public class GameEvent {
    private String message;
    private Object payload;
    private double scheduledTime;

    public GameEvent(String message, Object payload){
        this.message = message;
        this.payload = payload;

    }

    public void setScheduledTime(double scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
    public double getScheduledTime(){
        return this.scheduledTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getPayload() {
        return payload;
    }

    public void setPayload(Object payload) {
        this.payload = payload;
    }

}

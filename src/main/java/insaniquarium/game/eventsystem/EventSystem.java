package insaniquarium.game.eventsystem;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

public class EventSystem {
    private static EventSystem instance;

    private Set<GameListener> listeners;


    private List<GameEvent> events;

    private double inGameTime;

    private EventSystem() {

        listeners = new CopyOnWriteArraySet<>();
        events = new CopyOnWriteArrayList<>();
    }

    public static EventSystem getInstance() {
        if (instance == null) {
            instance = new EventSystem();
        }
        return instance;
    }
    public void addListener(GameListener listener) {
        listeners.add(listener);
    }

    public void removeListener(GameListener listener) {
        listeners.remove(listener);
    }
    public void fireEvent(GameEvent event, double delay) {
        double currentTime = this.inGameTime;
        double scheduledTime = currentTime + delay;
        event.setScheduledTime(scheduledTime);
        events.add(event);
    }
    public void update(double inGameTime){
        this.inGameTime = inGameTime;
        processEvents();
    }

    public void processEvents() {

            for (GameEvent event : events) {
                for (GameListener listener: listeners) {
                    if (event.getScheduledTime() <= this.inGameTime) {
                        listener.processEvent(event);
                        events.remove(event);
                    }
                }
            }


    }
    public void reset(){
        this.events.clear();
        this.listeners.clear();
    }
}

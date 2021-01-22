package br.edu.uffs.simplesim.simulator.components;

import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;

public abstract class EventExecutor extends Component implements Comparable<EventExecutor> {
    public abstract Optional<Event> executeNextEvent();

    protected final Queue<Event> eventsQueue = new PriorityQueue<>();

    public boolean hasEventsLeftInTheQueue() {
        return eventsQueue.size() > 0;
    }

    public boolean hasNoEventsLeftInTheQueue() {
        return eventsQueue.size() == 0;
    }

    public void enqueueEvent(Event event) {
        eventsQueue.add(event);
    }

    @Override
    public int compareTo(EventExecutor otherEventExecutor) {
        if (this instanceof Router && otherEventExecutor instanceof Router)
            return eventsQueue.peek().compareTo(otherEventExecutor.eventsQueue.peek());
        else if (this instanceof Router)
            return -1;
        else if (otherEventExecutor instanceof Router)
            return 1;
        else
            return eventsQueue.peek().compareTo(otherEventExecutor.eventsQueue.peek());
    }


}

package br.edu.uffs.simplesim.simulator.components;

import java.util.Optional;

public interface EventExecutor {
    public Optional<Event> execute(Event event);
}

package br.edu.uffs.simplesim.simulator.components;

import br.edu.uffs.simplesim.simulator.components.configuration.ExitConfiguration;

import java.util.Optional;

public class Exit extends Component implements EventExecutor {
    public Exit(ExitConfiguration exitConfiguration) {
        super.setName(exitConfiguration.getName());
    }

    @Override
    public Optional<Event> execute(Event event) {
        System.out.println("Saiu"); //TODO
        return Optional.empty(); // Always return empty because the temporary unit exited the simulation
    }
}

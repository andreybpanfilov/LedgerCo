package tel.panfilov.geektrust.ledgerco.cli.impl;

import tel.panfilov.geektrust.ledgerco.cli.Command;
import tel.panfilov.geektrust.ledgerco.cli.CommandRegistry;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public class CommandRegistryImpl implements CommandRegistry {

    private final Map<String, Command> commands;

    public CommandRegistryImpl(List<Command> commands) {
        this.commands = commands.stream().collect(Collectors.toMap(
                Command::getName,
                Function.identity()
        ));
    }

    @Override
    public Optional<Command> getCommand(String cmd) {
        return Optional.ofNullable(commands.get(cmd));
    }

}

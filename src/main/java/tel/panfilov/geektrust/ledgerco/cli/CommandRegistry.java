package tel.panfilov.geektrust.ledgerco.cli;

import java.util.Optional;

public interface CommandRegistry {

    Optional<Command> getCommand(String cmd);



}

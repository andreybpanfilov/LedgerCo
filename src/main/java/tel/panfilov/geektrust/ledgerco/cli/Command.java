package tel.panfilov.geektrust.ledgerco.cli;

import tel.panfilov.geektrust.ledgerco.util.Try;

public interface Command {

    String getName();

    Try<CommandResult> execute(String... args);

}

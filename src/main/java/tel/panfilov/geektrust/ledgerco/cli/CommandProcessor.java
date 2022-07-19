package tel.panfilov.geektrust.ledgerco.cli;

import tel.panfilov.geektrust.ledgerco.cli.impl.CommandFailure;
import tel.panfilov.geektrust.ledgerco.util.Try;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Optional;

public class CommandProcessor {

    private final CommandRegistry commandRegistry;

    public CommandProcessor(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }

    public void process(BufferedReader reader, BufferedWriter writer) throws IOException {
        String cmd;
        for (int lineNo = 0; (cmd = reader.readLine()) != null; lineNo++) {
            CommandResult result = processCommand(cmd, lineNo);
            if (!result.isEmpty()) {
                writer.write(result + System.lineSeparator());
            }
            if (result.isFail()) {
                break;
            }
        }
    }

    protected CommandResult processCommand(String cmd, int lineNo) {
        String[] parts = cmd.split("\\s+");
        Optional<Command> command = commandRegistry.getCommand(parts[0]);
        if (!command.isPresent()) {
            return fail(lineNo, cmd, "UNRECOGNISED_COMMAND");
        }
        Try<CommandResult> result = command.get().execute(parts);
        if (result.isFail()) {
            Throwable throwable = result.getLeft();
            return fail(lineNo, cmd, throwable.getMessage());
        }
        return result.getRight();
    }

    protected CommandFailure fail(int lineNo, String cmd, String cause) {
        return new CommandFailure(lineNo, cmd, cause);
    }

}

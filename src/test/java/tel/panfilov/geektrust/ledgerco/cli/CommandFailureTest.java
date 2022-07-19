package tel.panfilov.geektrust.ledgerco.cli;

import org.junit.jupiter.api.Test;
import tel.panfilov.geektrust.ledgerco.AbstractLedgerCoTest;
import tel.panfilov.geektrust.ledgerco.cli.impl.CommandFailure;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandFailureTest extends AbstractLedgerCoTest {

    @Test
    void resultIsEmptyIfCauseIsNull() {
        CommandFailure commandFailure = new CommandFailure(1, "CMD", null);
        assertThat(commandFailure.isEmpty()).isTrue();

        commandFailure = new CommandFailure(1, "CMD", "cause");
        assertThat(commandFailure.isEmpty()).isFalse();
    }

}

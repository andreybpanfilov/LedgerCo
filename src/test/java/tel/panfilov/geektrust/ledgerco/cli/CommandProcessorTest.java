package tel.panfilov.geektrust.ledgerco.cli;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import tel.panfilov.geektrust.ledgerco.AbstractLedgerCoTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;

import static org.assertj.core.api.Assertions.assertThat;

public class CommandProcessorTest extends AbstractLedgerCoTest {

    CommandProcessor commandProcessor;

    @BeforeEach
    void tierUp() {
        commandProcessor = Main.createProcessor();
    }

    @Test
    void testUnrecognisedCommand() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String command = "CMD";
        try (
                BufferedReader in = new BufferedReader(new StringReader(command));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(baos));
        ) {
            commandProcessor.process(in, out);
        }
        assertThat(baos.toString().trim())
                .isEqualTo("ERROR LINE_NO 0 CMD UNRECOGNISED_COMMAND");
    }

    @Test
    void testCommandFailure() throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        String command = "LOAN";
        try (
                BufferedReader in = new BufferedReader(new StringReader(command));
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(baos));
        ) {
            commandProcessor.process(in, out);
        }
        assertThat(baos.toString().trim())
                .isEqualTo("ERROR LINE_NO 0 LOAN Invalid number of arguments");
    }



}

package tel.panfilov.geektrust.ledgerco.cli;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static org.assertj.core.api.Assertions.assertThat;

public class MainTest {

    @ParameterizedTest
    @CsvSource(delimiterString = ";", value = {"input1.txt;output1.txt", "input2.txt;output2.txt"})
    void testLoad(String input, String output) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try (InputStream in = getStream(input); InputStream expected = getStream(output)) {
            Main.main(in, baos);
            assertThat(new ByteArrayInputStream(baos.toByteArray()))
                    .hasSameContentAs(expected);
        }
    }

    protected InputStream getStream(String resource) {
        return CommandProcessorTest.class.getResourceAsStream("/" + resource);
    }


}

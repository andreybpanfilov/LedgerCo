package tel.panfilov.geektrust.ledgerco.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class AssertTest {

    @Test
    void assertions() {
        assertThatThrownBy(() -> Assert.isTrue(false, "test"))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("test");

        assertThatCode(() -> Assert.isTrue(true,"test"))
                .doesNotThrowAnyException();

        assertThatThrownBy(() -> Assert.hasLength(null, "test"))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("test");

        assertThatThrownBy(() -> Assert.hasLength("", "test"))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("test");

        assertThatCode(() -> Assert.hasLength(" ","test"))
                .doesNotThrowAnyException();

        assertThatCode(() -> Assert.hasLength("a","test"))
                .doesNotThrowAnyException();
    }

}

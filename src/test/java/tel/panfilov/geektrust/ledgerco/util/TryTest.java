package tel.panfilov.geektrust.ledgerco.util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class TryTest {

    @Test
    void tryLeftOrRightIsNotNull() {
        assertThatThrownBy(() -> Try.success(null)).isInstanceOf(NullPointerException.class);
        assertThatThrownBy(() -> Try.failure(null)).isInstanceOf(NullPointerException.class);
    }

    @Test
    void successIsNotFail() {
        assertThat(Try.failure(new IllegalArgumentException()))
                .satisfies(t -> assertThat(t.isFail()).isTrue())
                .satisfies(t -> assertThat(t.isSuccess()).isFalse());

        assertThat(Try.success(""))
                .satisfies(t -> assertThat(t.isFail()).isFalse())
                .satisfies(t -> assertThat(t.isSuccess()).isTrue());
    }

}

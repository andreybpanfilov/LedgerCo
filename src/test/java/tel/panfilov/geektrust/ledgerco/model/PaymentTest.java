package tel.panfilov.geektrust.ledgerco.model;

import org.junit.jupiter.api.Test;
import tel.panfilov.geektrust.ledgerco.AbstractLedgerCoTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentTest extends AbstractLedgerCoTest {

    @Test
    void equalsAndHashCodeContract() {
        Payment first = new Payment(1, new BigDecimal("300"));
        Payment second = new Payment(1, new BigDecimal("300"));
        assertThat(first)
                .isEqualTo(first)
                .isNotEqualTo(second);
        assertThat(first.hashCode())
                .isNotEqualTo(second.hashCode());
    }

    @Test
    void testToString() {
        Payment first = new Payment(1, new BigDecimal("300"));
        assertThat(first.toString())
                .startsWith("Payment")
                .contains("paymentNo=1")
                .contains("amount=300");
    }

    @Test
    void gettersReturnValidValues() {
        Payment first = new Payment(1, new BigDecimal("300"));
        assertThat(first)
                .satisfies(c -> assertThat(c.getAmount()).isEqualTo("300"))
                .satisfies(c -> assertThat(c.getPaymentNo()).isEqualTo(1));
    }

}

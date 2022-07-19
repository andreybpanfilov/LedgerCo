package tel.panfilov.geektrust.ledgerco.model.vo;

import org.junit.jupiter.api.Test;
import tel.panfilov.geektrust.ledgerco.AbstractLedgerCoTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class PaymentScheduleTest extends AbstractLedgerCoTest {

    @Test
    void equalsAndHashCodeContract() {
        PaymentSchedule first = validSchedule();
        PaymentSchedule second = validSchedule();
        PaymentSchedule third = new PaymentSchedule(BigDecimal.TEN, 1, BigDecimal.ONE);
        PaymentSchedule fourth = new PaymentSchedule(new BigDecimal("12000"), 1, BigDecimal.ONE);
        PaymentSchedule fifth = new PaymentSchedule(new BigDecimal("12000"), 60, BigDecimal.ONE);
        PaymentSchedule sixth = new PaymentSchedule(new BigDecimal("1200"), 60, BigDecimal.ONE);

        //noinspection ConstantConditions,EqualsWithItself
        assertThat(first)
                .satisfies(s -> assertThat(s.equals(s)).isTrue())
                .satisfies(s -> assertThat(s.equals(null)).isFalse())
                .satisfies(s -> assertThat(s.equals(new Object())).isFalse())
                .isEqualTo(first)
                .isEqualTo(second)
                .isNotEqualTo(third)
                .isNotEqualTo(fourth)
                .isNotEqualTo(fifth)
                .isNotEqualTo(sixth);
        assertThat(first.hashCode())
                .isEqualTo(second.hashCode());
    }

    @Test
    void testToString() {
        PaymentSchedule first = validSchedule();
        assertThat(first.toString())
                .startsWith("PaymentSchedule")
                .contains("total=" + first.getTotal())
                .contains("months=" + first.getMonths())
                .contains("monthlyPayment=" + first.getMonthlyPayment());
    }

    @Test
    void gettersReturnValidValues() {
        PaymentSchedule first = validSchedule();
        assertThat(first)
                .satisfies(c -> assertThat(c.getTotal()).isEqualTo("12000"))
                .satisfies(c -> assertThat(c.getMonths()).isEqualTo(60))
                .satisfies(c -> assertThat(c.getMonthlyPayment()).isEqualTo("200"));
    }

}

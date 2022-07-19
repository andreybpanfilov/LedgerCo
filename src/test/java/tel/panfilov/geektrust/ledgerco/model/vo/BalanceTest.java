package tel.panfilov.geektrust.ledgerco.model.vo;

import org.junit.jupiter.api.Test;
import tel.panfilov.geektrust.ledgerco.AbstractLedgerCoTest;
import tel.panfilov.geektrust.ledgerco.model.LoanId;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class BalanceTest extends AbstractLedgerCoTest {

    @Test
    void equalsAndHashCodeContract() {
        LoanId firstId = loanId("NAB", "Andrey");
        LoanId secondId = loanId("CBA", "Andrey");
        Balance first = new Balance(firstId, BigDecimal.TEN, 1);
        Balance second = new Balance(firstId, BigDecimal.TEN, 1);
        Balance third = new Balance(firstId, BigDecimal.ZERO, 10);
        Balance forth = new Balance(firstId, BigDecimal.ZERO, 1);
        Balance fifth = new Balance(firstId, BigDecimal.TEN, 10);
        Balance sixth = new Balance(secondId, BigDecimal.TEN, 1);
        //noinspection ConstantConditions,EqualsWithItself
        assertThat(first)
                .isEqualTo(first)
                .satisfies(c -> assertThat(c.equals(c)).isTrue())
                .satisfies(c -> assertThat(c.equals(null)).isFalse())
                .satisfies(c -> assertThat(c.equals(new Object())).isFalse())
                .isEqualTo(second)
                .isNotEqualTo(third)
                .isNotEqualTo(forth)
                .isNotEqualTo(fifth)
                .isNotEqualTo(sixth);
        assertThat(first.hashCode())
                .isEqualTo(second.hashCode());
    }

    @Test
    void testToString() {
        LoanId firstId = loanId("NAB", "Andrey");
        Balance first = new Balance(firstId, BigDecimal.TEN, 1);
        assertThat(first.toString())
                .startsWith("Balance")
                .contains("amountPaid=" + first.getAmountPaid())
                .contains("paymentsLeft=" + first.getPaymentsLeft());
    }


}

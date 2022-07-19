package tel.panfilov.geektrust.ledgerco.model;

import org.junit.jupiter.api.Test;
import tel.panfilov.geektrust.ledgerco.AbstractLedgerCoTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class LoanTest extends AbstractLedgerCoTest {

    @Test
    void equalsAndHashCodeContract() {
        Loan first = validLoan("NAB", "Andrey");
        Loan second = validLoan("NAB", "Andrey");
        //noinspection ConstantConditions,EqualsWithItself
        assertThat(first)
                .isEqualTo(first)
                .satisfies(l -> assertThat(l.equals(l)).isTrue())
                .satisfies(l -> assertThat(l.equals(null)).isFalse())
                .satisfies(l -> assertThat(l.equals(new Object())).isFalse())
                .isEqualTo(second);
        assertThat(first.hashCode())
                .isEqualTo(second.hashCode());
    }

    @Test
    void testToString() {
        Loan first = validLoan("NAB", "Andrey");
        assertThat(first.toString())
                .startsWith("Loan");
    }

    @Test
    void gettersReturnValidValues() {
        Loan first = validLoan("NAB", "Andrey");
        assertThat(first)
                .satisfies(c -> assertThat(c.getId().getBank()).isEqualTo("NAB"))
                .satisfies(c -> assertThat(c.getId().getBorrower()).isEqualTo("Andrey"));
    }

    @Test
    void lumpSums() {
        Loan loan = validLoan("NAB", "Andrey");
        assertThat(loan.getLumpSum(0)).isEqualTo("0");

        loan.addPayment(new Payment(0, BigDecimal.TEN));
        assertThat(loan.getLumpSum(0)).isEqualTo("10");
        assertThat(loan.getLumpSum(1)).isEqualTo("10");
        assertThat(loan.getLumpSum(10)).isEqualTo("10");

        loan.addPayment(new Payment(0, BigDecimal.TEN));
        assertThat(loan.getLumpSum(0)).isEqualTo("20");
        assertThat(loan.getLumpSum(1)).isEqualTo("20");
        assertThat(loan.getLumpSum(10)).isEqualTo("20");

        loan.addPayment(new Payment(1, BigDecimal.TEN));
        assertThat(loan.getLumpSum(0)).isEqualTo("20");
        assertThat(loan.getLumpSum(1)).isEqualTo("30");
        assertThat(loan.getLumpSum(10)).isEqualTo("30");
    }


}

package tel.panfilov.geektrust.ledgerco.model;

import org.junit.jupiter.api.Test;
import tel.panfilov.geektrust.ledgerco.AbstractLedgerCoTest;

import static org.assertj.core.api.Assertions.assertThat;

public class LoanIdTest extends AbstractLedgerCoTest {

    @Test
    void equalsAndHashCodeContract() {
        LoanId first = loanId("NAB", "Andrey");
        LoanId second = loanId("NAB", "Andrey");
        LoanId third = loanId("CBA", "Andrey");
        LoanId fourth = loanId("NAB", "Andrew");
        //noinspection ConstantConditions,EqualsWithItself
        assertThat(first)
                .satisfies(id -> assertThat(id.equals(id)).isTrue())
                .satisfies(id -> assertThat(id.equals(null)).isFalse())
                .satisfies(id -> assertThat(id.equals(new Object())).isFalse())
                .isEqualTo(second)
                .extracting(LoanId::hashCode)
                .isEqualTo(second.hashCode());
        assertThat(second).isEqualTo(first);
        assertThat(first)
                .isNotEqualTo(third)
                .isNotEqualTo(fourth);
        assertThat(third)
                .isNotEqualTo(fourth);
    }

    @Test
    void toStringTest() {
        LoanId first = loanId("NAB", "Andrey");
        assertThat(first.toString())
                .startsWith("LoanId")
                .contains("bank='NAB'")
                .contains("borrower='Andrey");
    }

    @Test
    void gettersReturnValidValues() {
        LoanId loanId = loanId("NAB", "Andrey");
        assertThat(loanId)
                .satisfies(id -> assertThat(id.getBank()).isEqualTo("NAB"))
                .satisfies(id -> assertThat(id.getBorrower()).isEqualTo("Andrey"));
    }

}

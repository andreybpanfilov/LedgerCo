package tel.panfilov.geektrust.ledgerco.model.vo;

import org.junit.jupiter.api.Test;
import tel.panfilov.geektrust.ledgerco.AbstractLedgerCoTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class LoanConditionsTest extends AbstractLedgerCoTest {

    @Test
    void equalsAndHashCodeContract() {
        LoanConditions first = validConditions();
        LoanConditions second = validConditions();
        LoanConditions third = new LoanConditions(BigDecimal.TEN, 1, BigDecimal.ONE);
        LoanConditions forth = new LoanConditions(new BigDecimal("10000"), 1, BigDecimal.ONE);
        LoanConditions fifth = new LoanConditions(new BigDecimal("10000"), 5, BigDecimal.ONE);
        LoanConditions sixth = new LoanConditions(new BigDecimal("1000"), 5, BigDecimal.ONE);
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
        LoanConditions first = validConditions();
        assertThat(first.toString())
                .startsWith("LoanConditions")
                .contains("principal=" + first.getPrincipal())
                .contains("term=" + first.getTerm())
                .contains("interestRate=" + first.getInterestRate());
    }

    @Test
    void gettersReturnValidValues() {
        LoanConditions first = validConditions();
        assertThat(first)
                .satisfies(c -> assertThat(c.getPrincipal()).isEqualTo("10000"))
                .satisfies(c -> assertThat(c.getInterestRate()).isEqualTo("4"))
                .satisfies(c -> assertThat(c.getTerm()).isEqualTo(5));
    }

}

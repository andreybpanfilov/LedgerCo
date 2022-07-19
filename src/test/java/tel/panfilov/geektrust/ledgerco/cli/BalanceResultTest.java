package tel.panfilov.geektrust.ledgerco.cli;

import org.junit.jupiter.api.Test;
import tel.panfilov.geektrust.ledgerco.AbstractLedgerCoTest;
import tel.panfilov.geektrust.ledgerco.cli.impl.BalanceResult;
import tel.panfilov.geektrust.ledgerco.model.Loan;
import tel.panfilov.geektrust.ledgerco.model.vo.Balance;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

public class BalanceResultTest extends AbstractLedgerCoTest {

    @Test
    void resultIsEmptyIfBalanceIsNull() {
        Loan loan = validLoan("NAB", "Andrey");

        BalanceResult balanceResult = new BalanceResult(null);
        assertThat(balanceResult.isEmpty()).isTrue();

        balanceResult = new BalanceResult(new Balance(loan.getId(), BigDecimal.TEN, 1));
        assertThat(balanceResult.isEmpty()).isFalse();
    }

}

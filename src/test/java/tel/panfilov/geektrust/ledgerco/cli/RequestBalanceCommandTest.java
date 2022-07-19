package tel.panfilov.geektrust.ledgerco.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tel.panfilov.geektrust.ledgerco.AbstractLedgerCoTest;
import tel.panfilov.geektrust.ledgerco.cli.impl.BalanceResult;
import tel.panfilov.geektrust.ledgerco.cli.impl.RequestBalanceCommand;
import tel.panfilov.geektrust.ledgerco.model.vo.Balance;
import tel.panfilov.geektrust.ledgerco.util.Try;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.type;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RequestBalanceCommandTest extends AbstractLedgerCoTest {

    @Mock
    LoanServiceAdapter loanService;

    @InjectMocks
    RequestBalanceCommand requestBalanceCommand;


    @Test
    void nameOfRequestBalanceCommand() {
        assertThat(requestBalanceCommand.getName()).isEqualTo("BALANCE");
    }

    @Test
    void executeRequestBalanceCommand() {
        String bank = "NAB";
        String borrower = "Andrey";
        String paymentNo = "2";
        String[] args = new String[]{requestBalanceCommand.getName(), bank, borrower, paymentNo};

        Balance balance = new Balance(loanId(bank, borrower), BigDecimal.TEN, 1);

        when(loanService.getBalance(bank, borrower, 2))
                .thenReturn(balance);

        assertThat(requestBalanceCommand.execute(args))
                .satisfies(t -> assertThat(t.isSuccess()).isTrue())
                .extracting(Try::getRight, type(BalanceResult.class))
                .satisfies(r -> assertThat(r.isEmpty()).isFalse())
                .extracting(BalanceResult::getBalance, type(Balance.class))
                .satisfies(b -> assertThat(b.getLoanId()).isEqualTo(balance.getLoanId()))
                .satisfies(b -> assertThat(b.getAmountPaid()).isEqualTo(balance.getAmountPaid()))
                .satisfies(b -> assertThat(b.getPaymentsLeft()).isEqualTo(balance.getPaymentsLeft()));
        verify(loanService, times(1))
                .getBalance(eq(bank), eq(borrower), eq(2));
    }

    @Test
    void executeRequestBalanceCommandWithInvalidArguments() {
        String bank = "NAB";
        String borrower = "Andrey";
        String[] args = new String[]{requestBalanceCommand.getName(), bank, borrower};
        assertThat(requestBalanceCommand.execute(args))
                .satisfies(t -> assertThat(t.isSuccess()).isFalse())
                .satisfies(t -> assertThat(t.isFail()).isTrue())
                .extracting(Try::getLeft)
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .extracting(Exception::getMessage)
                .isEqualTo("Invalid number of arguments");
    }

}

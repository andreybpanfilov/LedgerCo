package tel.panfilov.geektrust.ledgerco.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tel.panfilov.geektrust.ledgerco.AbstractLedgerCoTest;
import tel.panfilov.geektrust.ledgerco.cli.impl.CreateLoanCommand;
import tel.panfilov.geektrust.ledgerco.model.Loan;
import tel.panfilov.geektrust.ledgerco.util.Try;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.type;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateLoanCommandTest extends AbstractLedgerCoTest {

    @Mock
    LoanServiceAdapter loanService;

    @InjectMocks
    CreateLoanCommand createLoanCommand;


    @Test
    void nameOfCreateLoanCommand() {
        assertThat(createLoanCommand.getName()).isEqualTo("LOAN");
    }

    @Test
    void executeCreateLoanCommand() {
        String bank = "NAB";
        String borrower = "Andrey";
        String principal = "10000";
        String term = "2";
        String interestRate = "3";

        Loan loan = validLoan(bank, borrower);

        String[] args = new String[]{createLoanCommand.getName(), bank, borrower, principal, term, interestRate};
        when(loanService.createLoan(bank, borrower, principal, 2, interestRate))
                .thenReturn(loan);

        assertThat(createLoanCommand.execute(args))
                .satisfies(t -> assertThat(t.isSuccess()).isTrue())
                .extracting(Try::getRight, type(CommandResult.class))
                .satisfies(r -> assertThat(r.isEmpty()).isTrue());

        verify(loanService, times(1))
                .createLoan(eq(bank), eq(borrower), eq(principal), eq(2), eq(interestRate));
    }

    @Test
    void executeCreateLoanCommandWithInvalidArguments() {
        String bank = "NAB";
        String borrower = "Andrey";
        String[] args = new String[]{createLoanCommand.getName(), bank, borrower};
        assertThat(createLoanCommand.execute(args))
                .satisfies(t -> assertThat(t.isSuccess()).isFalse())
                .satisfies(t -> assertThat(t.isFail()).isTrue())
                .extracting(Try::getLeft)
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .extracting(Exception::getMessage)
                .isEqualTo("Invalid number of arguments");
    }

}

package tel.panfilov.geektrust.ledgerco.cli;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tel.panfilov.geektrust.ledgerco.AbstractLedgerCoTest;
import tel.panfilov.geektrust.ledgerco.cli.impl.CreatePaymentCommand;
import tel.panfilov.geektrust.ledgerco.util.Try;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.InstanceOfAssertFactories.type;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class CreatePaymentCommandTest extends AbstractLedgerCoTest {

    @Mock
    LoanServiceAdapter loanService;

    @InjectMocks
    CreatePaymentCommand createPaymentCommand;


    @Test
    void nameOfCreatePaymentCommand() {
        assertThat(createPaymentCommand.getName()).isEqualTo("PAYMENT");
    }

    @Test
    void executeCreatePaymentCommand() {
        String bank = "NAB";
        String borrower = "Andrey";
        String amount = "10000";
        String paymentNo = "2";
        String[] args = new String[]{createPaymentCommand.getName(), bank, borrower, amount, paymentNo};

        assertThat(createPaymentCommand.execute(args))
                .satisfies(t -> assertThat(t.isSuccess()).isTrue())
                .extracting(Try::getRight, type(CommandResult.class))
                .satisfies(r -> assertThat(r.isEmpty()).isTrue());

        verify(loanService, times(1))
                .addPayment(eq(bank), eq(borrower), eq(amount), eq(2));
    }

    @Test
    void executeCreatePaymentCommandWithInvalidArguments() {
        String bank = "NAB";
        String borrower = "Andrey";
        String[] args = new String[]{createPaymentCommand.getName(), bank, borrower};
        assertThat(createPaymentCommand.execute(args))
                .satisfies(t -> assertThat(t.isSuccess()).isFalse())
                .satisfies(t -> assertThat(t.isFail()).isTrue())
                .extracting(Try::getLeft)
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .extracting(Exception::getMessage)
                .isEqualTo("Invalid number of arguments");
    }

}

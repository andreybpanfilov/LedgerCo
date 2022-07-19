package tel.panfilov.geektrust.ledgerco.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import tel.panfilov.geektrust.ledgerco.AbstractLedgerCoTest;
import tel.panfilov.geektrust.ledgerco.model.Loan;
import tel.panfilov.geektrust.ledgerco.model.LoanId;
import tel.panfilov.geektrust.ledgerco.repository.impl.LoanRepositoryImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class LoanRepositoryImplTest extends AbstractLedgerCoTest {

    @InjectMocks
    LoanRepositoryImpl loanRepository;

    @Test
    void emptyRepositoryAssertions() {
        LoanId loanId = loanId("NAB", "Andrey");
        assertThat(loanRepository.findById(loanId)).isNotPresent();
    }

    @Test
    void storeAndQueryLoan() {
        Loan loan = validLoan("NAB", "Andrey");
        loanRepository.save(loan);
        assertThat(loanRepository.findById(loan.getId()))
                .isPresent().get()
                .isEqualTo(loan)
                .isNotSameAs(loan);
    }

    @Test
    void storingDuplicateLoan() {
        Loan first = validLoan("NAB", "Andrey");
        Loan second = validLoan("NAB", "Andrey");
        loanRepository.save(first);
        assertThatThrownBy(() -> loanRepository.save(second))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Loan already exists");
    }

    @Test
    void updatingExistingLoan() {
        Loan first = validLoan("NAB", "Andrey");
        loanRepository.save(first);
        first.addPayment(validPayment(first));
        loanRepository.update(first);
        assertThat(loanRepository.findById(first.getId()))
                .isPresent().get()
                .isEqualTo(first)
                .isNotSameAs(first)
                .satisfies(l -> assertThat(l.getPayments()).isNotEmpty());
    }

}

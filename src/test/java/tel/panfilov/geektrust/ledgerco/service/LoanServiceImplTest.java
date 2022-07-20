package tel.panfilov.geektrust.ledgerco.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tel.panfilov.geektrust.ledgerco.AbstractLedgerCoTest;
import tel.panfilov.geektrust.ledgerco.model.Loan;
import tel.panfilov.geektrust.ledgerco.model.LoanId;
import tel.panfilov.geektrust.ledgerco.model.Payment;
import tel.panfilov.geektrust.ledgerco.model.vo.LoanConditions;
import tel.panfilov.geektrust.ledgerco.model.vo.PaymentSchedule;
import tel.panfilov.geektrust.ledgerco.repository.LoanRepository;
import tel.panfilov.geektrust.ledgerco.service.impl.LoanServiceImpl;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceImplTest extends AbstractLedgerCoTest {

    @Mock
    private LoanRepository loanRepository;

    @InjectMocks
    private LoanServiceImpl loanService;

    Loan validLoan;

    @BeforeEach
    void tearUp() {
        validLoan = validLoan("NAB", "Andrey");
    }

    @Test
    void createLoan() {
        LoanId loanId = validLoan.getId();
        LoanConditions conditions = validLoan.getConditions();
        PaymentSchedule schedule = validLoan.getSchedule();
        Loan loan = createLoan(validLoan);
        assertThat(loan)
                .satisfies(created -> assertThat(created.getId()).isEqualTo(loanId))
                .satisfies(created -> assertThat(created.getConditions()).isEqualTo(conditions))
                .satisfies(created -> assertThat(created.getSchedule()).isEqualTo(schedule))
                .satisfies(created -> assertThat(created.getPayments()).isEmpty());
        verify(loanRepository, times(1)).save(loan);
    }

    @Test
    void failCreateDuplicateLoan() {
        doNothing().
                doThrow(new IllegalArgumentException("Exception anchor"))
                .when(loanRepository).save(validLoan);

        Loan loan = createLoan(validLoan);

        assertThatThrownBy(() -> createLoan(validLoan))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Exception anchor");
        verify(loanRepository, times(2)).save(loan);
    }

    @Test
    void queryExistingLoan() {
        when(loanRepository.findById(validLoan.getId()))
                .thenReturn(Optional.of(validLoan));

        createLoan(validLoan);

        assertThat(loanService.findLoan(validLoan.getId())).isPresent();
        verify(loanRepository, times(1))
                .findById(validLoan.getId());
    }

    @Test
    void findNonExistingLoan() {
        Loan loan = validLoan("NONEXISTING", "NONEXISTING");
        when(loanRepository.findById(loan.getId())).thenReturn(Optional.empty());

        assertThat(loanService.findLoan(loan.getId())).isNotPresent();
        verify(loanRepository, times(1)).findById(loan.getId());
    }

    @Test
    void getNonExistingLoan() {
        Loan loan = validLoan("NONEXISTING", "NONEXISTING");
        when(loanRepository.findById(loan.getId())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> loanService.getLoan(loan.getId()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Loan not found");

        verify(loanRepository, times(1)).findById(loan.getId());
    }

    @Test
    void createPayment() {
        when(loanRepository.findById(validLoan.getId()))
                .thenReturn(Optional.of(validLoan));
        Loan loan = createPayment(validLoan.getId(), BigDecimal.TEN, 1);
        assertThat(loan.getPayments())
                .isNotEmpty().last()
                .satisfies(p -> {
                    assertThat(p.getAmount()).isEqualTo(BigDecimal.TEN);
                    assertThat(p.getPaymentNo()).isEqualTo(1);
                });
    }

    @Test
    void failToAddPaymentExceedingDebt() {
        when(loanRepository.findById(validLoan.getId()))
                .thenReturn(Optional.of(validLoan));
        assertThatThrownBy(() -> createPayment(validLoan.getId(), validLoan.getSchedule().getTotal(), 1))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Lump amount exceeds debt");
    }

    @Test
    void createLoanAssertions() {
        assertThatThrownBy(() -> loanService.createLoan(null, BigDecimal.TEN, 1, BigDecimal.ONE))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Loan id is null");
        assertThatThrownBy(() -> loanService.createLoan(validLoan.getId(), null, 1, BigDecimal.ONE))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Principal is null");
        assertThatThrownBy(() -> loanService.createLoan(validLoan.getId(), BigDecimal.ZERO, 1, BigDecimal.ONE))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Principal must be positive");
        assertThatThrownBy(() -> loanService.createLoan(validLoan.getId(), BigDecimal.TEN, 0, BigDecimal.ONE))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Term must be positive");
        assertThatThrownBy(() -> loanService.createLoan(validLoan.getId(), BigDecimal.TEN, 1, null))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Interest rate is null");
        assertThatThrownBy(() -> loanService.createLoan(validLoan.getId(), BigDecimal.TEN, 1, BigDecimal.ZERO))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Interest rate must be positive");
    }

    @Test
    void createPaymentAssertions() {
        assertThatThrownBy(() -> createPayment(null, new BigDecimal("-2"), 1))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Loan id is null");
        assertThatThrownBy(() -> createPayment(validLoan.getId(), null, 1))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Lump amount is null");
        assertThatThrownBy(() -> createPayment(validLoan.getId(), new BigDecimal("-2"), 1))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Lump amount must be positive");
        assertThatThrownBy(() -> createPayment(validLoan.getId(), BigDecimal.TEN, -2))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Negative Payment No");
    }

    @Test
    void findLoanAssertions() {
        assertThatThrownBy(() -> loanService.findLoan(null))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Loan id is null");
    }

    @Test
    void getBalanceAssertions() {
        assertThatThrownBy(() -> loanService.getBalance(null, 0))
                .isExactlyInstanceOf(IllegalArgumentException.class)
                .hasMessage("Loan id is null");
    }

    @Test
    void checkRoundingMode() {
        Loan loan = loanService.createLoan(new LoanId("NAB", "Andrey"), new BigDecimal("2000"), 2, new BigDecimal("2"));
        when(loanRepository.findById(loan.getId()))
                .thenReturn(Optional.of(loan));

        // 2080 / 24 = 86.6(6)
        assertThat(loan.getSchedule().getMonthlyPayment()).isEqualTo("87");

        assertThat(loanService.getBalance(loan.getId(), 0))
                .satisfies(b -> assertThat(b.getAmountPaid()).isEqualTo("0"))
                .satisfies(b -> assertThat(b.getPaymentsLeft()).isEqualTo(24));

        assertThat(loanService.getBalance(loan.getId(), 24))
                .satisfies(b -> assertThat(b.getAmountPaid()).isEqualTo("2080"))
                .satisfies(b -> assertThat(b.getPaymentsLeft()).isEqualTo(0));

        loan.addPayment(new Payment(20, new BigDecimal("0.01")));
        // 23 * 87 + 0.01
        assertThat(loanService.getBalance(loan.getId(), 23))
                .satisfies(b -> assertThat(b.getAmountPaid()).isEqualTo("2001.01"))
                .satisfies(b -> assertThat(b.getPaymentsLeft()).isEqualTo(1));
    }

    @Test
    void checkBalancePaymentNoOutOfRange() {
        Loan loan = loanService.createLoan(new LoanId("NAB", "Andrey"), new BigDecimal("2000"), 2, new BigDecimal("2"));
        when(loanRepository.findById(loan.getId()))
                .thenReturn(Optional.of(loan));

        int months = loan.getSchedule().getMonths();
        assertThat(loanService.getBalance(loan.getId(), months))
                .satisfies(b -> assertThat(b.getPaymentsLeft()).isEqualTo(0))
                .satisfies(b -> assertThat(b.getAmountPaid()).isEqualTo(loan.getSchedule().getTotal()));

        assertThat(loanService.getBalance(loan.getId(), months * 2))
                .satisfies(b -> assertThat(b.getPaymentsLeft()).isEqualTo(0))
                .satisfies(b -> assertThat(b.getAmountPaid()).isEqualTo(loan.getSchedule().getTotal()));

        assertThat(loanService.getBalance(loan.getId(), 0))
                .satisfies(b -> assertThat(b.getPaymentsLeft()).isEqualTo(loan.getSchedule().getMonths()))
                .satisfies(b -> assertThat(b.getAmountPaid()).isEqualTo(BigDecimal.ZERO));

        assertThatThrownBy(() -> loanService.getBalance(loan.getId(), -1))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Negative Payment No");
    }

    protected Loan createLoan(Loan loan) {
        return loanService.createLoan(
                loan.getId(),
                loan.getConditions().getPrincipal(),
                loan.getConditions().getTerm(),
                loan.getConditions().getInterestRate()
        );
    }

    protected Loan createPayment(LoanId loanId, BigDecimal amount, int paymentNo) {
        return loanService.addPayment(loanId, amount, paymentNo);
    }

}

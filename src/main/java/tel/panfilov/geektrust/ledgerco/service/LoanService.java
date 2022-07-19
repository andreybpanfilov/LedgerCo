package tel.panfilov.geektrust.ledgerco.service;

import tel.panfilov.geektrust.ledgerco.model.Loan;
import tel.panfilov.geektrust.ledgerco.model.LoanId;
import tel.panfilov.geektrust.ledgerco.model.vo.Balance;

import java.math.BigDecimal;
import java.util.Optional;

public interface LoanService {

    Loan createLoan(LoanId id, BigDecimal principal, int term, BigDecimal interestRate);

    Loan addPayment(LoanId loanId, BigDecimal amount, int paymentNo);

    Balance getBalance(LoanId loanId, int paymentNo);

    Optional<Loan> findLoan(LoanId loanId);

    default Loan getLoan(LoanId loanId) {
        return findLoan(loanId)
                .orElseThrow(() -> new IllegalArgumentException("Loan not found"));
    }

}

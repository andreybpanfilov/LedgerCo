package tel.panfilov.geektrust.ledgerco.repository;

import tel.panfilov.geektrust.ledgerco.model.Loan;
import tel.panfilov.geektrust.ledgerco.model.LoanId;

import java.util.Optional;

public interface LoanRepository {

    Optional<Loan> findById(LoanId loanId);

    void save(Loan loan);

    void update(Loan loan);

}

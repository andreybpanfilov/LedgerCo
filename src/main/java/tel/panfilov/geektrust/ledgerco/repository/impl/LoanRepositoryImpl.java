package tel.panfilov.geektrust.ledgerco.repository.impl;

import tel.panfilov.geektrust.ledgerco.model.Loan;
import tel.panfilov.geektrust.ledgerco.model.LoanId;
import tel.panfilov.geektrust.ledgerco.repository.LoanRepository;
import tel.panfilov.geektrust.ledgerco.util.Assert;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LoanRepositoryImpl implements LoanRepository {

    private final Map<LoanId, Loan> loans = new HashMap<>();

    @Override
    public Optional<Loan> findById(LoanId loanId) {
        return Optional.ofNullable(loans.get(loanId));
    }

    public void save(Loan loan) {
        LoanId loanId = loan.getId();
        Assert.isTrue(!loans.containsKey(loanId), "Loan already exists");
        loans.put(loanId, new Loan(loan));
    }

    @Override
    public void update(Loan loan) {
        loans.put(loan.getId(), new Loan(loan));
    }

}

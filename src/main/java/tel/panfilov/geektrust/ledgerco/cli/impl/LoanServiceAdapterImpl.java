package tel.panfilov.geektrust.ledgerco.cli.impl;

import tel.panfilov.geektrust.ledgerco.cli.LoanServiceAdapter;
import tel.panfilov.geektrust.ledgerco.model.Loan;
import tel.panfilov.geektrust.ledgerco.model.LoanId;
import tel.panfilov.geektrust.ledgerco.model.vo.Balance;
import tel.panfilov.geektrust.ledgerco.service.LoanService;

import java.math.BigDecimal;

public class LoanServiceAdapterImpl implements LoanServiceAdapter {

    private final LoanService loanService;

    public LoanServiceAdapterImpl(LoanService loanService) {
        this.loanService = loanService;
    }

    @Override
    public Loan createLoan(String bank, String borrower, String principal, int term, String interestRate) {
        return loanService.createLoan(new LoanId(bank, borrower), new BigDecimal(principal), term, new BigDecimal(interestRate));
    }

    @Override
    public Loan addPayment(String bank, String borrower, String amount, int paymentNo) {
        return loanService.addPayment(new LoanId(bank, borrower), new BigDecimal(amount), paymentNo);
    }

    @Override
    public Balance getBalance(String bank, String borrower, int paymentNo) {
        return loanService.getBalance(new LoanId(bank, borrower), paymentNo);
    }

}

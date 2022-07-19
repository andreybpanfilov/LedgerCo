package tel.panfilov.geektrust.ledgerco.cli;

import tel.panfilov.geektrust.ledgerco.model.Loan;
import tel.panfilov.geektrust.ledgerco.model.vo.Balance;

public interface LoanServiceAdapter {

    Loan createLoan(String bank, String borrower, String principal, int term, String interestRate);

    Loan addPayment(String bank, String borrower, String amount, int paymentNo);

    Balance getBalance(String bank, String borrower, int paymentNo);

}

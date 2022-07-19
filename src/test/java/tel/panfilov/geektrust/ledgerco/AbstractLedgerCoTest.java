package tel.panfilov.geektrust.ledgerco;

import tel.panfilov.geektrust.ledgerco.model.Loan;
import tel.panfilov.geektrust.ledgerco.model.LoanId;
import tel.panfilov.geektrust.ledgerco.model.Payment;
import tel.panfilov.geektrust.ledgerco.model.vo.LoanConditions;
import tel.panfilov.geektrust.ledgerco.model.vo.PaymentSchedule;

import java.math.BigDecimal;

public abstract class AbstractLedgerCoTest {

    protected LoanId loanId(String bankName, String borrowerName) {
        return new LoanId(bankName, borrowerName);
    }

    protected Loan validLoan(String bankName, String borrowerName) {
        return new Loan(loanId(bankName, borrowerName), validConditions(), validSchedule());
    }

    protected LoanConditions validConditions() {
        return new LoanConditions(new BigDecimal("10000"), 5, new BigDecimal("4"));
    }

    protected PaymentSchedule validSchedule() {
        return new PaymentSchedule(new BigDecimal("12000"), 60, new BigDecimal("200"));
    }

    protected Payment validPayment(Loan loan) {
        return new Payment(1, BigDecimal.TEN);
    }

}

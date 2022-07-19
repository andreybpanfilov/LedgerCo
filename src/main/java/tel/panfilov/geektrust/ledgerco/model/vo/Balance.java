package tel.panfilov.geektrust.ledgerco.model.vo;

import tel.panfilov.geektrust.ledgerco.model.LoanId;

import java.math.BigDecimal;
import java.util.Objects;

public class Balance {

    private final LoanId loanId;
    private final BigDecimal amountPaid;
    private final int paymentsLeft;

    public Balance(LoanId loanId, BigDecimal amountPaid, int paymentsLeft) {
        this.loanId = loanId;
        this.amountPaid = amountPaid;
        this.paymentsLeft = paymentsLeft;
    }

    public LoanId getLoanId() {
        return loanId;
    }

    public BigDecimal getAmountPaid() {
        return amountPaid;
    }

    public int getPaymentsLeft() {
        return paymentsLeft;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Balance balance = (Balance) o;
        return getPaymentsLeft() == balance.getPaymentsLeft() && getLoanId().equals(balance.getLoanId()) && getAmountPaid().equals(balance.getAmountPaid());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLoanId(), getAmountPaid(), getPaymentsLeft());
    }

    @Override
    public String toString() {
        return "Balance{" +
                "loanId=" + loanId +
                ", amountPaid=" + amountPaid +
                ", paymentsLeft=" + paymentsLeft +
                '}';
    }

}

package tel.panfilov.geektrust.ledgerco.model;

import java.math.BigDecimal;

public class Payment {

    private final int paymentNo;

    private final BigDecimal amount;

    public Payment(int paymentNo, BigDecimal amount) {
        this.paymentNo = paymentNo;
        this.amount = amount;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public int getPaymentNo() {
        return paymentNo;
    }

    @Override
    @Deprecated
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    @Deprecated
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Payment{" +
                ", paymentNo=" + paymentNo +
                ", amount=" + amount +
                '}';
    }

}

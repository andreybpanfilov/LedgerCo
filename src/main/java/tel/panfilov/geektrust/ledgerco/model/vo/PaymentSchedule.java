package tel.panfilov.geektrust.ledgerco.model.vo;

import java.math.BigDecimal;
import java.util.Objects;

public class PaymentSchedule {

    private final BigDecimal total;

    private final int months;

    private final BigDecimal monthlyPayment;

    public PaymentSchedule(BigDecimal total, int months, BigDecimal monthlyPayment) {
        this.total = total;
        this.months = months;
        this.monthlyPayment = monthlyPayment;
    }

    public int getMonths() {
        return months;
    }

    public BigDecimal getMonthlyPayment() {
        return monthlyPayment;
    }

    public BigDecimal getTotal() {
        return total;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PaymentSchedule that = (PaymentSchedule) o;
        return getMonths() == that.getMonths() && getTotal().equals(that.getTotal()) && getMonthlyPayment().equals(that.getMonthlyPayment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTotal(), getMonths(), getMonthlyPayment());
    }

    @Override
    public String toString() {
        return "PaymentSchedule{" +
                "total=" + total +
                ", months=" + months +
                ", monthlyPayment=" + monthlyPayment +
                '}';
    }

}

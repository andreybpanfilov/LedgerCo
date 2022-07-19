package tel.panfilov.geektrust.ledgerco.model;

import tel.panfilov.geektrust.ledgerco.model.vo.LoanConditions;
import tel.panfilov.geektrust.ledgerco.model.vo.PaymentSchedule;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Loan {

    private final LoanId id;

    private final LoanConditions conditions;

    private final PaymentSchedule schedule;

    private final List<Payment> payments;

    public Loan(Loan loan) {
        this(loan.id, loan.conditions, loan.schedule, loan.payments);
    }

    public Loan(LoanId id, LoanConditions conditions, PaymentSchedule schedule) {
        this(id, conditions, schedule, null);
    }

    public Loan(LoanId id, LoanConditions conditions, PaymentSchedule schedule, List<Payment> payments) {
        this.id = id;
        this.conditions = conditions;
        this.schedule = schedule;
        this.payments = new ArrayList<>();
        if (payments != null) {
            this.payments.addAll(payments);
        }
    }

    public LoanId getId() {
        return id;
    }

    public LoanConditions getConditions() {
        return conditions;
    }

    public PaymentSchedule getSchedule() {
        return schedule;
    }

    public List<Payment> getPayments() {
        return Collections.unmodifiableList(payments);
    }

    public void addPayment(Payment payment) {
        this.payments.add(payment);
    }

    public BigDecimal getLumpSum(int paymentNo) {
        return getPayments().stream()
                .filter(payment -> payment.getPaymentNo() <= paymentNo)
                .map(Payment::getAmount)
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Loan loan = (Loan) o;
        return getId().equals(loan.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Loan{" +
                "id=" + id +
                ", conditions=" + conditions +
                ", schedule=" + schedule +
                '}';
    }
}

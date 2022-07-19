package tel.panfilov.geektrust.ledgerco.model.vo;

import java.math.BigDecimal;
import java.util.Objects;

public class LoanConditions {

    private final BigDecimal principal;

    private final int term;

    private final BigDecimal interestRate;

    public LoanConditions(BigDecimal principal, int term, BigDecimal interestRate) {
        this.principal = principal;
        this.interestRate = interestRate;
        this.term = term;
    }

    public BigDecimal getPrincipal() {
        return principal;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public int getTerm() {
        return term;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoanConditions that = (LoanConditions) o;
        return getTerm() == that.getTerm() && getPrincipal().equals(that.getPrincipal()) && getInterestRate().equals(that.getInterestRate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPrincipal(), getTerm(), getInterestRate());
    }

    @Override
    public String toString() {
        return "LoanConditions{" +
                "principal=" + principal +
                ", term=" + term +
                ", interestRate=" + interestRate +
                '}';
    }

}

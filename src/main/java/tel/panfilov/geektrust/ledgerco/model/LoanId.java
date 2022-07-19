package tel.panfilov.geektrust.ledgerco.model;

import java.util.Objects;

public class LoanId {

    private final String bank;

    private final String borrower;

    public LoanId(String bank, String borrower) {
        this.bank = bank;
        this.borrower = borrower;
    }

    public String getBank() {
        return bank;
    }

    public String getBorrower() {
        return borrower;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoanId loanId = (LoanId) o;
        return bank.equals(loanId.bank) && borrower.equals(loanId.borrower);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bank, borrower);
    }

    @Override
    public String toString() {
        return "LoanId{" +
                "bank='" + bank + '\'' +
                ", borrower='" + borrower + '\'' +
                '}';
    }

}

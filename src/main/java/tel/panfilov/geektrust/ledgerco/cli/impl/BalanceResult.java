package tel.panfilov.geektrust.ledgerco.cli.impl;

import tel.panfilov.geektrust.ledgerco.cli.CommandResult;
import tel.panfilov.geektrust.ledgerco.model.vo.Balance;

public class BalanceResult implements CommandResult {

    private final Balance balance;

    public BalanceResult(Balance balance) {
        this.balance = balance;
    }

    @Override
    public boolean isEmpty() {
        return balance == null;
    }

    @Override
    public boolean isFail() {
        return isEmpty();
    }

    public Balance getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Balance balance = getBalance();
        sb.append(balance.getLoanId().getBank()).append(" ");
        sb.append(balance.getLoanId().getBorrower()).append(" ");
        sb.append(balance.getAmountPaid()).append(" ");
        sb.append(balance.getPaymentsLeft());
        return sb.toString();
    }

}

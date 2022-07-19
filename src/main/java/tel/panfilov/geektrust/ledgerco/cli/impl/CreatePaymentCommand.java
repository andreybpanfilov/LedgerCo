package tel.panfilov.geektrust.ledgerco.cli.impl;

import tel.panfilov.geektrust.ledgerco.cli.Command;
import tel.panfilov.geektrust.ledgerco.cli.CommandResult;
import tel.panfilov.geektrust.ledgerco.cli.LoanServiceAdapter;
import tel.panfilov.geektrust.ledgerco.util.Assert;
import tel.panfilov.geektrust.ledgerco.util.Try;

public class CreatePaymentCommand implements Command {

    private static final int BANK_POSITION = 1;

    private static final int BORROWER_POSITION = 2;

    private static final int AMOUNT_POSITION = 3;

    private static final int PAYMENT_NO_POSITION = 4;

    private static final String NAME = "PAYMENT";

    private final LoanServiceAdapter loanService;

    public CreatePaymentCommand(LoanServiceAdapter loanService) {
        this.loanService = loanService;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Try<CommandResult> execute(String... args) {
        return Try.of(() -> {
            Assert.isTrue(args.length == 5, "Invalid number of arguments");
            loanService.addPayment(
                    args[BANK_POSITION],
                    args[BORROWER_POSITION],
                    args[AMOUNT_POSITION],
                    Integer.parseInt(args[PAYMENT_NO_POSITION])
            );
            return CommandResult.EMPTY;
        });
    }

}

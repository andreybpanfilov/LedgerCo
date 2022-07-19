package tel.panfilov.geektrust.ledgerco.cli.impl;

import tel.panfilov.geektrust.ledgerco.cli.Command;
import tel.panfilov.geektrust.ledgerco.cli.CommandResult;
import tel.panfilov.geektrust.ledgerco.cli.LoanServiceAdapter;
import tel.panfilov.geektrust.ledgerco.util.Assert;
import tel.panfilov.geektrust.ledgerco.util.Try;

public class CreateLoanCommand implements Command {

    private static final int BANK_POSITION = 1;
    private static final int BORROWER_POSITION = 2;

    private static final int PRINCIPAL_POSITION = 3;

    private static final int TERM_POSITION = 4;

    private static final int INTEREST_RATE_POSITION = 5;

    private static final String NAME = "LOAN";

    private final LoanServiceAdapter loanService;

    public CreateLoanCommand(LoanServiceAdapter loanService) {
        this.loanService = loanService;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public Try<CommandResult> execute(String... args) {
        return Try.of(() -> {
            Assert.isTrue(args.length == 6, "Invalid number of arguments");
            loanService.createLoan(
                    args[BANK_POSITION],
                    args[BORROWER_POSITION],
                    args[PRINCIPAL_POSITION],
                    Integer.parseInt(args[TERM_POSITION]),
                    args[INTEREST_RATE_POSITION]
            );
            return CommandResult.EMPTY;
        });
    }

}

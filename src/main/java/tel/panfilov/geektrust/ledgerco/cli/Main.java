package tel.panfilov.geektrust.ledgerco.cli;

import tel.panfilov.geektrust.ledgerco.cli.impl.CommandRegistryImpl;
import tel.panfilov.geektrust.ledgerco.cli.impl.CreateLoanCommand;
import tel.panfilov.geektrust.ledgerco.cli.impl.CreatePaymentCommand;
import tel.panfilov.geektrust.ledgerco.cli.impl.LoanServiceAdapterImpl;
import tel.panfilov.geektrust.ledgerco.cli.impl.RequestBalanceCommand;
import tel.panfilov.geektrust.ledgerco.repository.impl.LoanRepositoryImpl;
import tel.panfilov.geektrust.ledgerco.service.LoanService;
import tel.panfilov.geektrust.ledgerco.service.impl.LoanServiceImpl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        try (InputStream in = Files.newInputStream(Paths.get(args[0]))) {
            main(in, System.out);
        }
    }

    public static void main(InputStream in, OutputStream out) throws IOException {
        BufferedWriter writer = null;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            writer = new BufferedWriter(new OutputStreamWriter(out));
            CommandProcessor commandProcessor = createProcessor();
            commandProcessor.process(reader, writer);
        } finally {
            if (writer != null) {
                writer.flush();
            }
        }
    }

    public static CommandProcessor createProcessor() {
        LoanService loanService = new LoanServiceImpl(new LoanRepositoryImpl());
        LoanServiceAdapter loanServiceAdapter = new LoanServiceAdapterImpl(loanService);
        List<Command> commands = new ArrayList<>();
        commands.add(new CreateLoanCommand(loanServiceAdapter));
        commands.add(new CreatePaymentCommand(loanServiceAdapter));
        commands.add(new RequestBalanceCommand(loanServiceAdapter));
        return new CommandProcessor(new CommandRegistryImpl(commands));
    }

}

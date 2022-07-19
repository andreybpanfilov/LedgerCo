package tel.panfilov.geektrust.ledgerco.cli.impl;

import tel.panfilov.geektrust.ledgerco.cli.CommandResult;

public class CommandFailure implements CommandResult {

    private final int lineNo;

    private final String command;

    private final String cause;

    public CommandFailure(int lineNo, String command, String cause) {
        this.lineNo = lineNo;
        this.command = command;
        this.cause = cause;
    }

    @Override
    public boolean isFail() {
        return true;
    }

    public int getLineNo() {
        return lineNo;
    }

    public String getCommand() {
        return command;
    }

    public String getCause() {
        return cause;
    }

    @Override
    public boolean isEmpty() {
        return cause == null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("ERROR LINE_NO ").append(getLineNo()).append(" ");
        sb.append(getCommand()).append(" ");
        sb.append(getCause());
        return sb.toString();
    }

}

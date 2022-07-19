package tel.panfilov.geektrust.ledgerco.cli;

public interface CommandResult {

    CommandResult EMPTY = new EmptyResult();

    boolean isEmpty();

    boolean isFail();

    class EmptyResult implements CommandResult {

        @Override
        public boolean isEmpty() {
            return true;
        }

        @Override
        public boolean isFail() {
            return false;
        }

    }


}

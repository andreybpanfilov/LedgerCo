package tel.panfilov.geektrust.ledgerco.util;

import java.util.Objects;
import java.util.concurrent.Callable;

public class Try<R> extends Either<Exception, R> {

    protected Try(Exception left, R right) {
        super(left, right);
    }

    public static <R> Try<R> of(Callable<R> callable) {
        Objects.requireNonNull(callable);
        try {
            return success(callable.call());
        } catch (Exception ex) {
            return failure(ex);
        }
    }

    public static <R> Try<R> failure(Exception left) {
        Objects.requireNonNull(left);
        return new Try<>(left, null);
    }

    public static <R> Try<R> success(R right) {
        Objects.requireNonNull(right);
        return new Try<>(null, right);
    }

    public boolean isSuccess() {
        return right != null;
    }

    public boolean isFail() {
        return left != null;
    }

}

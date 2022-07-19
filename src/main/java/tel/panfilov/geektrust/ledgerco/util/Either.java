package tel.panfilov.geektrust.ledgerco.util;

public class Either<L, R> {

    final L left;

    final R right;

    protected Either(L left, R right) {
        this.left = left;
        this.right = right;
    }

    public L getLeft() {
        return left;
    }

    public R getRight() {
        return right;
    }

}

package tel.panfilov.geektrust.ledgerco.util;

public final class Assert {

    private Assert() {

    }

    public static void isTrue(boolean expression, String message) {
        if (!expression) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void hasLength(String str, String message) {
        isTrue(str != null && !str.isEmpty(), message);
    }

}

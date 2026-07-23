public class Error {

    public final java.lang.Error value;

    public Error(String message) {

        value = new java.lang.Error(message);
        ErrorHandler.Throw(this);

    }

    public Error(String message, boolean doNotThrow) {

        value = new java.lang.Error(message);

    }

    @Override
    public String toString() {
        return value.getMessage();
    }

}

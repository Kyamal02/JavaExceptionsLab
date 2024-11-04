package exceptions;

/**
 * Исключение, выбрасываемое при нулевом значении шагов.
 */
public class ZeroStepException extends InvalidStepException {
    public ZeroStepException(String message) {
        super(message);
    }
}

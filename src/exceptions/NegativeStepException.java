package exceptions;

/**
 * Исключение, выбрасываемое при отрицательном значении шагов.
 */
public class NegativeStepException extends InvalidStepException {
    private final String correctedDirection;
    private final int correctedSteps;

    public NegativeStepException(String message, String correctedDirection, int correctedSteps) {
        super(message);
        this.correctedDirection = correctedDirection;
        this.correctedSteps = correctedSteps;
    }

    public String getCorrectedDirection() {
        return correctedDirection;
    }

    public int getCorrectedSteps() {
        return correctedSteps;
    }
}

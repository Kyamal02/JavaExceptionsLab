package exceptions;

/**
 * Исключение, выбрасываемое при превышении максимального допустимого количества шагов.
 */
public class StepLimitExceededException extends InvalidStepException {
    private final int maxAllowedSteps;
    private final int actualSteps;

    public StepLimitExceededException(String message, int maxAllowedSteps, int actualSteps) {
        super(message);
        this.maxAllowedSteps = maxAllowedSteps;
        this.actualSteps = actualSteps;
    }

    public int getMaxAllowedSteps() {
        return maxAllowedSteps;
    }

    public int getActualSteps() {
        return actualSteps;
    }
}

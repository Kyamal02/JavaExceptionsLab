package exceptions;

/**
 * Исключение, возникающее, если шаги не могут быть интерпретированы как допустимое число.
 */
public class InvalidStepException extends RobotException {
    public InvalidStepException(String message) {
        super(message);
    }
}

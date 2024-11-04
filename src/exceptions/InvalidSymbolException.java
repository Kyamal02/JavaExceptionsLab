package exceptions;

/**
 * Исключение, возникающее при использовании недопустимого символа направления.
 */
public class InvalidSymbolException extends RobotException {
    public InvalidSymbolException(String message) {
        super(message);
    }
}

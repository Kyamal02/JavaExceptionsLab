package exceptions;

/**
 * Исключение, возникающее при некорректном формате команды.
 */
public class InvalidCommandFormatException extends RobotException {
    public InvalidCommandFormatException(String message) {
        super(message);
    }
}

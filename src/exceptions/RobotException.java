package exceptions;

/**
 * Базовый класс исключений для событий, связанных с роботом.
 */
public class RobotException extends Exception {
    public RobotException(String message) {
        super(message);
    }
}

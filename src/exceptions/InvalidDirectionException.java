package exceptions;

/**
 * Исключение, возникающее при использовании недопустимого направления.
 */
public class InvalidDirectionException extends InvalidSymbolException {
    public InvalidDirectionException(String message) {
        super(message);
    }
}



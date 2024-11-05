package exceptions;

/**
 * Исключение, возникающее при отсутствии пробела между направлением и шагами.
 */
public class MissingSpaceException extends InvalidCommandFormatException {
    private final String correctedCommand;

    public MissingSpaceException(String message, String correctedCommand) {
        super(message);
        this.correctedCommand = correctedCommand;
    }

    public String getCorrectedCommand() {
        return correctedCommand;
    }
}

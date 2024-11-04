package exceptions;

/**
 * Исключение, возникающее при использовании последовательности из нескольких направлений вместо одного.
 */
public class MultipleDirectionsException extends InvalidDirectionException {
    private final String[] individualDirections;

    /**
     * Конструктор принимает сообщение и массив направлений для выполнения.
     * @param message Сообщение об ошибке.
     * @param individualDirections Массив направлений, на которые следует разделить команду.
     */
    public MultipleDirectionsException(String message, String[] individualDirections) {
        super(message);
        this.individualDirections = individualDirections;
    }

    /**
     * Получить массив отдельных направлений.
     * @return Массив направлений.
     */
    public String[] getIndividualDirections() {
        return individualDirections;
    }
}

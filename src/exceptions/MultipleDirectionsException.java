package exceptions;

/**
 * Исключение, возникающее при использовании последовательности из нескольких направлений вместо одного.
 */
public class MultipleDirectionsException extends InvalidDirectionException {
    private final String[] individualDirections;
    private final int steps;


    /**
     * Конструктор принимает сообщение и массив направлений для выполнения.
     * @param message Сообщение об ошибке.
     * @param individualDirections Массив направлений, на которые следует разделить команду.
     * @param steps Количество шагов.
     */
    public MultipleDirectionsException(String message, String[] individualDirections, int steps) {
        super(message);
        this.individualDirections = individualDirections;
        this.steps = steps;
    }

    /**
     * Получить массив отдельных направлений.
     * @return Массив направлений.
     */
    public String[] getIndividualDirections() {
        return individualDirections;
    }

    /**
     * Получить массив отдельных направлений.
     * @return Получить количество шагов.
     */
    public int getSteps() {
        return steps;
    }
}

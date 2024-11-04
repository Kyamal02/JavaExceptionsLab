package exceptions;

/**
 * Исключение, возникающее при использовании строчной буквы направления.
 */
public class LowercaseDirectionException extends InvalidDirectionException {
    private final String correctedDirection;

    /**
     * Конструктор принимает сообщение и исправленное направление.
     * @param message Сообщение об ошибке
     * @param correctedDirection Исправленное направление (заглавная буква)
     */
    public LowercaseDirectionException(String message, String correctedDirection) {
        super(message);
        this.correctedDirection = correctedDirection;
    }

    /**
     * Метод для получения исправленного направления.
     * @return Исправленное направление
     */
    public String getCorrectedDirection() {
        return correctedDirection;
    }
}
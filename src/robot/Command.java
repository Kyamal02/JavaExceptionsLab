package robot;

/**
 * Класс, представляющий команду для робота.
 */
public class Command {
    private String direction;
    private int steps;

    /**
     * Конструктор команды.
     * @param direction Направление движения (N, S, E, W).
     * @param steps Количество шагов.
     */
    public Command(String direction, int steps) {
        this.direction = direction;
        this.steps = steps;
    }

    // Геттеры для направления и количества шагов
    public String getDirection() {
        return direction;
    }

    public int getSteps() {
        return steps;
    }
}

package robot;

/**
 * Класс, представляющий робота и его перемещения.
 */
public class Robot {
    private int x;
    private int y;

    /**
     * Конструктор без параметров, устанавливает начальные координаты (0, 0).
     */
    public Robot() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Конструктор с заданными координатами.
     * @param x Начальная координата x.
     * @param y Начальная координата y.
     */
    public Robot(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Метод для перемещения робота по команде.
     * @param command Команда для перемещения.
     */
    public void move(Command command) {
        String dir = command.getDirection();
        int steps = command.getSteps();

        switch (dir) {
            case "N":
                y += steps;
                break;
            case "S":
                y -= steps;
                break;
            case "E":
                x += steps;
                break;
            case "W":
                x -= steps;
                break;
            default:
                // Недопустимое направление (будет обработано позже)
                break;
        }
    }

    // Геттеры для координат
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}

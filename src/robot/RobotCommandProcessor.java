package robot;

/**
 * Класс для обработки команд из файла.
 */
public class RobotCommandProcessor {
    /**
     * Метод для разбора строки команды и создания объекта Command.
     * @param line Строка команды.
     * @return Объект Command.
     */
    public Command parseCommand(String line) {
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 2) {
            // Некорректный формат команды (обработка будет добавлена позже)
            return null;
        }

        String direction = parts[0];
        int steps;
        try {
            steps = Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            // Некорректное число шагов (обработка будет добавлена позже)
            return null;
        }

        return new Command(direction, steps);
    }
}

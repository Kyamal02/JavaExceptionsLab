package robot;

import exceptions.InvalidSymbolException;
import exceptions.RobotException;

/**
 * Класс для обработки команд из файла.
 */
public class RobotCommandProcessor {
    /**
     * Метод для разбора строки команды и создания объекта Command.
     * @param line Строка команды.
     * @return Объект Command.
     * @throws RobotException Если команда некорректна.
     */
    public Command parseCommand(String line) throws RobotException {
        // Разбиваем строку на части по пробелам
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 2) {
            // Некорректный формат команды (обработка будет добавлена в следующих заданиях)
            return null;
        }

        String direction = parts[0];
        String stepsStr = parts[1];

        // Проверяем, что направление является одним из разрешённых символов
        if (!direction.equals("N") && !direction.equals("S") &&
                !direction.equals("E") && !direction.equals("W")) {
            // Если направление не является N, S, E или W, выбрасываем исключение
            throw new InvalidSymbolException("Недопустимый символ направления: " + direction);
        }

        // Остальная часть кода будет добавлена позже

        return null; // Временный возврат
    }
}

package robot;

import exceptions.InvalidDirectionException;
import exceptions.InvalidSymbolException;
import exceptions.LowercaseDirectionException;
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

        // Проверяем, что направление состоит из одной буквы
        if (!direction.matches("[a-zA-Z]")) {
            throw new InvalidSymbolException("Недопустимое направление: " + direction);
        }

        // Проверка, если направление — строчная буква (и одна из "n", "s", "e", "w")
        if (direction.matches("[nsew]")) {
            String originalDirection = direction; // Сохраняем исходное направление
            String correctedDirection = direction.toUpperCase(); // Преобразуем в заглавную букву
            throw new LowercaseDirectionException(
                    "Направление '" + originalDirection + "' было преобразовано в '" + correctedDirection + "'",
                    correctedDirection
            );
        }

        // Проверка, если буква не является одной из допустимых заглавных букв
        if (!direction.equals("N") && !direction.equals("S") &&
                !direction.equals("E") && !direction.equals("W")) {
            throw new InvalidDirectionException("Недопустимое направление: " + direction);
        }

        // Остальная часть кода (например, проверка шагов) будет добавлена позже
        return new Command(direction, Integer.parseInt(stepsStr)); // временно возвращаем объект Command
    }
}

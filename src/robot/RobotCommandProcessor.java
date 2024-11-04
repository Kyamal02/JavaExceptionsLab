package robot;

import exceptions.*;

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
            // Некорректный формат команды
            return null;
        }

        String direction = parts[0];
        String stepsStr = parts[1];

        // Проверяем, что шаги представляют собой целое число
        int steps;
        try {
            steps = Integer.parseInt(stepsStr);
        } catch (NumberFormatException e) {
            throw new InvalidStepException("Шаги не являются допустимым числом: " + stepsStr);
        }
        // Проверка на несколько допустимых символов
        if (direction.matches("[NSEW]{2,}")) {
            String[] individualDirections = direction.split("");
            throw new MultipleDirectionsException(
                    "Обнаружено несколько направлений: " + direction,
                    individualDirections
            );
        }

        // Проверяем, что направление состоит из одной буквы
        if (!direction.matches("[a-zA-Z]")) {
            throw new InvalidSymbolException("Недопустимое направление: " + direction);
        }

        // Проверка, если направление — строчная буква (и одна из "n", "s", "e", "w")
        if (direction.matches("[nsew]")) {
            String originalDirection = direction;
            String correctedDirection = direction.toUpperCase();
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


        return new Command(direction, steps);
    }
}

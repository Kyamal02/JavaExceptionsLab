package robot;

import exceptions.*;

/**
 * Класс для обработки команд из файла.
 */
public class RobotCommandProcessor {
    private final int maxSteps; // Значение K

    public RobotCommandProcessor(int maxSteps) {
        this.maxSteps = maxSteps;
    }

    /**
     * Метод для разбора строки команды и создания объекта Command.
     * @param line Строка команды.
     * @return Объект Command.
     * @throws RobotException Если команда некорректна.
     */
    public Command parseCommand(String line) throws RobotException {
        // Удаляем начальные и конечные пробелы и проверяем, что строка не пустая
        line = line.trim();
        if (line.isEmpty()) {
            throw new InvalidCommandFormatException("Пустая строка недопустима как команда.");
        }

        // Проверяем, содержит ли строка пробел
        if (!line.contains(" ")) {
            // Пытаемся разделить строку на направление и шаги без пробела
            String directionPart = line.substring(0, 1);
            String stepsPart = line.substring(1);

            // Проверяем, что направление является буквой
            if (directionPart.matches("[a-zA-Z]") && !stepsPart.isEmpty()) {
                // Проверяем, что шаги являются числом
                try {
                    Integer.parseInt(stepsPart);
                    String correctedCommand = directionPart + " " + stepsPart;
                    throw new MissingSpaceException(
                            "Отсутствует пробел между направлением и шагами в команде: " + line,
                            correctedCommand
                    );
                } catch (NumberFormatException e) {
                    // Шаги не являются числом, выбрасываем обычное исключение
                    throw new InvalidCommandFormatException("Некорректный формат команды: " + line);
                }
            } else {
                throw new InvalidCommandFormatException("Некорректный формат команды: " + line);
            }
        }

        // Разбиваем строку на части по пробелам
        String[] parts = line.split("\\s+");
        if (parts.length != 2) {
            // Некорректный формат команды
            throw new InvalidCommandFormatException("Некорректный формат команды: " + line);
        }

        String direction = parts[0];
        String stepsStr = parts[1];

        int steps;
        try {
            steps = Integer.parseInt(stepsStr);
        } catch (NumberFormatException e) {
            throw new InvalidStepException("Шаги не являются допустимым числом: " + stepsStr);
        }
        if (Math.abs(steps) > maxSteps) {
            throw new StepLimitExceededException(
                    "Количество шагов превышает максимально допустимое значение: " + steps,
                    maxSteps,
                    steps
            );
        }

        // Проверка на нулевые шаги
        if (steps == 0) {
            throw new ZeroStepException("Количество шагов не может быть нулевым.");
        }

        // Проверка на несколько допустимых символов
        if (direction.matches("[NSEW]{2,}")) {
            String[] individualDirections = direction.split("");
            throw new MultipleDirectionsException(
                    "Обнаружено несколько направлений: " + direction,
                    individualDirections,
                    steps
            );
        }

        // Проверяем, что направление состоит из одной буквы
        if (!direction.matches("[a-zA-Z]")) {
            throw new InvalidSymbolException("Недопустимое направление: " + direction);
        }

        // Проверка на строчные буквы направления
        if (direction.matches("[nsew]")) {
            String correctedDirection = direction.toUpperCase();
            throw new LowercaseDirectionException(
                    "Направление '" + direction + "' было преобразовано в '" + correctedDirection + "'",
                    correctedDirection
            );
        }

        // Проверка на недопустимые заглавные буквы
        if (!direction.equals("N") && !direction.equals("S") &&
                !direction.equals("E") && !direction.equals("W")) {
            throw new InvalidDirectionException("Недопустимое направление: " + direction);
        }

        // Проверка на отрицательные шаги
        if (steps < 0) {
            String correctedDirection;
            switch (direction) {
                case "N":
                    correctedDirection = "S";
                    break;
                case "S":
                    correctedDirection = "N";
                    break;
                case "E":
                    correctedDirection = "W";
                    break;
                case "W":
                    correctedDirection = "E";
                    break;
                default:
                    throw new InvalidDirectionException("Недопустимое направление: " + direction);
            }
            throw new NegativeStepException(
                    "Отрицательные шаги преобразованы: " + direction + " " + steps + " -> " + correctedDirection + " " + (-steps),
                    correctedDirection,
                    -steps
            );
        }

        return new Command(direction, steps);
    }

}

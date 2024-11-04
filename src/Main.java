import exceptions.InvalidDirectionException;
import exceptions.InvalidSymbolException;
import exceptions.LowercaseDirectionException;
import exceptions.RobotException;
import robot.Robot;
import robot.Command;
import robot.RobotCommandProcessor;

import java.io.*;

/**
 * Основной класс программы.
 */
public class Main {
    public static void main(String[] args) {
        // Файлы для чтения команд и записи результатов
        String inputFileName = "files/commands.txt";
        String outputFileName = "files/result.txt";

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))
        ) {
            // Чтение начальных координат робота из первой строки файла
            String initialPositionLine = reader.readLine();
            int x = 0;
            int y = 0;
            if (initialPositionLine != null && !initialPositionLine.isEmpty()) {
                // Парсинг координат, если они заданы
                String[] coords = initialPositionLine.trim().split("\\s+");
                if (coords.length == 2) {
                    x = Integer.parseInt(coords[0]);
                    y = Integer.parseInt(coords[1]);
                }
            }

            // Создание робота с начальными координатами
            Robot robot = new Robot(x, y);
            RobotCommandProcessor processor = new RobotCommandProcessor();

            String line;
            while ((line = reader.readLine()) != null) {
                // Проверяем на команду завершения ("Q" или "X" завершат выполнение команд)
                if (line.equals("Q") || line.equals("X")) {
                    break;
                }

                try {
                    // Парсинг команды и её выполнение
                    Command command = processor.parseCommand(line);
                    robot.move(command); // Выполняем перемещение робота
                    writer.write("Команда выполнена: " + line + "\n");
                    writer.write("Положение робота: (" + robot.getX() + ", " + robot.getY() + ")\n");

                } catch (LowercaseDirectionException e) {
                    // Обработка случая, когда направление указано строчной буквой
                    String correctedDirection = e.getCorrectedDirection();
                    int steps = Integer.parseInt(line.split("\\s+")[1]); // Извлекаем количество шагов
                    Command correctedCommand = new Command(correctedDirection, steps);

                    writer.write("Исправление команды: " + line + " на " + correctedDirection + "\n");
                    robot.move(correctedCommand); // Выполняем команду с корректировкой
                    writer.write("Команда выполнена (после исправления): " + correctedCommand.getDirection() + " " + correctedCommand.getSteps() + "\n");
                    writer.write("Положение робота: (" + robot.getX() + ", " + robot.getY() + ")\n");

                } catch (InvalidDirectionException e) {
                    // Обработка некорректного направления
                    writer.write("Ошибка при выполнении команды: " + line + "\n");
                    writer.write("Описание ошибки: " + e.getMessage() + "\n");
                    throw e;

                } catch (InvalidSymbolException e) {
                    // Обработка неверного символа в команде
                    writer.write("Ошибка при выполнении команды: " + line + "\n");
                    writer.write("Описание ошибки: " + e.getMessage() + "\n");
                    throw e;

                } catch (RobotException e) {
                    // Обработка других ошибок, связанных с командой
                    writer.write("Ошибка при выполнении команды: " + line + "\n");
                    writer.write("Описание ошибки: " + e.getMessage() + "\n");
                    throw e;
                }

                writer.write("\n");
            }

            // Запись конечного положения робота
            writer.write("Конечное положение робота: (" + robot.getX() + ", " + robot.getY() + ")\n");

        } catch (Exception e) {
            // Обработка всех непредвиденных исключений, например, проблем с чтением файла
            e.printStackTrace();
        }
    }
}

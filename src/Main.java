import exceptions.InvalidDirectionException;
import exceptions.InvalidSymbolException;
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
        String inputFileName = "files/commands.txt";
        String outputFileName = "files/result.txt";

        try (
                BufferedReader reader = new BufferedReader(new FileReader(inputFileName));
                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))
        ) {
            // Чтение начальных координат
            String initialPositionLine = reader.readLine();
            int x = 0;
            int y = 0;
            if (initialPositionLine != null && !initialPositionLine.isEmpty()) {
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
                // Проверяем на команду завершения
                if (line.equals("Q") || line.equals("X")) {
                    break;
                }

                try {
                    // Обработка команды
                    Command command = processor.parseCommand(line);
                    writer.write("Команда выполнена: " + line + "\n");
                    writer.write("Положение робота: (" + robot.getX() + ", " + robot.getY() + ")\n");

                } catch (InvalidDirectionException e) {
                    // Обработка исключения InvalidDirectionException
                    writer.write("Ошибка при выполнении команды: " + line + "\n");
                    writer.write("Описание ошибки: " + e.getMessage() + "\n");
                    throw e;

                } catch (InvalidSymbolException e) {
                    // Обработка исключения InvalidSymbolException
                    writer.write("Ошибка при выполнении команды: " + line + "\n");
                    writer.write("Описание ошибки: " + e.getMessage() + "\n");
                    throw e;
                } catch (RobotException e) {
                    // Обработка других исключений RobotException
                    writer.write("Ошибка при выполнении команды: " + line + "\n");
                    writer.write("Описание ошибки: " + e.getMessage() + "\n");
                    throw e;
                }

                writer.write("\n");
            }

            writer.write("Конечное положение робота: (" + robot.getX() + ", " + robot.getY() + ")\n");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

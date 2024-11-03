package main;

import robot.Robot;
import robot.Command;
import robot.RobotCommandProcessor;

import java.io.*;

/**
 * Основной класс программы.
 */
public class Main {
    public static void main(String[] args) {
        String inputFileName = "commands.txt";
        String outputFileName = "result.txt";

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
                if (line.equalsIgnoreCase("Q") || line.equalsIgnoreCase("X")) {
                    break;
                }

                // Обработка команды (обработка исключений будет добавлена позже)
                Command command = processor.parseCommand(line);
                if (command != null) {
                    robot.move(command);
                    writer.write("Команда выполнена: " + line + "\n");
                    writer.write("Положение робота: (" + robot.getX() + ", " + robot.getY() + ")\n");
                } else {
                    writer.write("Некорректная команда: " + line + "\n");
                }
                writer.write("\n");
            }

            writer.write("Конечное положение робота: (" + robot.getX() + ", " + robot.getY() + ")\n");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

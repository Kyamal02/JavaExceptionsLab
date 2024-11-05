import exceptions.*;
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

            Robot robot = new Robot(x, y);
            //задаем K
            RobotCommandProcessor processor = new RobotCommandProcessor(10);
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.equals("Q") || line.equals("X")) {
                    break;
                }

                try {
                    // Парсинг команды и её выполнение
                    Command command = processor.parseCommand(line);
                    robot.move(command);
                    writer.write("Команда выполнена: " + line + "\n");
                    writer.write("Положение робота: (" + robot.getX() + ", " + robot.getY() + ")\n");
                } catch (MissingSpaceException e) {
                    String correctedLine = e.getCorrectedCommand();
                    writer.write("Исправление команды из-за отсутствия пробела: " + line + " на " + correctedLine + "\n");
                    try {
                        // Повторный парсинг с исправленной командой
                        Command correctedCommand = processor.parseCommand(correctedLine);
                        robot.move(correctedCommand);
                        writer.write("Команда выполнена (после исправления): " + correctedCommand.getDirection() + " "
                                + correctedCommand.getSteps() + "\n");
                        writer.write("Положение робота: (" + robot.getX() + ", " + robot.getY() + ")\n");
                    } catch (RobotException ex) {
                        writer.write("Ошибка при обработке исправленной команды: " + correctedLine + "\n");
                        writer.write("Описание: " + ex.getMessage() + "\n");
                        throw ex;
                    }

                } catch (InvalidCommandFormatException e) {
                    writer.write("Некорректный формат команды: " + line + "\n");
                    writer.write("Описание: " + e.getMessage() + "\n");

                } catch (ZeroStepException e) {
                    writer.write("Команда пропущена (нулевые шаги): " + line + "\n");
                    writer.write("Описание: " + e.getMessage() + "\n");
                    // Команда не выполняется и удаляется из списка (ничего не делаем)

                } catch (StepLimitExceededException e) {
                    writer.write("Команда не выполнена: " + line + "\n");
                    writer.write("Описание: " + e.getMessage() + "\n");
                    // Команда не выполняется

                } catch (LowercaseDirectionException e) {
                    String correctedDirection = e.getCorrectedDirection();
                    int steps = Integer.parseInt(line.split("\\s+")[1]);
                    Command correctedCommand = new Command(correctedDirection, steps);
                    writer.write("Исправление команды: " + line + " на " + correctedDirection + "\n");
                    robot.move(correctedCommand);
                    writer.write("Команда выполнена (после исправления): " + correctedCommand.getDirection() + " " + correctedCommand.getSteps() + "\n");
                    writer.write("Положение робота: (" + robot.getX() + ", " + robot.getY() + ")\n");

                } catch (NegativeStepException e) {
                    String correctedDirection = e.getCorrectedDirection();
                    int correctedSteps = e.getCorrectedSteps();
                    Command correctedCommand = new Command(correctedDirection, correctedSteps);
                    writer.write("Исправление команды: " + line + " на " + correctedDirection + " " + correctedSteps + "\n");
                    robot.move(correctedCommand);
                    writer.write("Команда выполнена (после исправления): " + correctedCommand.getDirection() + " " + correctedCommand.getSteps() + "\n");
                    writer.write("Положение робота: (" + robot.getX() + ", " + robot.getY() + ")\n");

                } catch (MultipleDirectionsException e) {
                    String[] directions = e.getIndividualDirections();
                    int steps = e.getSteps();
                    writer.write("Обнаружено несколько направлений в команде: " + line + "\n");
                    for (String dir : directions) {
                        int currentSteps = steps;
                        String currentDir = dir;

                        // Обработка отрицательных шагов
                        if (currentSteps < 0) {
                            String correctedDirection;
                            switch (currentDir) {
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
                                    writer.write("Недопустимое направление: " + currentDir + "\n");
                                    continue;
                            }
                            currentSteps = -currentSteps;
                            writer.write("Отрицательные шаги преобразованы: " + currentDir + " " + (-steps) + "" +
                                    " -> " + correctedDirection + " " + currentSteps + "\n");
                            currentDir = correctedDirection;
                        }

                        Command splitCommand = new Command(currentDir, currentSteps);
                        robot.move(splitCommand);
                        writer.write("Команда выполнена: " + currentDir + " " + currentSteps + "\n");
                        writer.write("Положение робота: (" + robot.getX() + ", " + robot.getY() + ")\n");
                    }
                } catch (InvalidStepException e) {
                    writer.write("Ошибка при выполнении команды: " + line + "\n");
                    writer.write("Описание ошибки: " + e.getMessage() + "\n");
                    throw e;

                } catch (InvalidDirectionException e) {
                    writer.write("Ошибка при выполнении команды: " + line + "\n");
                    writer.write("Описание ошибки: " + e.getMessage() + "\n");
                    throw e;

                } catch (InvalidSymbolException e) {
                    writer.write("Ошибка при выполнении команды: " + line + "\n");
                    writer.write("Описание ошибки: " + e.getMessage() + "\n");
                    throw e;

                } catch (RobotException e) {
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

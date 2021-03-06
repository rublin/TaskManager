package org.rublin;

import org.rublin.command.Operation;
import org.rublin.repository.JdbcRepository;
import org.rublin.repository.TaskRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Communicate with user using console.
 *
 * @author Ruslan Sheremet
 * @since 1.0
 */
public class ConsoleHelper {
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
    private static final String CHOOSE_OPERATION = "Please choose an operation";
    private static final String ADD_TASK = "1 - Add new task";
    private static final String SHOW_ACTIVE_TASK = "2 - Show tasks";
    private static final String CLOSE_TASK = "3 - Close task";
    private static final String SHOW_CLOSED_TASK = "4 - Show closed tasks";
    private static final String RETURN = "5 - Return to main menu";
    private static final String EXIT = "0 - Exit";

    private static final String INVALID = "Please specify valid data";
    private static final String OK = "Operation successful";
    private static final String FAIL = "Operation failed";

    public static TaskRepository repository = JdbcRepository.getRepository();

    private static BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    public static void writeMessage(String message) {
        System.out.println(message);
    }
    public static String readString() {
        String line = "";
        try {
            line = reader.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    public static Operation mainMenu() {
        do {
            writeMessage("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            writeMessage(CHOOSE_OPERATION);
            writeMessage("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            writeMessage(ADD_TASK);
            writeMessage(SHOW_ACTIVE_TASK);
            writeMessage("");
            writeMessage(EXIT);
            writeMessage("~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            try {
                int command = getValidInt();
                if (command >=0 && command <= 2)
                    return Operation.getOperationByOrdinal(command);
                else
                    throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                writeMessage(INVALID);
            }
        } while (true);
    }
    public static Operation taskMenu() {
        do {
            writeMessage("\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            writeMessage(CHOOSE_OPERATION);
            writeMessage("~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
            writeMessage(CLOSE_TASK);
            writeMessage(SHOW_CLOSED_TASK);
            writeMessage(RETURN);
            writeMessage("~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
            try {
                int command = getValidInt();
                if (command >=3 && command <= 5)
                    return Operation.getOperationByOrdinal(command);
                else
                    throw new IllegalArgumentException();
            } catch (IllegalArgumentException e) {
                writeMessage(INVALID);
            }
        } while (true);

    }

    public static int getValidInt() {
        do {
            String result = readString();
            if (result.matches("\\d+"))
                return Integer.valueOf(result);
            else
                writeInvalidWarning();
        } while (true);
    }

    public static LocalDateTime getValidDateTime() {
        do {
            writeMessage("Date and Time (for example, 30.01.2016 15:03): ");
            String result = readString();
            if (result.matches("\\d{2}\\.\\d{2}\\.\\d{4}\\s\\d{2}\\:\\d{2}"))
                return LocalDateTime.parse(result, FORMATTER);
            else
                writeInvalidWarning();
        } while (true);
    }

    public static String getValidPriority() {
        do {
            writeMessage("Priority (high, middle, low): ");
            String result = readString();
            if (result.toUpperCase().matches("(HIGH|MIDDLE|LOW)"))
                return result.toUpperCase();
            else
                writeInvalidWarning();
        } while (true);
    }

    public static void writeInvalidWarning() {
        writeMessage(INVALID);
    }

    public static String getValidDescription() {
        writeMessage("Description: ");
        return ConsoleHelper.readString();
    }

    public static void writeOperationOkMessage() {
        writeMessage(OK);
    }

    public static void writeOperationFailedMessage() {
        writeMessage(FAIL);
    }
}

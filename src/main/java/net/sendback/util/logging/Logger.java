package net.sendback.util.logging;

import net.sendback.Main;
import net.sendback.util.files.FileWriterUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Logger {
    private static final ArrayList<String> logs = new ArrayList<>();

    private static final FileWriterUtil logfile = new FileWriterUtil(Main.getPath().toString() + "/logs/" + LocalDate.now() + ".log");

    public static void log(String message) {
        log(message, LogType.INFO);
    }

    public static void log(String message, LogType type) {
        String formattedLog = formatLog(message, type);
        logs.add(formattedLog);
        System.out.println(formattedLog);
        logfile.appendLine(formattedLog);
    }

    public static void log(Exception error) {
        String stacktrace = buildStackTrace(error);
        String formattedLog = formatLog(stacktrace, LogType.ERROR);
        logs.add(formattedLog);
        System.out.println(formattedLog);
        logfile.appendLine(formattedLog);
    }

    private static String formatLog(String message, LogType type) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        return String.format("[%s | %s] %s", time, type, message);
    }

    public static String buildStackTrace(Exception error) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        StringBuilder stacktrace = new StringBuilder();
        stacktrace.append(String.format("%s: %s\n", error.getClass().getSimpleName(), error.getMessage()));
        for(StackTraceElement element : error.getStackTrace()) {
            stacktrace.append("\t").append(element.toString());
            if(element != error.getStackTrace()[error.getStackTrace().length - 1]) {
                stacktrace.append("\n");
            }
        }
        return stacktrace.toString();
    }

    public static ArrayList<String> getLogs() {
        return logs;
    }
}

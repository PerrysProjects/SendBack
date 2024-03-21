package net.gts_projekt.util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Logger {
    private static final ArrayList<String> logs = new ArrayList<>();

    public static void log(String log, LogType type) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        log = String.format("[%s | %s] %s", time, type, log);
        logs.add(log);
        System.out.println(log);
    }

    public static void log(Exception error) {
        String time = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
        String stacktrace = String.valueOf(error.getStackTrace()[0]);
        for(int i = 1; i < error.getStackTrace().length; i++) {
            stacktrace += "\n" + error.getStackTrace()[i];
        }
        String log = String.format("[%s | %s] %s", time, LogType.ERROR, stacktrace);
        logs.add(log);
        System.out.println(log);
    }
}

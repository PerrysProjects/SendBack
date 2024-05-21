package net.sendback.util;

public enum OperatingSystem {
    WINDOWS,
    MACOS,
    LINUX,
    UNKNOWN;

    public static OperatingSystem checkOS(String os) {
        OperatingSystem system = switch(os) {
            case "windows" -> WINDOWS;
            case "mac os x", "darwin" -> MACOS;
            case "linux" -> LINUX;
            default -> UNKNOWN;
        };

        Logger.log("Running on " + system + "!");
        return system;
    }
}

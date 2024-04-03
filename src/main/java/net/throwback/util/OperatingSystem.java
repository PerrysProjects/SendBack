package net.throwback.util;

public enum OperatingSystem {
    WINDOWS,
    MACOS,
    LINUX,
    UNKNOWN;

    public static OperatingSystem checkOS(String os) {
        return switch(os) {
            case "windows" -> WINDOWS;
            case "mac os x", "darwin" -> MACOS;
            case "linux" -> LINUX;
            default -> UNKNOWN;
        };
    }
}

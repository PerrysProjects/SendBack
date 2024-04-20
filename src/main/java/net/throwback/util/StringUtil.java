package net.throwback.util;

public class StringUtil {
    public static String capitalize(String str) {
        if(str == null || str.isEmpty()) {
            return str;
        }
        return Character.toUpperCase(str.charAt(0)) + str.substring(1);
    }
}

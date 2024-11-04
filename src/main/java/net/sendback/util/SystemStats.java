package net.sendback.util;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

public class SystemStats {
    public static String getMemoryUsage() {
        long usedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long maxMemory = Runtime.getRuntime().maxMemory();
        return String.format("RAM: %d MB / %d MB", usedMemory / (1024 * 1024), maxMemory / (1024 * 1024));
    }

    public static String getCpuUsage() {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
        double cpuLoad = osBean.getSystemCpuLoad() * 100;
        return String.format("CPU: %.1f%%", cpuLoad);
    }
}


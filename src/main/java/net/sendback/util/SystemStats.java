package net.sendback.util;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class SystemStats {
    public static String getMemoryUsage() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryBean.getNonHeapMemoryUsage();

        long usedHeapMemory = heapMemoryUsage.getUsed();
        long maxHeapMemory = heapMemoryUsage.getMax();
        long usedNonHeapMemory = nonHeapMemoryUsage.getUsed();
        long maxNonHeapMemory = nonHeapMemoryUsage.getMax();

        return String.format("Heap Memory: %d MB / %d MB\n" +
                        "Non-Heap Memory: Used: %d MB, Max: %d MB",
                bytesToMegabytes(usedHeapMemory), bytesToMegabytes(maxHeapMemory),
                bytesToMegabytes(usedNonHeapMemory), bytesToMegabytes(maxNonHeapMemory));
    }

    public static String getCpuUsage() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        if (osBean != null) {

            double cpuLoad = osBean.getCpuLoad(); // Returns a value between 0.0 and 1.0
            return String.format("CPU Usage: %.2f%%", cpuLoad * 100);
        } else {
            return "CPU Usage information not available on this operating system.";
        }
    }

    private static long bytesToMegabytes(long bytes) {
        return bytes / (1024 * 1024);
    }
}


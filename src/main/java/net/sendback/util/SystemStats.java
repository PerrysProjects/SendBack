package net.sendback.util;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;

public class SystemStats {
    private static double cpuSave;

    public static String[] getMemoryUsage() {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();
        MemoryUsage heapMemoryUsage = memoryBean.getHeapMemoryUsage();
        MemoryUsage nonHeapMemoryUsage = memoryBean.getNonHeapMemoryUsage();

        long usedHeapMemory = heapMemoryUsage.getUsed();
        long maxHeapMemory = heapMemoryUsage.getMax();
        long usedNonHeapMemory = nonHeapMemoryUsage.getUsed();
        long maxNonHeapMemory = nonHeapMemoryUsage.getMax();

        return new String[]{String.format("HM: %dMB / %dMB", bytesToMegabytes(usedHeapMemory), bytesToMegabytes(maxHeapMemory)),
                String.format("NHM: %dMB / %dMB", bytesToMegabytes(usedNonHeapMemory), bytesToMegabytes(maxNonHeapMemory))};
    }

    public static String getCpuUsage() {
        OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        if(osBean != null) {
            double cpuLoad = osBean.getCpuLoad();
            if(!Double.isNaN(cpuLoad)) {
                cpuSave = cpuLoad;
                return String.format("CPU: %.2f%%", cpuLoad * 100);
            } else {
                return String.format("CPU: %.2f%%", cpuSave * 100);
            }
        } else {
            return "CPU: -";
        }
    }

    private static long bytesToMegabytes(long bytes) {
        return bytes / (1024 * 1024);
    }
}


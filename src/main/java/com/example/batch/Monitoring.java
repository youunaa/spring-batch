package com.example.batch;

import com.sun.management.OperatingSystemMXBean;

import java.lang.management.ManagementFactory;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Monitoring {

    public static void main(String[] args) {
        SimpleDateFormat fm = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
        Date time = new Date();

        try {
            OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

            for (int i = 0; i < 100; i++) {
                System.out.println("***********************************************************");
                System.out.println(System.currentTimeMillis());
                System.out.println("CPU Usage : " + String.format("%.2f", osBean.getSystemCpuLoad() * 100));
                System.out.println("Memory Free Space : " + String.format("%.2f", (double) osBean.getFreePhysicalMemorySize() / 1024 / 1024 / 1024));
                System.out.println("Memory Total Space : " + String.format("%.2f", (double) osBean.getTotalPhysicalMemorySize() / 1024 / 1024 / 1024));

                Thread.sleep(3000);
            }

        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

}

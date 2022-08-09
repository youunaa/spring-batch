package com.example.batch;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

@SpringBootTest
class BatchApplicationTests {

    @Test
    void contextLoads() {
        System.out.println("getDiskSpace()[1] = " + getDiskSpace()[1]);
    }


    /**
     * 디스크 용량
     */
    public String[] getDiskSpace() {
        File root = null;
        try {
            root = new File(File.pathSeparator);
            String[] list = new String[2];
            list[0] = toMB(root.getTotalSpace());
            list[1] = toMB(root.getUsableSpace());
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String[] getCPUProcess() {
        OperatingSystemMXBean osmx = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
        operatingSystemMXBean.getSystemCpuLoad();

        return list;
    }

    public String toMB(long size) {
        return String.valueOf((int) (size / (1024 * 1024)));
    }

}

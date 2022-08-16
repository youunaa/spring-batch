package com.example.batch.websocket;

import com.example.batch.metric.model.Metric;
import com.example.batch.metric.model.MetricType;
import com.example.batch.websocket.model.Socket;
import com.sun.management.OperatingSystemMXBean;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import java.lang.management.ManagementFactory;

@Controller
public class WebSocketController {

    /**
     *
     * @param socketVO
     * @return
     */
    @SendTo("/send")
    @MessageMapping("/receive")
    public Metric SocketHandler(Socket socketVO) {
        OperatingSystemMXBean osBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);

        String cpuVal = String.format("%.2f", osBean.getSystemCpuLoad() * 100);
        String memorySize = String.format("%.2f", (double) osBean.getFreePhysicalMemorySize() / 1024 / 1024 / 1024);
        String memoryTotal = String.format("%.2f", (double) osBean.getTotalPhysicalMemorySize() / 1024 / 1024 / 1024);

        Metric metric = Metric.builder()
                .cpuVal(cpuVal)
                .memorySize(memorySize)
                .memoryTotal(memoryTotal)
                .build();

        return metric;
    }

}

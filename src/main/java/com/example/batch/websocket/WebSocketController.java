package com.example.batch.websocket;

import com.example.batch.metric.model.Metric;
import com.example.batch.metric.model.MetricType;
import com.example.batch.redis.RedisService;
import com.example.batch.websocket.model.Socket;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@RequiredArgsConstructor
@Controller
public class WebSocketController {

    private final RedisService redisService;

    /**
     * @param socketVO
     * @return
     */
    @SendTo("/send")
    @MessageMapping("/receive")
    public Metric SocketHandler(Socket socketVO) {
        Metric metric = Metric.builder()
                .cpuVal(redisService.getRedisKey(MetricType.CPU.name()))
                .memorySize(redisService.getRedisKey(MetricType.MemorySize.name()))
                .memoryTotal(redisService.getRedisKey(MetricType.MemoryTotal.name()))
                .build();
        return metric;
    }

}

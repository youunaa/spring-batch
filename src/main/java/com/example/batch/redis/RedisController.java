package com.example.batch.redis;

import com.example.batch.base.BaseController;
import com.example.batch.base.BaseModel;
import com.example.batch.base.BodyModel;
import com.example.batch.metric.model.MetricModel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("redis")
@RestController
public class RedisController extends BaseController {

    private final RedisService redisService;

    /**
     * Redis 저장
     * @param param
     * @return
     */
    @PostMapping("/add")
    public BaseModel addRedisKey(@RequestBody Map<String, Object> param) {
        BodyModel body = new BodyModel();
        MetricModel model = MetricModel.builder()
                .type(param.get("key").toString())
                .value(param.get("value").toString())
                .build();
        redisService.addRedisKey(model);
        return ok(body);
    }

    /**
     * Redis 조회
     * @param key
     * @return
     */
    @GetMapping("/{key}")
    public BaseModel getRedisKey(@PathVariable String key) {
        BodyModel body = new BodyModel();
        body.setBody(redisService.getRedisKey(key));
        return ok(body);
    }

    /**
     * Redis List 조회
     * @param key
     * @return
     */
    @GetMapping("/list/{key}")
    public BaseModel getRedisList(@PathVariable String key) {
        BodyModel body = new BodyModel();
        body.setBody(redisService.getRedisList(key));
        return ok(body);
    }

}

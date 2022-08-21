package com.example.batch.redis;

import com.example.batch.base.BaseController;
import com.example.batch.base.BaseModel;
import com.example.batch.base.BodyModel;
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
     * Redis key-value 저장
     * @param param
     * @return
     */
    @PostMapping("/add")
    public BaseModel addRedisKey(@RequestBody Map<String, Object> param) {
        BodyModel body = new BodyModel();
        redisService.addRedisKey(param.get("key").toString(), param.get("value"));
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

}

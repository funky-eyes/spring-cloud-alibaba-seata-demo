package icu.funkye.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.seata.spring.annotation.GlobalTransactional;

@Service
public class DemoService {
    @Autowired
    ITestService testService;
    private static final Map<String, Object> cacheResult = new ConcurrentHashMap<>();

    @GlobalTransactional
    public void commit() {
        testService.commit();
        throw new RuntimeException();
    }

    @GlobalTransactional
    public void rollback() {
        testService.commit();
        throw new RuntimeException();
    }

    @GlobalTransactional
    public void commitCache(String uuid) {
        cacheResult.put(uuid, "请稍等");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        testService.commit();
        cacheResult.put(uuid, "下单成功");
    }

    public Object getCache(String uuid) {
        return cacheResult.get(uuid);
    }

}

package icu.funkye.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import icu.funkye.service.ITestService;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author funkye
 * @since 2019-03-20
 */
@RestController
@RequestMapping("/test")
public class TestController {
    @Autowired
    private ITestService testService;
    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    /**
     * 测试提交分布式事务接口
     * 
     * @return
     */
    @GetMapping(value = "seataCommit")
    @GlobalTransactional
    public Object seataCommit() {
        testService.commit();
        int i=1/0;
        return true;
    }

}

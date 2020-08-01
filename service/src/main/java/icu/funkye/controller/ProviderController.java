package icu.funkye.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import icu.funkye.entity.Test;
import icu.funkye.service.ITestService;
import io.seata.core.context.RootContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProviderController {
    private final static Logger logger = LoggerFactory.getLogger(ProviderController.class);
    @Autowired
    ITestService testService;

    @RequestMapping("/commit")
    public Object commit() {
        logger.info("全局事务id:{}", RootContext.getXID());
        return testService.Commit();
    }

    @RequestMapping("/commitById")
    @Transactional
    public Object commitById(@RequestParam(name = "id")Long id) {
        logger.info("全局事务id:{}", RootContext.getXID());
        Test t = testService.getOne(Wrappers.<Test>query().select("id", "two").eq("id", id).last("for update"));
        t.setTwo(t.getTwo() + 1);
        testService.updateById(t);
        return true;
    }
}

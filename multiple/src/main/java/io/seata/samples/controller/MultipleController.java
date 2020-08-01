package io.seata.samples.controller;

import io.seata.core.exception.TransactionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.seata.core.context.RootContext;
import io.seata.samples.service.DemoService;

@RestController
public class MultipleController {
    private final static Logger logger = LoggerFactory.getLogger(MultipleController.class);
    @Autowired
    DemoService demoService;

    @RequestMapping("/commit")
    public Object commit() throws TransactionException {
        logger.info("全局事务id:{}", RootContext.getXID());
        return demoService.testCommit();
    }
}

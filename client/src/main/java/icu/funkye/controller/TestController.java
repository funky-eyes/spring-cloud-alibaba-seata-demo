package icu.funkye.controller;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import icu.funkye.entity.Account;
import icu.funkye.entity.Orders;
import icu.funkye.entity.Product;
import icu.funkye.service.DemoService;
import icu.funkye.service.IAccountService;
import icu.funkye.service.IMultipleService;
import icu.funkye.service.IOrderService;
import icu.funkye.service.IProductService;
import icu.funkye.service.ITestService;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.spring.annotation.GlobalTransactional;
import io.seata.tm.api.GlobalTransactionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private IAccountService accountService;
    @Autowired
    private IOrderService orderService;
    @Autowired
    private IProductService productService;
    @Autowired
    private ITestService testService;
    @Autowired
    private IMultipleService multipleService;
    @Autowired
    private DemoService demoService;
    private Lock lock = new ReentrantLock();

    private ExecutorService executorService = Executors.newCachedThreadPool();

    /**
     * 全局异常捕获处理方案2
     *
     * @return
     */
    @GetMapping(value = "seataException")
    public Object seataException() {
        demoService.rollback();
        return true;
    }

    /**
     * 全局异常捕获处理方案1
     *
     * @return
     */
    @GetMapping(value = "seataException2")
    @GlobalTransactional
    public Object seataException2() throws TransactionException {
        try {
            testService.commit();
            int i = 1 / 0;
        } catch (Exception e) {
            // 手动API回滚
            GlobalTransactionContext.reload(RootContext.getXID()).rollback();
        }
        return true;
    }

    /**
     * 简单测试提交分布式事务接口
     *
     * @return
     */
    @GetMapping(value = "seataCommit")
    @GlobalTransactional
    public Object seataCommit() {
        testService.commit();
        return true;
    }

    /**
     * 异步进行缓存方案
     *
     * @return
     */
    @GetMapping(value = "seataCache")
    public Object seataCache() {
        String uuid = UUID.randomUUID().toString();
        executorService.execute(() -> demoService.commitCache(uuid));
        return uuid;
    }

    /**
     * 通过缓存值,从缓存得到事务执行结果
     *
     * @param uuid
     * @return
     */
    @GetMapping(value = "getResult")
    public Object getResult(String uuid) {
        return demoService.getCache(uuid);
    }

    /**
     * 秒杀下单分布式事务测试
     *
     * @return
     * @throws TransactionException
     */
    @GetMapping(value = "testCommit")
    @GlobalTransactional
    public Object testCommit() throws TransactionException {
        lock.lock();
        try {
            Product product = productService.getById(1);
            if (product.getStock() > 0) {
                LocalDateTime now = LocalDateTime.now();
                logger.info("seata分布式事务Id:{}", RootContext.getXID());
                Account account = accountService.getById(1);
                Orders orders = new Orders();
                orders.setCreateTime(now);
                orders.setProductId(product.getId());
                orders.setReplaceTime(now);
                orders.setSum(1);
                orders.setAmount(product.getPrice());
                orders.setAccountId(account.getId());
                product.setStock(product.getStock() - 1);
                account.setSum(account.getSum() != null ? account.getSum() + 1 : 1);
                account.setLastUpdateTime(now);
                productService.updateById(product);
                accountService.updateById(account);
                orderService.save(orders);
                return true;
            } else {
                return false;
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 多数据源事务测试
     *
     * @return
     */
    @RequestMapping("testMultipleService")
    @GlobalTransactional
    public Object testMultipleService() {
        Object o = multipleService.commit();
        return o;
    }
}

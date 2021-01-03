package icu.funkye.controller;

import java.time.LocalDateTime;
import icu.funkye.entity.Orders;
import icu.funkye.service.IOrderService;
import icu.funkye.service.IProductService;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
public class TestController {
    private final static Logger logger = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private IOrderService orderService;

    @Autowired
    private IProductService productService;

    /**
     * 秒杀下单分布式事务测试提交
     */
    @GetMapping(value = "testCommit")
    @GlobalTransactional
    public Object testCommit(@RequestParam(name = "id",defaultValue = "1") Integer id,
        @RequestParam(name = "sum", defaultValue = "1") Integer sum) {
        Boolean ok = productService.reduceStock(id, sum);
        if (ok) {
            LocalDateTime now = LocalDateTime.now();
            Orders orders = new Orders();
            orders.setCreateTime(now);
            orders.setProductId(id);
            orders.setReplaceTime(now);
            orders.setSum(sum);
            orderService.save(orders);
            return "ok";
        } else {
            return "fail";
        }
    }

    /**
     * 秒杀下单分布式事务测试回滚
     */
    @GetMapping(value = "testRollback")
    @GlobalTransactional
    public Object testRollback(@RequestParam(name = "id") Integer id,
        @RequestParam(name = "sum", defaultValue = "1") Integer sum) {
        Boolean ok = productService.reduceStock(id, sum);
        if (ok) {
            LocalDateTime now = LocalDateTime.now();
            Orders orders = new Orders();
            orders.setCreateTime(now);
            orders.setProductId(id);
            orders.setReplaceTime(now);
            orders.setSum(sum);
            orderService.save(orders);
            throw new RuntimeException("mock fail");
        } else {
            return "fail";
        }
    }

    @RequestMapping("/productPage")
    public Object productPage(@RequestParam(name = "current", defaultValue = "1") Long current,
        @RequestParam(name = "size", defaultValue = "10") Long size) {
        return productService.page(current, size);
    }

    @RequestMapping("/orderPage")
    public Object orderPage(@RequestParam(name = "current", defaultValue = "1") Long current,
        @RequestParam(name = "size", defaultValue = "10") Long size) {
        return orderService.page(current, size);
    }

    @RequestMapping("/getByProductId")
    public Object getByProductId(@RequestParam(name = "id") Integer id) {
        return productService.getById(id);
    }

    @RequestMapping("/getByOrdersId")
    public Object getByOrdersId(@RequestParam(name = "id") Integer id) {
        return orderService.getById(id);
    }

    @RequestMapping("/addStock")
    @GlobalTransactional
    public Object addStock(@RequestParam(name = "id") Integer id,
        @RequestParam(name = "sum", defaultValue = "1") Integer sum) {
        return productService.addStock(id, sum);
    }

}

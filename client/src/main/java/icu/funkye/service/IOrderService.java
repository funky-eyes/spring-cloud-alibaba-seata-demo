package icu.funkye.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import icu.funkye.entity.Orders;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author funkye
 * @date 2020/4/13
 */
@FeignClient(value = "order-service")
public interface IOrderService {

    @RequestMapping(value = "/save")
    Boolean save(@RequestBody Orders orders);

    @RequestMapping("/page") String page(@RequestParam(name = "current") Long current, @RequestParam(name = "size") Long size);

    @RequestMapping("/getById")
    String getById(@RequestParam(name = "id") Integer id);
}

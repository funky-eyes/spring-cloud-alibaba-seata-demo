package icu.funkye.service;

import icu.funkye.service.failback.OrderFailBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import icu.funkye.entity.Orders;

/**
 * @author funkye
 * @date 2020/4/13
 */
@FeignClient(value = "order-service", fallback = OrderFailBack.class)
public interface IOrderService {

    @RequestMapping(value = "/save")
    Boolean save(@RequestBody Orders orders);

}

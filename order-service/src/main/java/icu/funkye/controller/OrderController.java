package icu.funkye.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import icu.funkye.entity.Orders;
import icu.funkye.service.IOrdersService;

/**
 * @author funkye
 */
@RestController
public class OrderController {
    private final static Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    IOrdersService ordersService;

    @RequestMapping("/save")
    public Boolean save(@RequestBody Orders orders) {
        return ordersService.save(orders);
    }
}

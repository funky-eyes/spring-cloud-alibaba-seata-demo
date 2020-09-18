package icu.funkye.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @GlobalTransactional
    public Boolean save(@RequestBody Orders orders) {
        return ordersService.save(orders);
    }

    @RequestMapping("/page")
    public String page(@RequestParam(name = "current") Long current, @RequestParam(name = "size") Long size) {
        Page<Orders> page = new Page<>(current, size);
        return JSON.toJSONString(ordersService.page(page, Wrappers.<Orders>query().orderByDesc("id")));
    }

    @RequestMapping("/getById")
    public String getById(@RequestParam(name = "id") Integer id) {
        return JSON.toJSONString(ordersService.getById(id));
    }

}

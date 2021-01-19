package icu.funkye.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import icu.funkye.entity.Product;
import icu.funkye.service.IProductService;
import io.seata.spring.annotation.GlobalTransactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author funkye
 */
@RestController
public class ProductController {
    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    IProductService productService;

    @RequestMapping("/reduceStock")
    @GlobalTransactional(lockRetryInternal = 10,lockRetryTimes = 30)
    public Boolean reduceStock(@RequestParam(name = "id") Integer id, @RequestParam(name = "sum") Integer sum) {
        return productService.reduceStock(id, sum);
    }

    @RequestMapping("/page")
    public String page(@RequestParam(name = "current") Long current, @RequestParam(name = "size") Long size) {
        Page<Product> page = new Page<>(current, size);
        return JSON.toJSONString(productService.page(page, Wrappers.<Product>query().orderByDesc("id")));
    }

    @RequestMapping("/getById")
    public String getById(@RequestParam(name = "id") Integer id) {
        return JSON.toJSONString(productService.getById(id));
    }

    @RequestMapping("/addStock")
    @GlobalTransactional
    public Object addStock(@RequestParam(name = "id") Integer id, @RequestParam(name = "sum") Integer sum) {
        return productService.addStock(id, sum);
    }

}

package icu.funkye.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import icu.funkye.entity.Product;
import icu.funkye.service.IProductService;

/**
 * @author funkye
 */
@RestController
public class ProductController {
    private final static Logger logger = LoggerFactory.getLogger(ProductController.class);
    @Autowired
    IProductService productService;

    @RequestMapping("/getById")
    public Product getById(@RequestParam(name = "id") Integer id) {
        return productService.getById(id);
    }

    @RequestMapping("/updateById")
    public Boolean updateById(@RequestBody Product product) {
        logger.info("product:{}",product);
        return productService.updateById(product);
    }
}

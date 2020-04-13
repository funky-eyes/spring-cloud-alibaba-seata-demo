package icu.funkye.service;

import icu.funkye.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author funkye
 */
@FeignClient(value="product-service")
public interface IProductService {
    @RequestMapping(value = "/getById")
    Product getById(@RequestParam(value = "id") Integer id);

    @RequestMapping(value = "/updateById")
    Boolean updateById(@RequestBody Product product);
}

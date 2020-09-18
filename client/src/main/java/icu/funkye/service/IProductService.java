package icu.funkye.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import icu.funkye.entity.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author funkye
 */
@FeignClient(value = "product-service")
public interface IProductService {

    @RequestMapping(value = "/reduceStock")
    Boolean reduceStock(@RequestParam(name = "id") Integer id, @RequestParam(name = "sum") Integer sum);

    @RequestMapping("/page") String page(@RequestParam(name = "current") Long current, @RequestParam(name = "size") Long size);

    @RequestMapping("/getById")
    String getById(@RequestParam(name = "id") Integer id);

    @RequestMapping("/addStock")
    Object addStock(@RequestParam(name = "id") Integer id, @RequestParam(name = "sum") Integer sum);
}

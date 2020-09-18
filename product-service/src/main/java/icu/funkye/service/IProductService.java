package icu.funkye.service;

import com.baomidou.mybatisplus.extension.service.IService;

import icu.funkye.entity.Product;

/**
 * <p>
 * 功能 服务类
 * </p>
 *
 * @author Funkye
 */
public interface IProductService extends IService<Product> {
    Boolean reduceStock(Integer id, Integer amount);

    Object addStock(Integer id, Integer sum);
}

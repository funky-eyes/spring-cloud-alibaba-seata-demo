package icu.funkye.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import icu.funkye.entity.Product;
import icu.funkye.mapper.ProductMapper;
import icu.funkye.service.IProductService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Override
    @Transactional
    public Boolean reduceStock(Integer id, Integer sum) {
        Product product = getOne(Wrappers.<Product>query().eq("id", id).last("for update"));
        int orderAmount = product.getStock() - sum;
        if (orderAmount >= 0) {
            product.setStock(orderAmount);
            updateById(product);
            return true;
        } else {
            return false;
        }
    }

    @Override
    @Transactional
    public Object addStock(Integer id, Integer sum) {
        Product product = getOne(Wrappers.<Product>query().eq("id", id).last("for update"));
        product.setStock(product.getStock() + sum);
        if (updateById(product)) {
            return true;
        } else {
            return false;
        }
    }

}

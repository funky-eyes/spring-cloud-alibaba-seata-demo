package icu.funkye.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.funkye.entity.Product;
import icu.funkye.mapper.ProductMapper;
import icu.funkye.service.IProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

    @Override
    @Transactional
    public Boolean reduceStock(Integer id, Integer sum) {
        return update(Wrappers.<Product>lambdaUpdate().eq(Product::getId, id).ge(Product::getStock, sum)
            .setSql("stock=stock-" + sum));
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

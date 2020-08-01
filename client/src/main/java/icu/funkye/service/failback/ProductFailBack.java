package icu.funkye.service.failback;

import icu.funkye.entity.Product;
import icu.funkye.service.IProductService;
import org.springframework.stereotype.Component;

@Component
public class ProductFailBack implements IProductService {

    @Override
    public Product getById(Integer id) {
        return null;
    }

    @Override
    public Boolean updateById(Product product) {
        return null;
    }
}

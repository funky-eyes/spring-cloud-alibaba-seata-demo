package io.seata.samples.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.seata.samples.entity.Product;
import io.seata.samples.mapper.ProductMapper;
import io.seata.samples.service.IProductService;

@DS(value = "master_3")
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}

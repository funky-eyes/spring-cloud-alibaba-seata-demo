package icu.funkye.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import icu.funkye.entity.Product;
import icu.funkye.mapper.ProductMapper;
import icu.funkye.service.IProductService;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}

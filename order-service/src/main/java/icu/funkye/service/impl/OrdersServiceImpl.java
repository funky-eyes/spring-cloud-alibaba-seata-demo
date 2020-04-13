package icu.funkye.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import icu.funkye.entity.Orders;
import icu.funkye.mapper.OrdersMapper;
import icu.funkye.service.IOrdersService;

@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}

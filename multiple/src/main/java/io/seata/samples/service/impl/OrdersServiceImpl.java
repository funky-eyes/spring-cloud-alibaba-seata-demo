package io.seata.samples.service.impl;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import io.seata.samples.entity.Orders;
import io.seata.samples.mapper.OrdersMapper;
import io.seata.samples.service.IOrdersService;
import org.springframework.stereotype.Service;

@DS(value = "master_2")
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

}

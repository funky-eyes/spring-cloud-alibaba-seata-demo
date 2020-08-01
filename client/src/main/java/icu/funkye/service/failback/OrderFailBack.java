package icu.funkye.service.failback;

import icu.funkye.entity.Orders;
import icu.funkye.service.IOrderService;
import org.springframework.stereotype.Component;

@Component
public class OrderFailBack implements IOrderService {
    @Override
    public Boolean save(Orders orders) {
        //抛出异常回滚方式
        throw new RuntimeException();
        //手动api方式回滚,不推荐
        //GlobalTransactionContext.reload(RootContext.getXID()).rollback();
    }
}

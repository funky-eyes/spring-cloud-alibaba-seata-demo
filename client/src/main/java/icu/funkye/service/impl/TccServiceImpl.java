package icu.funkye.service.impl;

import java.util.Map;
import icu.funkye.service.TccService;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TccServiceImpl implements TccService {

    final static Logger logger = LoggerFactory.getLogger(TccServiceImpl.class);

    /**
     * 定义两阶段提交 name = 该tcc的bean名称,全局唯一 commitMethod = commit 为二阶段确认方法 rollbackMethod = rollback 为二阶段取消方法
     * BusinessActionContextParameter注解 传递参数到二阶段中
     *
     * @param params
     *            -入参
     * @return String
     */
    @TwoPhaseBusinessAction(name = "insert", commitMethod = "commitTcc", rollbackMethod = "cancel")
    public void insert(@BusinessActionContextParameter(paramName = "params") Map<String, String> params) {
        logger.info("此处可以预留资源,或者利用tcc的特点,与AT混用,二阶段时利用一阶段在此处存放的消息,通过二阶段发出,比如redis,mq等操作");
    }

    /**
     * 确认方法、可以另命名，但要保证与commitMethod一致 context可以传递try方法的参数
     *
     * @param context
     *            上下文
     * @return boolean
     */
    public void commitTcc(BusinessActionContext context) {
        logger.info("预留资源真正处理,或者发出mq消息和redis入库");
    }

    /**
     * 二阶段取消方法
     *
     * @param context
     *            上下文
     * @return boolean
     */
    public void cancel(BusinessActionContext context) {
        logger.info("预留资源释放,或清除一阶段准备让二阶段提交时发出的消息缓存");
    }

}

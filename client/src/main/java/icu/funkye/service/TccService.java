package icu.funkye.service;

import java.util.Map;
import io.seata.rm.tcc.api.BusinessActionContext;
import io.seata.rm.tcc.api.BusinessActionContextParameter;
import io.seata.rm.tcc.api.LocalTCC;
import io.seata.rm.tcc.api.TwoPhaseBusinessAction;

@LocalTCC
public interface TccService {

    /**
     * 定义两阶段提交 name = 该tcc的bean名称,全局唯一 commitMethod = commit 为二阶段确认方法 rollbackMethod = rollback 为二阶段取消方法
     * BusinessActionContextParameter注解 传递参数到二阶段中
     *
     * @param params
     *            -入参
     * @return String
     */
    @TwoPhaseBusinessAction(name = "insert", commitMethod = "commitTcc", rollbackMethod = "cancel")
    void insert(@BusinessActionContextParameter(paramName = "params") Map<String, String> params);

    /**
     * 确认方法、可以另命名，但要保证与commitMethod一致 context可以传递try方法的参数
     *
     * @param context
     *            上下文
     * @return boolean
     */
    void commitTcc(BusinessActionContext context);

    /**
     * 二阶段取消方法
     *
     * @param context
     *            上下文
     * @return boolean
     */
    void cancel(BusinessActionContext context);

}

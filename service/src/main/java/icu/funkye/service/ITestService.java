package icu.funkye.service;


import com.baomidou.mybatisplus.extension.service.IService;
import icu.funkye.entity.Test;

/**
 * <p>
 * 功能 服务类
 * </p>
 *
 * @author Funkye
 * @since 2019-04-10
 */
public interface ITestService extends IService<Test> {

    Object Commit();
}
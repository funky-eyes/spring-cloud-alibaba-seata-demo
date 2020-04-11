package icu.funkye.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <p>
 * 功能 服务类
 * </p>
 *
 * @author Funkye
 * @since 2019-04-10
 */
@FeignClient(value="demo-service")
public interface ITestService {
    @RequestMapping(value = "/commit")
    Object commit();
}
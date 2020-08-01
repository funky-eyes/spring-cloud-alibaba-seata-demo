package icu.funkye.service;

import icu.funkye.service.failback.AccountFailBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import icu.funkye.entity.Account;

/**
 * @author funkye
 * @date 2020/4/13
 */

@FeignClient(value = "account-service", fallback = AccountFailBack.class)
public interface IAccountService {
    @RequestMapping(value = "/getById")
    Account getById(@RequestParam(value = "id") Integer id);

    @RequestMapping(value = "/updateById")
    Boolean updateById(@RequestBody Account account);
}

package icu.funkye.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import icu.funkye.entity.Account;
import icu.funkye.service.IAccountService;

/**
 * @author funkye
 */
@RestController
public class AccountController {

    private final static Logger logger = LoggerFactory.getLogger(AccountController.class);
    @Autowired
    IAccountService accountService;

    @RequestMapping("/getById")
    public Account getById(@RequestParam Integer id) {
        return accountService.getById(id);
    }

    @RequestMapping("/updateById")
    public Boolean updateById(@RequestBody Account account) {
        return accountService.updateById(account);
    }
}

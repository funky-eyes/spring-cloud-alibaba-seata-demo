package io.seata.samples.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import io.seata.samples.entity.Account;
import io.seata.samples.mapper.AccountMapper;
import io.seata.samples.service.IAccountService;

@DS(value = "master_1")
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

}

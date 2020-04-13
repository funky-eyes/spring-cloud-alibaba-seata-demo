package icu.funkye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import icu.funkye.entity.Account;
import icu.funkye.mapper.AccountMapper;
import icu.funkye.service.IAccountService;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account> implements IAccountService {

}

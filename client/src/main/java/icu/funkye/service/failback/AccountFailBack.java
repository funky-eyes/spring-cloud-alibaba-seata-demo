package icu.funkye.service.failback;

import icu.funkye.entity.Account;
import icu.funkye.service.IAccountService;
import org.springframework.stereotype.Component;

@Component
public class AccountFailBack implements IAccountService {

    @Override
    public Account getById(Integer id) {
        throw new RuntimeException();
    }

    @Override
    public Boolean updateById(Account account) {
        throw new RuntimeException();
    }
}

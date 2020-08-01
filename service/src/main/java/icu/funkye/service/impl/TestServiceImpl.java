package icu.funkye.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.funkye.entity.Test;
import icu.funkye.mapper.TestMapper;
import icu.funkye.service.ITestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {

    @Override
    @Transactional
    public Object Commit() {
        Test t = getOne(Wrappers.<Test>query().select("id", "two").eq("id", 1)
            .last("for update"));
        t.setTwo(t.getTwo() + 1);
        updateById(t);
        return true;
    }

}

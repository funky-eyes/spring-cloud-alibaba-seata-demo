package icu.funkye.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import icu.funkye.entity.Test;
import icu.funkye.mapper.TestMapper;
import icu.funkye.service.ITestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;

@Service
public class TestServiceImpl extends ServiceImpl<TestMapper, Test> implements ITestService {

    @Override
    @Transactional
    public Object Commit() {
        update(Wrappers.<Test>lambdaUpdate().eq(Test::getId,1).setSql("two=two+1"));
        return true;
    }

}

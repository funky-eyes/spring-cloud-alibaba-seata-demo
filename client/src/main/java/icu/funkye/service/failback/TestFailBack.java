package icu.funkye.service.failback;

import icu.funkye.service.ITestService;
import org.springframework.stereotype.Component;

@Component
public class TestFailBack implements ITestService {

    @Override public Object commit() {
        return null;
    }

    @Override public Object commitById(Long id) {
        return null;
    }
}

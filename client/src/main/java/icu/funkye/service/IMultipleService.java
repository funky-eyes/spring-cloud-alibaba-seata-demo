package icu.funkye.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "multiple-service")
public interface IMultipleService {

    @RequestMapping(value = "/commit")
    Object commit();
}

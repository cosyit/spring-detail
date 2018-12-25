package day_01.service.impl;

import day_01.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceV1Impl implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("HelloServiceV1Impl.sayHello from V1");
    }

    @Override
    public void sayHi() {
        System.out.println("HelloServiceV1Impl.sayHi from V1");
    }
}

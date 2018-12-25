package day_02.service.impl;

import day_02.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImplV1 implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("HelloServiceV1Impl.sayHello from V1");
    }

    @Override
    public void sayHi() {
        System.out.println("HelloServiceV1Impl.sayHi from V1");
    }
}

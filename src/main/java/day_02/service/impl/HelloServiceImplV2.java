package day_02.service.impl;

import day_02.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImplV2 implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("HelloServiceV2Impl.sayHello from V2");
    }

    @Override
    public void sayHi() {
        System.out.println("HelloServiceV2Impl.sayHi from V2");
    }
}

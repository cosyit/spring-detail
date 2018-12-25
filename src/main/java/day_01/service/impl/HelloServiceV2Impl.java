package day_01.service.impl;

import day_01.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceV2Impl implements HelloService {
    @Override
    public void sayHello() {
        System.out.println("HelloServiceV2Impl.sayHello from V2");
    }

    @Override
    public void sayHi() {
        System.out.println("HelloServiceV2Impl.sayHi from V2");
    }
}

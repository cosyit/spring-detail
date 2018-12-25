package day_02.controller;


import day_02.annotation.RoutingInjected;
import day_02.annotation.RoutingSwitch;
import day_02.service.HelloService;
import org.springframework.stereotype.Controller;


@Controller
public class HelloController {

    @RoutingInjected
    private HelloService helloService;


    @RoutingSwitch("A")
    public void sayHello(){
        this.helloService.sayHello();
    }

    public void sayHi(){
        this.helloService.sayHi();
    }
}

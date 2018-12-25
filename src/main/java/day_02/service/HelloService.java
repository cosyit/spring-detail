package day_02.service;


import day_02.annotation.RoutingSwitch;

@RoutingSwitch("hello.switch")
public interface HelloService{

    @RoutingSwitch("A")
    void sayHello();

    void sayHi();
}
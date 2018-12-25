package day_02.main;

import day_02.MainConfig;
import day_02.controller.HelloController;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class MainTest {
    public static void main(String[] args) {

        //
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
        //
        HelloController helloControler = ctx.getBean("helloController", HelloController.class);
        //
        helloControler.sayHello();
    }
}

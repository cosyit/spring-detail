package day_02.spring;

import day_02.annotation.RoutingInjected;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.Map;


@Component // 豆豆后置处理器的要点，1. implement BeanPostProcessor.  2.要注册到容器中，直接@Component 快捷方便。
public class RoutingBeanPostProcessor  implements BeanPostProcessor {

    @Autowired
    private ApplicationContext applicationContext;



    //init 方法前，为了不影响初始化工作，再说，这里返回的bean也不是最终的bean，所以就不在这里做一些处理注解的工作。
    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        System.out.println(" 豆豆后置处理器  : init 方法前");
      Date date =   new Date(applicationContext.getStartupDate());
        System.out.println("启动时间为:"+date);
        return o;
    }



    // 把处理注解的工作放在这个地方。
    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        System.out.println("豆豆后置处理器 :  init 方法后");

        // 获取bean 的类类型。
        Class clazz = o.getClass();
        System.out.println("此bean 的类类型 》"+o.getClass()+">>>>>>>>>>>>>>>>>>>>>");

        Field[] fields =clazz.getDeclaredFields();// 点到源码查看，发现：返回的是 这个对象或者这个接口声明的。
                //fields= clazz.getFields(); // all the accessible public fields .只能获得public 修饰的fields.
        for(Field f :fields){
            //isAnnotationPresent(AnnotationX.class)的设计主要是为了方便访问标记注释 。就是说参数中的这个类型的注解，是不是在此f上。
            if(f.isAnnotationPresent(RoutingInjected.class)){ //方法返回true，如果指定类型的注释存在于此元素上,否则返回false。
                //如果f这个field 有@RoutingInjected 修饰。  present 这个词 有 "在","现在"，"出席"的意思。我觉得“在”这个意思很符合现在的场景。
                System.out.println(f.getName()+"---在");

                //希望组合的是一个接口的属性。
                if(!f.getType().isInterface()){  // 如果注解修饰的属性类型不是接口。 那么就抛出这个注解必须要修饰在接口上，处理上选择终止程序。
                     throw new BeanCreationException("RoutingInjected field must be declared as an interface:" + f.getName()
                            + " @Class " + clazz.getName());
                }

            try{
                    this.handleRoutingInjected(f, o, f.getType());
                }catch (Exception e){
                    throw new BeanCreationException("Exception thrown when handleAutowiredRouting", e);
                }

            }

/*
//            把代码放在这里就能发现大量spring容器启动的时候，内置的一些对象了。信息量比较大，你若有空打开看看即可。
            try{
                this.handleRoutingInjected(f, o, f.getType());
            }catch (Exception e){
                throw new BeanCreationException("Exception thrown when handleAutowiredRouting", e);
            }*/

        }

        return o;
    }


    //一个私有的方法，本类内部作的一个处理： 就是来处理 @RoutingInjected注解的。
    private void handleRoutingInjected(Field field,Object bean,Class type) throws  IllegalAccessException {

        //找到候选人，申请者，这个应用中管理的。如果我没猜错就是注册在容器中的bean。这个不是一个Map吗？我把它遍历出来看看。
        Map<String, Object> candidates = this.applicationContext.getBeansOfType(type);

           for(Map.Entry<String,Object> entry :candidates.entrySet()){

            //类名首字母小写   =====   该类的对象
            System.out.println( "类名首字母小写 作为Key :"+entry.getKey() + " ===== 该类的对象作为值 : " + entry.getValue());
        }

        //把field 变为 在反射的时候是，可以访问的。 当isAccessible()的结果是false时不允许通过反射访问该字段 。
        field.setAccessible(true);

        if(candidates.size() ==1 ){
            field.set(bean,candidates.values().iterator().next());
        }else if(candidates.size() == 2 ){
            Object proxy = RoutingBeanProxyFactory.createProxy(type,candidates);
            field.set(bean,proxy);
        }else {
            throw new IllegalArgumentException("Find more than 2 beans for type: " + type);
        }
    }
}
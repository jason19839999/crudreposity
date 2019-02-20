package datacenter.crudreposity.aapractise.design_mode;

import lombok.Synchronized;
import org.hibernate.annotations.Synchronize;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
public class Singleton {
    //饿汉模式  加上synchronized 线程同步
    private static Singleton instance = new Singleton();
    public synchronized static Singleton getInstance(){
        return instance;
    }

    //用volatile 修饰防止指令重排序  ，即 new 的流程是 ①分配内存  ②初始化对象 ③设置instance3指向刚刚分配的内存地址，此时 instance3 != null.
    //保证按照上述顺序执行，否则有可能是①③②的顺序了，对象没有初始化完成，就被别人调用了。
    private  static  volatile  Singleton instance3 = new Singleton();
    public synchronized static Singleton getInstance3(){
        return instance3;
    }

    //懒汉模式
    private static Singleton instance2;
//    public synchronized Singleton getInstance2(){
    public static  Singleton getInstance2(){
        synchronized (Singleton.class) {
            if (instance2 == null) {
                return instance2;
            }else{
                return instance2;
            }
        }
    }

    //懒汉模式
    //用volatile 修饰防止指令重排序  ，即 new 的流程是 ①分配内存  ②初始化对象 ③设置instance3指向刚刚分配的内存地址，此时 instance3 != null.
    //保证按照上述顺序执行，否则有可能是①③②的顺序了，对象没有初始化完成，就被别人调用了。
    private static volatile Singleton instance4;
    public static  Singleton getInstance4(){
        //第一次检测
        if(instance4 == null) {
            //同步
            synchronized (Singleton.class) {
                if (instance4 == null) {
                    //多线程环境下可能会出现问题的地方
                    instance4 = new Singleton();
                }
            }
        }
        return  instance4;
    }



}

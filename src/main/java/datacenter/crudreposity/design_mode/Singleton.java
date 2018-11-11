package datacenter.crudreposity.design_mode;

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

    //懒汉模式
    private static Singleton instance2;
    public synchronized Singleton getInstance2(){
        if(instance2 == null){
            return instance2;
        }
        return instance2;
    }





}

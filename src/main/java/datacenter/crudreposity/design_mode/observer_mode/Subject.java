package datacenter.crudreposity.design_mode.observer_mode;

import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @描述  目标对象，它知道观察它的观察者，提供注册（添加）和删除观察者的接口
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
public class Subject {

    //用来保存注册的观察者对象
    CopyOnWriteArraySet<Observer> observers = new CopyOnWriteArraySet<Observer>();

    //保存观察者对象
    public void attach(Observer observer){
        observers.add(observer);
    }

    //删除集合中的指定观察者
    public void dettach(Observer observer){
        observers.remove(observer);
    }

    //通知所有注册的观察者对象
    protected void notifyObservers(){
        for(Observer observer:observers){
            observer.update(this);
        }
    }


}

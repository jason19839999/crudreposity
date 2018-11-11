package datacenter.crudreposity.design_mode.observer_mode.weather_observer_mode;

import datacenter.crudreposity.design_mode.observer_mode.ConcreteSubject;
import datacenter.crudreposity.design_mode.observer_mode.Observer;
import datacenter.crudreposity.design_mode.observer_mode.Subject;

/**
 * @描述 具体的观察者对象，实现更新的方法，使自身的状态和目标的状态保持一致
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
public class WeatherConcreteObserver implements WeatherObserver {

    //观察者的名字
    private String observerName;

    //天气内容的情况,这个消息从目标处获取
    private String weatherContent;

    //提醒的内容  不同的观察者，提醒不同的内容
    private String remindThings;

    public String getObserverName() {
        return observerName;
    }

    public void setObserverName(String observerName) {
        this.observerName = observerName;
    }

    public String getWeatherContent() {
        return weatherContent;
    }

    public void setWeatherContent(String weatherContent) {
        this.weatherContent = weatherContent;
    }

    public String getRemindThings() {
        return remindThings;
    }

    public void setRemindThings(String remindThings) {
        this.remindThings = remindThings;
    }

    /**
     * 获取目标类的状态同步到观察者的状态中
     * @param subject
     */
    @Override
    public void update(WeatherSubject subject) {
        weatherContent = ((WeatherConcreteSubject)subject).getWeatherContent();
        System.out.println(observerName + "收到了" + weatherContent + "," + remindThings);
    }

}

package datacenter.crudreposity.design_mode.observer_mode.weather_observer_mode;

import datacenter.crudreposity.design_mode.observer_mode.Subject;

/**
 * @描述 具体的目标对象，负责把有关状态存入到相应的观察者对象中
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
public class WeatherConcreteSubject extends WeatherSubject {

    //获取天气的内容信息
    private String weatherContent;

    public String getWeatherContent() {
        return weatherContent;
    }

    public void setWeatherContent(String weatherContent) {
        this.weatherContent = weatherContent;
        //内容有了，通知所有订阅天气的人
        this.notifyObservers();
    }


}

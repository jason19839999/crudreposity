package datacenter.crudreposity.design_mode.observer_mode.weather_observer_mode;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

/**
 * @描述  测试用
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */

@SpringBootApplication
@EnableAsync
@Service
public class Client {

    public static void main(String[] args) throws Exception {
        //1 创建目标
        WeatherConcreteSubject weather = new WeatherConcreteSubject();

        //2 创建观察者
        WeatherConcreteObserver observerGirl = new WeatherConcreteObserver();
        observerGirl.setObserverName("小明的女朋友");
        observerGirl.setRemindThings("今天天气很好，适合啪啪啪！");
        WeatherConcreteObserver objserverMum = new WeatherConcreteObserver();
        objserverMum.setObserverName("丈母娘");
        objserverMum.setRemindThings("今天天气很好，适合购物！");
        //3 注册观察者
        weather.attach(observerGirl);
        weather.attach(objserverMum);
        //4 目标发布天气
        weather.setWeatherContent("明天天气晴朗，蓝天白云，气温28度！");
    }



}

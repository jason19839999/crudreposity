package datacenter.crudreposity.aapractise.design_mode.observer_mode.weather_observer_mode;

import datacenter.crudreposity.aapractise.design_mode.observer_mode.Subject;

/**
 * @描述  这是一个观察者接口，定义一个更新的接口给那些在目标发生改变的时候被通知的 【对象】
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
public interface WeatherObserver {

    /**
     * 更新的接口  传入目标对象，方便获取相应的目标对象的状态
     */
    public void update(WeatherSubject subject);


}

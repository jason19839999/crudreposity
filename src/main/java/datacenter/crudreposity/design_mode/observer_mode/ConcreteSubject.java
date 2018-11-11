package datacenter.crudreposity.design_mode.observer_mode;

/**
 * @描述 具体的目标对象，负责把有关状态存入到相应的观察者对象中
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
public class ConcreteSubject extends Subject {

    //目标对象的状态
    private String subjectState;

    public String getSubjectState() {
        return subjectState;
    }

    public void setSubjectState(String subjectState) {
        this.subjectState = subjectState;
        this.notifyObservers();
    }
}

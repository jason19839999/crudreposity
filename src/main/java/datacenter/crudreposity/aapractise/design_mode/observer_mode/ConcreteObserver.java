package datacenter.crudreposity.aapractise.design_mode.observer_mode;

/**
 * @描述 具体的观察者对象，实现更新的方法，使自身的状态和目标的状态保持一致
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
public class ConcreteObserver implements Observer {

    //观察者的状态
    private String observerState;

    /**
     * 获取目标类的状态同步到观察者的状态中
     * @param subject
     */
    @Override
    public void update(Subject subject) {
        observerState = ((ConcreteSubject)subject).getSubjectState();
    }

}

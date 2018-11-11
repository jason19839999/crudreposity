package datacenter.crudreposity.design_mode.template_mode;

public abstract class RefreshBeverage {

    public final void prepareBeverageTemplate() {
        //制备咖啡或者茶
        //①将水煮沸
        boilWater();
        //②冲泡咖啡或者茶
        brew();
        //③倒入杯中
        pourInCup();
        //④利用钩子方法，判断是否加入糖和牛奶
        if (isCustomerWantsCondiments()) {

            //⑤加入调料，糖和牛奶，或者其他东西，可以在子类实现
            addCondiments();
        }
    }

    protected boolean isCustomerWantsCondiments() {
        return true;
    }

    private void boilWater() {
        System.out.println("将水煮沸");
    }

    private void pourInCup() {
        System.out.println("将饮料倒入杯中");
    }

    protected abstract void brew();
    protected abstract void addCondiments();


}

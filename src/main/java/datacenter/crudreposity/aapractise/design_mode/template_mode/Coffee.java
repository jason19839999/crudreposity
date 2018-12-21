 package datacenter.crudreposity.aapractise.design_mode.template_mode;

/*
 * 制作咖啡
 */
public class Coffee extends RefreshBeverage {

	@Override
	protected void brew() {
		System.out.println("用沸水冲泡咖啡");

	}

	@Override
	protected void addCondiments() {
		System.out.println("加入糖和牛奶");
	}

}

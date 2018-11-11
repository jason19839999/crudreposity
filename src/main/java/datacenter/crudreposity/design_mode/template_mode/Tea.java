 package datacenter.crudreposity.design_mode.template_mode;

/*
 * 制作茶
 */
public class Tea extends RefreshBeverage {

	@Override
	protected void brew() {
		System.out.println("用80°的热水浸泡茶叶5分钟");
	}

	@Override
	protected void addCondiments() {
		System.out.println("添加冰糖");
	}

	@Override
	/*
	 * 添加钩子方法实现,决定是否执行特定的方法
	 * @see com.imooc.pattern.template.RefreshBeverage#isCustomerWantsCondiments()
	 */
	protected boolean isCustomerWantsCondiments(){
		return false;
	}
	
}

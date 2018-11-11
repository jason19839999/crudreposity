package datacenter.crudreposity.design_mode.factory_mode_abstract;
/**
 * 创建圣诞人工厂
 * @author Administrator
 *
 */
public class MCFactory implements PersonFactory {

	@Override
	public Boy getBoy() {
		// TODO Auto-generated method stub
		return new MCBoy();
	}

	@Override
	public Girl getGirl() {
		// TODO Auto-generated method stub
		return new MCGirl();
	}

}

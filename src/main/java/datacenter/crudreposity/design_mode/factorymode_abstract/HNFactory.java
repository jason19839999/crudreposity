package datacenter.crudreposity.design_mode.factorymode_abstract;
/**
 * 创建新年人工厂
 * @author Administrator
 *
 */
public class HNFactory implements PersonFactory {

	@Override
	public Boy getBoy() {
		// TODO Auto-generated method stub
		return new HNBoy();
	}

	@Override
	public Girl getGirl() {
		// TODO Auto-generated method stub
		return new HNGirl();
	}

}

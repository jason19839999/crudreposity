package datacenter.crudreposity.design_mode.factorymode_abstract;
/**
 * 人工厂接口
 * @author Administrator
 *
 */
public interface PersonFactory {

	//获取男孩
	public Boy getBoy();
	//获取女孩
	public Girl getGirl();
	
}

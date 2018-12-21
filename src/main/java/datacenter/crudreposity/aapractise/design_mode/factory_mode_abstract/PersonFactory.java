package datacenter.crudreposity.aapractise.design_mode.factory_mode_abstract;
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

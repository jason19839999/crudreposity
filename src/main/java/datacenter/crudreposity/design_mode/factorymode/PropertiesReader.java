package datacenter.crudreposity.design_mode.factorymode;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 读取配置
 * @author Administrator
 *
 */
public class PropertiesReader {

	
	public Map<String, String> getProperties() {

		Properties props = new Properties();
		Map<String, String> map = new ConcurrentHashMap<String, String>(); // HashMap<String, String>();
		try {
//			spring boot getResourceAsStream读取resource properties
			InputStream in = getClass().getResourceAsStream("/config/type.properties");
			if(in != null){
				props.load(in);
			}else{
				return map;
			}
			Enumeration en = props.propertyNames();
			while (en.hasMoreElements()) {
				String key = (String) en.nextElement();
				String property = props.getProperty(key);
				map.put(key, property);
				System.out.println(key + "  " + property);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return map;
	}
}

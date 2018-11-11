package datacenter.crudreposity.design_mode.factory_mode;

import java.util.Map;

/**
 * @author Administrator
 */
public class HairFactory {

    /**
     * 通过关键字创建对象
     *
     * @param key
     * @return
     */
    public HairInterface getHair(String key) {
        if ("left".equals(key)) {
            return new LeftHair();
        } else if ("right".equals(key)) {
            return new RightHair();
        }
        return null;
    }

    /**
     * 通过类名创建对象
     *
     * @param className
     * @return
     */
    public HairInterface getHairByClass(String className) {

        try {
            HairInterface hair = (HairInterface) Class.forName(className).newInstance();
            return hair;
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 通过配置创建对象
     *
     * @param className
     * @return
     */
    public HairInterface getHairByClassKey(String key) {

        try {
            HairInterface hair = null;
            Map<String, String> map = new PropertiesReader().getProperties();
            if (map.size() > 0) {
                hair = (HairInterface) Class.forName(map.get(key)).newInstance();
            } else {
                hair = (HairInterface) Class.forName("in").newInstance();
            }
            return hair;
        } catch (InstantiationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }
}

package datacenter.crudreposity.aapractise.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
public class ReflectionBootstrap {
    public static void main(String args[]) {
//   https://www.cnblogs.com/tech-bird/p/3525336.html
//  Reflection（反射）是Java被视为动态语言的关键，反射机制允许程序在执行期借助于Reflection API取得任何类的內部信息，
// 并能直接操作任意对象的内部属性及方法。
        Class cz = null;
        cz = Person.class;
        Constructor<Person> constructor = null;
        Field field[] = cz.getDeclaredFields();
        Method methods[] = cz.getDeclaredMethods();
        System.out.println(cz);
        try {
//            constructor = (Constructor<Person>)cz.getConstructor(Person.class);
//  类装载器是用来把类(class)装载进 JVM 的。JVM 规范定义了两种类型的类装载器：启动类装载器(bootstrap)和用户自定义装载器(user-defined class loader)。
// JVM在运行时会产生3个类加载器组成的初始化加载器层次结构 ，如下图所示：
            //1. 获取一个系统的类加载器(可以获取，当前这个类PeflectTest就是它加载的)
            ClassLoader classLoader = ClassLoader.getSystemClassLoader();
            System.out.println("getSystemClassLoader: ---" + classLoader);
            //2. 获取系统类加载器的父类加载器（扩展类加载器，可以获取）.
            classLoader = classLoader.getParent();
            System.out.println("SystemClassLoader 的父类: ---" + classLoader);
            //3. 获取扩展类加载器的父类加载器（bootstrapClassLoader 他获取其他语言原生库的   引导类加载器，不可获取）.
            classLoader = classLoader.getParent();
            System.out.println("ExtClassLoader 的父类:" + classLoader);
            //4. 测试当前类由哪个类加载器进行加载（系统类加载器）:
            classLoader = Class.forName("datacenter.crudreposity.aapractise.reflection.ReflectionBootstrap")
                    .getClassLoader();
            System.out.println("ReflectionBootstrap ClassLoader的父类: ---" + classLoader);
            //5. 测试 JDK 提供的 Object 类由哪个类加载器负责加载（引导类）
            classLoader = Class.forName("java.lang.Object")
                    .getClassLoader();
            System.out.println("java.lang.Object的加载类: ---" + classLoader);

            //调用某个类的方法
            Object obj = cz.newInstance();
            //  获取指定的方法
            //  需要参数名称和参数列表，无参则不需要写
            Method method = cz.getDeclaredMethod("computeAge", Integer.class);
            //如果调用的是私有方法，那么调用之前必须加上以下设置。
            method.setAccessible(true);
            int age = (int) method.invoke(obj, 18);
//   自定义工具方法
//            Object object = new Person();
//       这种反射实现的主要功能是可配置和低耦合。只需要类名和方法名，而不需要一个类对象就可以执行一个方法。
//       如果我们把全类名和方法名放在一个配置文件中，就可以根据调用配置文件来执行方法
//            1. 把类对象和类方法名作为参数，执行方法
            Class cz2 = Class.forName("datacenter.crudreposity.aapractise.reflection.Person");
            Object object = cz2.newInstance();
            Integer age2= (Integer)invoke(object, "computeAge", 36);

            Object objects[] = new Object[2];
            objects[0] = "zhanglaosan";
            objects[1] = 19;
            invoke(object, "test", objects);
//            2.把全类名和方法名作为参数，执行方法
            objects[1] = 22;
            invoke2("datacenter.crudreposity.aapractise.reflection.Person",
                    "test",
                     objects);

//            调用注解
            //判断类是否存在注解
            if(cz.isAnnotationPresent(AgeValidator.class)){

            }
            Method method2 = cz.getDeclaredMethod("setAge", Integer.class);
            //判断类的方法是否存在注解
            if(method2.isAnnotationPresent(AgeValidator.class)){

            }
            int val = 30;
            //获取指定名称的注解
//            Annotation annotation = method2.getAnnotation(AgeValidator.class);
//            if(annotation != null){
//                if(annotation instanceof AgeValidator){
//                    AgeValidator ageValidator = (AgeValidator) annotation;
//                    if(val < ageValidator.min() || val > ageValidator.max()){
//                        throw new RuntimeException("年龄非法");
//                    }
//                }
//            }
            AgeValidator ageValidator = method2.getAnnotation(AgeValidator.class);
            if(ageValidator != null){
                if(val < ageValidator.min() || val > ageValidator.max()){
                        throw new RuntimeException("年龄非法");
                    }
            }
            method2.invoke(obj, 26);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**    1. 把类对象和类方法名作为参数，执行方法
     * @param obj:        方法执行的那个对象.
     * @param methodName: 类的一个方法的方法名. 该方法也可能是私有方法.
     * @param args:       调用该方法需要传入的参数
     * @return: 调用方法后的返回值
     */
    public static Object invoke(Object obj, String methodName, Object... args) throws Exception {
        //1. 获取 Method 对象
        //   因为getMethod的参数为Class列表类型，所以要把参数args转化为对应的Class类型。
        Class[] parameterTypes = new Class[args.length];
        for (int i = 0; i < args.length; i++) {
            parameterTypes[i] = args[i].getClass();
            System.out.println(parameterTypes[i]);
        }

        Method method = obj.getClass().getDeclaredMethod(methodName, parameterTypes);
//        1.如果使用getDeclaredMethod，就不能获取父类方法，如果使用getMethod，就不能获取私有方法
//　　　　2. 执行 Method 方法
//        3. 返回方法的返回值
        return method.invoke(obj, args);
    }

    /**   2.把全类名和方法名作为参数，执行方法
     * @param className: 某个类的全类名
     * @param methodName: 类的一个方法的方法名. 该方法也可能是私有方法.
     * @param args: 调用该方法需要传入的参数
     * @return: 调用方法后的返回值
     */
    public static Object invoke2(String className, String methodName, Object ... args){
        Object obj = null;

        try {
            obj = Class.forName(className).newInstance();
            //调用上一个方法
            return invoke(obj, methodName, args);
        }catch(Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取 clazz 的 methodName 方法. 该方法可能是私有方法, 还可能在父类中(私有方法)
     * 如果在该类中找不到此方法，就向他的父类找，一直到Object类为止
     　　　* 这个方法的另一个作用是根据一个类名，一个方法名，追踪到并获得此方法
     * @param clazz
     * @param methodName
     * @param parameterTypes
     * @return
     */
    public Method getMethod(Class clazz, String methodName,
                            Class ... parameterTypes){

        for(;clazz != Object.class; clazz = clazz.getSuperclass()){
            try {
                Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
                return method;
            } catch (Exception e) {}
        }

        return null;
    }

    /**
     *
     * @param obj: 某个类的一个对象
     * @param methodName: 类的一个方法的方法名.
     * 该方法也可能是私有方法, 还可能是该方法在父类中定义的(私有)方法
     * @param args: 调用该方法需要传入的参数
     * @return: 调用方法后的返回值
     */
    public Object invoke2(Object obj, String methodName,
                          Object ... args){
        //1. 获取 Method 对象
        Class [] parameterTypes = new Class[args.length];
        for(int i = 0; i < args.length; i++){
            parameterTypes[i] = args[i].getClass();
        }

        try {
            Method method = getMethod(obj.getClass(), methodName, parameterTypes);
            method.setAccessible(true);
            //2. 执行 Method 方法
            //3. 返回方法的返回值
            return method.invoke(obj, args);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}

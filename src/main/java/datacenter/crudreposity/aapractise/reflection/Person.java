package datacenter.crudreposity.aapractise.reflection;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */
public class Person {
    private String name;
    private String age;

    public Person() {
    }

    public Person(String name, String age) {
        this.name = name;
        this.age = age;
    }

    public Integer computeAge(Integer age){
        System.out.println("计算年龄" + age);
        return age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public void test(String name,Integer age){
        System.out.println("调用成功---姓名：" + name + "---年龄：" +  age);
    }
}

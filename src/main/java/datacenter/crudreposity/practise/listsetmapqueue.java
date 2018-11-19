package datacenter.crudreposity.practise;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */

@SpringBootApplication
@EnableAsync
@Service
public class listsetmapqueue {

    public static void main(String[] args) throws Exception {
        //set的用法:不能存储相同的元素。 https://blog.csdn.net/Dzy_water/article/details/79144206
        //TreeSet         :会将里面的元素默认排序。TreeSet(内部实现二叉树) 主要作用:排序 Comparable，Comparator，compareTo
        //Hashset         :它是无顺序的，利用hash算法给赋值,使用HashSet 主要用来去重,当Set集合在进行存储的时候,hashCode值相同时,
        // 会调用equals方法进行对比是同一个对象就不存;当hashCode值不相同时 不用调用equals方法,可以直接存
        //LinkedHashSet   :有序 怎么存就怎么取出来

//        看到array，就要想到角标。
//        看到link，就要想到first，last。
//        看到hash，就要想到hashCode,equals.                                       去重
//        看到tree，就要想到两个接口。Comparable，Comparator       排序
        setsPractise();

    }



    private static void setsPractise(){
        Set<Integer> sets = new TreeSet<>();
        Set<Integer> sets2 = new HashSet<>();
        int a =90;
        int b = 10;
        int c = 50;
        ((TreeSet) sets).add(a);
        ((TreeSet) sets).add(b);
        ((TreeSet) sets).add(c);

        ((HashSet) sets2).add(a);
        ((HashSet) sets2).add(b);
        ((HashSet) sets2).add(c);
        ((HashSet) sets2).add(90);
        //*********************************************************************
        TreeSet ts = new TreeSet(new MyComparator());
        ts.add(new Book("think in java", 100));
        ts.add(new Book("java 核心技术", 75));
        ts.add(new Book("现代操作系统", 50));
        ts.add(new Book("java就业教程", 35));
        ts.add(new Book("think in java", 100));
        ts.add(new Book("ccc in java", 100));
        System.out.println(ts);
        //*********************************************************************
        HashSet<Book> sets3 = new HashSet<>();
        sets3.add(new Book("think in java", 100));
        sets3.add(new Book("think in java", 100));
        sets3.add(new Book("java 核心技术", 75));
        sets3.add(new Book("现代操作系统", 50));
        //*********************************************************************
        int i = 0;

    }
}

class MyComparator implements Comparator {

    public int compare(Object o1, Object o2) {
        Book b1 = (Book) o1;
        Book b2 = (Book) o2;
//        System.out.println(b1+" comparator "+b2);
        if (b1.getPrice() > b2.getPrice()) {
            return -1;
        }
        if (b1.getPrice() < b2.getPrice()) {
            return 1;
        }
        return b1.getName().compareTo(b2.getName());
    }

}

class Book {
    private String name;
    private int price;

    public int hashCode(){  // 重写hashCode()方法。
        return name.hashCode()+ price * 55;
    }
    //去重   // 重写equals()方法。
    public boolean equals(Object obj){
        if(this == obj)
            return true;
        if(obj == null)
            return false;
        if(obj instanceof Book){
            Book p = (Book)obj;
            return name.equals(p.name) && price==p.price;
        }
        return false;
    }

    public Book() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Book(String name, int price) {

        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book [name=" + name + ", price=" + price + "]";
    }


}
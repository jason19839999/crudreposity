package datacenter.crudreposity.aapractise.arrays;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @描述
 * @创建人 shicong.zhang
 * @创建时间 $date$
 * @修改人和其它信息
 */

@SpringBootApplication
@EnableAsync
@Service
public class SetPractise {

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
        sets.add(a);
        sets.add(b);
        sets.add(c);

        sets2.add(a);
        sets2.add(b);
        sets2.add(c);
        sets2.add(90);

        //*********************************************************************
        //主要作用用于排序
        TreeSet<Book> ts = new TreeSet<Book>(new MyComparator());
        ts.add(new Book("think in java", 100));
        ts.add(new Book("java 核心技术", 75));
        ts.add(new Book("现代操作系统", 50));
        ts.add(new Book("java就业教程", 35));
        ts.add(new Book("think in java", 100));
        ts.add(new Book("ccc in java", 100));
        //遍历treeset
        Iterator<Book> iteratorTS = ts.iterator();
        while(iteratorTS.hasNext()){
            Book obj = iteratorTS.next();
            //处理业务逻辑
            //处理完业务逻辑，及时将obj释放掉，这样gc就主动回收了，特别注意
            obj= null;
        }
        Book objFirst = (Book)ts.first();
        Book objLast = (Book)ts.last();

        System.out.println(ts);
        //*********************************************************************
        //主要作用用于去重
        HashSet<Book> sets3 = new HashSet<Book>(2000);
        sets3.add(new Book("think in java", 100));
        sets3.add(new Book("think in java", 100));
        sets3.add(new Book("java 核心技术", 75));
        sets3.add(new Book("现代操作系统", 50));
        //遍历hashset
        Iterator<Book> iteratorHS = sets3.iterator();
        while (iteratorHS.hasNext()){
            Book obj = iteratorHS.next();
            //处理业务逻辑
            //处理完业务逻辑，及时将obj释放掉，这样gc就主动回收了。特别注意
            obj = null;
        }
        //*********************************************************************
        //有序 怎么存就怎么取出来
        Set<Integer> set4 = new LinkedHashSet<>();
        set4.add(100);
        set4.add(106);
        set4.add(101);
        set4.add(108);
        set4.add(102);
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
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
        //set的用法:不能存储相同的元素。
        // TreeSet:会将里面的元素默认排序。
        //Hashset:它是无顺序的，利用hash算法给赋值
//        看到array，就要想到角标。
//        看到link，就要想到first，last。
//        看到hash，就要想到hashCode,equals.
//        看到tree，就要想到两个接口。Comparable，Comparator
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

        TreeSet ts = new TreeSet(new MyComparator());
        ts.add(new Book("think in java", 100));
        ts.add(new Book("java 核心技术", 75));
        ts.add(new Book("现代操作系统", 50));
        ts.add(new Book("java就业教程", 35));
        ts.add(new Book("think in java", 100));
        ts.add(new Book("ccc in java", 100));
        System.out.println(ts);

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
    private double price;

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

    public void setPrice(double price) {
        this.price = price;
    }

    public Book(String name, double price) {

        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book [name=" + name + ", price=" + price + "]";
    }


}
package datacenter.crudreposity.aapractise.arrays;

import java.util.*;

public class MapPractise {
    //尽量指定集合的大小
    //HashMap的使用
    public static Map<String, String> map = new HashMap<>(1000);

    //TreeMap的使用→ 这里要注意一下  排序针对的是key的部分，不对value进行排序
    public static Map<Pig, String> mapTreeMap = new TreeMap(new MyComparatorPig());

    public static void main(String args[]) {

        //HashMap的使用
        map.put("name1", "1");
        map.put("name1", "12");
        map.put(null, null);
        map.put("name2", "2");
        map.put("name3", "3");
        //通过entrySet遍历
        Set<Map.Entry<String, String>> set1 = map.entrySet();
        Iterator<Map.Entry<String, String>> iterator1 = set1.iterator();
        while (iterator1.hasNext()) {
            Map.Entry<String, String> entry = iterator1.next();
            String key = entry.getKey();
            String value = entry.getValue();
        }

        //通过keySet遍历
        Set<String> set2 = map.keySet();
        Iterator<String> iterator2 = set2.iterator();
        while (iterator2.hasNext()) {
            String key = iterator2.next();
            String value = map.get(key);
        }

        //treeMap的使用
        Pig pig1 = new Pig();
        pig1.setName("aa");
        pig1.setPrice(100);
        mapTreeMap.put(pig1,"BJ");
        pig1 = new Pig();
        pig1.setName("bb");
        pig1.setPrice(10);
        mapTreeMap.put(pig1,"SH");
        pig1 = new Pig();
        pig1.setName("cc");
        pig1.setPrice(50);
        mapTreeMap.put(pig1,"GD");
        //查询此key的value
        pig1 = new Pig();
        pig1.setName("bb");
        pig1.setPrice(10);
        String value = mapTreeMap.get(pig1);
        //通过entrySet遍历
        Set<Map.Entry<Pig,String>> set3 = mapTreeMap.entrySet();
        Iterator<Map.Entry<Pig,String>> iterator3 = set3.iterator();
        while(iterator3.hasNext()){
            Map.Entry<Pig,String>  entry = iterator3.next();
            Pig key = entry.getKey();
            String value2 = entry.getValue();
        }

        int i = 0;
    }


}

class MyComparatorPig implements Comparator {

    public int compare(Object o1, Object o2) {
        Pig b1 = (Pig) o1;
        Pig b2 = (Pig) o2;
//        System.out.println(b1+" comparator "+b2);
        if (b1.getPrice() < b2.getPrice()) {
            return -1;
        }
        if (b1.getPrice() > b2.getPrice()) {
            return 1;
        }
        return b1.getName().compareTo(b2.getName());
    }

}

class Pig {
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
        if(obj instanceof Pig){
            Pig p = (Pig)obj;
            return name.equals(p.name) && price==p.price;
        }
        return false;
    }

    public Pig() {

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

    public Pig(String name, int price) {

        this.name = name;
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book [name=" + name + ", price=" + price + "]";
    }


}
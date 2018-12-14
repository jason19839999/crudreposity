package datacenter.crudreposity.aapractise.arrays;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HashTablePractise {
    public static Hashtable<String,String > hashtable = new Hashtable<String,String>(100);

    public static void main(String args[]){
        //hashtable key和value都不能为null
        hashtable.put("name1","1");
        hashtable.put("name2","2");
//        hashtable.put("name1",null);
//        hashtable.put(null,null);

        //通过entrySet遍历HashTable
       Set<Map.Entry<String,String>> entrySet = hashtable.entrySet();
        Iterator<Map.Entry<String,String>> iterator = entrySet.iterator();
        while(iterator.hasNext()){
            Map.Entry<String,String> obj = iterator.next();
            String key = obj.getKey();
            String value = obj.getValue();
        }

        //通过keyset遍历HashTable
        Set<String> set = hashtable.keySet();
         Iterator<String> iterator1 = set.iterator();
         while(iterator1.hasNext()){
             String key = iterator1.next();
             String value = hashtable.get(key);
         }

         //总结：和HashMap的不同点  ①key和value 都不能为null  ,hashMap可以 ② synchronized修饰，也就是说hashTable是同步容器，Vector（它对应List）和Stack也是。

       int i =0;
    }


}

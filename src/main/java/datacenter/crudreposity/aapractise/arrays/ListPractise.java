package datacenter.crudreposity.aapractise.arrays;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class ListPractise {

    private static List<Integer> lstArrayList = new ArrayList<Integer>(100);
    private static List<Integer> lstLinkList = new LinkedList<Integer>();

//    性能顺序：lambda parallelStream().forEach()>
//                      lambda stream().forEach()≈lambda forEach()>
//                     classical iterator≈classical forEach>
//                    classical for
    public static void main(String args[]){
        lstArrayList.add(1);
        lstArrayList.add(2);
        lstArrayList.add(3);
        Iterator<Integer> inIterator1 = lstArrayList.iterator();
        while(inIterator1.hasNext()){
          Integer tmp = inIterator1.next();
        }
        for (Integer tmp:lstArrayList
             ) {
            Integer t = tmp;
        }

        //stream去重  判断1是否在lstArrayList存在，这个很有用，比对数据很有用
        boolean isRepeat = lstArrayList.stream().anyMatch(u -> u.equals(1));
        isRepeat = false;


//        Integer[] inss = new Integer[2];
//        int [] arrays = {0,1,2,3,4,5};
        Integer[] ins = {1,2,3,4,5,6};
        int sum = Stream.of(ins).reduce(0, Integer::sum);



    }


}

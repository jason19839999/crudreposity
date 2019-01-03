package datacenter.crudreposity.aapractise.arrays;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class ArrayListHashMap {

    public static void main(String[] args) {
        int [] arrays = {1,2,3,4,5};
        int [] result = new int[2];
        result = twoIndex(arrays,7);
        result = null;
    }

    private static int [] twoIndex(int [] arrays,int target){
        int [] result = new int [2];
        HashMap<Integer,Integer> hashMap = new HashMap<>();
        for(int i=0;i<arrays.length-1;i++){
            int temp = 0;
            temp = target - arrays[i];
            if(hashMap.containsValue(temp)){
                result[0] = getKey(temp,hashMap);
                result[1] =i;
                return  result;
            }else{
                hashMap.put(i,arrays[i]);
            }
        }
        return result;
    }

    private static int getKey(int value,HashMap<Integer,Integer> hashMap){
       Set<Map.Entry<Integer,Integer>> set = hashMap.entrySet();
       Iterator<Map.Entry<Integer,Integer>> iterator = set.iterator();
       while(iterator.hasNext()){
           Map.Entry<Integer,Integer> entry = iterator.next();
           if(entry.getValue().equals(value)){
               return entry.getKey();
           }
       }
        return 0;
    }

}

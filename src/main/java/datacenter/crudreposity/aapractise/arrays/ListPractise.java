package datacenter.crudreposity.aapractise.arrays;

import org.hibernate.id.IncrementGenerator;

import java.util.*;
import java.util.stream.Stream;

public class ListPractise {

    private static List<Integer> lstArrayList = new ArrayList<Integer>(100);
    private static List<Integer> lstLinkList = new LinkedList<Integer>();

    public static void main(String args[]) {
        lstArrayList.add(1);
        lstArrayList.add(2);
        lstArrayList.add(3);
        Iterator<Integer> inIterator1 = lstArrayList.iterator();
        while (inIterator1.hasNext()) {
            Integer tmp = inIterator1.next();
        }
        for (Integer tmp : lstArrayList
                ) {
            Integer t = tmp;
        }
        //stream去重  判断1是否在lstArrayList存在，这个很有用，比对数据很有用
        boolean isRepeat = lstArrayList.stream().anyMatch(u -> u.equals(1));
        isRepeat = false;
        //        Integer[] inss = new Integer[2];
        //        int [] arrays = {0,1,2,3,4,5};
        Integer[] ins = {1, 2, 3, 4, 5, 6};
        int sum = Stream.of(ins).reduce(0, Integer::sum);

        //通过Collections.sort进行排序
        Collections.sort(lstArrayList, new Comparator<Integer>() {
            @Override
            public int compare(Integer integer, Integer t1) {
               if(integer > t1){
                   return -1;
               }else if(integer < t1){
                   return 1;
               }else{
                   return 0;
               }
            }
        });
       //总结：this.elementData = Arrays.copyOf(this.elementData, var3);
        // System.arraycopy(var0, 0, var3, 0, Math.min(var0.length, var1));
        // ArrayList每次插入都重新复制一遍数组，这里数据量大的时候会影响性能。
        //    性能顺序：lambda parallelStream().forEach()>
        //                      lambda stream().forEach()≈lambda forEach()>
        //                     classical iterator≈classical forEach>
        //                    classical for
        //************************************************************************************************************************************

        lstLinkList.add(4);
        lstLinkList.add(5);
        lstLinkList.add(6);
        Iterator<Integer> iterator2 = lstLinkList.iterator();
        while(iterator2.hasNext()){
            Integer tmp = iterator2.next();
        }
        //总结：lstLinkList  双向链表结构的list
        //    代码采用for循环、迭代器、foreach方式，遍历包含10万个元素的LinkedList，通过输出结果可以看出，
        // foreach语句效率最高，其次是迭代器，效率最差的是for循环

        //    LinkedList存储元素的数据结构是【双向链表结构】，由存储元素的结点连接而成，每一个节点都包含前一个节点的引用，
        // 后一个节点的引用和节点存储的值。当一个新节点插入时，只需要修改其中保持先后关系的节点的引用即可。
        //        Node(LinkedList.Node<E> var1, E var2, LinkedList.Node<E> var3) {
        //            this.item = var2;
        //            this.next = var3;
        //            this.prev = var1;
        //        }

        //        void linkLast(E var1) {     → add() 方法
        //            LinkedList.Node var2 = this.last;
        //            LinkedList.Node var3 = new LinkedList.Node(var2, var1, (LinkedList.Node)null);
        //            this.last = var3;
        //            if (var2 == null) {
        //                this.first = var3;
        //            } else {
        //                var2.next = var3;
        //            }
        //
        //            ++this.size;
        //            ++this.modCount;
        //        }

    }


}

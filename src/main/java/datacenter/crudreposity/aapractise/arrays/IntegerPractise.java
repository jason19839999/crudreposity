package datacenter.crudreposity.aapractise.arrays;

public class IntegerPractise {


    public static void main(String [] args){
        Integer i = new Integer(100);
        Integer i2 = new Integer(100);
        Integer j = 100;
        int k = 100;
        Integer h = 128;  //由于-128--127之间实在缓存里面取，不是new  Integer出来的，所以是不同的对象
        Integer l = 128;
//        Integer h = 127;
//        Integer l = 127;
        boolean isEquals = i == j;
        boolean isRealEquals = i.equals(i2);
        boolean isEquals2 = i == k;
        boolean isEquals3 = h ==l;
        boolean isEquals4 = h.equals(l);
        String tmp ="";

    }
}

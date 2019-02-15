package datacenter.crudreposity.aapractise.threadProcessor;

public class threadBootstrap {

    public static void main(String args[]) throws Exception{

        myThreadPoolProcessor.myPool();

        //当类的局部变量和静态变量作为锁对象时的区别
        StaticSynchronized ss = new StaticSynchronized();
        StaticSynchronized ss2 = new StaticSynchronized();
        StaticSynchronized ss3 = new StaticSynchronized();

        new Thread(ss).start();
        new Thread(ss).start();
        new Thread(ss).start();

//        new Thread(ss).start();
//        new Thread(ss2).start();
//        new Thread(ss3).start();

    }
}

package datacenter.crudreposity.aapractise.threadProcessor;

import java.util.concurrent.locks.ReentrantLock;

public class StaticSynchronized implements Runnable {

    //static  和非static 区别  static 修饰的相当于类锁，类的不同对象时多个线程访问是同步的，
    // 而非static和在代码块用this是一样的，同一个对象时 → 多个线程访问时同步的，不同对象时 → 多个线程访问时异步的。
//    private  Object locks = new Object();
    private static Object locks = new Object();

    //公平锁  非公平锁
    private ReentrantLock rt = new ReentrantLock(false);
    @Override
    public void run() {
        while(true) {
            try {
                rt.lock();
                System.out.println("get lock ： " + Thread.currentThread().getName());
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                if (rt != null) {
                    rt.unlock();
                }
            }
        }
    }

//    @Override
//    public void run() {
//        synchronized (locks){
//            System.out.println("线程名称 开始： " + Thread.currentThread().getName());
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("线程名称 结束： " + Thread.currentThread().getName());
//        }
//
//    }

//    @Override
//    public void run() {
//        synchronized (StaticSynchronized.class){
//            System.out.println("线程名称 开始： " + Thread.currentThread().getName());
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("线程名称 结束： " + Thread.currentThread().getName());
//        }
//
//    }

//    @Override
//    public void run() {
//        synchronized (this){
//            System.out.println("线程名称 开始： " + Thread.currentThread().getName());
//            try {
//                Thread.sleep(200);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            System.out.println("线程名称 结束： " + Thread.currentThread().getName());
//        }
//
//    }


}

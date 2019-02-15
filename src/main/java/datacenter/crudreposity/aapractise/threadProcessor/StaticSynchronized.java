package datacenter.crudreposity.aapractise.threadProcessor;

public class StaticSynchronized implements Runnable {

    //static  和非static 区别  static 修饰的相当于类锁，类的不同对象时 → 多个线程访问是同步的，
    // 而非static和在代码块用this是一样的，同一个对象时 → 多个线程访问时同步的，不同对象时 → 多个线程访问时异步的。
//    private  Object locks = new Object();
    private  static Object locks = new Object();


    @Override
    public void run() {
        synchronized (locks){
            System.out.println("线程名称 开始： " + Thread.currentThread().getName());
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程名称 结束： " + Thread.currentThread().getName());
        }

    }

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

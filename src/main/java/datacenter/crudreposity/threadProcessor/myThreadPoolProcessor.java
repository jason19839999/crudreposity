package datacenter.crudreposity.threadProcessor;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class myThreadPoolProcessor {
    //我创建了一个包含2条线程的线程池，但执行3个任务，从结果可以看出第三个任务使用的线程名称与第一个任务相同，即任务3与任务1使用同一条线程。
    // 还可以看出，任务3实在前两个任务完成后再执行的。
    public static void myPool() {
        ExecutorService service = Executors.newFixedThreadPool(3);
//        service.execute(new PrintStr("A"));// AB同时执行
//        service.execute(new PrintStr("B"));
//        service.execute(new PrintStr("C"));// 在AB完成后执行
//        service.shutdown();

//        也就是说， CompletionService实现了生产者提交任务和消费者获取结果的解耦，生产者和消费者都不用关心任务的完成顺序，由 CompletionService来保证，
//        消费者一定是按照任务完成的先后顺序来获取执行结果。
//        应用场景
//        当向Executor提交多个任务并且希望获得它们在完成之后的结果，如果用FutureTask，可以循环获取task，并调用get方法去获取task执行结果，但是如果task还未完成，
//       获取结果的线程将阻塞直到task完成，由于不知道哪个task优先执行完毕，使用这种方式效率不会很高。
//      在jdk5时候提出接口CompletionService，它整合了Executor和BlockingQueue的功能，可以更加方便在多个任务执行时获取到任务执行结果。
//        ---------------------
//                作者：miaomiaoLoveCode
//        来源：CSDN
//        原文：https://blog.csdn.net/u010185262/article/details/56017175
//        版权声明：本文为博主原创文章，转载请附上博文链接！
//        long startTime = System.currentTimeMillis(); // 开始时间
//        CompletionService completionService = new ExecutorCompletionService(service);
//        completionService.submit(new PrintStr2("A"));
//        completionService.submit(new PrintStr3("B"));
//        completionService.submit(new PrintStr4("C"));
//        int count = 0;
//        String result = "";
//        while (count < 3) { // 等待三个任务完成
//            Future<String> future =  completionService.poll();
//            if ( future != null) {
//                try {
//                    result += String.valueOf(future.get()) + "---";
//                    count++;
//                }catch (Exception ex){
//                    ex.printStackTrace();
//                }
//            }else{
//                System.out.println("存在尚未执行完的任务");
//            }
//        }
//        System.out.println(result);
//        long costTime = System.currentTimeMillis() - startTime; // 消耗时间
//        System.out.println("myPool() 总耗时：" + costTime + " 毫秒");
//        service.shutdown();
        try {
            long startTime = System.currentTimeMillis(); // 开始时间
            Future<String> future2 = service.submit(new PrintStr2("A"));
            Future<String> future3 = service.submit(new PrintStr3("B"));
            Future<String> future4 = service.submit(new PrintStr4("C"));

            String result4 = String.valueOf(future4.get());
            String result3 = String.valueOf(future3.get());
            String result2 = String.valueOf(future2.get());
            long costTime = System.currentTimeMillis() - startTime; // 消耗时间
            System.out.println("myPool() 总耗时：" + costTime + " 毫秒");
            result2 = "";
            service.shutdown();
        } catch (Exception ex) {
            ex.printStackTrace();
        }


    }


    //分析结果，newCachedThreadPool（）创建的线程池，线程数量根据需要创建。即如果池中没有空闲线程，则创建一条新线程（3个任务创建了3个线程）。
    // 若有有空闲线程，则复用（任务D、E复用了线程2和2）。
    public static void myPool2() throws Exception {
        ExecutorService service = Executors.newCachedThreadPool();
        service.execute(new PrintStr("A"));
        service.execute(new PrintStr("B"));
        service.execute(new PrintStr("C"));
        // 等待以上任务执行完毕
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        service.execute(new PrintStr("D"));// 会复用空闲的Thread
        service.execute(new PrintStr("E"));// 会复用空闲的Thread
        service.execute(new PrintStr("F"));// 会复用空闲的Thread
        //调用返回值的callable
        Future<String> future = service.submit(new MyCallableTask());
        String result = future.get();
        service.shutdown();
    }


}

class PrintStr implements Runnable {
    String str;

    public PrintStr(String str) {
        this.str = str;
    }

    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(str + " : " + Thread.currentThread().getName());
    }
}

class PrintStr2 implements Callable<String> {
    String str;

    public PrintStr2(String str) {
        this.str = str;
    }

    public String call() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(str + " : " + Thread.currentThread().getName());
        return "OK2";
    }
}

class PrintStr3 implements Callable<String> {
    String str;

    public PrintStr3(String str) {
        this.str = str;
    }

    public String call() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(str + " : " + Thread.currentThread().getName());
        return "OK3";
    }
}

class PrintStr4 implements Callable<String> {
    String str;

    public PrintStr4(String str) {
        this.str = str;
    }

    public String call() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(str + " : " + Thread.currentThread().getName());
        return "OK4";
    }
}

//线程池的意义在于：
// 1、减少在创建和销毁线程上所花的时间以及系统资源的开销，提升任务执行性能。
// 2、控制进程中线程数量的峰值，避免系统开销过大。
//通过线程池，可创建一定数量的线程，并由线程池管理。在需要执行任务时，直接使用其中的线程。任务执行完成后，线程保留，并可用于执行下一个任务。如果任务比线程多，则等待线程空闲。

//线程池使用中的几个关键对象关系：
//Executor：是一个执行接口，只包含 void execute(Runnable command)声明。
//ExecutorService：继承Executor接口，提供管理、终止线程的方法，可以跟踪任务执行状况生成 Future。
//Executors：定义 Executor、ExecutorService、ScheduledExecutorService、ThreadFactory 和 Callable 类的工厂和实用方法。用于创建ExecutorService 或ThreadFactory 等。
//可以看出Executors充当的是一个工具角色，主要作用是创建和处理。

//下面详细介绍一下线程池的几个常用方法。
//1、创建线程池
//创建线程池常用如下两个方法：
//ExecutorService service1 = Executors.newFixedThreadPool(2);
//ExecutorService service2 = Executors.newCachedThreadPool();
//Executors.newFixedThreadPool(2) 初始化指定数量的线程，如果线程意外终止，将重建并替换。
//Executors.newCachedThreadPool() 根据需要创建新线程的线程池，但是在以前构造的线程可用时将重用它们。若创建的线程空闲60秒以上则将其销毁并移除。
//2、关闭线程池
//关闭线程池有两种方法
//void shutdown()：
//启动一次顺序关闭，正在执行的任务会继续执行，但不接受新任务。所有任务完成后，关闭线程池。
//List < Runnable > shutdownNow():
//试图停止所有执行中的任务，暂停等待中的任务，并返回等待执行的任务列表，立即关闭线程池。
//注意：无法保证能够停止正在处理的活动执行任务，但是会尽力尝试。无法响应中断的任务可能无法终止。








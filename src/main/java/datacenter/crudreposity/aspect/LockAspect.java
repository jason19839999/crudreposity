package datacenter.crudreposity.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Scope;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Component
@Scope
@Aspect
@Order(1)
//order越小越是最先执行，但更重要的是最先执行的最后结束。order默认值是2147483647
public class LockAspect {
	/**
     * 思考：为什么不用synchronized
     * service 默认是单例的，并发下lock只有一个实例
     */
	private static  Lock lock = new ReentrantLock(true);//互斥锁 参数默认false，不公平锁  
	
	//Service层切点     用于记录错误日志
	@Pointcut("@annotation(datacenter.crudreposity.aspect.Servicelock)")
	public void lockAspect() {
		
	}
	
    @Around("lockAspect()")
    public  Object around(ProceedingJoinPoint joinPoint) throws InterruptedException {
		lock.lock();
//		lock.tryLock();
//		lock.lockInterruptibly();
//    	lock.tryLock(100,TimeUnit.SECONDS);
    	Object obj = null;
		try {
			obj = joinPoint.proceed();  //执行完这个，再执行AuthorizeAspect，进行用户身份验证，如果通过执行相应的Controller,最后执行 finally  lock.unlock();
		} catch (Throwable e) {
			e.printStackTrace();
		} finally{
			lock.unlock();
		}
    	return obj;
    } 
}


//二.什么ReentrantLock  : 以对象的方式来操作对象锁.相对于sychronized需要在finally中去释放锁

//	  除了synchronized的功能,多了三个高级功能.
//
//			等待可中断,公平锁,绑定多个Condition.
//
//          1.等待可中断
//
//	                    在持有锁的线程长时间不释放锁的时候,等待的线程可以选择放弃等待.   tryLock(long timeout, TimeUnit unit)
//
//			2.公平锁
//
//	                   按照申请锁的顺序来一次获得锁称为公平锁.synchronized的是非公平锁,ReentrantLock可以通过构造函数实现公平锁.    new RenentrantLock(boolean fair)
//
//          3.绑定多个Condition
//
//	                    通过多次newCondition可以获得多个Condition对象,可以简单的实现比较复杂的线程同步的功能.通过await(),signal();

//不同：
//			1.ReentrantLock功能性方面更全面，比如时间锁等候，可中断锁等候，锁投票等，因此更有扩展性。在多个条件变量和高度竞争锁的地方，
//            用ReentrantLock更合适，ReentrantLock还提供了Condition，对线程的等待和唤醒等操作更加灵活，一个ReentrantLock可以有多个Condition实例，所以更有扩展性。
//			2.ReentrantLock必须在finally中释放锁，否则后果很严重，编码角度来说使用synchronized更加简单，不容易遗漏或者出错。
//			3.ReentrantLock 的性能比synchronized会好点。
//			4.ReentrantLock提供了可轮询的锁请求，他可以尝试的去取得锁，如果取得成功则继续处理，取得不成功，可以等下次运行的时候处理，所以不容易产生死锁，而synchronized
//         则一旦进入锁请求要么成功，要么一直阻塞，所以更容易产生死锁。



//        1、ReentrantLock 拥有Synchronized相同的并发性和内存语义，此外还多了 锁投票，定时锁等候和中断锁等候
//
//		              线程A和B都要获取对象O的锁定，假设A获取了对象O锁，B将等待A释放对O的锁定，
//
//		             如果使用 synchronized ，如果A不释放，B将一直等下去，不能被中断
//
//		            如果 使用ReentrantLock，如果A不释放，可以使B在等待了足够长的时间以后，中断等待，而干别的事情
//
//		      ReentrantLock获取锁定与三种方式：
//		           a)  lock(), 如果获取了锁立即返回，如果别的线程持有锁，当前线程则一直处于休眠状态，直到获取锁
//
//		           b) tryLock(), 如果获取了锁立即返回true，如果别的线程正持有锁，立即返回false；
//
//		           c) tryLock (long timeout, TimeUnit  unit)，   如果获取了锁定立即返回true，如果别的线程正持有锁，会等待参数给定的时间，在等待的过程中，
//                        如果获取了锁定，就返回true，如果等待超时，返回false；返回到等待池继续等待。
//
//		          d) lockInterruptibly:如果获取了锁定立即返回，如果没有获取锁定，当前线程处于休眠状态，直到或者锁定，或者当前线程被别的线程中断
//
//		2、synchronized是在JVM层面上实现的，不但可以通过一些监控工具监控synchronized的锁定，而且在代码执行时出现异常，JVM会自动释放锁定，
//                但是使用Lock则不行，lock是通过代码实现的，要保证锁定一定会被释放，就必须将 unLock()放到finally{} 中
//
//		3、在资源竞争不是很激烈的情况下，Synchronized的性能要优于ReetrantLock，但是在资源竞争很激烈的情况下，Synchronized的性能会下降几十倍，
//               但是ReetrantLock的性能能维持常态；
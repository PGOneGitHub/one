package msb.chizhang.study.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class StudyTestReentrantLock2 {
    public static void main(String[] arr) {
        Lock lock = new ReentrantLock();

        Thread t1 = new Thread(() -> {
            try {
                lock.lock();
                System.out.println("t1 Start");
                for (int i = 0; i < 50; i++) {
                    TimeUnit.SECONDS.sleep(1);
                    System.out.println("t1..." + i);
                }
                System.out.println("t1 End");
            } catch (InterruptedException e) {
                System.out.println("Interrupted!");
            } finally {
                lock.unlock();
            }
        });

        t1.start();

        Thread t2 = new Thread(() -> {
            try {
                lock.lockInterruptibly();   // 对interrupt进行响应
                System.out.println("t2 Start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t2 End");
            } catch (InterruptedException e) {
                System.out.println("t2 Interrupted!");
            } finally {
                try {
                    lock.unlock();  // todo ??
                } catch (Exception e) {

                }
            }
        });

        t2.start();

        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t2.interrupt();     // 打断线程2
    }
}

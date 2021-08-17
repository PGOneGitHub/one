package msb.chizhang.study.lock;

import java.util.concurrent.TimeUnit;

public class StudyTestLock {
    /**
     * Synchronized与ReentrantLock的区别:
     *
     * synchronized自动加锁、自动释放锁；reentrantLock手动加锁、手动释放锁
     *
     * synchronized有锁升级的过程；reentrantLock是CAS的实现，有等待队列AQS的概念
     */
    synchronized void m1() {
        for (int i = 0; i < 10; i++) {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
            System.out.println(i);

            if (i == 2) {
                m3();   // 证明锁可以重录
            }
        }
    }

    synchronized void m2() {
        System.out.println("m2 ...");
    }

    synchronized void m3() {
        System.out.println("m3 ...");
    }

    public static void main(String[] arr) {
        StudyTestLock rl = new StudyTestLock();
        new Thread(rl::m1).start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(rl::m2).start(); // 得不到锁，没法立即执行
    }
}

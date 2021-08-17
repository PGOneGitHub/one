package msb.chizhang.study.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 可以替代synchronized
 */
public class StudyTestReentrantLock1 {
    Lock lock = new ReentrantLock();

    /**
     * 可以替代synchronized
     */
    void m1() {
        try {
            lock.lock();
            // ...
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    void TryLockTest1() {
        try {
            lock.lock();
            for (int i = 0; i < 10; i++) {
                TimeUnit.SECONDS.sleep(1);
                System.out.println(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.lock();
        }
    }

    void TryLockTest2() {
        boolean locked = false;
        try {
            locked = lock.tryLock(5, TimeUnit.SECONDS);
            System.out.println("try..." + locked);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            if (locked) {
                lock.lock();
            }
        }
    }

    public static void main(String[] arr) {
        StudyTestReentrantLock1 rl = new StudyTestReentrantLock1();
        new Thread(rl::TryLockTest1).start();
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(rl::TryLockTest2).start();
    }
}

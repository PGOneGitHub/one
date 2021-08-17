package msb.chizhang.study.lock;

import java.util.concurrent.Semaphore;

/**
 * 限流-最多允许多少线程同时运行
 * 与线程池区分：Semaphore代表多个线程中，允许同时运行的线程数只有n个，
 * 以下列例子为例，允许线程池中n个线程同时acquire，但同一时间acquire到的只有2个
 */
public class StudyTestSemaphore {
    public static void main(String[] args) {
        // permits:锁的数量，当数量为1时，只有T1线程首先能获得锁，T1执行完成，T2才能获得锁执行
        //fair:是否公平。先进入等待队列的线程先获得锁
        //Semaphore s = new Semaphore(2);

        /**
         * 参数1：得到许可的上限设为2
         * 参数2：公平锁,默认false非公平
         */
        Semaphore s = new Semaphore(2, true);
        //允许一个线程同时执行
        //Semaphore s = new Semaphore(1);

        new Thread(() -> {
            try {
                // 锁的数量减1（持有锁）
                s.acquire();    // 等待获得许可

                System.out.println("T1 running...");
                Thread.sleep(200);
                System.out.println("T1 running...");

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                // 恢复锁的数量（释放锁）
                s.release();
            }
        }).start();

        new Thread(() -> {
            try {
                s.acquire();

                System.out.println("T2 running...");
                Thread.sleep(200);
                System.out.println("T2 running...");

                s.release();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}

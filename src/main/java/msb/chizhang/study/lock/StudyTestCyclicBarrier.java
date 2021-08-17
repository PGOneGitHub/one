package msb.chizhang.study.lock;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 栅栏
 * 场景：等齐所有线程才能继续往下跑，
 * 需要访问数据库，网络，文件/并发执行
 * 不是限流（而限流实际会用Guava RateLimiter）
 */
public class StudyTestCyclicBarrier {
    public static void main(String[] arr) {
        CyclicBarrier barrier2 = new CyclicBarrier(20, () -> System.out.println("满人"));

        CyclicBarrier barrier = new CyclicBarrier(20, new Runnable() {
            @Override
            public void run() {
                System.out.println("满人，出发");
            }
        });

        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    barrier.await();
                } catch (InterruptedException e) {

                } catch (BrokenBarrierException e) {

                }
            }).start();
        }
    }
}

package msb.chizhang.study.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;

public class StudyTestLockSupport {
    public static void main(String[] arr) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                System.out.println(i);

                if (i == 5) {
                    // 线程停止
                    LockSupport.park();
                }

                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        t.start();

        // 如果放在这里，是和t线程同时执行的，代表上手就不让他停，unpark可以先于park之前调用
        //LockSupport.unpark(t);

        try {
            TimeUnit.SECONDS.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after 8 seconds");
        LockSupport.unpark(t);
    }
}

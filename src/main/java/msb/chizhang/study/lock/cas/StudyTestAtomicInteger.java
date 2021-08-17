package msb.chizhang.study.lock.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StudyTestAtomicInteger {
    AtomicInteger count = new AtomicInteger(0);

    void m() {
        for (int i = 0; i < 1000; i++) {
            count.incrementAndGet();
        }
    }

    public static void main(String[] arr) {
        StudyTestAtomicInteger t = new StudyTestAtomicInteger();
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            threads.add(new Thread(t::m, "thread-" + i));
        }

        threads.forEach((o) -> o.start());
        threads.forEach((o) -> {
            try {
                o.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        System.out.println(t.count);
    }
}

package msb.chizhang.study.lock;

import javax.xml.bind.annotation.W3CDomHandler;
import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

public class MSProducersAndConsumers<T> {
    private final LinkedList<T> lists = new LinkedList<>();
    private final int MAX = 10;
    private int count = 0;

    public synchronized void put(T t) {
        while (lists.size() == MAX) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        lists.add(t);
        ++count;
        this.notifyAll();   // 瑕疵(生产者只负责叫醒消费者，而不是另外一个生产者也来抢这个锁)
    }

    public synchronized T get() {
        T t = null;
        while (lists.size() == 0) {
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        t = lists.removeFirst();
        count--;
        this.notifyAll();
        return t;
    }

    public static void main(String[] arr) {
        MSProducersAndConsumers<String> c = new MSProducersAndConsumers();

        // 消费者
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                for (int j = 0; j < 5; j++) System.out.println("消费" + c.get());
            }, "c" + i).start();
        }

        try {
            TimeUnit.SECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 生产
        for (int i = 0; i < 2; i++) {
            new Thread(() -> {
                for (int j = 0; j < 25; j++) c.put(Thread.currentThread().getName() + " " + j);
            }, "p" + i).start();
        }
    }
}

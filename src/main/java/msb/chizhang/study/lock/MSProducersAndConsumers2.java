package msb.chizhang.study.lock;

import java.util.LinkedList;
import java.util.Locale;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MSProducersAndConsumers2<T> {
    private final LinkedList<T> lists = new LinkedList<>();
    private final int MAX = 10;
    private int count = 0;

    private Lock lock = new ReentrantLock();
    // synchronized只有一个等待队列，但是Condition可以new多个等待队列
    private Condition producer = lock.newCondition();   // new一个生产者的等待队列
    private Condition consumer = lock.newCondition();   // new一个消费者的等待队列

    public void put(T t) {
        try {
            lock.lock();
            while (lists.size() == MAX) {
                producer.wait();
            }

            lists.add(t);
            ++count;
            consumer.signalAll();   // 通知消费者线程进行消费
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public T get() {
        T t = null;
        try {
            lock.lock();
            while (lists.size() == 0) {
                consumer.wait();
            }

            t = lists.removeFirst();
            count--;
            producer.signalAll();   // 通知生产者线程进行生产
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
        return t;
    }
}

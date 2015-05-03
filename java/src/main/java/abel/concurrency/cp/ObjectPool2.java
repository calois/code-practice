package abel.concurrency.cp;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class ObjectPool2<T> implements ObjectPool<T> {

	private BlockingQueue<T> dataQueue = new LinkedBlockingQueue<>();

	private Lock lock = new ReentrantLock();
	private Condition condition = lock.newCondition();

	@Override
	public void print() {
		System.out.println(dataQueue.size());
	}

	public ObjectPool2(int initSize, int maxActive, int maxIdle) {
		dataQueue.addAll(IntStream.range(0, initSize).parallel()
				.mapToObj(it -> {
					return createObject();
				}).collect(Collectors.toList()));
		Thread thread = new Thread(() -> {
			for (int i = 0; i < maxActive - initSize; i++) {
				while (dataQueue.size() >= maxIdle) {
					lock.lock();
					try {
						condition.wait();
					} catch (Exception e) {
					} finally {
						lock.unlock();
					}
				}
				dataQueue.add(createObject());
			}
		});
		thread.start();
	}

	@Override
	public T checkout() {
		T t = dataQueue.poll();
		if (null == t) {
			lock.lock();
			condition.signalAll();
			lock.unlock();
			try {
				return dataQueue.take();
			} catch (InterruptedException e) {
				return null;
			}
		}
		return t;

	}

	@Override
	public void checkin(T t) {
		dataQueue.add(t);
	}

}

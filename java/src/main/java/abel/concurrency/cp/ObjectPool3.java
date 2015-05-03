package abel.concurrency.cp;

import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;

public abstract class ObjectPool3<T> implements ObjectPool<T> {

	private BlockingQueue<T> factoryQueue;
	private Queue<T> remainingQueue;

	@Override
	public void print() {
		System.out.println(factoryQueue.size() + remainingQueue.size());
	}

	public ObjectPool3(int maxActive, int maxIdle) {
		factoryQueue = new LinkedBlockingDeque<T>(maxIdle);
		remainingQueue = new ConcurrentLinkedQueue<T>();
		Thread thread = new Thread(() -> {
			for (int i = 0; i < maxActive; i++) {
				factoryQueue.add(createObject());
			}
		});
		thread.start();
	}

	@Override
	public T checkout() {
		T t = remainingQueue.poll();
		if (null == t) {
			try {
				return factoryQueue.take();
			} catch (InterruptedException e) {
				return null;
			}
		}
		return t;

	}

	@Override
	public void checkin(T t) {
		if (!factoryQueue.offer(t)) {
			remainingQueue.add(t);
		}
	}

}

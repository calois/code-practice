package abel.concurrency.lock;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.IntStream;

public class ReadWriteLock1 {
	private int number = 0;
	private ReadWriteLock lock = new ReentrantReadWriteLock();
	private Lock readLock = lock.readLock();
	private Lock writeLock = lock.writeLock();

	public void read() {
		readLock.lock();
		System.out.println(number);
		readLock.unlock();
	}

	public void write() {
		writeLock.lock();
		System.out.println("Write Starts");
		try {
			TimeUnit.SECONDS.sleep(2);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		number++;
		System.out.println(number);
		System.out.println("Write Ends");
		writeLock.unlock();
	}

	public static void main(String[] args) throws InterruptedException {
		ReadWriteLock1 lock = new ReadWriteLock1();
		ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors
				.newCachedThreadPool();
		IntStream.range(0, 5).forEach(it -> {
			executor.execute(() -> {
				while (true) {
					lock.read();
					try {
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		});
		executor.execute(() -> {
			while (true) {
				lock.write();
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});

		executor.shutdown();
		executor.awaitTermination(1, TimeUnit.DAYS);
	}
}

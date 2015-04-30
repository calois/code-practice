package abel.concurrency.lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Lock1 {

	Lock lock = new ReentrantLock();
	Condition condition = lock.newCondition();

	private int number = 0;

	public void add() {
		lock.lock();
		while (number >= 50) {
			try {
				condition.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		number++;
		condition.signalAll();
		System.out.println(number);
		lock.unlock();
	}

	public void sub() {
		lock.lock();
		while (number <= 0) {
			try {
				condition.await();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		number--;
		System.out.println(number);
		condition.signalAll();
		lock.unlock();
	}

	public static void main(String[] args) {
		final Lock1 lock1 = new Lock1();
		Thread thread1 = new Thread(() -> {
			while (true) {
				lock1.add();
			}
		});
		Thread thread2 = new Thread(() -> {
			while (true) {
				lock1.sub();
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	);
		thread1.start();
		thread2.start();
	}
}

package abel.concurrency.syn;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Semaphore1 {

	private Semaphore semaphore;

	public Semaphore1(int n) {
		semaphore = new Semaphore(n);
	}

	public void take() throws InterruptedException {
		semaphore.acquire();
		try {
			System.out.println(Thread.currentThread().getName());
			TimeUnit.SECONDS.sleep(5);
		} finally {
			semaphore.release();
		}
	}

	public static void main(String[] args) {
		final Semaphore1 s = new Semaphore1(5);
		IntStream.range(0, 10).forEach(it -> {
			Thread thread = new Thread(() -> {
				while (true) {
					try {
						s.take();
					} catch (Exception e) {
						// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}	);
			thread.start();
		});
	}
}

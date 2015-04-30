package abel.concurrency.syn;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CountDownLatch1 implements Runnable {
	private CountDownLatch latch;

	public CountDownLatch1(int n) {
		latch = new CountDownLatch(n);
	}

	public void arrive() {
		System.out.println("Arrive");
		latch.countDown();
	}

	@Override
	public void run() {
		try {
			latch.await();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("All done");
	}

	public static void main(String[] args) {
		final CountDownLatch1 latch = new CountDownLatch1(5);
		Thread thread = new Thread(latch);
		thread.start();
		IntStream.range(0, 5).forEach(it -> {
			Thread t = new Thread(() -> {
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				latch.arrive();
			});
			t.start();
		});
	}
}

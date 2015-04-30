package abel.concurrency.syn;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class CyclicBarrier1 {

	public static void main(String[] args) {
		final CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> {
			System.out.println("All done");
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		IntStream.range(0, 3).forEach(
				it -> {
					Thread thread = new Thread(() -> {
						while (true) {
							try {
								TimeUnit.SECONDS.sleep(1);
							} catch (Exception e) {
								// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName()
								+ " is waiting");
						try {
							cyclicBarrier.await();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						System.out.println(Thread.currentThread().getName()
								+ " End waiting");
					}
				}	);
					thread.start();
				});
	}
}

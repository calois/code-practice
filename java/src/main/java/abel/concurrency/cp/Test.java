package abel.concurrency.cp;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Test {

	public static void main(String[] args) throws InterruptedException {
		long a = System.currentTimeMillis();

/*		ObjectPool<Test> pool = new ObjectPool2<Test>(2, 10, 3) {

			@Override
			public Test createObject() {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return new Test();
			}
		};*/
				ObjectPool<Test> pool = new ObjectPool3<Test>( 10, 3) {

			@Override
			public Test createObject() {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return new Test();
			}
		};
/*
		ObjectPool<Test> pool2 = new ObjectPool1<Test>(10) {

			@Override
			public Test createObject() {
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return new Test();
			}
		};*/
		ExecutorService es = Executors.newCachedThreadPool();
		IntStream.range(0, 20).forEach(it -> {
			es.execute(() -> {
				Test t = pool.checkout();
				System.out.println(Thread.currentThread().getName());
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				pool.checkin(t);
			});
		});
		es.shutdown();
		es.awaitTermination(1, TimeUnit.DAYS);
		long b = System.currentTimeMillis();
		System.out.println(b - a);
		pool.print();
	}
}

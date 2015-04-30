package abel.concurrency.syn;

import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletionService1 {

	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		final CompletionService<String> cs = new ExecutorCompletionService<String>(
				es);
		es.execute(() -> {
			while (true) {
				cs.submit(() -> {
					return "test " + Thread.currentThread().getName();
				});
			}
		});

		es.execute(() -> {
			while (true) {
				try {
					System.out.println(cs.take().get());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		// es.shutdown();
	}
}

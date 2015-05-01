package abel.concurrency;

import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Queue1 {

	public static void main(String[] args) throws InterruptedException {
		final Collection<String> s = new ConcurrentLinkedDeque<>();
		ExecutorService es = Executors.newCachedThreadPool();
		IntStream.range(0, 10000).forEach(it -> {
			es.submit(() -> {
				s.add("asdad");
			});
		});
		es.shutdown();
		es.awaitTermination(1, TimeUnit.DAYS);
		System.out.println(s.size());
	}

}

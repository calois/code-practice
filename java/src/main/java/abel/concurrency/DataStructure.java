package abel.concurrency;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class DataStructure {

	public static void main(String[] args) throws InterruptedException {
		final ConcurrentMap<String, Integer> map = new ConcurrentHashMap<>();
		ExecutorService es = Executors.newCachedThreadPool();
		final String key = "key";
		IntStream.range(0, 1000).forEach(it -> {
			es.submit(() -> {
				map.putIfAbsent(key, 0);
				map.computeIfPresent(key, (k, v) -> v + 1);
			});
		});
		es.shutdown();
		es.awaitTermination(1, TimeUnit.DAYS);
		System.out.println(map.get(key));
	}
}

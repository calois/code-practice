package abel.concurrency.call;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Call1 {

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		ExecutorService service = Executors.newCachedThreadPool();
		Future<String> f = service.submit(() -> {
			TimeUnit.SECONDS.sleep(1);
			return "asdasd";
		});
		service.shutdown();
		System.out.println(f.get());
	}
}

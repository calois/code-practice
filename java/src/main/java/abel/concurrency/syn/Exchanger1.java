package abel.concurrency.syn;

import java.util.concurrent.Exchanger;
import java.util.concurrent.TimeUnit;

public class Exchanger1 {

	public static void main(String[] args) {
		final Exchanger<String> exchanger = new Exchanger<>();
		Thread t1 = new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				System.out.println(exchanger.exchange(Thread.currentThread()
						.getName()));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		t1.start();
		Thread t2 = new Thread(() -> {
			try {
				TimeUnit.SECONDS.sleep(2);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				System.out.println(exchanger.exchange("1asdads"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		t2.start();
	}

}

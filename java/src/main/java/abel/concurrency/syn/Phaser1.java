package abel.concurrency.syn;

import java.util.Random;
import java.util.concurrent.Phaser;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Phaser1 implements Runnable {

	private Phaser phaser;

	public Phaser1(Phaser phaser) {
		this.phaser = phaser;
	}

	public static void main(String[] args) {
		final Phaser phaser = new Phaser();
		IntStream.range(0, 5).forEach(it -> {
			Thread t = new Thread(new Phaser1(phaser));
			t.start();
		});
	}

	@Override
	public void run() {
		phaser.register();
		System.out.println(Thread.currentThread().getName() + " register");
		snap();
		phaser.arriveAndAwaitAdvance();
		System.out.println(Thread.currentThread().getName() + " phase1");
		snap();
		phaser.arriveAndAwaitAdvance();
		System.out.println(Thread.currentThread().getName() + " phase2");
		snap();
		phaser.arriveAndDeregister();
		System.out.println(Thread.currentThread().getName() + " deregister");
	}

	private static final Random R = new Random();

	private void snap() {

		try {
			TimeUnit.SECONDS.sleep(R.nextInt(5));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

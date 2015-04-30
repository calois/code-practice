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
		final Phaser phaser = new Phaser() {
			@Override
			protected boolean onAdvance(int phase, int registeredParties) {
				System.out.println(phase + " " + registeredParties);
				snap();
				return super.onAdvance(phase, registeredParties);
			}
		};
		IntStream.range(0, 5).forEach(it -> {
			Thread t = new Thread(new Phaser1(phaser));
			t.start();
		});
	}

	@Override
	public void run() {
		System.out.println(Thread.currentThread().getName() + " register");
		phaser.register();
		snap();
		System.out.println(Thread.currentThread().getName() + " phase1");
		phaser.arriveAndAwaitAdvance();
		snap();
		System.out.println(Thread.currentThread().getName() + " phase2");
		phaser.arriveAndAwaitAdvance();
		snap();
		System.out.println(Thread.currentThread().getName() + " deregister");
		phaser.arriveAndDeregister();
	}

	private static final Random R = new Random();

	private static void snap() {

		try {
			TimeUnit.SECONDS.sleep(R.nextInt(5));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

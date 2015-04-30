package abel.concurrency.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoin1 extends RecursiveTask<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] number;
	int from;
	int to;

	public ForkJoin1(int[] number, int from, int to) {
		this.number = number;
		this.from = from;
		this.to = to;
	}

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		int[] number = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
				14, 15, 16, 17, 18, 19, 20 };
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoin1 f = new ForkJoin1(number, 0, number.length - 1);
		pool.execute(f);
		pool.shutdown();
		System.out.println(f.get());
		System.out.println((1 + 20) * 20 / 2);
	}

	@Override
	protected Integer compute() {
		if (to - from <= 2) {
			int sum = 0;
			for (int i = from; i <= to; i++) {
				sum += number[i];
			}
			return sum;
		}
		int mid = (to + from) / 2;
		ForkJoin1 f1 = new ForkJoin1(number, from, mid);
		ForkJoin1 f2 = new ForkJoin1(number, mid + 1, to);
		invokeAll(f1, f2);
		try {
			return f1.get() + f2.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return 0;
	}

}

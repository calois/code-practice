package abel.concurrency.forkjoin;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoin2 extends RecursiveTask<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int[] number;
	int from;
	int to;

	public ForkJoin2(int[] number, int from, int to) {
		this.number = number;
		this.from = from;
		this.to = to;
	}

	public static void main(String[] args) throws InterruptedException,
			ExecutionException {
		int[] number = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13,
				14, 15, 16, 17, 18, 19, 20 };
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoin2 f = new ForkJoin2(number, 0, number.length - 1);
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
		ForkJoin2 f1 = new ForkJoin2(number, from, mid);
		f1.fork();
		ForkJoin2 f2 = new ForkJoin2(number, mid + 1, to);
		f2.fork();
		return f1.join() + f2.join();
	}

}

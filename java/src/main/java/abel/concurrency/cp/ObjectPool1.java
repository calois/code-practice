package abel.concurrency.cp;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

public abstract class ObjectPool1<T> implements ObjectPool<T> {

	private List<T> objectList = new ArrayList<>();

	private Semaphore semaphore;

	public ObjectPool1(int n) {
		semaphore = new Semaphore(n);
	}

	@Override
	public void print() {
		System.out.println(objectList.size());
	}

	@Override
	public T checkout() {
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		synchronized (this) {
			if (objectList.isEmpty()) {
				objectList.add(createObject());
			}
			return objectList.remove(0);
		}
	}

	@Override
	public void checkin(T t) {
		synchronized (this) {
			objectList.add(t);
		}
		semaphore.release();
	}

}

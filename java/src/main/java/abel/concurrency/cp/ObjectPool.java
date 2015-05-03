package abel.concurrency.cp;

public interface ObjectPool<T> {
	T createObject();

	T checkout();

	void checkin(T t);

}

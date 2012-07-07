package util;

import java.util.Queue;

public interface CircularQueue<T> extends Queue<T>{
	
	public T move();

}

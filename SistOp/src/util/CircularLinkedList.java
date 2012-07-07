package util;

import java.util.Collection;
import java.util.LinkedList;

public class CircularLinkedList<E> extends LinkedList<E> implements CircularQueue<E> {

	public CircularLinkedList() {
		// TODO Auto-generated constructor stub
	}

	public CircularLinkedList(Collection<? extends E> c) {
		super(c);
		// TODO Auto-generated constructor stub
	}
	
	public E move() {
		E e = this.poll();
		this.add(e);
		return e;
	}

}
